package com.tim20.rivera.controller;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.repository.RentableRepository;
import com.tim20.rivera.service.CottageAvailabilityService;
import com.tim20.rivera.service.CottageOwnerService;
import com.tim20.rivera.service.CottageService;
import com.tim20.rivera.service.ReservationService;
import com.tim20.rivera.util.Availability;
import com.tim20.rivera.util.AvailabilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/cottage")
public class CottageController {

    @Autowired
    private CottageService cottageService;

    @Autowired
    private RentableRepository rentableRepository;

    @Autowired
    private CottageAvailabilityService cottageAvailabilityService;

    @Autowired
    private CottageOwnerService cottageOwnerService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping(path = "/add-cottage")
    public ResponseEntity<Integer> addCottage(
            CottageDTO cottage,
            @RequestPart("images") MultipartFile[] multipartFiles
    ) throws IOException {

        Integer id = cottageService.insert(cottage, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping(path = "/get-cottages")
    public List<Cottage> getCottages() {
        return cottageService.findAll();
    }

    @GetMapping(path = "get-cottage")
    public CottageDTO getCottage(@RequestParam("id") Integer id) {
        return cottageService.getById(id);
    }

    @GetMapping(path = "get-full-cottage")
    public CottageProfileDTO getFullCottage(@RequestParam("id") Integer id) throws IOException {
        return cottageService.getFullById(id);
    }

    @PostMapping(path = "update-cottage")
    public ResponseEntity<String> updateCottage(
            CottageDTO cottage,
            @RequestPart(value = "images", required = false) MultipartFile[] multipartFiles
    ) throws IOException {
        cottageService.update(cottage, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "delete-cottage")
    public ResponseEntity<String> deleteCottage(@RequestParam Integer id) {
        cottageService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "test")
    public List<Rentable> test() {
        return rentableRepository.findAll();
    }

    @PostMapping("define-availability")
    public ResponseEntity<String> defineAvailability(@RequestBody AvailabilityRequest availabilityRequest) {
        System.out.println(availabilityRequest);
        cottageAvailabilityService.defineAvailability(availabilityRequest);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping("get-availabilities")
    public List<Availability> getAvailabilities(@RequestParam String from, @RequestParam String to, @RequestParam Integer id) {
        LocalDateTime fromDateTime = LocalDateTime.parse(from);
        LocalDateTime toDateTime = LocalDateTime.parse(to);
        return cottageAvailabilityService.getAvailabilities(id, fromDateTime, toDateTime);
    }

    @GetMapping("test-availability")
    public ResponseEntity<String> testAvailability() {
        cottageAvailabilityService.testBigAvailability();
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("remove-availabilities")
    public ResponseEntity removeAvailabilities(@RequestParam Integer id) {
        cottageAvailabilityService.removeAvailabilities(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path="/search-cottages")
    public List<CottageDTO> searchCottages(SearchParams searchParams) {
        return cottageService.searchCottages(searchParams);
    }

    @PostMapping(path="/search-cottages-for-owner")
    public List<CottageDTO> searchCottagesForOwner(SearchParams searchParams) {
        return cottageService.searchCottagesForOwner(searchParams);
    }

    @PostMapping(path="/search-reservations-for-owner")
    public List<ReservationDTO> searchReservationsForOwner(SearchParams searchParams) {
        return reservationService.searchReservationsForOwner(searchParams);
    }


    @GetMapping(path= "/get-reservations-by-owner")
    public List<ReservationDTO> getReservationsByOwner(){
        return reservationService.getReservationsByOwner();
    }

    @GetMapping(path= "/get-reservations-by-cottage")
    public List<ReservationDTO> getReservationsByCottage(){
        return null;
    }
}
