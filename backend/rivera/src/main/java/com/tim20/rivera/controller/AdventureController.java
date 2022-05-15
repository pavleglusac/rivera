package com.tim20.rivera.controller;

import com.tim20.rivera.dto.AdventureDTO;
import com.tim20.rivera.dto.AdventureProfileDTO;
import com.tim20.rivera.service.AdventureAvailabilityService;
import com.tim20.rivera.dto.SearchParams;
import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.service.AdventureService;
import com.tim20.rivera.util.Availability;
import com.tim20.rivera.util.AvailabilityRequest;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdventureController {
    @Autowired
    private AdventureService adventureService;

    @Autowired
    private AdventureAvailabilityService adventureAvailabilityService;

    @PostMapping(path = "add-adventure")
    public ResponseEntity<Integer> addAdventure(
            AdventureDTO adventure,
            @RequestPart("images") MultipartFile[] multipartFiles
    ) throws IOException {

        Integer id = adventureService.addAdventure(adventure, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping(path = "get-adventure")
    public AdventureDTO getAdventure(@RequestParam("id") Integer id) {
        return adventureService.getAdventure(id);
    }

    @PostMapping(path="/search-adventures")
    public List<AdventureDTO> searchAdventures(SearchParams searchParams) {
        System.out.println(searchParams.getOwnerUsername());
        return adventureService.searchAdventures(searchParams);
    }

    @GetMapping(path = "/get-adventures")
    public List<AdventureDTO> getAdventures() {
        return adventureService.getAdventures();
    }

    @PostMapping(path = "update-adventure")
    public ResponseEntity<String> updateAdventure(
            AdventureDTO adventure,
            @RequestPart(value = "images", required = false) MultipartFile[] multipartFiles
    ) throws IOException {

        adventureService.updateAdventure(adventure, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "get-full-adventure")
    public AdventureProfileDTO getFullAdventure(@RequestParam("id") Integer id) throws IOException {
        return adventureService.getFullById(id);
    }

    @PostMapping(path = "delete-adventure")
    public ResponseEntity<String> deleteAdventure(@RequestParam Integer id) {
        adventureService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }


    @PostMapping("define-availability")
    public ResponseEntity<String> defineAvailability(@RequestBody AvailabilityRequest availabilityRequest) {
        System.out.println(availabilityRequest);
        adventureAvailabilityService.defineAvailability(availabilityRequest);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping("get-availabilities")
    public List<Availability> getAvailabilities(@RequestParam String from, @RequestParam String to, @RequestParam Integer adventureId) {
        LocalDateTime fromDateTime = LocalDateTime.parse(from);
        LocalDateTime toDateTime = LocalDateTime.parse(to);
        return adventureAvailabilityService.getAvailabilities(adventureId, fromDateTime, toDateTime);
    }

    @GetMapping("test-availability")
    public ResponseEntity<String> testAvailability() {
        adventureAvailabilityService.testBigAvailability();
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("remove-availabilities")
    public ResponseEntity removeAvailabilities(@RequestParam Integer id) {
        adventureAvailabilityService.removeAvailabilities(id);
        return ResponseEntity.ok().build();
    }

}
