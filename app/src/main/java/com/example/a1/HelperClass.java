package com.example.a1;

public class HelperClass {

    String fullName;
    String email;
    String phone;
    String userName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;

    public HelperClass(String fullName, String email, String phone, String userName, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
    }

    public HelperClass() {
    }
}
