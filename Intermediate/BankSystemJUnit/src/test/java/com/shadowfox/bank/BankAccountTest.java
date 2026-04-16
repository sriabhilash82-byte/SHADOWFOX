package com.shadowfox.bank;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount acc = new BankAccount("201", "TestUser", new BigDecimal("1000"));
        acc.deposit(new BigDecimal("500"));

        assertEquals(new BigDecimal("1500"), acc.getBalance());
    }

    @Test
    public void testWithdraw() {
        BankAccount acc = new BankAccount("201", "TestUser", new BigDecimal("1000"));
        acc.withdraw(new BigDecimal("300"));

        assertEquals(new BigDecimal("700"), acc.getBalance());
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        BankAccount acc = new BankAccount("201", "TestUser", new BigDecimal("1000"));

        assertThrows(IllegalArgumentException.class, () -> {
            acc.withdraw(new BigDecimal("1500"));
        });
    }

    @Test
    public void testDepositNegativeAmount() {
        BankAccount acc = new BankAccount("201", "TestUser", new BigDecimal("1000"));

        assertThrows(IllegalArgumentException.class, () -> {
            acc.deposit(new BigDecimal("-100"));
        });
    }

    @Test
    public void testTransactionHistory() {
        BankAccount acc = new BankAccount("201", "TestUser", new BigDecimal("1000"));

        acc.deposit(new BigDecimal("500"));
        acc.withdraw(new BigDecimal("200"));

        assertEquals(2, acc.getTransactions().size());
    }
}