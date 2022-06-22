package com.tim20.rivera.dto;

import com.tim20.rivera.model.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CottageProfileDTO {
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
    private String rooms;
    private int capacity;
    private String cancellationTerms;
    private Boolean canBeChanged;
    private List<String> pictures;
    private List<ReviewProfileDTO> reviews;
    private List<DiscountProfileDTO> discounts;
    private CottageOwnerCottageProfileDto owner;
}
