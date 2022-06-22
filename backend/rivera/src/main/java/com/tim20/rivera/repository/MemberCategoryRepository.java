package com.tim20.rivera.repository;

import com.tim20.rivera.model.MemberCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Integer> {
}
