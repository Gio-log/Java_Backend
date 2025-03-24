package com.example.pasir_kochanski_cezary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class InfoController {

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        return ResponseEntity.ok(
                Map.of(
                        "appName", "Aplikacja Budżetowa",
                        "version", "1.0",
                        "message", "Witaj w aplikacji budżetowej stworzonej ze Spring Boot!"
                )
        );
    }
}