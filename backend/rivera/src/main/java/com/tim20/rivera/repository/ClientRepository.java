package com.tim20.rivera.repository;

import com.tim20.rivera.model.Client;
import com.tim20.rivera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Client findByUsername(String username);

    @Query(value = "select * from Client c where CONCAT(c.name, ' ', c.surname) LIKE ?2 LIMIT ?1", nativeQuery = true)
    List<Client> searchClient(int numberOfResults, String text);
}
