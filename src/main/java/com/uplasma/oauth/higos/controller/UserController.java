package com.uplasma.oauth.higos.controller;

import com.uplasma.oauth.higos.entity.User;
import com.uplasma.oauth.higos.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

}
