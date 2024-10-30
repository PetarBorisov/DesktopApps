package com.example.flower_shop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ReceiptController {

    @FXML
    private TextArea receiptTextArea;

    public ReceiptController(TextArea receiptTextArea) {
        this.receiptTextArea = receiptTextArea;
    }

    public void setReceiptText(String text) {
        receiptTextArea.setText(text);
    }

    @FXML
    private void closeReceipt() {
        Stage stage = (Stage) receiptTextArea.getScene().getWindow();
        stage.close();
    }
}