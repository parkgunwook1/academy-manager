package com.aihelper.customer_bot.api.v1.user.dto;

import com.aihelper.customer_bot.api.v1.user.domain.user.User;

public record UserResponseDTO(
        int id,
        String email,
        String phone,
        String name,
        String role
){
    public static UserResponseDTO from(User u) {
        return new UserResponseDTO(
                u.getId(),
                u.getEmail(),
                u.getPhone(),
                u.getName(),
                u.getRole()
        );
    }
}
