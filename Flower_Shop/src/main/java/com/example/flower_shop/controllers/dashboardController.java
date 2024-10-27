package com.example.flower_shop.controllers;

import com.example.flower_shop.*;
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
import javafx.scene.chart.XYChart;
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
import javafx.util.StringConverter;
import org.w3c.dom.Document;

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
    private Button add_customersBTN;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_flowerID;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_flowerName;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_price;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_quantity;

    @FXML
    private ComboBox<?> purchase_flowerID;

    @FXML
    private ComboBox<?> purchase_flowerName;

    @FXML
    private ComboBox<Client> purchase_clientData;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private Button purchase_addCart;

    @FXML
    private TableView<CustomerData> purchase_tableView;

    @FXML
    private Label purchase_total;

    @FXML
    private Text username;

    @FXML
    private Button clients_Btn;

    @FXML
    private AnchorPane clients_form;

    @FXML
    private Button client_addBtn;

    @FXML
    private Button client_clearBtn;

    @FXML
    private TableColumn<?, ?> client_col_firstName;

    @FXML
    private TableColumn<?, ?> client_col_fathersName;

    @FXML
    private TableColumn<?, ?> client_col_id;

    @FXML
    private TableColumn<?, ?> client_col_lastName;

    @FXML
    private TableColumn<?, ?> client_col_phoneNumber;

    @FXML
    private Button client_deleteBtn;

    @FXML
    private TextField client_firstName;

    @FXML
    private TextField client_fathersName;

    @FXML
    private TextField client_id;

    @FXML
    private TextField client_lastName;

    @FXML
    private TextField client_phoneNumber;

    @FXML
    private TextField client_search;

    @FXML
    private TableView<Client> client_tableView;

    @FXML
    private Button client_updateBtn;




    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;

    public void homeAF() {

        String sql = "SELECT COUNT(flowerID) AS flowerCount FROM flowers WHERE status = 'Available'";

        connect = Database.connectDb();

        try {
            int countAF = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                countAF = result.getInt("flowerCount");
            }
            home_availableFlowers.setText(String.valueOf(countAF));
        }catch (Exception e) {e.printStackTrace();}
    }

    public void homeTI() {

        String sql = "SELECT SUM(total) FROM customer_info";

        connect = Database.connectDb();

        try {
            double countTI = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                countTI = result.getInt("SUM(total)");
            }

            home_totalIncome.setText("$" + String.valueOf(countTI));

        }catch (Exception e) {e.printStackTrace();}
    }

    public void homeTC(){

        String sql = "SELECT COUNT(id) FROM customer_info";

        connect = Database.connectDb();

        try {
            int countTC = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                countTC = result.getInt("COUNT(id)");
            }

            home_totalCustomers.setText(String.valueOf(countTC));

        }catch (Exception e) {e.printStackTrace();}
    }

    public void homeChart() {
        home_chart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7";

        connect = Database.connectDb();

        try {
            XYChart.Series chart = new XYChart.Series();


            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2))) ;

            }
            home_chart.getData().add(chart);

        }catch (Exception e) {e.printStackTrace();}
    }

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
        if (!(uri == null ||uri == "") ) {
            uri = uri.replace("\\", "\\\\");
        }
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
                    || uri == null || uri == "") {

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

    public void purchaseAddToCart() {
        purchaseCustomerId();

        String sql = "INSERT INTO customer (customerId, flowerId, name, quantity, price, date) "
                + "VALUES(?,?,?,?,?,?)";

        connect = Database.connectDb();

        try {
            Alert alert;

            if (purchase_flowerID.getSelectionModel().getSelectedItem() == null
                    || purchase_flowerName.getSelectionModel().getSelectedItem() == null
                    || qty == 0){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose the product first");
                alert.showAndWait();
            }else {
                double priceData = 0;
                double totalPrice ;

                String checkPrice = "SELECT name, price FROM flowers WHERE name = '"+
                        purchase_flowerName.getSelectionModel().getSelectedItem() +"'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkPrice);

                if (result.next()) {
                    priceData = result.getDouble("price");
                }

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setInt(2, (Integer) purchase_flowerID.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String) purchase_flowerName.getSelectionModel().getSelectedItem());
                prepare.setString(4, String.valueOf(qty));

                totalPrice = (priceData * qty);

                prepare.setString(5, String.valueOf(totalPrice));

                java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(new java.util.Date().getTime());
                prepare.setTimestamp(6, sqlTimestamp);



                prepare.executeUpdate();

                purchaseShowListData();

                purchaseDisplayTotal();



            }

        }catch (Exception e) {e.printStackTrace();}

    }

    public void purchasePay() {

        String sql = "INSERT INTO customer_info (customerId, total, date) VALUES(?,?,?)";

        connect = Database.connectDb();

        try {

            Alert alert;

            if (totalP == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong :3");
                alert.showAndWait();

            } else if (purchase_clientData.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Please select CLIENT !");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalP));

                    java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(new java.util.Date().getTime());
                    prepare.setTimestamp(3, sqlTimestamp);

                    prepare.executeUpdate();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFORMATION MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful! Thanks for the purchase!");
                    alert.showAndWait();


                    clearCart();
                    clearClientFieldInPurchase();
                    selectClientInPurchase();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearCart() {

        purchaseListD.clear();

        purchase_tableView.setItems(purchaseListD);

        spinner.setValue(0);

        totalP = 0;

        purchase_total.setText("$" + totalP);


        purchase_flowerID.getSelectionModel().clearSelection();
        purchase_flowerName.getSelectionModel().clearSelection();


        purchase_flowerID.getItems().clear();
        purchase_flowerName.getItems().clear();


        purchaseFlowerId();
    }

    private double totalP = 0;
    public void purchaseDisplayTotal() {
        purchaseCustomerId();
        String sql = "SELECT SUM(price) FROM customer WHERE customerId = '" + customerId + "'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }

            purchase_total.setText("$" + String.valueOf(totalP));

        }catch (Exception e) {e.printStackTrace();}

    }

    public void purchaseFlowerId() {

        String sql = "SELECT status, flowerId FROM flowers WHERE status = 'Available'";

        connect = Database.connectDb();

        try {
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getInt("flowerId"));
            }
            purchase_flowerID.setItems(listData);

            purchaseFlowerName();

        }catch (Exception e) {e.printStackTrace();
        }
    }

    public void purchaseFlowerName() {

        String sql = "SELECT flowerId, name FROM flowers WHERE flowerId = '"
                + purchase_flowerID.getSelectionModel().getSelectedItem()+"'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("name"));
            }
            purchase_flowerName.setItems(listData);

        }catch (Exception e) {e.printStackTrace();}
    }

    private SpinnerValueFactory<Integer> spinner;
    public void purchaseSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        purchase_quantity.setValueFactory(spinner);
    }

    private int qty;
    public void purchaseQuantity() {
        qty = purchase_quantity.getValue();
    }

    public ObservableList<CustomerData> purchaseListData() {
        purchaseCustomerId();

        ObservableList<CustomerData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customerId = '"+ customerId +"'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            CustomerData customer;

            while (result.next()) {
                customer = new CustomerData(result.getInt("customerId")
                        , result.getInt("flowerId")
                        , result.getString("name")
                        , result.getInt("quantity")
                        , result.getDouble("price")
                        , result.getDate("date"));

                listData.add(customer);
            }

        }catch (Exception e) {e.printStackTrace();
        }return listData;
    }

    private ObservableList<CustomerData> purchaseListD;
    public void purchaseShowListData() {

        purchaseListD =  purchaseListData();

        purchase_col_flowerID.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
        purchase_col_flowerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableView.setItems(purchaseListD);

    }

    private int customerId;
    public void purchaseCustomerId() {

        String sqlCustomer = "SELECT MAX(customerId) AS maxCustomerId FROM customer";
        String sqlCustomerInfo = "SELECT MAX(customerId) AS maxCustomerId FROM customer_info";

        connect = Database.connectDb();

        try {
            // Изпълнение на първата заявка (от таблицата customer)
            PreparedStatement stmtCustomer = connect.prepareStatement(sqlCustomer);
            ResultSet rsCustomer = stmtCustomer.executeQuery();

            if (rsCustomer.next()) {
                customerId = rsCustomer.getInt("maxCustomerId");

            } else {
                customerId = 0;  // Ако няма резултат, започнете от 0

            }

            // Изпълнение на втората заявка (от таблицата customer_info)
            PreparedStatement stmtCustomerInfo = connect.prepareStatement(sqlCustomerInfo);
            ResultSet rsCustomerInfo = stmtCustomerInfo.executeQuery();

            int countData = 0;
            if (rsCustomerInfo.next()) {
                countData = rsCustomerInfo.getInt("maxCustomerId");

            } else {
                System.out.println("No customerId found in customer_info table.");
            }

            // Проверка и инкрементиране на customerId
            if (customerId == 0 && countData == 0) {
                // Ако и двете таблици са празни, започнете от 1
                customerId = 1;

            } else if (customerId <= countData) {
                // Ако customerId от customer е по-малко или равно на countData от customer_info, увеличете го
                customerId = countData + 1;

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            clients_form.setVisible(false);

            home_btn.setStyle(" -fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327);");
            availableFlowers_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");
            clients_Btn.setStyle("-fx-background-color: transparent");

            homeAF();
            homeTI();
            homeTC();
            homeChart();

        } else if (event.getSource() == availableFlowers_btn) {
            home_form.setVisible(false);
            availableFlowers_form.setVisible(true);
            purchase_form.setVisible(false);
            clients_form.setVisible(false);

            availableFlowers_btn.setStyle(" -fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327);");
            home_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");
            clients_Btn.setStyle("-fx-background-color: transparent");

            // Показване на актуализираните данни за наличните цветя
            availableFlowerShowListData();
            availableFlowersStatus();
            availableFlowersSearch();

        } else if (event.getSource() == purchase_btn) {
            home_form.setVisible(false);
            availableFlowers_form.setVisible(false);
            purchase_form.setVisible(true);
            clients_form.setVisible(false);

            purchase_btn.setStyle(" -fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327);");
            availableFlowers_btn.setStyle("-fx-background-color: transparent");
            home_btn.setStyle("-fx-background-color: transparent");
            clients_Btn.setStyle("-fx-background-color: transparent");


            purchaseShowListData();
            purchaseFlowerId();
            purchaseFlowerName();
            purchaseSpinner();
            purchaseDisplayTotal();

          //  clearCart();

        } else if (event.getSource() == clients_Btn) {
            home_form.setVisible(false);
            availableFlowers_form.setVisible(false);
            purchase_form.setVisible(false);
            clients_form.setVisible(true);  // Показване на формата за клиенти

            clients_Btn.setStyle(" -fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327);");
            home_btn.setStyle("-fx-background-color: transparent");
            availableFlowers_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");



        }

    }
    public ObservableList<Client> availableClientsData(){
        ObservableList<Client> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM clients";

        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Client client;

            while (result.next()) {
                client = new Client(
                        result.getInt("id"),
                        result.getString("firstName"),
                        result.getString("fathersName"),
                        result.getString("lastName"),
                        result.getString("phoneNumber")
                );
                listData.add(client);
            }

        }catch (Exception e) {e.printStackTrace();
        }
        return listData;
    }


    private ObservableList<Client> availableClientList;
    public void clientsShowListData(){

        availableClientList = availableClientsData();

        client_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        client_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        client_col_fathersName.setCellValueFactory(new PropertyValueFactory<>("fathersName"));
        client_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        client_col_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        client_tableView.setItems(availableClientList);

    }

    public void addClients(){

        String sql = "INSERT INTO clients (id, firstName, fathersName, lastName, phoneNumber) "
                + "VALUES(?, ?, ?, ?, ?)";

        connect = Database.connectDb();

        try {

            Alert alert;

            if (client_id.getText().isEmpty()
                    || client_firstName.getText().isEmpty()
                    || client_lastName.getText().isEmpty()
                    || client_fathersName.getText().isEmpty()
                    || client_phoneNumber.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields");
                alert.showAndWait();

            }else {
                //Check if the Client ID is already EXIST;
                String checkDataClients = "SELECT id FROM clients WHERE id = '"
                        + client_id.getText() +"'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkDataClients);
            }

            if (result.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Client ID" + client_id.getText() + "was already exist !");
                alert.showAndWait();
            }else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, client_id.getText());
                prepare.setString(2, client_firstName.getText());
                prepare.setString(3, client_fathersName.getText());
                prepare.setString(4, client_lastName.getText());
                prepare.setString(5, client_phoneNumber.getText());

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

                //Show Updated Table
                clientsShowListData();

                clearClients();
            }

        }catch (Exception e) {e.printStackTrace();
        }

    }

    public void updateClients() {

        // SQL заявка за актуализация на клиентски данни
        String updateSql = "UPDATE clients SET firstName = ?, fathersName = ?, lastName = ?, phoneNumber = ? WHERE id = ?";

        connect = Database.connectDb();

        try {
            Alert alert;

            if (client_id.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("ID field cannot be empty");
                alert.showAndWait();
                return;
            }


            if (client_firstName.getText().isEmpty()
                    || client_fathersName.getText().isEmpty()
                    || client_lastName.getText().isEmpty()
                    || client_phoneNumber.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return;
            }


            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Client ID: " + client_id.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                PreparedStatement updateStmt = connect.prepareStatement(updateSql);
                updateStmt.setString(1, client_firstName.getText());
                updateStmt.setString(2, client_fathersName.getText());
                updateStmt.setString(3, client_lastName.getText());
                updateStmt.setString(4, client_phoneNumber.getText());
                updateStmt.setString(5, client_id.getText()); // Уверяваме се, че използваме същото ID за WHERE условието


                int rowsUpdated = updateStmt.executeUpdate();


                if (rowsUpdated > 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();


                    clientsShowListData();

                    clearClients();

                } else {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Update failed: No client found with ID " + client_id.getText());
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClients() {
        String sql = "DELETE FROM clients WHERE id = ?";
        connect = Database.connectDb();

        try {
            Alert alert;

            // Проверка само за ID
            if (client_id.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a Client ID to delete.");
                alert.showAndWait();
                return; // Прекратяване на метода
            }

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Client ID: " + client_id.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {

                PreparedStatement preparedStatement = connect.prepareStatement(sql);
                preparedStatement.setString(1, client_id.getText());


                int rowsDeleted = preparedStatement.executeUpdate();


                if (rowsDeleted > 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("No client found with ID: " + client_id.getText());
                    alert.showAndWait();
                }


                clientsShowListData();


                clearClients();
            }
        }catch (Exception e) {e.printStackTrace();}
    }

    public void clearClients(){

        client_id.setText("");
        client_firstName.setText("");
        client_fathersName.setText("");
        client_lastName.setText("");
        client_phoneNumber.setText("");


    }

    public void clientSearch() {

        FilteredList<Client> filter = new FilteredList<>(availableClientList, e -> true);

        client_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(PredicateClient -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (PredicateClient.getId().toString().contains(searchKey)) {
                    return true;
                } else if (PredicateClient.getFirstName().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (PredicateClient.getFathersName().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (PredicateClient.getLastName().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (PredicateClient.getPhoneNumber().toString().contains(searchKey)) {
                    return true;
                }else
                    return false;
            });
        });

        SortedList<Client> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(client_tableView.comparatorProperty());

        client_tableView.setItems(sortList);
    }

    public void selectClient(){
        Client client = client_tableView.getSelectionModel().getSelectedItem();
        int num = client_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) return;

        client_id.setText(String.valueOf(client.getId()));
        client_firstName.setText(client.getFirstName());
        client_fathersName.setText(client.getFathersName());
        client_lastName.setText(client.getLastName());
        client_phoneNumber.setText(client.getPhoneNumber());
    }

    public void selectClientInPurchase() {
        // Получаваме данните за клиентите
        ObservableList<Client> availableClients = availableClientsData();


        // Проверка за налични клиенти
        if (availableClients == null || availableClients.isEmpty()) {

            purchase_clientData.getItems().clear(); // Осигуряване, че ComboBox е празен
            return; // Спиране на метода, ако няма клиенти
        }

        // Изчистване на текущите елементи в ComboBox
        purchase_clientData.getItems().clear();

        // Добавяме клиентите в ComboBox
        purchase_clientData.getItems().addAll(availableClients);


        // Настройваме StringConverter
        purchase_clientData.setConverter(new StringConverter<Client>() {
            @Override
            public String toString(Client client) {
                if (client == null) {
                    return "";
                }
                return client.getFirstName() + " " + client.getFathersName() + " " + client.getLastName();
            }

            @Override
            public Client fromString(String string) {
                return null;
            }
        });
        // Проверка за избран клиент
        purchase_clientData.setOnAction(event -> {
            if (purchase_clientData.getValue() != null) {
                Client selectedClient = purchase_clientData.getValue();
            }
        });
    }
    public void clearClientFieldInPurchase(){

        purchase_clientData.getItems().clear();
        purchase_clientData.setValue(null);
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

        homeAF();
        homeTI();
        homeTC();
        homeChart();

        availableFlowerShowListData();
        availableFlowersStatus();
        availableFlowersSearch();


        purchaseShowListData();
        purchaseFlowerId();
        purchaseFlowerName();
        purchaseSpinner();
        purchaseDisplayTotal();
        selectClientInPurchase();


        clientsShowListData();
        clientSearch();






    }
}