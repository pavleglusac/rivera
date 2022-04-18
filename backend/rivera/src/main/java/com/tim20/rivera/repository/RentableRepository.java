package com.tim20.rivera.repository;

import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentableRepository extends JpaRepository<Rentable, Integer> {

}
