package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientRentableDto {
    private String name;
    private String surname;
    private String email;
    private String photo;
}
