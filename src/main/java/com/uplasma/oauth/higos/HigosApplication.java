package com.uplasma.oauth.higos;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class HigosApplication {

    public static void main(String[] args) {
        SpringApplication.run(HigosApplication.class, args);
    }

}