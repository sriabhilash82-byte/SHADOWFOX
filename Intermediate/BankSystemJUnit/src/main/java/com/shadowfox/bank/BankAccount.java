package com.shadowfox.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private String accountId;
    private String accountHolderName;
    private BigDecimal balance;
    private List<Transaction> transactions;

    public BankAccount(String accountId, String accountHolderName, BigDecimal initialBalance) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        balance = balance.add(amount);
        transactions.add(new Transaction("DEPOSIT", amount));
    }

    public void withdraw(BigDecimal amount) {
        validateAmount(amount);

        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        balance = balance.subtract(amount);
        transactions.add(new Transaction("WITHDRAW", amount));
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}