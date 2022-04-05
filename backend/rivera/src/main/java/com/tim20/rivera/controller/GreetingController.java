package com.tim20.rivera.controller;

import com.tim20.rivera.model.Greeting;
import com.tim20.rivera.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
