/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.*;

/**
 *
 * @author ADMIN
 */
public class AccountDAO extends DBContext {

    public boolean changePassword(int accountID, String oldPassword, String newPassword, String renewPassword) {
        String verifyOldPasswordSql = "SELECT * FROM account WHERE AccountID = ? AND password = ?";
        String updatePasswordSql = "UPDATE account SET password = ? WHERE AccountID = ?";

        // Verify the old password
        try {
            //For adding MD5 later

            // Check if the old password is correct
            PreparedStatement verifyStatement = connection.prepareStatement(verifyOldPasswordSql);
            verifyStatement.setInt(1, accountID);
            verifyStatement.setString(2, oldPassword);
            ResultSet rs = verifyStatement.executeQuery();

            if (rs.next()) {
                // Check if new passwords match
                if (newPassword.equals(renewPassword)) {
                    // Update the password 
                    PreparedStatement updateStatement = connection.prepareStatement(updatePasswordSql);
                    updateStatement.setString(1, newPassword);
                    updateStatement.setInt(2, accountID);

                    int rowsAffected = updateStatement.executeUpdate();
                    return rowsAffected > 0;
                } else {
                    System.out.println("New password and confirm password do not match.");
                }
            } else {
                System.out.println("Old password is incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Account findAccountByEmail(String email) {
        // SQL query to find the account by email from Admin, Staff, or Customer
        String findAccountSql = """
        SELECT a.AccountID, a.Username, a.Password, a.RoleID
        FROM Account a
        JOIN Admin ad ON a.AccountID = ad.AccountID
        WHERE ad.Email = ?
        UNION
        SELECT a.AccountID, a.Username, a.Password, a.RoleID
        FROM Account a
        JOIN Staff s ON a.AccountID = s.AccountID
        WHERE s.Email = ?
        UNION
        SELECT a.AccountID, a.Username, a.Password, a.RoleID
        FROM Account a
        JOIN Customer c ON a.AccountID = c.AccountID
        WHERE c.Email = ?;
    """;

        try {
            // Prepare the statement with the provided email
            PreparedStatement findAccountStatement = connection.prepareStatement(findAccountSql);
            findAccountStatement.setString(1, email);
            findAccountStatement.setString(2, email);
            findAccountStatement.setString(3, email);

            // Execute the query and process the result set
            ResultSet rs = findAccountStatement.executeQuery();

            // If an account is found
            if (rs.next()) {
                // Create and populate an Account object
                Account account = new Account();
                account.setAccountID(rs.getInt("AccountID"));
                account.setUsername(rs.getString("Username"));
                account.setPassword(rs.getString("Password"));
                account.setRoleID(rs.getInt("RoleID"));

                return account; // Return the found account
            } else {
                System.out.println("No account found for the provided email.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if no account is found
    }

}
