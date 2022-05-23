package com.tim20.rivera.repository;

import com.tim20.rivera.model.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesRepository extends JpaRepository<Rules, Integer> {
}
