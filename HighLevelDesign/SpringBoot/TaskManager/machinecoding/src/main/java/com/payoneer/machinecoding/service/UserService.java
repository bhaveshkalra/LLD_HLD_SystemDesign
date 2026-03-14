package com.payoneer.machinecoding.service;

import com.payoneer.machinecoding.entity.User;
import com.payoneer.machinecoding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public List<User> getUsers(){
        return repo.findAll();
    }
}