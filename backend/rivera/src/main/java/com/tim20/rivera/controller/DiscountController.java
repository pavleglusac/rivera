package com.tim20.rivera.controller;

import com.tim20.rivera.dto.DiscountOfferDTO;
import com.tim20.rivera.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @GetMapping(path="/last-minute")
    public List<DiscountOfferDTO> getLastMinute() {
        return discountService.getLastMinuteOffers();
    }

    @GetMapping(path="/best-price")
    public List<DiscountOfferDTO> getBestPrice() {
        return discountService.getBestPriceOffers();
    }

    @GetMapping(path="/most-popular")
    public List<DiscountOfferDTO> getMostPopular() {
        return discountService.getMostPopularOffers();
    }

    @GetMapping(path="/recommended")
    public List<DiscountOfferDTO> getRecommended() {
        return discountService.getRecommendedOffers();
    }
}
