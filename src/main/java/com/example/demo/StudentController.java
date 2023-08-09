package com.example.demo;

import com.example.demo.model.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class StudentController implements Initializable {

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
    @FXML
    private Button btnAdd;
     ObservableList<Student> studentList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxKhoi.getItems().addAll(khoi);
        choiceBoxKhoi.setOnAction(event -> {
            String selectedKhoi = choiceBoxKhoi.getValue();
            updateLopOptions(selectedKhoi);
        });
        setupTableColumns();
        setupIndexColumn();

        try {
            studentList = DatabaseConnection.getData();
            table.setItems(studentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        searchFilter();
        handleChoiBoxLop();

    }
    @FXML
    private void refreshTable(ActionEvent event){
        try {
//            studentList.clear();
            studentList = DatabaseConnection.getData();
            table.setItems(studentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void close(MouseEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void getAddStudent(ActionEvent event){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("addStudent.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (IOException e){

        }
    }
    @FXML
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
    @FXML
    private void searchFilter() {
        FilteredList<Student> filteredList = new FilteredList<>(studentList,e->true);
        txtSearch.setOnKeyReleased(e->{
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue)->{
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

@FXML
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


    //
    private void getStudentInClass(String selectedLop) throws SQLException{
        studentList.clear();
        Connection conn = DatabaseConnection.getDBConnection();
        String query = "SELECT * " +
                "FROM student " +
                "JOIN user ON user.userID = student.userID "+
                "JOIN study ON study.studentID = student.studentID "+
                "JOIN class ON class.classID = study.classID "+
                "WHERE class.className = '"+ selectedLop +"'";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
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
    private void loadDate() {

        Connection connection = DatabaseConnection.getDBConnection();
//        refreshTable();

        setupTableColumns();

        //add cell of button edit
        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFoctory = (TableColumn<Student, String> param) -> {
            // make cell containing buttons
            final TableCell<Student, String> cell = new TableCell<Student, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                student = studentsTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `student` WHERE id  ="+student.getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }





                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            student = studentsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/tableView/addStudent.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddStudentController addStudentController = loader.getController();
                            addStudentController.setUpdate(true);
                            addStudentController.setTextField(student.getId(), student.getName(),
                                    student.getBirth().toLocalDate(),student.getAdress(), student.getEmail());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();




                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        studentsTable.setItems(StudentList);


    }
}
