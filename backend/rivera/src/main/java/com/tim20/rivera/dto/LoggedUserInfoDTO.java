package com.tim20.rivera.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoggedUserInfoDTO {
    private String username;
    private String role;
    private LocalDateTime lastPasswordChange;
}
