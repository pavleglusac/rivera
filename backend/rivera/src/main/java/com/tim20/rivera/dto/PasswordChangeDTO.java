package com.tim20.rivera.dto;

import lombok.Data;

@Data
public class PasswordChangeDTO {
    String username;
    String oldPassword;
    String newPassword;

}
