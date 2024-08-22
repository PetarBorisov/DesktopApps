module com.example.flower_shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    requires java.sql;

    requires com.dlsc.formsfx;

    opens com.example.flower_shop to javafx.fxml;
    exports com.example.flower_shop;
}