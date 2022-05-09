package com.tim20.rivera.controller;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.exception.ResourceConflictException;
import com.tim20.rivera.model.Client;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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
    private ClientService clientService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PersonService personService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            JwtAuthenticationRequestDTO authenticationRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            System.out.println(authenticationRequest.getUsername() + ", " + authenticationRequest.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println(authentication.getPrincipal());
        Person user = (Person) authentication.getPrincipal();
        if(!ownerService.checkIfApprovedOrNonExistent(user.getUsername())){
            return null;
        }
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        } catch (AuthenticationException ae) {
            return null;
        }
    }

    @GetMapping("/get-logged-user")
    public PersonDTO getLoggedUser() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        System.out.println(((Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return personService.personToDTO((Person) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    @GetMapping("/get-logged-username")
    public String getLoggedUsername() {
        return ((Person) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername();
    }

    @PostMapping("/signup")
    public ResponseEntity<Person> addOwner(OwnerRequestDTO ownerRequestDTO) throws InterruptedException {

        Owner existUser = this.ownerService.findByUsername(ownerRequestDTO.getUsername());

        if (existUser != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Person user = this.ownerService.save(ownerRequestDTO);
        emailService.sendNotificaitionAsync(ownerRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/signupClient")
    public ResponseEntity<Person> addClient(ClientRequestDTO clientRequestDTO) {
        Client existUser = this.clientService.findByUsername(clientRequestDTO.getUsername());
        if (existUser != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Person user = this.clientService.save(clientRequestDTO);
        emailService.sendNotificaitionClientAsync(clientRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(path = "check-if-username-exists")
    public Boolean ifExists(String username) {
        Person existUser = personService.findByUsername(username);
        return existUser != null;
    }

    @GetMapping(path = "getRole")
    public String getRole() {
        try {
            System.out.println(((Person) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getRoles());
            return ((Person) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getRoles().get(0).getName();
        } catch (Exception e) {
            return "unauthenticated";
        }
    }

    @GetMapping(path = "getRoleByUsername")
    public String getRole(@RequestParam String username) {
        Client client = clientService.findByUsername(username);
        if (client != null)
            return "CLIENT";
        Owner owner = ownerService.findByUsername(username);
        if (owner != null)
            return "OWNER";
        return "";
    }
}