package com.tim20.rivera.controller;


import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.service.DiscountService;
import com.tim20.rivera.service.OwnerService;
import com.tim20.rivera.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping(path = "reviews-for-owner")
    public List<ReviewProfileDTO> getReviewsForOwner(String ownerId) {
        return ownerService.getReviewsForOwner(ownerId);
    }

    @GetMapping(path = "discounts-for-owner")
    public List<DiscountOfferDTO> getDiscountsForOwner(String ownerId) {
        Owner owner = ownerService.findByUsername(ownerId);
        if (owner == null) return null;
        return discountService.getDiscountsForOwner(owner);
    }

    @GetMapping(path = "get-attendance")
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER','BOAT_OWNER','FISHING_INSTRUCTOR')")
    public List<AttendanceDTO> getAttendance(String startDate, String endDate, String type) {
        return ownerService.getAttendance(startDate, endDate, type);
    }

    @GetMapping(path = "get-income")
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER','BOAT_OWNER','FISHING_INSTRUCTOR')")
    public List<IncomeFrontDTO> getIncome(String startDate, String endDate) {
        return ownerService.getIncome(startDate, endDate);
    }

    @GetMapping(path = "get-owner-loyalty")
    public OwnerLoyaltyDTO getOwnerLoyalty(@RequestParam("username") String username) {
        return ownerService.getOwnerLoyalty(username);
    }

    @PostMapping(path = "add-discount")
    public void addDiscount(DiscountDTO discountDTO) {
        discountService.addDiscount(discountDTO);
    }


    @GetMapping(path = "get-client-username-for-current-reservation")
    public String getClientUsernameForCurrentReservation(String id){
        return reservationService.getClientUsernameForCurrentReservation(id);
    }

    @PostMapping(path ="delete-discount")
    public ResponseEntity<String> deleteDiscount(@RequestParam Integer id){
        discountService.deleteDiscount(id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
