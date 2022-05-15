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
    @Override
    public String toString() {
        return "CottageOwner{" +
                '}';
    }
}
