package com.pluralsight.accountingledger.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {

    private int id;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;
    private TransactionType type;

    public Transaction(int id,
                       LocalDate date,
                       LocalTime time,
                       String description,
                       String vendor,
                       double amount,
                       TransactionType type) {

        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return date + " " + time + " | " + description + " | " + vendor + " | $" + amount;
    }
}
