package com.example.EcoMoto.service.service;


import com.example.EcoMoto.dto.loan.LoanRequestDto;
import com.example.EcoMoto.dto.loan.LoanResultDto;
import org.springframework.stereotype.Service;

@Service
public interface LoanService {
    LoanResultDto calculateLoan(LoanRequestDto request);
}