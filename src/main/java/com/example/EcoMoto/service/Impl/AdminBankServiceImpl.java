package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.dto.admin.AdminBankDto;
import com.example.EcoMoto.entity.Bank;
import com.example.EcoMoto.repository.BankRepository;
import com.example.EcoMoto.service.service.AdminBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBankServiceImpl implements AdminBankService {

    @Autowired
    private BankRepository bankRepository;

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Bank getBankById(Long id) {
        return bankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank not found"));
    }

    @Override
    public Bank createBank(AdminBankDto dto) {
        Bank bank = new Bank();
        bank.setName(dto.getName());
        bank.setInterestRate(dto.getInterestRate());
        return bankRepository.save(bank);
    }

    @Override
    public Bank updateBank(Long id, AdminBankDto dto) {
        Bank bank = getBankById(id);
        bank.setName(dto.getName());
        bank.setInterestRate(dto.getInterestRate());
        return bankRepository.save(bank);
    }

    @Override
    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }
}
