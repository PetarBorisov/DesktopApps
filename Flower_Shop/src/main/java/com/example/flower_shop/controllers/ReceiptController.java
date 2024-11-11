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
    public void printReceipt() {
        // Вземаме текста за разписката
        String receiptText = receiptTextArea.getText();

        if (receiptText.isEmpty()) {
            // Ако няма разписка, не правим нищо
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Няма разписка за печат.");
            alert.showAndWait();
            return;
        }

        // Създаваме текстов елемент за печат
        Text textForPrint = new Text(receiptText);

        // Създаваме принтерски джоб
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(null)) {
            // Печатаме текста
            boolean printed = printerJob.printPage(textForPrint);

            if (printed) {
                printerJob.endJob();
            }
        }}

}