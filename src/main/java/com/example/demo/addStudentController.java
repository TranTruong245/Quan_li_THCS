package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class addStudentController implements Initializable {
    @FXML
    private TextField tfUserID;
    @FXML
    private TextField tfStudentID;
    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfGender;
    @FXML
    private DatePicker tfDoB;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfParentName;
    @FXML
    private TextField tfParentPhone;
    @FXML
    private TextField tfAccount;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfRole;
    @FXML
    private ChoiceBox cbRole;
    private String[] role = {"Học sinh", "Giáo viên", "Admin"};
    @FXML
    private Button btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbRole.getItems().addAll(role);
//        cbRole.setOnAction(event ->{
//            String selectedRole = (String) cbRole.getValue();
//        });

    }
    @FXML
    private void save(MouseEvent event){
        String userID = tfUserID.getText();
        String studentID = tfStudentID.getText();
        String userName = tfUserName.getText();
        String gender = tfGender.getText();
        LocalDate dob = tfDoB.getValue();
        Date DoB = java.sql.Date.valueOf(dob);
        String email = tfEmail.getText();
        String phoneNumber = tfPhoneNumber.getText();
        String address = tfAddress.getText();
        String role = cbRole.getValue().toString() ;
        String account = tfAccount.getText();
        String password = tfPassword.getText();
        String parentName = tfParentName.getText();
        String parentPhone = tfParentPhone.getText();
        if(userID.isEmpty()||studentID.isEmpty()||userName.isEmpty()||gender.isEmpty()||
                address.isEmpty()||role.isEmpty()||address.isEmpty()||account.isEmpty()||DoB==null||
                password.isEmpty()||parentName.isEmpty()||parentPhone.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all data");
            alert.showAndWait();
        }else{
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Xác nhận");
                alert2.setHeaderText(null);
                alert2.setContentText("Bạn có chắc chắn muốn thực hiện không?");
                ButtonType result = alert2.showAndWait().orElse(ButtonType.CANCEL);
                if (result == ButtonType.OK) {
                    insert();
                    // Hiển thị alert thông báo thành công
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Thành công");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Insert Data đã được thực hiện thành công.");
                    successAlert.showAndWait();
                } else {
                    // Xử lý khi người dùng không xác nhận
                    // ...
                }
        }
    }
    private void insert(){
        try{
            Connection conn = DatabaseConnection.getDBConnection();
            String insertUserQuery = "INSERT INTO user(userID, userName, gender, DoB, email, phoneNumber, address, role, account, password) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst2 = conn.prepareStatement(insertUserQuery);
            pst2.setString(1,tfUserID.getText());
            pst2.setString(2,tfUserName.getText());
            pst2.setString(3,tfGender.getText());
            pst2.setDate(4,java.sql.Date.valueOf(tfDoB.getValue()));
            pst2.setString(5,tfEmail.getText());
            pst2.setString(6,tfPhoneNumber.getText());
            pst2.setString(7,tfAddress.getText());
            pst2.setString(8,cbRole.getValue().toString());
            pst2.setString(9,tfAccount.getText());
            pst2.setString(10,tfPassword.getText());
            pst2.executeUpdate();
            String insertStudentQuery   ="INSERT INTO student(studentID, userID, parentName, parentPhone) VALUES(?,?,?,?)";
            PreparedStatement pts1 = conn.prepareStatement(insertStudentQuery);
            pts1.setString(1,tfStudentID.getText());
            pts1.setString(2,tfUserID.getText());
            pts1.setString(3,tfParentName.getText());
            pts1.setString(4,tfParentPhone.getText());
            pts1.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
