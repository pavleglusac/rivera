package com.tim20.rivera.dto;

import com.tim20.rivera.model.MemberCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ClientLoyaltyDTO {
    private String username;
    private Integer pointsPerReservation;
    private Integer numberOfPenalties;
    private Integer numberOfPoints;
    private List<MemberCategory> allLoyalties;
}
