module com.example.dafiflowers {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;


    opens com.example.dafiflowers to javafx.fxml;
    exports com.example.dafiflowers;
}