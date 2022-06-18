package com.tim20.rivera.controller;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.repository.RentableRepository;
import com.tim20.rivera.service.*;
import com.tim20.rivera.util.Availability;
import com.tim20.rivera.util.AvailabilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private AvailabilityService availabilityService;

    @Autowired
    private CottageOwnerService cottageOwnerService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping(path = "/add-cottage")
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
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
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
    public ResponseEntity<String> updateCottage(
            CottageDTO cottage,
            @RequestPart(value = "images", required = false) MultipartFile[] multipartFiles
    ) throws IOException {
        cottageService.update(cottage, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "delete-cottage")
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
    public ResponseEntity<String> deleteCottage(@RequestParam Integer id) {
        cottageService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "test")
    public List<Rentable> test() {
        return rentableRepository.findAll();
    }

    @PostMapping("define-availability")
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER,BOAT_OWNER')")
    public ResponseEntity<String> defineAvailability(@RequestBody AvailabilityRequest availabilityRequest) {
        System.out.println(availabilityRequest);
        availabilityService.defineAvailability(availabilityRequest);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping("get-availabilities")
    public List<Availability> getAvailabilities(@RequestParam String from, @RequestParam String to, @RequestParam Integer id) {
        LocalDateTime fromDateTime = LocalDateTime.parse(from);
        LocalDateTime toDateTime = LocalDateTime.parse(to);
        return availabilityService.getAvailabilities(id, fromDateTime, toDateTime);
    }

    @GetMapping("test-availability")
    public ResponseEntity<String> testAvailability() {
        availabilityService.testBigAvailability();
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("remove-availabilities")
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER,BOAT_OWNER')")
    public ResponseEntity removeAvailabilities(@RequestParam Integer id) {
        availabilityService.removeAvailabilities(id);
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

    @PostMapping(path="/search-all-reservations-for-owner")
    public List<OwnerReservationDTO> searchAllReservationsForOwner(String username, ReservationSearch search) {
        return reservationService.searchAllReservationsForOwner(username.trim(), search);
    }

    @PostMapping(path="/search-reservations-for-entity")
    public List<ReservationDTO> searchReservationsForEntity(Integer id, SearchParams search) {
        return reservationService.searchReservationsForEntity(id, search);
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
