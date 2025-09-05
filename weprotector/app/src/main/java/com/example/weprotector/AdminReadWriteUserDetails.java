package com.example.weprotector;

public class AdminReadWriteUserDetails {
    public String name;
    public String phone;
    public String email;
    public String password;
    public String role;

    public AdminReadWriteUserDetails() {

    }

    public AdminReadWriteUserDetails(String name, String phone, String email, String password, String role) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
