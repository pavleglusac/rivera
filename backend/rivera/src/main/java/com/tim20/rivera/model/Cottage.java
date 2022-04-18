package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class Cottage extends Rentable {
    @ElementCollection
    Map<Integer, Integer> rooms;
    @ManyToOne
    private CottageOwner owner;
}
