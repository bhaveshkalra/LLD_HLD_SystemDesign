package com.payoneer.machinecoding.controller;

import com.payoneer.machinecoding.entity.User;
import com.payoneer.machinecoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService service;

    @Autowired
    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getUsers(){
        return service.getUsers();
    }
}