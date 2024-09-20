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
public class AccoundDAO extends DBContext{
    
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
}
