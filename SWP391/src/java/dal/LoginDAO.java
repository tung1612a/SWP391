/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Account;

/**
 *
 * @author tran tung
 */
public class LoginDAO extends DBContext{
     public Account login(String username, String password) {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            

            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Account a = new Account();
                a.setAccountID(rs.getInt("AccountID"));
                a.setUsername(rs.getString("Username"));
                a.setPassword(rs.getString("Password"));
                a.setRoleID(rs.getInt("RoleID"));
                
                
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     
     
     
     public Account getId(String username) {
    String sql = "SELECT AccountID, Username FROM Account WHERE Username = ?";

    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setString(1, username);
        try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                Account a = new Account();
                a.setAccountID(rs.getInt("AccountID"));
                a.setUsername(rs.getString("username"));
                return a;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
     public static void main(String[] args) {
        
        LoginDAO d = new LoginDAO();
         Account a = d.login("tung", "123");
         if (a != null) {
        System.out.println("Login successful!");
        System.out.println("Account ID: " + a.getAccountID());
        System.out.println("Username: " + a.getUsername());
        System.out.println("Role ID: " + a.getRoleID());
    } else {
        System.out.println("Login failed. Invalid username or password.");
    }
    }
}
