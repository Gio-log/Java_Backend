package com.example.pasir_kochanski_cezary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test() {
        return "Hello, World!"
                +
                """
                {"appName": "Aplikacja Budżetowa",
                "version": "1.0",
                "message": "Witaj w aplikacji budżetowej stworzonej ze Spring
                Boot!"}""";
    }
    @GetMapping("/api/info")
    public String info() {
        return "Hello, World!"
                +
                """
                {"appName": "Aplikacja Budżetowa",
                "version": "1.0",
                "message": "Witaj w aplikacji budżetowej stworzonej ze Spring
                Boot!"}""";
    }
}
