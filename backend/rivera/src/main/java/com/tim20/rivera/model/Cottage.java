package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE cottage "
        + "SET deleted = true "
        + "WHERE id = ? and version = ?")
@Where(clause = "deleted = false")
public class Cottage extends Rentable {

    @ElementCollection
    Map<Integer, Integer> rooms;


    public int capacity() {
        int cap = 0;
        for (var entry : rooms.entrySet())
            cap += entry.getKey() * entry.getValue();
        return cap;
    }
}
