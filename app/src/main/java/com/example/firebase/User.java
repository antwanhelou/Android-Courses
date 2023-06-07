package com.example.firebase;

public class User {
    private String email;
    private String fullName;
    private String password;

    public User() {

    }

    public User(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public User(String fullNameTxt, String passwordTxt) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String phone) {
        this.email = email;
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
