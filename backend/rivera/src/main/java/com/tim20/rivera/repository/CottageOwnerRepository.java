package com.tim20.rivera.repository;

import com.tim20.rivera.model.Client;
import com.tim20.rivera.model.CottageOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageOwnerRepository extends JpaRepository<CottageOwner, String> {
}
