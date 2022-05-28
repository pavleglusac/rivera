package com.tim20.rivera.controller;


import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.service.DiscountService;
import com.tim20.rivera.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<Timestamp,Integer> getAttendance(LocalDateTime startDate, LocalDateTime endDate, String type){
        return ownerService.getAttendance(startDate, endDate, type);
    }
    @GetMapping(path = "get-income")
    public List<IncomeFrontDTO> getIncome(LocalDateTime startDate, LocalDateTime endDate){
        return ownerService.getIncome(startDate, endDate);
    }


}
