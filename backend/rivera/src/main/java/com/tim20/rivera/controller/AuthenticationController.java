package com.tim20.rivera.controller;

import com.tim20.rivera.dto.JwtAuthenticationRequestDTO;
import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.dto.UserTokenState;
import com.tim20.rivera.exception.ResourceConflictException;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.service.EmailService;
import com.tim20.rivera.service.OwnerService;
import com.tim20.rivera.service.PersonService;
import com.tim20.rivera.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            JwtAuthenticationRequestDTO authenticationRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        Person user = (Person) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
            }
        catch (AuthenticationException ae){
            return null;
        }
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<Person> addUser(OwnerRequestDTO ownerRequestDTO, UriComponentsBuilder ucBuilder) throws InterruptedException {

        Person existUser = this.userService.findByUsername(ownerRequestDTO.getUsername());

        if (existUser != null) {
            throw new ResourceConflictException(ownerRequestDTO.getUsername(), "Username already exists");
        }
        Person user = this.ownerService.save(ownerRequestDTO);
        emailService.sendNotificaitionAsync(ownerRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}