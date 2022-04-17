package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private String photo;
    private Boolean deleted;
    private Integer numberOfPenalties;
    private Integer numberOfPoints;
}
