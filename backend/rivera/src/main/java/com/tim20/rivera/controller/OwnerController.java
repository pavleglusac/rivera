package com.tim20.rivera.controller;


import com.tim20.rivera.dto.DiscountOfferDTO;
import com.tim20.rivera.dto.ReviewProfileDTO;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.service.DiscountService;
import com.tim20.rivera.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
