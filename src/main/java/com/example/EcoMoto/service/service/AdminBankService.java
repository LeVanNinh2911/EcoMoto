package com.example.EcoMoto.service.service;

import com.example.EcoMoto.dto.admin.AdminBankDto;
import com.example.EcoMoto.entity.Bank;

import java.util.List;

public interface AdminBankService {
    List<Bank> getAllBanks();
    Bank getBankById(Long id);
    Bank createBank(AdminBankDto dto);
    Bank updateBank(Long id, AdminBankDto dto);
    void deleteBank(Long id);
}

