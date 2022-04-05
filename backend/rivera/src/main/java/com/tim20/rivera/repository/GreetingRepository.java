package com.tim20.rivera.repository;

import com.tim20.rivera.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, Integer> {
}
