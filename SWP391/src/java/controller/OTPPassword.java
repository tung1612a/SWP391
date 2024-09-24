/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
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

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "OTPPassword", urlPatterns = {"/OTP"})
public class OTPPassword extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OTPcheck</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OTPcheck at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve old, new, and re-entered passwords from the request
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String renewPassword = request.getParameter("renewPassword");
        PasswordUtil pw = new PasswordUtil();

        EmailUtility em = new EmailUtility();
        AccountDAO ad = new AccountDAO();

        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("id"); // Ensure session has valid ID

        // Check the input for password validation
        String message = ad.checkInput(id, pw.hashPassword(oldPassword), newPassword, renewPassword);

        // If validation fails, forward back to ChangePassword.jsp with the error message
        if (!"true".equals(message)) {
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangePassword.jsp");
            dispatcher.forward(request, response);
            return; // Stop further execution after forwarding
        }

        // Save the new password in the session for later use
        session.setAttribute("newPassword", newPassword);

        // Retrieve the user's email based on their account ID
        String email = ad.findEmailByAccountID(id);

        RandomGenerate rd = new RandomGenerate();
        // Generate a random OTP
        String otp = rd.generateNumberOTP();

        try {
            // Send the OTP via email
            em.sendEmail(email, "OTP Đổi mật khẩu", "Đây là OTP : " + otp);
        } catch (MessagingException ex) {
            Logger.getLogger(OTPPassword.class.getName()).log(Level.SEVERE, null, ex);

            // Handle the error gracefully, redirect with error message
            request.setAttribute("message", "Unable to send OTP, please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangePassword.jsp");
            dispatcher.forward(request, response);
            return; // Stop further execution after forwarding
        }

        // Save the OTP in the session for later verification
        session.setAttribute("otp", otp);

        // Redirect the user to the OTP verification page
        response.sendRedirect("OTPPassword.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/changepass");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("message", "Invalid OTP. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/OTPPassword.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
