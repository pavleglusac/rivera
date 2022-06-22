package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Where(clause="deleted=false and (select o.deleted from fishing_instructor o where o.username = owner_username) = false")
public class Adventure extends Rentable {
    private Integer capacity;
    @ElementCollection
    private List<String> fishingEquipment;
}
