package com.tim20.rivera.controller;

import com.tim20.rivera.dtos.AdventureDTO;
import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.Greeting;
import com.tim20.rivera.repository.AdventureRepository;
import com.tim20.rivera.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping(path="hello")
    public String hello(){
        return "hello";
    }



}
