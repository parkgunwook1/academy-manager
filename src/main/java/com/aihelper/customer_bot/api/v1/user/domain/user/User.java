package com.aihelper.customer_bot.api.v1.user.domain.user;


import java.time.LocalDateTime;

public class User {

    private int id;
    private String email;
    private String phone;
    private String name;
    private String role;
    private String passwordHash;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public User(int id, String email, String phone, String name, String role, String passwordHash, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.role = role;
        this.passwordHash = passwordHash;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
