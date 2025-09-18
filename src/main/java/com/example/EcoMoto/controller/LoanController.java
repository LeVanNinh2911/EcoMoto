package com.example.EcoMoto.controller;

import com.example.EcoMoto.dto.loan.LoanRequestDto;
import com.example.EcoMoto.dto.loan.LoanResultDto;
import com.example.EcoMoto.service.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/calculate")
    public LoanResultDto calculateLoan(@RequestBody LoanRequestDto request) {
        return loanService.calculateLoan(request);
    }
}