package com.tim20.rivera.repository;

import com.tim20.rivera.model.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatRepository  extends JpaRepository<Boat, Integer> {
}
