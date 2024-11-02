package com.example.flower_shop;

public class Client {
    private Integer id;
    private String firstName;
    private String fathersName;
    private String lastName;
    private String phoneNumber;
    private String fullName;

    public Client(Integer id, String firstName, String fathersName, String lastName, String phoneNumber,String fullName) {
        this.id = id;
        this.firstName = firstName;
        this.fathersName = fathersName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
    }

    public String getFullName() {
        return firstName + " " + fathersName + " " + lastName;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}