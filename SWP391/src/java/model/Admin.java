/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Admin {
    private int adminID;
    private String name;
    private String phoneNumber;
    private String email;
    private int accountID;

    public Admin() {
    }

    public Admin(int adminID, String name, String phoneNumber, String email, int accountID) {
        this.adminID = adminID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accountID = accountID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public String toString() {
        return "Admin{" + "adminID=" + adminID + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + ", accountID=" + accountID + '}';
    }
    
    
}
