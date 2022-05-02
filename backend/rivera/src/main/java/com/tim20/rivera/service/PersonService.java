package com.tim20.rivera.service;

import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.model.CottageOwner;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.repository.CottageOwnerRepository;
import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService{
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Person findByUsername(String username){
        return personRepository.findByUsername(username);
    }
}
