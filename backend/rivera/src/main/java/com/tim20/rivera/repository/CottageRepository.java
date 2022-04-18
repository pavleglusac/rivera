package com.tim20.rivera.repository;

import com.tim20.rivera.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CottageRepository  extends JpaRepository<Cottage, Integer> {
}
