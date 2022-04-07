package com.tim20.rivera.repository;

import com.tim20.rivera.model.FishingInstructor;
import com.tim20.rivera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishingInstructorRepository extends JpaRepository<FishingInstructor, Integer> {
}
