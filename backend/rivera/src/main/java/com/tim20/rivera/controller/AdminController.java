package com.tim20.rivera.controller;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.service.OwnerService;
import com.tim20.rivera.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    OwnerService ownerService;

    @GetMapping(path = "get-owner")
    public OwnerRequestDTO getPerson(@RequestParam("username") String username) throws IOException {
        return ownerService.findByUsername(username);
    }

    @PostMapping(path = "activate-owner")
    public void activatePerson(@RequestParam("username") String username) throws IOException {
        ownerService.activateOwner(username);
    }
    @PostMapping(path = "deactivate-owner")
    public void deactivatePerson(@RequestParam("username") String username) throws IOException {
        ownerService.deactivateOwner(username);
    }
}
