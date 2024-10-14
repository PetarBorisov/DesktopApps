package com.example.flower_shop;

import java.util.Date;

public class CustomerInfo {

    private Integer customerId;
    private Integer flowerId;
    private String name;
    private Integer quantity;
    private Double price;
    private Date date;

    public CustomerInfo(Integer customerId, Integer flowerId, String name, Integer quantity, Double price, Date date) {
        this.customerId = customerId;
        this.flowerId = flowerId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getFlowerId() {
        return flowerId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
