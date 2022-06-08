package com.tim20.rivera.controller;

import com.tim20.rivera.dto.ProfileDTO;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    private PersonService personService;

    @GetMapping(path = "profile")
    public ProfileDTO getProfile(@RequestParam("id") String id) {
        Person person = personService.findByUsername(id);
        if(person == null) return null;
        return personService.personToProfileDTO(person);
    }
}
