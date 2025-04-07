package com.example.pretesting_mtls_client.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoAPI {

    @GetMapping
    public ResponseEntity<String> getProtected(){
        System.out.println("protejat");
        return ResponseEntity.ok("Sunt protejat gata");
    }
}
