package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Boat extends Rentable {

    private BoatType type;
    private Double length;
    private Integer capacity;
    private Integer enginesNumber;
    private Double enginePower;
    private Double maxSpeed;

    @ElementCollection
    private List<String> fishingEquipment;
    @ElementCollection
    private List<String> navigationEquipment;

}
