package com.tim20.rivera.controller;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(path = "person-by-username")
    public PersonDTO getPersonByUsername(@RequestParam("username") String username) {
        Person person = personService.findByUsername(username);
        if(person == null) {
            System.out.println(username + " is null!");
            return null;
        }
        return personService.personToDTO(person);
    }

    @PostMapping(path = "update-person")
    public ResponseEntity<String> updatePerson(PersonDTO personDTO) {
        Boolean success = personService.updatePerson(personDTO);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "update-person-photo")
    public ResponseEntity<String> updateClientPhoto(@RequestParam("username") String username, @RequestPart(value = "image") MultipartFile image) throws IOException {
        personService.updatePersonPhoto(username, image);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "change-password")
    public ResponseEntity<UserTokenState> changePassword(HttpServletRequest req, @RequestBody PasswordChangeDTO dto) {
        System.out.println(req.getHeader("Authorization"));
        System.out.println(dto.getUsername());
        System.out.println(dto.getNewPassword());
        System.out.println(dto.getOldPassword());
        Person person = ((Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personService.changePassword(dto);
    }

    @GetMapping("/get-logged-username-novi")
    public String getLoggedUsername() {
        try {
            String uname =  ((Person) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername();
            System.out.println(uname);
            return uname;
        } catch (Exception e) {
            return "";
        }
    }

    @GetMapping(path = "zdravo")
    public void zdravo() {
        Person person = ((Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        System.out.println(person.getUsername());
    }

    @PostMapping(path="request-termination")
    public ResponseEntity<String> requestTermination(@RequestBody TerminationRequestDTO dto) {
        System.out.println(dto.getUsername());
        System.out.println(dto.getDescription());
        if(personService.requestTermination(dto)) {
            return ResponseEntity.status(200).body("ok");
        } else {
            return ResponseEntity.status(401).body("already exists!");
        }
    }

    @GetMapping(path="search-person")
    public List<ProfileDTO> searchPerson(@RequestParam int numberOfResults, @RequestParam String search, @RequestParam String type) {
        return personService.searchPerson(numberOfResults, search, type);
    }

    @DeleteMapping(path = "person")
    public void deletePerson(@RequestParam String username) {
        personService.delete(username);
    }


}
