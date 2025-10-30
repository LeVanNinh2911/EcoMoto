package com.example.EcoMoto.controller;

import com.example.EcoMoto.dto.auth.JwtResponse;
import com.example.EcoMoto.dto.auth.LoginRequest;
import com.example.EcoMoto.dto.auth.RegisterRequest;
import com.example.EcoMoto.service.AuthService;
import com.example.EcoMoto.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;


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
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        // 🔹 1. Kiểm tra header có tồn tại và đúng định dạng không
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "message", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7); // bỏ 'Bearer '

        try {
            // 🔹 2. Xác thực token
            boolean isValid = jwtUtils.validateToken(token);
            if (isValid) {
                String email = jwtUtils.getEmailFromToken(token);
                String role = jwtUtils.getRoleFromToken(token);

                return ResponseEntity.ok(Map.of(
                        "valid", true,
                        "email", email,
                        "role", role
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("valid", false, "message", "Invalid or expired token"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "message", "Token verification failed"));
        }
    }


}
