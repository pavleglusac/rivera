package com.tim20.rivera.controller;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.model.MemberCategory;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.model.Rules;
import com.tim20.rivera.service.AdminService;
import com.tim20.rivera.service.EmailService;
import com.tim20.rivera.service.OwnerService;
import com.tim20.rivera.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    OwnerService ownerService;

    @Autowired
    EmailService emailService;

    @Autowired
    AdminService adminService;

    @GetMapping(path = "get-owner")
    public OwnerRequestDTO getPerson(@RequestParam("username") String username) throws IOException {
        return ownerService.findByUsernameDTO(username);
    }

    @PostMapping(path = "activate-owner")
    public void activatePerson(@RequestParam("username") String username) throws IOException {
        System.out.println("activate");
        ownerService.activateOwner(username);
    }

    @PostMapping(path = "deactivate-owner")
    public void deactivatePerson(@RequestParam("username") String username, @RequestParam("reason") String reason) throws IOException, InterruptedException {
        System.out.println("deactivate");
        System.out.println(reason);
        System.out.println(username);
        emailService.sendNotificaitionToOwnerUsername(username, "Account request declined", reason);
        ownerService.deactivateOwner(username);
    }

    @GetMapping(path="categories")
    public List<MemberCategory> getCategories() {
        return adminService.getCategories();
    }

    @PostMapping(path="categories")
    public ResponseEntity<String> updateCategories(@RequestBody ArrayList<MemberCategory> categories) {
        if(categories.stream().noneMatch(x -> x.getNumberOfPoints() == 0 && x.getForOwner())) {
            return ResponseEntity.badRequest().body("Must have zero point category!");
        }

        if(categories.stream().noneMatch(x -> x.getNumberOfPoints() == 0 && !x.getForOwner())) {
            return ResponseEntity.badRequest().body("Must have zero point category!");
        }

        adminService.updateCategories(categories);
        return ResponseEntity.ok().body("Ok");
    }

    @GetMapping(path="rules")
    public Rules getRules() {
        return adminService.getRules();
    }

    @PostMapping(path="rules")
    public ResponseEntity<String> postRules(@RequestBody Rules rules) {
        if(rules.getIncomePercentage() > 100 || rules.getIncomePercentage() < 0) {
            return ResponseEntity.badRequest().body("Must be in range [0, 100]!");
        }
        adminService.updateRules(rules);
        return ResponseEntity.ok().body("ok");
    }
}
