package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.dto.loan.LoanRequestDto;
import com.example.EcoMoto.dto.loan.LoanResultDto;
import com.example.EcoMoto.dto.loan.PaymentScheduleDto;
import com.example.EcoMoto.entity.Bank;
import com.example.EcoMoto.entity.Product;
import com.example.EcoMoto.repository.BankRepository;
import com.example.EcoMoto.repository.ProductRepository;
import com.example.EcoMoto.service.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BankRepository bankRepository;

    @Override
    public LoanResultDto calculateLoan(LoanRequestDto request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Bank bank = bankRepository.findById(request.getBankId())
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        double price = product.getPrice().doubleValue();
        double downPayment = price * request.getDownPaymentPercent() / 100.0;
        double loanAmount = price - downPayment;

        int periods = request.getLoanYears() * 12; // ⚡ chú ý tên field DTO
        double monthlyRate = bank.getInterestRate() / 100.0 / 12;

        double principalPerPeriod = loanAmount / periods;
        double remaining = loanAmount;
        double totalInterest = 0;

        List<PaymentScheduleDto> schedules = new ArrayList<>();

        for (int i = 1; i <= periods; i++) {
            double interest = remaining * monthlyRate;
            double principal = principalPerPeriod;
            double payment = principal + interest;
            double beginBalance = remaining;

            remaining -= principal;
            totalInterest += interest;

            schedules.add(new PaymentScheduleDto(
                    i,
                    round(beginBalance),  // số dư đầu kỳ
                    round(principal),
                    round(interest),
                    round(payment),
                    round(remaining)      // số dư cuối kỳ
            ));
        }

        double totalPay = loanAmount + totalInterest;

        return new LoanResultDto(round(totalInterest), round(totalPay), schedules);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
