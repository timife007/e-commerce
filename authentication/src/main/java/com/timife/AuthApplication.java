package com.timife;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AuthApplication {
    public static final Logger logger = LoggerFactory.getLogger(AuthApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(AuthApplication.class, args);
    }
}



