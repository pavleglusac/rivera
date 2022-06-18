package com.tim20.rivera.controller;


import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.service.DiscountService;
import com.tim20.rivera.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private DiscountService discountService;

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
    public List<AttendanceDTO> getAttendance(String startDate, String endDate, String type) {
        return ownerService.getAttendance(startDate, endDate, type);
    }

    @GetMapping(path = "get-income")
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
}
