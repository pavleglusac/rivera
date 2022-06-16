package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE person SET deleted = true WHERE id = ?")
@Where(clause="deleted=false")
public class Owner extends Person {
    private String signUpDescription;
    private Integer numberOfPoints;
    @OneToMany(mappedBy = "owner")
    private List<Review> reviews;
    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Rentable> rentables;

    @PreRemove
    public void preRemove() {
        rentables.forEach(x -> x.setDeleted(true));
    }
}
