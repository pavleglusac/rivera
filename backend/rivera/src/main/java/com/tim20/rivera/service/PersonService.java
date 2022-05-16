package com.tim20.rivera.service;

import com.tim20.rivera.dto.ClientDTO;
import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.dto.PersonDTO;
import com.tim20.rivera.dto.ProfileDTO;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.CottageOwnerRepository;
import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService{
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Person findByUsername(String username){
        return personRepository.findByUsername(username);
    }

    public PersonDTO personToDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setEmail(person.getEmail());
        dto.setPassword(person.getPassword());
        dto.setName(person.getName());
        dto.setSurname(person.getSurname());
        dto.setPhoneNumber(person.getPhoneNumber());
        dto.setAddress(person.getAddress());
        dto.setCity(person.getCity());
        dto.setCountry(person.getCountry());
        dto.setPhoto(person.getPhoto());
        dto.setDeleted(person.getDeleted());
        dto.setUsername(person.getUsername());

        return dto;
    }

    public ProfileDTO personToProfileDTO(Person person) {
        ProfileDTO dto = new ProfileDTO();
        dto.setName(person.getName());
        dto.setSurname(person.getSurname());
        dto.setProfilePhoto(person.getPhoto());
        dto.setUsername(person.getUsername());
        List<String> roleNames = person.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        dto.setRole(String.join("", roleNames));
        if(person instanceof FishingInstructor) {
            dto.setDescription(((FishingInstructor) person).getBiography());
        } else {
            dto.setDescription("");
        }
        List<MemberCategoryCalendar> calendars = person.getCategories();
        if(calendars.size() > 0) {
            dto.setMembership(calendars.get(calendars.size() - 1).getCategory().getName());
            dto.setMembershipColor(calendars.get(calendars.size() - 1).getCategory().getColor());
        } else {
            dto.setMembership("No category");
            dto.setMembershipColor("#000000");
        }
        return dto;
    }
}
