package com.example.flower_shop.controllers;

import com.example.flower_shop.Database;
import com.example.flower_shop.FlowersData;
import com.example.flower_shop.GetData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button availableFlowers_btn;

    @FXML
    private AnchorPane availableFlowers_form;

    @FXML
    private Button aviailableFlowers_addBtn;

    @FXML
    private Button aviailableFlowers_clearBtn;

    @FXML
    private TableColumn<?, ?> aviailableFlowers_col_flowerId;

    @FXML
    private TableColumn<?, ?> aviailableFlowers_col_flowerName;

    @FXML
    private TableColumn<?, ?> aviailableFlowers_col_price;

    @FXML
    private TableColumn<?, ?> aviailableFlowers_col_status;

    @FXML
    private Button aviailableFlowers_deleteBtn;

    @FXML
    private TextField aviailableFlowers_flowerID;

    @FXML
    private TextField aviailableFlowers_flowerName;

    @FXML
    private ImageView aviailableFlowers_imageView;

    @FXML
    private Button aviailableFlowers_importBtn;

    @FXML
    private TextField aviailableFlowers_price;

    @FXML
    private TextField aviailableFlowers_search;

    @FXML
    private ComboBox<?> aviailableFlowers_status;

    @FXML
    private TableView<?> aviailableFlowers_tableView;

    @FXML
    private Button aviailableFlowers_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Label home_availableFlowers;

    @FXML
    private Button home_btn;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalCustomers;

    @FXML
    private Label home_totalIncome;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button minimize;

    @FXML
    private Button purchase_btn;

    @FXML
    private TableColumn<?, ?> purchase_col_flowerID;

    @FXML
    private TableColumn<?, ?> purchase_col_flowerName;

    @FXML
    private TableColumn<?, ?> purchase_col_price;

    @FXML
    private TableColumn<?, ?> purchase_col_quantity;

    @FXML
    private ComboBox<?> purchase_flowerID;

    @FXML
    private ComboBox<?> purchase_flowerName;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private Spinner<?> purchase_quantity;

    @FXML
    private TableView<?> purchase_tableView;

    @FXML
    private Label purchase_total;

    @FXML
    private Text username;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public ObservableList<FlowersData> availableFlowerListData() {
        ObservableList<FlowersData> listData = FXCollections.observableArrayList() ;

            String sql = "SELECT * FROM flowers";

            connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            FlowersData flower;

            while (result.next()) {
                flower = new FlowersData(
                        result.getInt("flowerId"),
                        result.getString("name"),
                        result.getString("status"),
                        result.getDouble("price"),
                        result.getDate("flowerDate"),
                        result.getString("image")
                );
                listData.add(flower);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;

    }

    public void displayUsername(){
        String user = GetData.username;
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            availableFlowers_form.setVisible(false);
            purchase_form.setVisible(false);

            home_btn.setStyle(" -fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327);");
            availableFlowers_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");

        }else if (event.getSource() == availableFlowers_btn) {
            home_form.setVisible(false);
            availableFlowers_form.setVisible(true);
            purchase_form.setVisible(false);

            availableFlowers_btn.setStyle(" -fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327);");
            home_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");

        }else if (event.getSource() == purchase_btn) {
            home_form.setVisible(false);
            availableFlowers_form.setVisible(false);
            purchase_form.setVisible(true);

            purchase_btn.setStyle(" -fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327);");
            availableFlowers_btn.setStyle("-fx-background-color: transparent");
            home_btn.setStyle("-fx-background-color: transparent");
        }
    }
    private double x = 0;
    private double y = 0;

    public void logout(){

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logoutBtn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("/fxml/hello-view.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void close(){
        System.exit(0);
    }

    public  void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      displayUsername();
    }
}
