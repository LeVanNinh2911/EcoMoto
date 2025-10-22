package com.example.EcoMoto.controller;

import com.example.EcoMoto.dto.compare.CompareRequest;
import com.example.EcoMoto.service.service.CompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compare")
public class CompareController {

    @Autowired
    private CompareService compareService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> compareProducts(
            @RequestBody CompareRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Map<String, Object> result = compareService.compareProducts(request, userDetails);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/history")
    public ResponseEntity<List<Map<String, Object>>> getCompareHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<Map<String, Object>> history = compareService.getCompareHistory(userDetails);
        return ResponseEntity.ok(history);
    }

}

