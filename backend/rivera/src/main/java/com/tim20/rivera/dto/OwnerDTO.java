package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OwnerDTO {
    private String email;
    private String username;
    private String name;
    private String surname;
    private String photo;
}
