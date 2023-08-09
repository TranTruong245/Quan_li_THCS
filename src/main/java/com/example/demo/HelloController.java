package com.example.demo;

import com.example.demo.model.Student;
import com.example.demo.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.demo.DatabaseConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxKhoi;
    @FXML
    private ChoiceBox<String> choiceBoxLop;
    private String[] khoi = {"6","7","8","9"};
    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn<Student,String> studentIDCol;
    @FXML
    private TableColumn<Student,String> studentNameCol;
    @FXML
    private TableColumn<Student, Date> DoBCol;
    @FXML
    private TableColumn<Student,String> genderCol;
    @FXML
    private TableColumn<Student,String> addressCol;
    @FXML
    private TableColumn<Student,String> sdtCol;
    private ObservableList<Student> studentList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxKhoi.getItems().addAll(khoi);
        choiceBoxKhoi.setOnAction(event -> {
            String selectedKhoi = choiceBoxKhoi.getValue();
            updateLopOptions(selectedKhoi);
        });
//        choiceBoxLop.setOnAction(event -> {
//            onLopSelected();
//        });

//        studentList = FXCollections.observableArrayList(
//                new Student("20193162","1","Trần Công Trường","Nam", new Date(),"truong.tc","3232342","dfdf","học sinh","truongtc","1234","Trinh Lien","9329324")
//
//        );
        try {
            getData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        studentIDCol.setCellValueFactory(new PropertyValueFactory<Student,String>("studentID"));
//        studentNameCol.setCellValueFactory(new PropertyValueFactory<Student,String>("userName"));
//        DoBCol.setCellValueFactory(new PropertyValueFactory<Student,Date>("DoB"));
//        genderCol.setCellValueFactory(new PropertyValueFactory<Student,String>("gender"));
//        addressCol.setCellValueFactory(new PropertyValueFactory<Student,String>("address"));
//        sdtCol.setCellValueFactory(new PropertyValueFactory<Student,String>("phoneNumber"));
//        table.setItems(studentList);

    }
    private void getData() throws SQLException {

        Connection connection = DatabaseConnection.getDBConnection();
        ObservableList<Student> studentList = FXCollections.observableArrayList();
//        String query =  "SELECT s.studentID, s.parentName, s.parentPhone, u.userName, u.gender, u.DoB, u.email, u.phoneNumber, u.address, u.role, u.account, u.password" +
//                        "FROM qlhsc2.student s"+
//                        "JOIN qlhsc2.user u ON s.userID = u.userID";
        String query ="SELECT student.studentID, user.userName, user.DoB, user.gender, user.address, user.phoneNumber, class.className " +
                "FROM student "+
                "JOIN user ON student.userID = user.userID "+
                "JOIN study ON student.studentID = study.studentID "+
                "JOIN class ON class.classID = study.classID ";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
//            (String studentID, String userID, String userName, String gender, Date DoB,String email,
//                    String phoneNumber,String address,
//                    String role, String account, String password, String parentName, String parentPhone)
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
            studentList.add(new Student(studentID,userID, userName, gender, DoB, email,
                    phoneNumber,address,role,account,password,parentName,parentPhone));

            studentIDCol.setCellValueFactory(new PropertyValueFactory<Student,String>("studentID"));
            studentNameCol.setCellValueFactory(new PropertyValueFactory<Student,String>("userName"));
            DoBCol.setCellValueFactory(new PropertyValueFactory<Student,Date>("DoB"));
            genderCol.setCellValueFactory(new PropertyValueFactory<Student,String>("gender"));
            addressCol.setCellValueFactory(new PropertyValueFactory<Student,String>("address"));
            sdtCol.setCellValueFactory(new PropertyValueFactory<Student,String>("phoneNumber"));
            table.setItems(studentList);
        }
    }




//    private void getData(String selectedLop) throws SQLException {
//
//        Connection connection = DatabaseConnection.getDBConnection();
//        ObservableList<Student> studentList = FXCollections.observableArrayList();
////        String query =  "SELECT s.studentID, s.parentName, s.parentPhone, u.userName, u.gender, u.DoB, u.email, u.phoneNumber, u.address, u.role, u.account, u.password" +
////                        "FROM qlhsc2.student s"+
////                        "JOIN qlhsc2.user u ON s.userID = u.userID";
//        String query ="SELECT s.studentID, u.userName, u.DoB, u.gender, u.address, u.phoneNumber, c.className" +
//                "FROM student s"+
//                "JOIN user u ON s.userID = u.userID"+
//                "JOIN study ON s.studentID = study.studentID"+
//                "JOIN class c ON c.classID = study.classID ";
//        PreparedStatement statement = connection.prepareStatement(query);
//        ResultSet rs = statement.executeQuery();
//        while (rs.next()){
//            String studentID = rs.getString("studentID");
//            String userName = rs.getString("userName");
//            Date dob = rs.getDate("DoB");
//            String gender = rs.getString("gender");
//            String address = rs.getString("address");
//            String phoneNumber = rs.getString("phoneNumber");
////            studentList.add(new Student(studentID, userName, gender, dob, address, phoneNumber));
//
//        }
//    }

//    private void getData(String selectedLop) throws SQLException {
//        Connection connection = DatabaseConnection.getDBConnection();
//        ObservableList<Object[]> studentList = FXCollections.observableArrayList();
//        String query = "SELECT s.studentID, u.userName, u.DoB, u.gender, u.address, u.phoneNumber, c.className " +
//                "FROM student s " +
//                "JOIN user u ON s.userID = u.userID " +
//                "JOIN study ON s.studentID = study.studentID " +
//                "JOIN class c ON c.classID = study.classID";
//        PreparedStatement statement = connection.prepareStatement(query);
//        ResultSet rs = statement.executeQuery();
//        while (rs.next()) {
//            Object[] studentData = new Object[7];
//            studentData[0] = rs.getString("studentID");
//            studentData[1] = rs.getString("userName");
//            studentData[2] = rs.getDate("DoB");
//            studentData[3] = rs.getString("gender");
//            studentData[4] = rs.getString("address");
//            studentData[5] = rs.getString("phoneNumber");
//            studentData[6] = rs.getString("className");
//
//            studentList.add(studentData);
//        }
//
//        table.setItems(convertToStudentList(studentList));
//    }
    private ObservableList<Student> convertToStudentList(ObservableList<Object[]> studentDataList) {
        ObservableList<Student> studentList = FXCollections.observableArrayList();

        for (Object[] studentData : studentDataList) {
            String studentID = (String) studentData[0];
            String userName = (String) studentData[1];
            Date dob = (Date) studentData[2];
            String gender = (String) studentData[3];
            String address = (String) studentData[4];
            String phoneNumber = (String) studentData[5];

            Student student = new Student();
            student.setStudentID(studentID);
            student.setUserName(userName);
            student.setDoB(dob);
            student.setGender(gender);
            student.setAddress(address);
            student.setPhoneNumber(phoneNumber);

            studentList.add(student);
        }

        return studentList;
    }

//    @FXML
//    private void onLopSelected() {
//        String selectedLop = choiceBoxLop.getValue();
//        if (selectedLop != null) {
//            try {
////                studentList.clear();
//                getData(selectedLop);
//            } catch (SQLException e) {
//                e.printStackTrace();
//                // Handle any exceptions that may occur during database interaction
//            }
//        }
//    }
    private void updateLopOptions(String selectedKhoi){
        String[] lopOptions;
        choiceBoxLop.getItems().clear();
        if ("6".equals(selectedKhoi)){
            choiceBoxLop.getItems().addAll("6A1","6A2");
        } else if ("7".equals(selectedKhoi)) {
            choiceBoxLop.getItems().addAll("7A1","7A2");
        }else if ("8".equals(selectedKhoi)) {
            choiceBoxLop.getItems().addAll("8A1","8A2");
        }else if ("9".equals(selectedKhoi)) {
            choiceBoxLop.getItems().addAll("9A1","9A2");
        }
    }

}