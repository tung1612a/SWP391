/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author tran tung
 */
public class Account {
    private int AccountID;
    private String Username;
    private String Password;
    private int RoleID;
    public Account(){
        
    }
    public Account(int AccountID, String Username, String Password, int RoleID) {
        this.AccountID = AccountID;
        this.Username = Username;
        this.Password = Password;
        this.RoleID = RoleID;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int AccountID) {
        this.AccountID = AccountID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int RoleID) {
        this.RoleID = RoleID;
    }

    @Override
    public String toString() {
        return "Account{" + "AccountID=" + AccountID + ", Username=" + Username + ", Password=" + Password + ", RoleID=" + RoleID + '}';
    }
    
}
