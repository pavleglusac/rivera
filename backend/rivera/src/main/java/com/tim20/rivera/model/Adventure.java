package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Adventure extends Rentable {
    private Integer capacity;
    @ElementCollection
    private List<String> fishingEquipment;
    @ManyToOne
    private FishingInstructor owner;
}
