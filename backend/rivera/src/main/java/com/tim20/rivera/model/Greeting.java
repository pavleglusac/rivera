package com.tim20.rivera.model;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Data
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;

}
