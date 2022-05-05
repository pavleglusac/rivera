package com.tim20.rivera.repository;

import com.tim20.rivera.model.Discount;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository  extends JpaRepository<Owner, Integer> {
    Owner findByUsername(String username);
}
