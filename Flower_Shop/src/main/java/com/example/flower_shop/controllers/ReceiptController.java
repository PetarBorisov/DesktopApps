package com.example.flower_shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReceiptController {

    @FXML
    private TextArea receiptTextArea;

    @FXML
    private Button printBtn;

    @FXML
    void printReceipt(ActionEvent event) {

    }

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