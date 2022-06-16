package com.tim20.rivera.repository;

import com.tim20.rivera.dto.ProfileDTO;
import com.tim20.rivera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByUsername(String username);

    @Query(value = "select * from Person p where CONCAT(p.name, ' ', p.surname) LIKE ?2 LIMIT ?1", nativeQuery = true)
    List<Person> searchPerson(int numberOfResults, String text);
}
