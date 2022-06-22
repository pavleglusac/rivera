package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE cottage_owner "
        + "SET deleted = true "
        + "WHERE username = ? and version = ?")
@Where(clause = "deleted = false")
public class CottageOwner extends Owner {
    @Override
    public String toString() {
        return "CottageOwner{" +
                '}';
    }
}
