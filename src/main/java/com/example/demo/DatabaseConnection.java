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

}
