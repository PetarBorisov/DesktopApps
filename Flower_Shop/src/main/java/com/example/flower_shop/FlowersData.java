package com.example.flower_shop;

import java.sql.Date;

public class FlowersData {


    private  Integer flowerId;
    private String name;
    private String status;
    private Double price;
    private Date date;
    private String image;


    public FlowersData(Integer flowerId, String name, String status, Double price, String image, Date date) {
        this.flowerId = flowerId;
        this.name = name;
        this.status = status;
        this.price = price;
        this.date = date;
        this.image = image;
    }

    public Integer getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(Integer flowerId) {
        this.flowerId = flowerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
