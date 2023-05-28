package com.example.firebase;

public class User {
    private String phone;
    private String fullName;
    private String password;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String phone, String fullName, String password) {
        this.phone = phone;
        this.fullName = fullName;
        this.password = password;
    }

    public User(String fullNameTxt, String passwordTxt) {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
