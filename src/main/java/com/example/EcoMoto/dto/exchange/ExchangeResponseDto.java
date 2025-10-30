package com.example.EcoMoto.dto.exchange;


import com.example.EcoMoto.entity.enums.ExchangeStatus;

import java.time.LocalDateTime;

public class ExchangeResponseDto {
    private Long id;
    private String userEmail;
    private String branchName;
    private LocalDateTime requestedTime;
    private ExchangeStatus status;
    private String note;

    public ExchangeResponseDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public LocalDateTime getRequestedTime() { return requestedTime; }
    public void setRequestedTime(LocalDateTime requestedTime) { this.requestedTime = requestedTime; }

    public ExchangeStatus getStatus() { return status; }
    public void setStatus(ExchangeStatus status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
