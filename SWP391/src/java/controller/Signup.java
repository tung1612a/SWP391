/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.SignupDao;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.Validation;

/**
 * Servlet implementation class Signup
 */
@WebServlet(name = "Signup", urlPatterns = {"/signup"})
public class Signup extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private SignupDao signupDao;

    @Override
    public void init() {
        // Khởi tạo đối tượng SignupDao để kết nối cơ sở dữ liệu
        signupDao = new SignupDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy dữ liệu từ form đăng ký
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        String customerName = (String) session.getAttribute("customerName");
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        String email = (String) session.getAttribute("email");

//        // Sử dụng Validation để kiểm tra mật khẩu
//        Validation validator = new Validation();
//        String passwordValidationResult = validator.ValidatePassword(password);
//
//        if (!passwordValidationResult.equals("true")) {
//            // Nếu mật khẩu không hợp lệ, lưu thông báo lỗi vào session
//            session.setAttribute("errorMessage", passwordValidationResult);
//            response.sendRedirect("Signup.jsp");
//            return;
//        }
        // Kiểm tra nếu đăng ký thành công
        boolean isRegistered = signupDao.registerCustomer(username, password, customerName, phoneNumber, email);

        if (isRegistered) {
            // Xóa thông báo lỗi trong session nếu đăng ký thành công
            session.removeAttribute("errorMessage");
            response.sendRedirect("Login.jsp");
            session.removeAttribute("username");
            session.removeAttribute("password");
            session.removeAttribute("customerName");
            session.removeAttribute("email");
            session.removeAttribute("phoneNumber");
        } else {
            // Nếu username đã tồn tại, lưu thông báo lỗi vào session
            session.setAttribute("errorMessage", "Username đã tồn tại. Vui lòng chọn username khác.");
            response.sendRedirect("Signup.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển hướng yêu cầu GET về trang signup.jsp (trang đăng ký)
        RequestDispatcher dispatcher = request.getRequestDispatcher("Signup.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Signup Servlet";
    }
}
