package com.tim20.rivera.controller;

import com.tim20.rivera.dtos.AdventureDTO;
import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.Greeting;
import com.tim20.rivera.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GreetingController {

    @Autowired
    private GreetingRepository repository;

    @GetMapping(path = "/greetings")
    public List<Greeting> getGreetings() {
        return repository.findAll();
    }

    @PostMapping(path = "/add-greeting")
    public Boolean addGreeting() {
        Greeting greeting = new Greeting();
        greeting.setContent("Ovo je neki greeting");
        repository.save(greeting);
        return true;
    }

    @GetMapping(path="hello")
    public String hello(){
        return "hello";
    }

    @PostMapping(path="add-adventure")
    public ResponseEntity<String> addAdventure(
                                                AdventureDTO adventure,
                                                @RequestPart("images") MultipartFile[] multipartFiles
                                               ) throws IOException {
        System.out.println("Heyy");
        Path path = Paths.get("src\\main\\resources\\static\\images\\" + adventure.getName());
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        for (MultipartFile mpf : multipartFiles) {
            String fileName = mpf.getOriginalFilename();
            try (InputStream inputStream = mpf.getInputStream()) {
                Path filePath = path.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

}
