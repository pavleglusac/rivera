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
    static final String SQ_CLIENT = "mysequence";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SQ_CLIENT)
    @SequenceGenerator(name = SQ_CLIENT, sequenceName = SQ_CLIENT,
            allocationSize = 1,initialValue=1)
    private Integer id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String country;
    @ElementCollection
    private List<String> pictures;
    @ElementCollection
    private List<String> subscribed;
    private String profilePicture;
    @ElementCollection
    private List<String> rulesOfConduct;
    @ElementCollection
    private List<String> additionalServices;
    @ManyToMany
    private List<Tag> tags;
    private Double averageScore;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.PERSIST)
    private List<Discount> discounts;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.ALL)
    private List<Pricelist> pricelists;
    @OneToOne(fetch = FetchType.LAZY)
    private Pricelist currentPricelist;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.ALL)
    private List<Calendar> calendars;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
    @ManyToOne
    private Owner owner;

}
