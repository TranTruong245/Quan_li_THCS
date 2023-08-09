package com.example.demo.model;

import java.util.Date;

public class Student extends User{
    private String studentID;
    private String userID;
    private String parentName;
    private String parentPhone;

    public Student(String studentID, String userID, String userName, String gender, Date DoB,String email, String phoneNumber,String address, String role, String account, String password, String parentName, String parentPhone) {
        super(userID,userName,gender, DoB, email, phoneNumber, address, role, account, password );
        this.studentID = studentID;
        this.userID = userID;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
    }
    public Student(){};
    public Student(String studentID, String userID, String userName, String gender, Date DoB) {
        super(userID,userName,gender, DoB);
        this.studentID = studentID;
    }


    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }
}

