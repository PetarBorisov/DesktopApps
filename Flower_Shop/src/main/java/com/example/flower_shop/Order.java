package com.example.flower_shop;

import java.time.LocalDate;
import java.util.List;

public class Order {

    private Integer id;
    private CustomerData customer;
    private List<FlowersData> flowers;
    private LocalDate date;
    private Double totalPrice;

    public Order(Integer id, CustomerData customer, List<FlowersData> flowers, LocalDate date, Double totalPrice) {
        this.id = id;
        this.customer = customer;
        this.flowers = flowers;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerData customer) {
        this.customer = customer;
    }

    public List<FlowersData> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<FlowersData> flowers) {
        this.flowers = flowers;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

