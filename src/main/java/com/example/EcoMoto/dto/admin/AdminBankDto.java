package com.example.EcoMoto.dto.admin;

public class AdminBankDto {
    private String name;
    private double interestRate;

    public AdminBankDto() {
    }

    public AdminBankDto(String name, double interestRate) {
        this.name = name;
        this.interestRate = interestRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}

