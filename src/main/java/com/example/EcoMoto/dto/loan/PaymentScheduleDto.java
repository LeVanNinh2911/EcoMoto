package com.example.EcoMoto.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PaymentScheduleDto {
    private int period;            // Kỳ trả
    private double openingBalance; // Dư nợ đầu kỳ
    private double principal;      // Trả gốc
    private double interest;       // Trả lãi
    private double totalPayment;   // Tổng trả
    private double closingBalance; // Dư nợ cuối kỳ

    public PaymentScheduleDto(int period, double openingBalance, double principal, double interest, double totalPayment, double closingBalance) {
        this.period = period;
        this.openingBalance = openingBalance;
        this.principal = principal;
        this.interest = interest;
        this.totalPayment = totalPayment;
        this.closingBalance = closingBalance;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }
}

