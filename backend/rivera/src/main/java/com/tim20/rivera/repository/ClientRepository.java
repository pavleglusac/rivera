package com.tim20.rivera.repository;

import com.tim20.rivera.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Client findByUsername(String username);
}
