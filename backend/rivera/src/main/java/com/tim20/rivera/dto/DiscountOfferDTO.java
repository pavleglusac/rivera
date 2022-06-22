package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class DiscountOfferDTO {
    Integer id;
    Integer rentableId;
    String name;
    LocalDateTime start;
    LocalDateTime end;
    Integer capacity;
    Double price;
    Double averageScore;
    String city;
    List<String> additionalServices;
    String country;
    String picture;
    boolean reserved;
}
