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
import model.*;
import utils.EmailUtility;
import utils.RandomGenerate;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ForgotPassword", urlPatterns = {"/ForgotPassword"})
public class ForgotPassword extends HttpServlet {

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
            out.println("<title>Servlet ForgotPassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPassword at " + request.getContextPath() + "</h1>");
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
        response.sendRedirect("ForgotPassword.jsp");
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
        AccountDAO ad = new AccountDAO();
        RandomGenerate rd = new RandomGenerate();
        EmailUtility em = new EmailUtility();
        String email = request.getParameter("email");

        Account acc = ad.findAccountByEmail(email);

        if (acc == null) {
            // Account not found, show error message
            request.setAttribute("message", "Account not found for this email.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ForgotPassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        String randomPass = rd.generateRandomString(8);

        boolean note = ad.changePassword(acc.getAccountID(), acc.getPassword(), randomPass, randomPass);

        if (note) {
            try {
                em.sendEmail(email, "Mật khẩu mới", "<div>Mật khẩu mới :   " + randomPass + "</div>"
                        + "<div> Xin hãy đổi mật khẩu sau khi đăng nhập </div>");
            } catch (MessagingException ex) {
                request.setAttribute("message", "Something failed, please try again |");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/ForgotPassword.jsp");
                dispatcher.forward(request, response);
            }
            request.setAttribute("message", """
                                            Reset successfuly, check your email for new password !
                                            Please change your password after login""");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ForgotPassword.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("message", "Something failed, please try again |");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ForgotPassword.jsp");
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
