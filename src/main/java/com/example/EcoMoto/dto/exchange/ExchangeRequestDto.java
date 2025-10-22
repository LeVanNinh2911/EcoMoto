package com.example.EcoMoto.dto.exchange;

import java.time.LocalDateTime;

public class ExchangeRequestDto {
    private Long branchId;
    private LocalDateTime requestedTime;
    private String note;

    public ExchangeRequestDto() {
    }

    public ExchangeRequestDto(Long branchId, LocalDateTime requestedTime, String note) {
        this.branchId = branchId;
        this.requestedTime = requestedTime;
        this.note = note;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public LocalDateTime getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(LocalDateTime requestedTime) {
        this.requestedTime = requestedTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

