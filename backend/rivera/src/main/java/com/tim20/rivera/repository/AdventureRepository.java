package com.tim20.rivera.repository;

import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdventureRepository extends JpaRepository<Adventure, Integer> {
    List<Adventure> findByOwnerUsername(String username);
}
