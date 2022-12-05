package com.flightman.flightmanapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@RestController
@EnableWebMvc
public class FlightmanApplication {

        public static void main(final String[] args) {
                SpringApplication.run(FlightmanApplication.class, args);
        }

        @GetMapping("/")
        public String hello(@RequestParam(value = "name", defaultValue = "Authorized User") final String name) {
                return String.format("Hello %s!", name);
        }

}