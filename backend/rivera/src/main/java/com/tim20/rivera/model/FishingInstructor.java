package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinitions;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class FishingInstructor extends Owner {
    private String biography;
}
