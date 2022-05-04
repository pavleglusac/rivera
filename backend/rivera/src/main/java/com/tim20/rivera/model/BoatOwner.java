package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class BoatOwner extends Owner {

    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER)
    private List<Boat> boats;
}
