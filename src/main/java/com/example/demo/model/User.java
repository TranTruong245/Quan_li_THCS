package com.example.demo.model;

import java.util.Date;

public class User {
    private String userID;
    private String userName;
    private String gender;
    private Date DoB = new Date();
    private String email;
    private String phoneNumber;
    private String address;
    private String role;
    private String account;
    private String password;

    public User(){};
    public User(String userID, String userName, String gender, Date doB, String email, String phoneNumber, String address, String role, String account, String password) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        DoB = doB;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.account = account;
        this.password = password;
    }

    public User(String userID, String userName, String gender, Date doB) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        DoB = doB;


    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDoB() {
        return DoB;
    }

    public void setDoB(Date doB) {
        DoB = doB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
