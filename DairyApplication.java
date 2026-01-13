package com.example.dairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.example.dairy"+"com.example.dairy.controller"+
		"com.example.dairy.model"+"com.example.dairy.repo"+"com.example.dairy.service"})
public class DairyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DairyApplication.class, args);
    }
}
