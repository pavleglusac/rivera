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

    public OwnerDTO(String email, String username, String name, String surname, String photo) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.photo = photo;
    }
}
