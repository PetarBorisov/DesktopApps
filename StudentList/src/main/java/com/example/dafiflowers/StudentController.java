package com.example.dafiflowers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField tCourse;

    @FXML
    private TextField tFName;

    @FXML
    private TextField tLastName;

    @FXML
    private TableColumn<Student, String> colCourse;

    @FXML
    private TableColumn<Student, String> colfName;

    @FXML
    private TableColumn<Student, Integer> colid;

    @FXML
    private TableColumn<Student, String> collName;

    @FXML
    private TableView<Student> table;
    int id = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     showStudents();
    }

    public ObservableList<Student> getStudents(){
        ObservableList<Student> students = FXCollections.observableArrayList();

        String query = "select* from students";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()){
                Student st = new Student();
                st.setId(rs.getInt("id"));
                st.setFirstName(rs.getString("FirstName"));
                st.setLastName(rs.getString("LastName"));
                st.setCourse(rs.getString("COURSE"));
                students.add(st);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }
    public void showStudents(){
        ObservableList<Student> list = getStudents();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
        colfName.setCellValueFactory(new PropertyValueFactory<Student,String>("firstName"));
        collName.setCellValueFactory(new PropertyValueFactory<Student,String>("lastName"));
        colCourse.setCellValueFactory(new PropertyValueFactory<Student,String>("course"));
    }

    @FXML
    void clearField(ActionEvent event) {
        clear();

    }

    @FXML
    void createStudent(ActionEvent event) {

        String insert = "insert into students(FirstName,LastName,COURSE) values(?,?,?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, tFName.getText());
            st.setString(2, tLastName.getText());
            st.setString(3, tCourse.getText());
            st.executeUpdate();
            clear();
            showStudents();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void getData(MouseEvent event) {

        Student student = table.getSelectionModel().getSelectedItem();
        id = student.getId();
        tFName.setText(student.getFirstName());
        tLastName.setText(student.getLastName());
        tCourse.setText(student.getCourse());
        btnSave.setDisable(true);
    }
    void clear(){
        tFName.setText(null);
      tLastName.setText(null);
      tCourse.setText(null);
      btnSave.setDisable(false);
    }

    @FXML
    void deleteStudent(ActionEvent event) {
        String delete = "delete from students where id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showStudents();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void updateStudent(ActionEvent event) {

        String update = "update students set FirstName = ?, LastName = ?, Course = ? where id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(update);
            st.setString(1, tFName.getText());
            st.setString(2, tLastName.getText());
            st.setString(3, tCourse.getText());
            st.setInt(4, id);
            st.executeUpdate();
            showStudents();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

