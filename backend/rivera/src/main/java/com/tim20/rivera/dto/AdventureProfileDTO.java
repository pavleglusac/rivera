package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdventureProfileDTO {
    private Integer id;
    private String name;
    private String description;
    private List<String> tags;
    private List<String> services;
    private List<String> rulesOfConduct;
    private Double perHour;
    private Double perDay;
    private Double averageScore;
    private String address;
    private String city;
    private String country;
    private String cancellationTerms;
    private Boolean canBeChanged;
    private List<String> pictures;
    private List<ReviewProfileDTO> reviews;
    private List<DiscountProfileDTO> discounts;
    private FishingInstructorAdventureProfileDto owner;
    private List<String> equipment;
    private Integer capacity;
    private List<ReservationDTO> reservations;
}


