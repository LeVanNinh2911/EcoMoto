package com.example.EcoMoto.dto.loan;

import lombok.Data;

@Data
public class LoanRequestDto {
    private Long productId;
    private Long bankId;
    private int loanYears;
    private double downPaymentPercent;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public int getLoanYears() {
        return loanYears;
    }

    public void setLoanYears(int loanYears) {
        this.loanYears = loanYears;
    }

    public double getDownPaymentPercent() {
        return downPaymentPercent;
    }

    public void setDownPaymentPercent(double downPaymentPercent) {
        this.downPaymentPercent = downPaymentPercent;
    }
}
