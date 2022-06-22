package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Where(clause="deleted=false and (select o.deleted from boat_owner o where o.username = owner_username) = false")
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
