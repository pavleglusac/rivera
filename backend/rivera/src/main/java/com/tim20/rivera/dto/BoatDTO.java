package com.tim20.rivera.dto;

import com.tim20.rivera.model.BoatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoatDTO {
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
    private BoatType type;
    private Double length;
    private Integer capacity;
    private Integer enginesNumber;
    private Double enginePower;
    private Double maxSpeed;

}
