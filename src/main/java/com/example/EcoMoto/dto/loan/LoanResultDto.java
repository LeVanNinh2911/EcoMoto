package com.example.EcoMoto.dto.loan;

import lombok.Data;

import java.util.List;

@Data
public class LoanResultDto {
    private double totalInterest;
    private double totalPayment;
    private List<PaymentScheduleDto> schedule;

    public LoanResultDto() {
    }

    public LoanResultDto(double totalInterest, double totalPayment, List<PaymentScheduleDto> schedule) {
        this.totalInterest = totalInterest;
        this.totalPayment = totalPayment;
        this.schedule = schedule;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public List<PaymentScheduleDto> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<PaymentScheduleDto> schedule) {
        this.schedule = schedule;
    }
}