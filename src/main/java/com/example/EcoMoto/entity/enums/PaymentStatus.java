package com.example.EcoMoto.entity.enums;

public enum PaymentStatus {
    UNPAID,                 // Chưa thanh toán
    DEPOSIT_PENDING,        // Đang chờ thanh toán tiền cọc
    DEPOSIT_PAID,           // Đã thanh toán tiền cọc
    AWAITING_BANK_TRANSFER, // Đang chờ thanh toán toàn bộ qua ngân hàng
    PAID,                   // Đã thanh toán toàn bộ
    INSTALLMENT_PENDING,    // Đang chờ thanh toán tiền cọc trả góp
    INSTALLMENT_ACTIVE      // Đang trả góp
}

