package com.shadowfox.bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public Transaction(String type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}