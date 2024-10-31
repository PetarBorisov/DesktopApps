package com.example.flower_shop;

import com.example.flower_shop.controllers.ReceiptController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class PaymentService {

    public void processPayment(int customerId, double totalAmount) {
        // Тук поставете логиката за запис в базата данни, проверка на полетата и т.н.

        if (isPaymentSuccessful(customerId, totalAmount)) {
            showReceipt();
        }
    }

    private boolean isPaymentSuccessful(int customerId, double totalAmount) {
        // Логика за проверка на успешността на плащането
        // и записа в базата данни

        // Пример:
        if (totalAmount <= 0 || customerId <= 0) {
            System.out.println("Невалидни данни за плащане.");
            return false;
        }

        System.out.println("Плащането е успешно!");
        return true;
    }

    private void showReceipt() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/receipt.fxml"));
            Parent root = loader.load();

            // Получаване на контролера и задаване на текста за разписката
            ReceiptController receiptController = loader.getController();
            receiptController.setReceiptText(generateReceiptText());

            Stage receiptStage = new Stage();
            receiptStage.initModality(Modality.APPLICATION_MODAL);
            receiptStage.setTitle("Стокова разписка");
            receiptStage.setScene(new Scene(root));
            receiptStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateReceiptText() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("СТОКОВА РАЗПИСКА №1234\n");
        receipt.append("Дата: ").append(LocalDate.now()).append("\n");
        receipt.append("Доставчик: Магазин за цветя\n");
        receipt.append("Клиент: Иван Иванов\n\n");

        // Примерен текст с артикули
        receipt.append("Артикул: Роза\n");
        receipt.append("Количество: 5\n");
        receipt.append("Цена: 10.00 лв.\n\n");

        // Добавяне на обща сума
        receipt.append("Общо: 50.00 лв.\n");

        return receipt.toString();
    }
}