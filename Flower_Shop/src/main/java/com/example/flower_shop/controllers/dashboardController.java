package com.example.flower_shop.controllers;

import com.example.flower_shop.Database;
import com.example.flower_shop.FlowersData;
import com.example.flower_shop.GetData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    private TableColumn<FlowersData, String> aviailableFlowers_col_flowerId;

    @FXML
    private TableColumn<FlowersData, String> aviailableFlowers_col_flowerName;

    @FXML
    private TableColumn<FlowersData, String> aviailableFlowers_col_price;

    @FXML
    private TableColumn<FlowersData, String> aviailableFlowers_col_status;

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
    private TableView<FlowersData> aviailableFlowers_tableView;

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

    private Image image;

    public void availableFlowersAdd() {

        String sql = "INSERT INTO flowers (flowerId, name, status, price, image, date)"
                + "VALUES(?,?,?,?,?,?)";

        connect = Database.connectDb();


        try {

            Alert alert;

            if (aviailableFlowers_flowerID.getText().isEmpty()
                    || aviailableFlowers_flowerName.getText().isEmpty()
                    || aviailableFlowers_status.getSelectionModel().getSelectedItem() == null
                    || aviailableFlowers_price.getText().isEmpty()
                    || GetData.path == null || GetData.path == "") {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
                // Check if the Flower ID is already EXIST
                String checkData = "SELECT flowerId FROM flowers WHERE flowerId = '"
                + aviailableFlowers_flowerID.getText() +"'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Flower ID" + aviailableFlowers_flowerID.getText() + "was already exist !" );
                    alert.showAndWait();
                }else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, aviailableFlowers_flowerID.getText());
                    prepare.setString(2, aviailableFlowers_flowerName.getText());
                    prepare.setString(3, (String) aviailableFlowers_status.getSelectionModel().getSelectedItem());
                    prepare.setString(4, aviailableFlowers_price.getText());

                    String uri = GetData.path;
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(5, uri);

                    java.util.Date date = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(6, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                 // Show Updated Tableview
                    availableFlowerShowListData();

                 // CLEAR ALL FIELDS!
                    availableFlowersClear();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void availableFlowersUpdate() {

        String uri = GetData.path;
        uri = uri.replace("\\", "\\\\");

        String sql = "UPDATE flowers SET name = '"
                + aviailableFlowers_flowerName.getText() +"', status = '"
                + aviailableFlowers_status.getSelectionModel().getSelectedItem() +"', price = '"
                + aviailableFlowers_price.getText() +"', image = '"
                + uri +"'WHERE flowerId = '" + aviailableFlowers_flowerID.getText() +"'";

        connect = Database.connectDb();
        try {
            Alert alert;

            if (aviailableFlowers_flowerID.getText().isEmpty()
                    || aviailableFlowers_flowerName.getText().isEmpty()
                    || aviailableFlowers_status.getSelectionModel().getSelectedItem() == null
                    || aviailableFlowers_price.getText().isEmpty()
                    || GetData.path == null || GetData.path == "") {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Flower ID:" + aviailableFlowers_flowerID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // Show Updated Tableview
                    availableFlowerShowListData();

                    // CLEAR ALL FIELDS!
                    availableFlowersClear();
                }


            }
        }catch (Exception e) {e.printStackTrace();}
    }

    public void availableFlowersDelete() {
        String sql = "DELETE FROM flowers WHERE flowerId = '"+aviailableFlowers_flowerID.getText()+"'";

        connect = Database.connectDb();

        try {
            Alert alert;

            if (aviailableFlowers_flowerID.getText().isEmpty()
                    || aviailableFlowers_flowerName.getText().isEmpty()
                    || aviailableFlowers_status.getSelectionModel().getSelectedItem() == null
                    || aviailableFlowers_price.getText().isEmpty()
                    || GetData.path == null || GetData.path == "") {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Flower ID:" + aviailableFlowers_flowerID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // Show Updated Tableview
                    availableFlowerShowListData();

                    // CLEAR ALL FIELDS!
                    availableFlowersClear();
                }
            }

        }catch (Exception e) {e.printStackTrace();}
    }

    public  void availableFlowersClear() {

        aviailableFlowers_flowerID.setText("");
        aviailableFlowers_flowerName.setText("");
        aviailableFlowers_status.getSelectionModel().clearSelection();
        aviailableFlowers_price.setText("");
        GetData.path = "";

        aviailableFlowers_imageView.setImage(null);

    }

        String listStatus[] = {"Available", "Not Available"};
    public void availableFlowersStatus() {

        List<String> listS = new ArrayList<>();
        for (String data : listStatus) {
            listS.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listS);
        aviailableFlowers_status.setItems(listData);

    }
    public void availableFlowersInsertImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            GetData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 129, 174, false, true);
            aviailableFlowers_imageView.setImage(image);

        }
    }

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
                        result.getString("image"),
                        result.getDate("date")
                );
                listData.add(flower);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<FlowersData> availableFlowersList;
    public void availableFlowerShowListData() {
        availableFlowersList = availableFlowerListData();

        aviailableFlowers_col_flowerId.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
        aviailableFlowers_col_flowerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        aviailableFlowers_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        aviailableFlowers_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        aviailableFlowers_tableView.setItems(availableFlowersList);
    }

    public void  availableFlowersSearch() {

        FilteredList<FlowersData> filter = new FilteredList<>(availableFlowersList, e -> true);

        aviailableFlowers_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(PredicateFlowerData -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (PredicateFlowerData.getFlowerId().toString().contains(searchKey)) {
                    return true;
                } else if (PredicateFlowerData.getName().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (PredicateFlowerData.getStatus().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (PredicateFlowerData.getPrice().toString().contains(searchKey)) {
                    return true;
                }else
                    return false;
            });
        });

        SortedList<FlowersData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(aviailableFlowers_tableView.comparatorProperty());

        aviailableFlowers_tableView.setItems(sortList);
    }

    public void availableFlowersSelect(){

        FlowersData flower = aviailableFlowers_tableView.getSelectionModel().getSelectedItem();
        int num = aviailableFlowers_tableView.getSelectionModel().getSelectedIndex();

        if ((num -1) < -1) return;

        aviailableFlowers_flowerID.setText(String.valueOf(flower.getFlowerId()));
        aviailableFlowers_flowerName.setText(flower.getName());
        aviailableFlowers_price.setText(String.valueOf(flower.getPrice()));

        GetData.path = flower.getImage();

        String uri = "file:" + flower.getImage();

        image = new Image(uri, 129, 174, false, true);
        aviailableFlowers_imageView.setImage(image);
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

            //TO SHOW THE UPDATED TABLEVIEW ONCE YOU CLICKED THE AVAILABLE FLOWERS BUTTON
            availableFlowerShowListData();
            availableFlowersStatus();
            availableFlowersSearch();

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

        availableFlowerShowListData();
        availableFlowersStatus();
        availableFlowersSearch();


    }
}
