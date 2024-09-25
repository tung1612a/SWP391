/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.AccountDAO;
import dal.PhoneNumberValidator;
import dal.SignupDao;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import utils.EmailUtility;
import utils.PasswordUtil;
import utils.RandomGenerate;
import utils.Validation;

/**
 *
 * @author ADMIN
 */

@WebServlet(name = "OTPRegister", urlPatterns = {"/OTPRegister"})
public class OTPRegister extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OTPRegister</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OTPRegister at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // Retrieve old, new, and re-entered passwords from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String customerName = request.getParameter("customerName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        
        PasswordUtil pw = new PasswordUtil();

        EmailUtility em = new EmailUtility();

        HttpSession session = request.getSession();

        // Sử dụng Validation để kiểm tra mật khẩu
        Validation validator = new Validation();
        String passwordValidationResult = validator.ValidatePassword(password);

        if (!passwordValidationResult.equals("true")) {
            // Nếu mật khẩu không hợp lệ, lưu thông báo lỗi vào session
            session.setAttribute("errorMessage", passwordValidationResult);
            response.sendRedirect("Signup.jsp");
            return;
        }
        
        SignupDao s = new SignupDao();
        if(s.checkUsernameExists(username)){
            // Nếu username đã tồn tại, lưu thông báo lỗi vào session
            session.setAttribute("errorMessage", "Username đã tồn tại. Vui lòng chọn username khác.");
            response.sendRedirect("Signup.jsp");
        }
        
        if (!PhoneNumberValidator.isValidPhoneNumber(phoneNumber)) {
            session.setAttribute("errorMessage", "Số điện thoại không hợp lệ!");
            response.sendRedirect("Signup.jsp");
            }
        
        // Save the new password in the session for later use
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setAttribute("customerName", customerName);
        session.setAttribute("phoneNumber", phoneNumber);
        session.setAttribute("email", email);

        RandomGenerate rd = new RandomGenerate();
        // Generate a random OTP
        String otp = rd.generateNumberOTP();

        try {
            // Send the OTP via email
            em.sendEmail(email, "OTP Đăng ký", "Đây là OTP : " + otp);
        } catch (MessagingException ex) {
            Logger.getLogger(OTPPassword.class.getName()).log(Level.SEVERE, null, ex);

            // Handle the error gracefully, redirect with error message
            request.setAttribute("message", "Unable to send OTP, please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Signup.jsp");
            dispatcher.forward(request, response);
            return; // Stop further execution after forwarding
        }

        // Save the OTP in the session for later verification
        session.setAttribute("otp", otp);

        // Redirect the user to the OTP verification page
        response.sendRedirect("OTPRegister.jsp");
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userOtp = request.getParameter("otp");
        String generatedOtp = (String) session.getAttribute("otp");

        if (userOtp != null && userOtp.equals(generatedOtp)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup");
            dispatcher.forward(request, response);
        } else {
            session.setAttribute("errorMessage", "Invalid OTP. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/OTPRegister.jsp");
            dispatcher.forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
