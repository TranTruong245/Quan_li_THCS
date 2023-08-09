package com.example.demo;
import com.example.demo.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

public class DatabaseConnection {
    public static String databaseName = "qlhsc2";
    public static String DbUserName = "root";
    public static String DbPassword ="root";
    public static String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private static Connection connection;
    public static Connection getDBConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,DbUserName,DbPassword);
            System.out.println("SQL connected");
        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }
    public static ObservableList<Student> getData() throws SQLException {
        Connection connection = getDBConnection();
        ObservableList<Student> studentList1 = FXCollections.observableArrayList();
            String querry = "SELECT * " +
                    "FROM student " +
                    "JOIN user ON user.userID = student.userID ";
            PreparedStatement statement = connection.prepareStatement(querry);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                String studentID = rs.getString("studentID");
                String userID = rs.getString("userID");
                String userName = rs.getString("userName");
                Date DoB = rs.getDate("DoB");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String address = rs.getString("address");
                String role = rs.getString("role");
                String account = rs.getString("account");
                String password = rs.getString("password");
                String parentName = rs.getString("parentName");
                String parentPhone = rs.getString("parentPhone");
                studentList1.add(new Student(studentID, userID, userName, gender, DoB,
                        email, phoneNumber, address, role, account, password, parentName, parentPhone));

        }
        return studentList1;
    }

}
