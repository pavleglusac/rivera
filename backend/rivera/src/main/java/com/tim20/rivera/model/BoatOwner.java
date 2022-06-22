package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE boat_owner "
        + "SET deleted = true "
        + "WHERE username = ? and version = ?")
@Where(clause = "deleted = false")
public class BoatOwner extends Owner {
    private BoatOwnerType type;
}
