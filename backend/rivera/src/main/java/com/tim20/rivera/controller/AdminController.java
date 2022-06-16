package com.tim20.rivera.controller;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.MemberCategory;
import com.tim20.rivera.model.Rules;
import com.tim20.rivera.service.AdminService;
import com.tim20.rivera.service.EmailService;
import com.tim20.rivera.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
        if (username == null || username.isBlank())
            return null;
        return ownerService.findByUsernameDTO(username);
    }

    @PostMapping(path = "activate-owner")
    public void activateOwner(@RequestParam("username") String username) throws IOException {
        System.out.println("activate");
        ownerService.activateOwner(username);
    }

    @PostMapping(path = "deactivate-owner")
    public void deactivateOwner(@RequestParam("username") String username, @RequestParam("reason") String reason) throws MessagingException {
        System.out.println("deactivate");
        System.out.println(reason);
        System.out.println(username);
        emailService.sendNotificaitionToOwnerUsername(username, "Account request declined", reason);
        ownerService.deactivateOwner(username);
    }

    @GetMapping(path = "categories")
    public List<MemberCategory> getCategories() {
        return adminService.getCategories();
    }

    @PostMapping(path = "categories")
    public ResponseEntity<String> updateCategories(@RequestBody ArrayList<MemberCategory> categories) {
        if (categories.stream().noneMatch(x -> x.getNumberOfPoints() == 0 && x.getForOwner())) {
            return ResponseEntity.badRequest().body("Must have zero point category!");
        }

        if (categories.stream().noneMatch(x -> x.getNumberOfPoints() == 0 && !x.getForOwner())) {
            return ResponseEntity.badRequest().body("Must have zero point category!");
        }

        adminService.updateCategories(categories);
        return ResponseEntity.ok().body("Ok");
    }

    @GetMapping(path = "rules")
    public Rules getRules() {
        return adminService.getRules();
    }

    @PostMapping(path = "rules")
    public ResponseEntity<String> postRules(@RequestBody Rules rules) {
        if (rules.getIncomePercentage() > 100 || rules.getIncomePercentage() < 0) {
            return ResponseEntity.badRequest().body("Must be in range [0, 100]!");
        }
        adminService.updateRules(rules);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping(path = "unregistered-usernames")
    public List<String> getUnregisteredUsernames() {
        return adminService.getUnregisteredUsernames();
    }

    @GetMapping(path = "registered-stats")
    public HashMap<String, Integer> getRegisteredStats() {
        return adminService.getRegisteredStats();
    }

    @GetMapping("pending-termination-requests")
    public List<TerminationRequestDTO> getTerminationRequests() {
        return adminService.getTerminationRequests();
    }

    @PostMapping(path = "terminate-person")
    public void terminatePerson(@RequestParam("username") String username) throws MessagingException {
        emailService.sendNotificaitionToUsername(username, "Termination accepted", "Your termination request has been accepted");
        adminService.terminatePerson(username);
    }

    @PostMapping(path = "reject-termination")
    public void terminatePerson(@RequestParam("username") String username, @RequestParam("reason") String reason) throws MessagingException {
        emailService.sendNotificaitionToUsername(username, "Termination rejected", reason);
        adminService.rejectTerminationRequest(username);
    }

    @GetMapping("system-income")
    public List<IncomeSystemDTO> getSystemIncome(@RequestParam("type") String type,
                                                 @RequestParam("startDate")
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                 LocalDateTime from,
                                                 @RequestParam("endDate")
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                 LocalDateTime to) {
        return adminService.getSystemIncome(type, from, to);
    }

    @GetMapping("pending-reports")
    public List<AdminReservationReportDTO> getPendingReprots() {
        return adminService.getPendingReports();
    }


    @PostMapping(value = "resolve-report",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public void resolveReport(@RequestBody ReportResolve resolve) throws MessagingException {
        System.out.println(resolve.getReportId() + " <-> " + resolve.getResponseText() + " <-> " + resolve.isAssignPenalty());

        adminService.resolveReport(resolve.getReportId(), resolve.getResponseText(), resolve.isAssignPenalty());
    }

    @GetMapping("pending-reviews")
    public List<AdminReviewDTO> getPendingReviews() {
        return adminService.getPendingReviews();
    }


    @PostMapping(value = "resolve-review",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public void resolveReport(@RequestBody ReviewResolve resolve) throws MessagingException {
        System.out.println(resolve.getReviewId() + " <-> " + resolve.getResponseText() + " <-> " + resolve.isAllowed());

        adminService.resolveReview(resolve.getReviewId(), resolve.getResponseText(), resolve.isAllowed());
    }

}

