package com.uplasma.oauth.higos.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestDemoController {

    @GetMapping("/role")
    @Secured("ROLE_adb")
    public Map<String,Object> getRole(){
        Map<String,Object> role = new HashMap<>();
        role.put("name","教师");
        role.put("id","1");
        return role;
    }

}
