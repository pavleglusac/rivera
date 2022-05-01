package com.tim20.rivera.service;

import com.tim20.rivera.dto.UserRequestDTO;
import com.tim20.rivera.model.CottageOwner;
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

    @Autowired
    private CottageOwnerRepository cottageRepository;

    public Person save(UserRequestDTO userRequestDTO){
        return personRepository.save(UserRequestDTOToUser(userRequestDTO));
    }
    public Person findByUsername(String username){
        return personRepository.findByUsername(username);
    }

    public Person UserRequestDTOToUser(UserRequestDTO userRequestDTO){
        Person person = new Person();
        person.setAddress(userRequestDTO.getAddress());
        person.setCity(userRequestDTO.getCity());
        person.setCountry(userRequestDTO.getCountry());
        person.setEmail(userRequestDTO.getEmail());
        person.setName(userRequestDTO.getName());
        person.setSurname(userRequestDTO.getSurname());
        person.setPhoto(userRequestDTO.getPhoto());
        person.setPhoneNumber(userRequestDTO.getPhoneNumber());
        person.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        return person;
    }
}
