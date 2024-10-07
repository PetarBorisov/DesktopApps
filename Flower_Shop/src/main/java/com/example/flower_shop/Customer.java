package com.example.flower_shop;


import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.util.List;

public class Customer {

    private int customerId;
    private String username;
    private List<MysqlxCrud.Order> orders;

    public Customer(int customerId, String username, List<MysqlxCrud.Order> orders) {
        this.customerId = customerId;
        this.username = username;
        this.orders = orders;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MysqlxCrud.Order> getOrders() {
        return orders;
    }

    public void setOrders(List<MysqlxCrud.Order> orders) {
        this.orders = orders;
    }
}

