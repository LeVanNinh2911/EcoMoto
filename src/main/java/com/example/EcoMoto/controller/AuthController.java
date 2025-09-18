package com.example.EcoMoto.controller;

import com.example.EcoMoto.dto.auth.JwtResponse;
import com.example.EcoMoto.dto.auth.LoginRequest;
import com.example.EcoMoto.dto.auth.RegisterRequest;
import com.example.EcoMoto.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ✅ API đăng ký
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String result = authService.register(request);
        return ResponseEntity.ok(result);
    }

    // ✅ API đăng nhập (trả JWT)
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse jwtResponse = authService.login(request);
        return ResponseEntity.ok(jwtResponse);
    }
}
