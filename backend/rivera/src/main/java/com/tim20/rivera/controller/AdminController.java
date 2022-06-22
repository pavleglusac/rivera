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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    OwnerService ownerService;

    @Autowired
    EmailService emailService;

    @Autowired
    AdminService adminService;


    @GetMapping(path = "owner-request")
    @PreAuthorize("hasRole('ADMIN')")
    public OwnerRequestDTO getPerson(@RequestParam("username") String username){
        if (username == null || username.isBlank())
            return null;
        return ownerService.findByUsernameDTO(username);
    }

    @PutMapping(path = "activate-owner")
    @PreAuthorize("hasRole('ADMIN')")
    public void activateOwner(@RequestParam("username") String username) {
        System.out.println("activate");
        ownerService.activateOwner(username);
    }

    @PutMapping(path = "deactivate-owner")
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateOwner(@RequestParam("username") String username, @RequestParam("reason") String reason) throws MessagingException {
        System.out.println("deactivate");
        System.out.println(reason);
        System.out.println(username);
        emailService.sendNotificaitionToOwnerUsername(username, "Account request declined", reason);
        ownerService.deactivateOwner(username);
    }

    @GetMapping(path = "categories")
    @PreAuthorize("hasRole('ADMIN')")
    public List<MemberCategory> getCategories() {
        return adminService.getCategories();
    }

    @PostMapping(path = "categories")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public Rules getRules() {
        return adminService.getRules();
    }

    @PostMapping(path = "rules")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> postRules(@RequestBody Rules rules) {
        if (rules.getIncomePercentage() > 100 || rules.getIncomePercentage() < 0) {
            return ResponseEntity.badRequest().body("Must be in range [0, 100]!");
        }
        adminService.updateRules(rules);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping(path = "unregistered-usernames")
    @PreAuthorize("hasRole('ADMIN')")
    public List<String> getUnregisteredUsernames() {
        return adminService.getUnregisteredUsernames();
    }

    @GetMapping(path = "registered-stats")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Integer> getRegisteredStats() {
        return adminService.getRegisteredStats();
    }

    @GetMapping("pending-termination-requests")
    @PreAuthorize("hasRole('ADMIN')")
    public List<TerminationRequestDTO> getTerminationRequests() {
        return adminService.getTerminationRequests();
    }


    @PostMapping(path = "resolve-termination")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> resolveTerminationRequest(
                                                    @RequestParam("username") String username,
                                                    @RequestParam("requestId") int requestId,
                                                    @RequestParam(value = "reason", required = false) String reason,
                                                    @RequestParam("accept") boolean accept)  {
        boolean ret;
        try {
            ret = adminService.resolveTerminationRequest(username, accept, requestId);
            if(!ret) return ResponseEntity.unprocessableEntity().body("Couldn't process request");
            adminService.notifyUserOnTerminationRequest(username, accept, reason);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body("Couldn't process request");
        }
    }



    @GetMapping("system-income")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminReservationReportDTO> getPendingReports() {
        return adminService.getPendingReports();
    }


    @PostMapping(value = "resolve-report",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> resolveReport(@RequestBody ReportResolve resolve) {
        System.out.println(resolve.getReportId() + " <-> " + resolve.getResponseText() + " <-> " + resolve.isAssignPenalty());
        try {
            boolean ret = adminService.resolveReport(resolve.getReportId());
            if(!ret) return ResponseEntity.unprocessableEntity().body("Couldn't process request");
            adminService.reportMail(resolve.getReportId(), resolve.getResponseText(), resolve.isAssignPenalty());
            adminService.assignPenalty(resolve.getReportId());
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body("Couldn't process request");
        }
    }

    @GetMapping("pending-reviews")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminReviewDTO> getPendingReviews() {
        return adminService.getPendingReviews();
    }


    @PostMapping(value = "resolve-review",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> resolveReport(@RequestBody ReviewResolve resolve) {
        System.out.println(resolve.getReviewId() + " <-> " + resolve.getResponseText() + " <-> " + resolve.isAllowed());

        try {
            int reviewId = resolve.getReviewId();
            boolean success = adminService.resolveReview(resolve.getReviewId(), resolve.isAllowed());
            if(!success) {
                return ResponseEntity.unprocessableEntity().body("Couldn't respond to review!");
            }
            adminService.sendReviewMails(reviewId, resolve.getResponseText(), resolve.isAllowed());
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body("Couldn't respond to review!");
        }
    }

}

