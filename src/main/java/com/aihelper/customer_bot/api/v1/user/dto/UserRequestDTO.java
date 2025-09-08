package com.aihelper.customer_bot.api.v1.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class UserRequestDTO {

    private String email;
    private String phone;
    private String role;

    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    private String name;
    private String password;

    public UserRequestDTO(String email, String phone, String role, String name, String password) {
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.createDate = LocalDateTime.now();
        this.name = name;
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
