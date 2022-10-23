package com.flightman.flightmanapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@RestController
public class FlightmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightmanApplication.class, args);
	}

        @GetMapping("/")
        public String hello(@RequestParam(value = "name", defaultValue = "Srishti") String name) {
                return String.format("Hello %s!", name);
        }

}
