package com.tim20.rivera.repository;

import com.tim20.rivera.model.AvailabilityPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityPattern, Integer> {
}
