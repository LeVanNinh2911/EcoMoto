package com.example.EcoMoto.controller.admin;

import com.example.EcoMoto.dto.admin.AdminBankDto;
import com.example.EcoMoto.entity.Bank;
import com.example.EcoMoto.service.service.AdminBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
public class AdminBankController {

    @Autowired
    private AdminBankService adminBankService;

    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        return ResponseEntity.ok(adminBankService.getAllBanks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBank(@PathVariable Long id) {
        return ResponseEntity.ok(adminBankService.getBankById(id));
    }

    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody AdminBankDto dto) {
        return ResponseEntity.ok(adminBankService.createBank(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody AdminBankDto dto) {
        return ResponseEntity.ok(adminBankService.updateBank(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        adminBankService.deleteBank(id);
        return ResponseEntity.noContent().build();
    }
}

