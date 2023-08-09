package com.example.demo;

import com.example.demo.model.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.sqlexception;
import java.util.Date;
import java.util.resourcebundle;
import java.util.function.Predicate;

public class HelloController1 implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxKhoi;
    @FXML
    private ChoiceBox<String> choiceBoxLop;
    @FXML
    private TextField txtSearch;
    private String[] khoi = {"6", "7", "8", "9"};
    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn<Student, Integer> indexCol;
    @FXML
    private TableColumn<Student, String> studentIDCol;
    @FXML
    private TableColumn<Student, String> studentNameCol;
    @FXML
    private TableColumn<Student, Date> DoBCol;
    @FXML
    private TableColumn<Student, String> genderCol;
    @FXML
    private TableColumn<Student, String> addressCol;
    @FXML
    private TableColumn<Student, String> sdtCol;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxKhoi.getItems().addAll(khoi);
        choiceBoxKhoi.setOnAction(event -> {
            String selectedKhoi = choiceBoxKhoi.getValue();
            updateLopOptions(selectedKhoi);
        });
//        studentList = FXCollections.observableArrayList();
        setupTableColumns();
        setupIndexColumn();

        try {
            String query = "SELECT * " +
                "FROM student " +
                "JOIN user ON user.userID = student.userID ";
            getData(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(studentList);
        searchFilter();
// bắt sự kiện chọn lớp
        handleChoiBoxLop();

    }
    private void handleChoiBoxLop(){
        choiceBoxLop.setOnAction(event -> {
            String selectedLop = choiceBoxLop.getValue();
            if (selectedLop != null) {
                try {
                    getStudentInClass(selectedLop);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void searchFilter() {
        FilteredList<Student> filteredList = new FilteredList<>(studentList,e->true);
        txtSearch.setOnKeyReleased(e->{
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue)->{
                System.out.println(studentList);
                filteredList.setPredicate((Predicate<? super Student>) cust ->{
                    if(newValue == null||newValue.isEmpty()){
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if (cust.getStudentID().contains(toLowerCaseFilter)) {
                        return true;
                    } else if (cust.getUserName().toLowerCase().contains(toLowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Student> students = new SortedList<>(filteredList);
            students.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(students);
        });
    }

    private void setupIndexColumn() {
        indexCol.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(column.getValue()) + 1));
        indexCol.setSortable(false);
        indexCol.setPrefWidth(50);
    }

    private void setupTableColumns() {
        studentIDCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        DoBCol.setCellValueFactory(new PropertyValueFactory<>("DoB"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        sdtCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    private void getData(String query) throws SQLException {
        Connection connection = DatabaseConnection.getDBConnection();
        ObservableList<Student> studentList = FXCollections.observableArrayList();
//        String query = "SELECT * " +
//                "FROM student " +
//                "JOIN user ON user.userID = student.userID "+
//                "JOIN study ON study.studentID = student.studentID "+
//                "WHERE study.classID = 1";
        PreparedStatement statement = connection.prepareStatement(query);
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
            studentList.add(new Student(studentID, userID, userName, gender, DoB,
                    email, phoneNumber, address, role, account, password, parentName, parentPhone));
        }
        table.setItems(studentList);
    }
    //
    private void getStudentInClass(String selectedLop) throws SQLException{
//        studentList.clear();
        String query = "SELECT * " +
                "FROM student " +
                "JOIN user ON user.userID = student.userID "+
                "JOIN study ON study.studentID = student.studentID "+
                "JOIN class ON class.classID = study.classID "+
                "WHERE class.className = '"+ selectedLop +"'";
        getData(query);
    }
    //Hàm setup choiboox lớp
    private void updateLopOptions(String selectedKhoi) {
        String[] lopOptions;
        choiceBoxLop.getItems().clear();
        if ("6".equals(selectedKhoi)) {
            choiceBoxLop.getItems().addAll("6A1", "6A2");
        } else if ("7".equals(selectedKhoi)) {
            choiceBoxLop.getItems().addAll("7A1", "7A2");
        } else if ("8".equals(selectedKhoi)) {
            choiceBoxLop.getItems().addAll("8A1", "8A2");
        } else if ("9".equals(selectedKhoi)) {
            choiceBoxLop.getItems().addAll("9A1", "9A2");
        }
    }
}
