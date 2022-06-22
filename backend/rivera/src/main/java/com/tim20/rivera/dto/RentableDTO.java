package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RentableDTO {
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
    private List<String> pictures;
}
