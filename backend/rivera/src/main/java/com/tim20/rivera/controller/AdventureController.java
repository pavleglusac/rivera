package com.tim20.rivera.controller;

import com.tim20.rivera.dtos.AdventureDTO;
import com.tim20.rivera.repository.AdventureRepository;
import com.tim20.rivera.service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api")
public class AdventureController {
    @Autowired
    private AdventureService adventureService;

    @PostMapping(path="add-adventure")
    public ResponseEntity<String> addAdventure(
            AdventureDTO adventure,
            @RequestPart("images") MultipartFile[] multipartFiles
    ) throws IOException {

        adventureService.addAdventure(adventure, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
