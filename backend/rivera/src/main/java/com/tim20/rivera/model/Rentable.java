package com.tim20.rivera.model;

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
    @OneToMany
    private List<Tag> tags;
    private Double averageScore;
    @OneToMany
    private List<Review> reviews;
    @OneToMany
    private List<Discount> discounts;
    @OneToMany
    private List<Pricelist> pricelists;
    @OneToOne
    private Pricelist currentPricelist;
    @OneToMany
    private List<Calendar> calendars;

}
