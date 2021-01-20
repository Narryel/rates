package ru.narryel.rateapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class RateappApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateappApplication.class, args);
    }

}
