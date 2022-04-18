package com.tim20.rivera.repository;

import com.tim20.rivera.model.FishingInstructor;
import com.tim20.rivera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FishingInstructorRepository extends JpaRepository<FishingInstructor, String> {
    public Optional<FishingInstructor> findByUsername(String username);
}
