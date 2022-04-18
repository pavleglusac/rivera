package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Owner extends Person {
    private String signUpDescription;
    private AccountStatus status;
    @OneToMany
    private List<OwnerCategoryCalendar> categories;
}
