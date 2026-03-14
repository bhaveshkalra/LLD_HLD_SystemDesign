package com.payoneer.machinecoding.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/")
    public String home(){
        return "Spring Boot Machine Coding Setup";
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello Bhavesh from Payoneer!";
    }
}