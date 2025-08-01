package com.witchshop.chimerologistservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chimerologist")  // Базовый путь
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Привет от Сhimerologist Service!";
    }
}