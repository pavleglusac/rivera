package com.tim20.rivera.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdventureDTO {
    private String name;
    private String description;
    private List<String> tags;
    private List<String> equipment;
    private List<String> services;
    private List<String> rulesOfConduct;
    private Double perHour;
    private Double perDay;
    private String address;
    private String city;
    private String country;

}
