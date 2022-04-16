package com.tim20.rivera.repository;

import com.tim20.rivera.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
