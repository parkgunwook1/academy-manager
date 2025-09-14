package com.aihelper.customer_bot.security.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String email;
    private String phone;
    private String role;

    private String provider;
    private String providerId;
    @CreationTimestamp
    private LocalDateTime createDate;
    private String username;
    private String password;
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    @Builder
    public User(String email, String phone, String role, String provider, String providerId,
                String username) {
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.username = username;
    }
}
