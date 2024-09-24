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
import utils.Validation;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ChangePassword", urlPatterns = {"/changepass"})
public class ChangePassword extends HttpServlet {

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
            out.println("<title>Servlet ChangePassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePassword at " + request.getContextPath() + "</h1>");
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
        // Get id
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");

            // Check if the user is logged in
        if (id == null) {
            // Not logged in, redirect to Login
            response.sendRedirect("Login.jsp");
        } else {
            // If logged in, redirect to the page
            response.sendRedirect("ChangePassword.jsp");
        }
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
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String renewPassword = request.getParameter("renewPassword");
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        // Validate password
        Validation val = new Validation();
        String errorMessage = val.ValidatePassword(newPassword);
        if(!errorMessage.equals("true")){
            request.setAttribute("message", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangePassword.jsp");
            dispatcher.forward(request, response);
        }
        // Check if logged in
        if(id != null) {
            // Logged in, change password and redirect to page with resultMessage
            AccountDAO ad = new AccountDAO();
            boolean note = ad.changePassword(id, oldPassword, newPassword, renewPassword);
            String resultMessage = "";
            if (note) {
                resultMessage += "succes";
            } else {
                resultMessage += "fail";
            }
            request.setAttribute("message", resultMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangePassword.jsp");
            dispatcher.forward(request, response);
        }
        else{
            // Not logged in, redirect to Login
            response.sendRedirect("Login.jsp");
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
