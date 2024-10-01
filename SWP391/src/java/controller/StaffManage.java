/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.*;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "StaffManage", urlPatterns = {"/StaffManage"})
public class StaffManage extends HttpServlet {

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
            out.println("<title>Servlet StaffManage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffManage at " + request.getContextPath() + "</h1>");
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
        StaffDAO sd = new StaffDAO();
        String search = request.getParameter("search");
        String sortColumn = request.getParameter("sortColumn");
        String sortOrder_raw = request.getParameter("sortOrder");
        String page_raw = request.getParameter("page");
        int page = 1;
        if (page_raw != null) {
            page = Integer.parseInt(page_raw);
        }
        int recordsPerPage = 10;
        boolean sortOrder = true;
        if (sortOrder_raw != null) {
            if (sortOrder_raw.equals("desc")) {
                sortOrder = false;
            }
        }

        // Fetch filtered, sorted, paginated staff list
        List<Staff> staffList = sd.findStaffByPage(page, recordsPerPage, search, sortColumn, sortOrder);
        //List<Staff> staffList = sd.findStaffByPage(page, 1, search, sortColumn, sortOrder);
        int totalRecords = sd.getTotalPages(recordsPerPage, search);
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        // Set attributes to pass to the JSP
        request.setAttribute("staffList", staffList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Forward to JSP
        request.getRequestDispatcher("staffManage.jsp").forward(request, response);
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
