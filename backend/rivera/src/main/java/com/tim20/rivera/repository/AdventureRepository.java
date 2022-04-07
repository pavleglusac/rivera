package com.tim20.rivera.repository;

import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdventureRepository extends JpaRepository<Adventure, Integer> {
}
