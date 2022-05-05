package com.tim20.rivera.controller;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.exception.ResourceConflictException;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.service.ClientService;
import com.tim20.rivera.service.EmailService;
import com.tim20.rivera.service.OwnerService;
import com.tim20.rivera.service.PersonService;
import com.tim20.rivera.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonService userService;
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private PersonService personService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            JwtAuthenticationRequestDTO authenticationRequest, HttpServletResponse response) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        System.out.println(authenticationRequest.getUsername()+", "+ authenticationRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication); 

        System.out.println(authentication.getPrincipal());
        Person user = (Person) authentication.getPrincipal();

        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
            }
        catch (AuthenticationException ae){
            return null;
        }
    }


    @GetMapping("/get-logged-user")
    public PersonDTO getLoggedUser(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        System.out.println(((Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return personService.personToDTO((Person)(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    @PostMapping("/signup")
    public ResponseEntity<Person> addUser(OwnerRequestDTO ownerRequestDTO, UriComponentsBuilder ucBuilder) throws InterruptedException {

        Owner existUser = this.ownerService.findByUsername(ownerRequestDTO.getUsername());

        if (existUser != null) {
            throw new ResourceConflictException(ownerRequestDTO.getUsername(), "Username already exists");
        }
        Person user = this.ownerService.save(ownerRequestDTO);
        emailService.sendNotificaitionAsync(ownerRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(path="check-if-exists")
    public Boolean ifExists(String username){
        Person existUser = personService.findByUsername(username);
        return existUser != null;
    }

    @GetMapping(path="getRole")
    public String getRole(){
        System.out.println(((Person)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getRoles());
        return ((Person)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getRoles().get(0).getName();
    }
}