/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.*;
public class SignupDao {

    // Phương thức kiểm tra xem username đã tồn tại trong cơ sở dữ liệu hay chưa
    public boolean checkUsernameExists(String username) {
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Lấy kết nối từ DBContext
            connection = dbContext.connection;

            // Câu lệnh SQL kiểm tra username tồn tại
            String query = "SELECT * FROM Account WHERE Username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);

            // Thực thi truy vấn
            resultSet = statement.executeQuery();

            // Nếu có kết quả, tức là username đã tồn tại
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                dbContext.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Nếu không có kết quả, tức là username chưa tồn tại
        return false;
    }

    // Phương thức để đăng ký Customer mới
    public boolean registerCustomer(String username, String password, String customerName, String phoneNumber, String email) {
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement accountStatement = null;
        PreparedStatement customerStatement = null;
        ResultSet generatedKeys = null;
        boolean isSuccess = false;

        try {
            // Kiểm tra nếu username đã tồn tại
            if (checkUsernameExists(username)) {
                return false; // Username bị trùng
            }

            // Kiểm tra số điện thoại hợp lệ
            if (!PhoneNumberValidator.isValidPhoneNumber(phoneNumber)) {
                throw new RuntimeException("Số điện thoại không hợp lệ!");
            }

            // Mã hóa mật khẩu bằng MD5
            String hashedPassword = PasswordUtil.hashPassword(password);

            // Lấy kết nối từ DBContext
            connection = dbContext.connection;
            connection.setAutoCommit(false);

            // Thêm dữ liệu vào bảng Account
            String accountQuery = "INSERT INTO Account (Username, Password, RoleID) VALUES (?, ?, ?)";
            accountStatement = connection.prepareStatement(accountQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            accountStatement.setString(1, username);
            accountStatement.setString(2, hashedPassword); // Lưu mật khẩu đã mã hóa
            accountStatement.setInt(3, 1); // Giả sử RoleID = 1 là quyền customer

            int affectedRows = accountStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }

            // Lấy AccountID vừa được sinh ra
            int accountId = 0;
            generatedKeys = accountStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                accountId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating account failed, no ID obtained.");
            }

            // Thêm dữ liệu vào bảng Customer
            String customerQuery = "INSERT INTO Customer (CustomerName, PhoneNumber, Email, Point, AccountID) VALUES (?, ?, ?, ?, ?)";
            customerStatement = connection.prepareStatement(customerQuery);
            customerStatement.setString(1, customerName);
            customerStatement.setString(2, phoneNumber); // Lưu số điện thoại đã kiểm tra
            customerStatement.setString(3, email);
            customerStatement.setInt(4, 0);  // Điểm khởi tạo là 0
            customerStatement.setInt(5, accountId);

            customerStatement.executeUpdate();
            connection.commit();
            isSuccess = true;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (accountStatement != null) {
                    accountStatement.close();
                }
                if (customerStatement != null) {
                    customerStatement.close();
                }
                dbContext.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }
}
