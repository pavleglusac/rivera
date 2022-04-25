package com.tim20.rivera.repository;

import com.tim20.rivera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByUsername(String username);
}
