package com.witchshop.artifactservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artifact")  // Базовый путь
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Привет от Artifact Service!";
    }
}