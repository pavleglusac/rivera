package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DiscountOfferDTO {
    Integer id;
    String name;
    LocalDateTime start;
    LocalDateTime end;
    Integer capacity;
    Double price;
    Double averageScore;
    String city;
    String country;
    String picture;
}
