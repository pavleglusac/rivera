package com.tim20.rivera.service;

import com.tim20.rivera.model.Role;
import com.tim20.rivera.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findByName(String name) {
        List<Role> roles = this.roleRepository.findByName(name);
        return roles;
    }
}
