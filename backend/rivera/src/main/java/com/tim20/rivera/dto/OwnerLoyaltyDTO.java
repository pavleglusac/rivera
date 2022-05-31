package com.tim20.rivera.dto;

import com.tim20.rivera.model.MemberCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OwnerLoyaltyDTO {
    private String username;
    private Integer numberOfPoints;
    private Integer pointsPerReservation;
    private Double incomePercentage;
    private List<MemberCategory> allLoyalties;
}
