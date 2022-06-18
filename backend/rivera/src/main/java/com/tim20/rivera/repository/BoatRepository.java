package com.tim20.rivera.repository;

import com.tim20.rivera.model.Boat;
import com.tim20.rivera.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoatRepository extends JpaRepository<Boat, Integer> {
    List<Boat> findByOwnerUsername(String username);
}