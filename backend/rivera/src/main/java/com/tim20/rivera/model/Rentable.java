package com.tim20.rivera.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties("rentable")
public class Rentable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    private String description;
    private String addresss;
    private String city;
    private String country;
    @ElementCollection
    private List<String> pictures;
    private String profilePicture;
    @ElementCollection
    private List<String> rulesOfConduct;
    @ElementCollection
    private List<String> addditionalServices;
    @ManyToMany
    private List<Tag> tags;
    private Double averageScore;
    @OneToMany(mappedBy = "rentable")
    private List<Review> reviews;
    @OneToMany(mappedBy = "rentable")
    private List<Discount> discounts;
    @OneToMany(mappedBy = "rentable")
    private List<Pricelist> pricelists;
    @OneToOne
    private Pricelist currentPricelist;
    @OneToMany(mappedBy = "rentable")
    private List<Calendar> calendars;
    @OneToMany(mappedBy = "rentable")
    private List<Reservation> reservations;

}
