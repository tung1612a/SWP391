package dal;



import java.sql.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author FPT University - PRJ301
 */
public class DBContext {

    protected Connection connection;
    protected PreparedStatement statement;
    protected ResultSet resultSet;
    
    public DBContext() {
        try {
            // Edit URL , username, password to authenticate with your MS SQL Server
            
            //CHI DUOC PHEP DOI 3 dong 26,27,28
            
            
            
            String url = "jdbc:sqlserver://localhost:1433;databaseName=5AnhLucDB";
            String username = "trantung";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        
        if (dbContext.connection != null) {
            System.out.println("Connection to the database was successful!");
        } else {
            System.out.println("Failed to connect to the database.");
        }

        // Close the connection to clean up resources
        dbContext.closeConnection();
    }
}
