package com.example.EcoMoto.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expirationMs}")
    private int jwtExpirationMs;

    // Tạo token với username và role
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // thêm role vào payload
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // Lấy username từ token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Lấy role từ token
    public String getRoleFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // Kiểm tra token hợp lệ
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT hết hạn: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT không được hỗ trợ: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("JWT không hợp lệ: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Chữ ký JWT không hợp lệ: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims trống: " + e.getMessage());
        }
        return false;
    }
}
