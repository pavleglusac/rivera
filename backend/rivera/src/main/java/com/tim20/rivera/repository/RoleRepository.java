package com.tim20.rivera.repository;

import com.tim20.rivera.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository  extends JpaRepository<Role, String> {
    List<Role> findByName(String name);
}
