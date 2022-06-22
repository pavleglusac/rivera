package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.annotation.sql.DataSourceDefinitions;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE fishing_instructor "
        + "SET deleted = true "
        + "WHERE username = ? and version = ?")
@Where(clause = "deleted = false")
public class FishingInstructor extends Owner {
    private String biography;

    @Override
    public String toString() {
        return "FishingInstructor{" +
                "biography='" + biography + '\'' +
                '}';
    }
}
