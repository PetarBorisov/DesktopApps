package com.example.flower_shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FlowerShopManagementsSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/hello-view.fxml")));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}