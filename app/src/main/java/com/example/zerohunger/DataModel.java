package com.example.zerohunger;

public class DataModel {
    private String address;
    private String date;
    private String expiryDate;
    private String mailId;
    private String name;
    private String phoneNumber;
    private String pincode;
    private String quantity;
    private String key; // Add this field to store the Firebase key

    // Default constructor for Firebase
    public DataModel() {
    }

    // Constructor with parameters (excluding key, as it is set later)
    public DataModel(String address, String date, String expiryDate, String mailId,
                     String name, String phoneNumber, String pincode, String quantity) {
        this.address = address;
        this.date = date;
        this.expiryDate = expiryDate;
        this.mailId = mailId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pincode = pincode;
        this.quantity = quantity;
    }

    // Getters and Setters for each field
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    // Getter and Setter for key
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
