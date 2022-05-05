package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CottageOwner extends Owner {
    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER)
    private List<Cottage> cottages;
}
