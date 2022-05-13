package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinitions;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class FishingInstructor extends Owner {
    private String biography;

    @Override
    public String toString() {
        return "FishingInstructor{" +
                "biography='" + biography + '\'' +
                '}';
    }
}
