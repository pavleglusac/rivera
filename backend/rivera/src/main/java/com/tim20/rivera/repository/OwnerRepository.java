package com.tim20.rivera.repository;

import com.tim20.rivera.model.Discount;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface OwnerRepository  extends JpaRepository<Owner, Integer> {
    Owner findByUsername(String username);

    @Query(value = "select distinct o from Owner o where CONCAT(o.name, ' ', o.surname) LIKE ?1")
    List<Owner> searchOwner(String text);
}
