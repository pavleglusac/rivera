package com.tim20.rivera.controller;

import com.tim20.rivera.dto.RentableDTO;
import com.tim20.rivera.dto.ReservationDTO;
import com.tim20.rivera.model.Review;
import com.tim20.rivera.service.AdventureService;
import com.tim20.rivera.service.BoatService;
import com.tim20.rivera.service.CottageService;
import com.tim20.rivera.service.RentableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RentableController {

    @Autowired
    private RentableService rentableService;
    @Autowired
    private CottageService cottageService;
    @Autowired
    private BoatService boatService;
    @Autowired
    private AdventureService adventureService;

    @GetMapping(path = "rentables-for-owner")
    public List<RentableDTO> getRentableByOwner(String ownerId) {
        return rentableService.getRentablesDtoForOwner(ownerId);
    }

    @GetMapping(path = "check-if-rentable-from-logged-user")
    public boolean checkIfRentableFromOwner(String rentableId) {
        return rentableService.checkIfRentableFromOwner(rentableId);
    }

    @GetMapping(path = "get-type-of-rentable")
    public String getTypeOfRentable(Integer id) {
        if(cottageService.getById(id) != null){
            return "cottage";
        }
        if(boatService.getById(id) != null){
            return "boat";
        }
        if(adventureService.getById(id) != null){
            return "adventure";
        }
        return "";
    }

    @GetMapping(path = "rentable-reservation")
    public List<ReservationDTO> getReservations(@RequestParam("id") Integer id) {
        return rentableService.getReservations(id);
    }

    @GetMapping(path="rentable-discounts")
    public List<ReservationDTO> getDiscountReservations(@RequestParam("id") Integer id) {
        return rentableService.getDiscountReservations(id);
    }

    @GetMapping(path = "get-reviews")
    public List<Review> getReviews(@RequestParam("id") Integer id) {
        return rentableService.getReviews(id);
    }

    @DeleteMapping(path="rentable")
    public void deleteRentable(@RequestParam("id") Integer id) {
        rentableService.delete(id);
    }
}
