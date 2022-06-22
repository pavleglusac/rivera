package com.tim20.rivera.repository;

import com.tim20.rivera.model.BoatOwner;
import com.tim20.rivera.model.CottageOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatOwnerRepository extends JpaRepository<BoatOwner, String> {
}
