package com.pluralsight.accountingledger.ui;

import com.pluralsight.accountingledger.model.Transaction;
import com.pluralsight.accountingledger.model.TransactionType;
import com.pluralsight.accountingledger.service.TransactionService;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class HomeScreen {
    private final TransactionService transactionService;
    private final LedgerScreen ledgerScreen;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public HomeScreen(TransactionService transactionService,
                      LedgerScreen ledgerScreen) {
        this.transactionService = transactionService;
        this.ledgerScreen = ledgerScreen;
    }

    public void printHomeScreen() {
        System.out.println(ANSI_PURPLE + "Welcome to TransactionApp");
        System.out.println("Choose an option:" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "1)" + ANSI_RESET + ANSI_PURPLE + " Add Deposit");
        System.out.println(ANSI_YELLOW + "2)" + ANSI_RESET + ANSI_PURPLE + " Make Payment (Debit)");
        System.out.println(ANSI_YELLOW + "3)" + ANSI_RESET + ANSI_PURPLE + " Ledger");
        System.out.println(ANSI_RED + "0)" + ANSI_RESET + ANSI_PURPLE + " Exit" + ANSI_RESET);
    }

    public void useHomeScreen(Scanner scanner, DateTimeFormatter dateTimeFormatter, LocalDate date) {
        boolean done = true;
        while (done) {
            printHomeScreen();
            if (!scanner.hasNextLine()) {
                return;
            }
            String input = scanner.nextLine();
            switch (input.toUpperCase()) {
                case "1" -> addTransactions(scanner, TransactionType.DEPOSIT, dateTimeFormatter);
                case "2" -> addTransactions(scanner, TransactionType.PAYMENT, dateTimeFormatter);
                case "3" -> ledgerScreen.ledgerChoiceMenu(scanner, date);
                case "0" -> done = false;
                default -> System.err.println("Invalid input please try again");
            }
        }
    }

    public void addTransactions(Scanner scanner, TransactionType type, DateTimeFormatter dateTimeFormatter) {
        System.out.println(ANSI_PURPLE + "Enter date and time (" + ANSI_YELLOW + "yyyy-MM-dd HH:mm:ss" + ANSI_RESET + ANSI_PURPLE + "):" + ANSI_RESET);
        if (!scanner.hasNextLine()) {
            return;
        }
        String dateTimeInput = scanner.nextLine();
        LocalTime time;
        LocalDate date;
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, dateTimeFormatter);
            date = dateTime.toLocalDate();
            time = dateTime.toLocalTime();
        } catch (Exception e) {
            System.err.println("Invalid format, try again.");
            return;
        }

        System.out.println(ANSI_PURPLE + "Description of transaction:" + ANSI_RESET);
        if (!scanner.hasNextLine()) {
            return;
        }
        String description = scanner.nextLine();

        System.out.println(ANSI_PURPLE + "Enter the Vendor: " + ANSI_RESET);
        if (!scanner.hasNextLine()) {
            return;
        }
        String vendor = scanner.nextLine();

        System.out.println(ANSI_PURPLE + "Enter Amount:" + ANSI_RESET);
        if (!scanner.hasNextLine()) {
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Invalid amount, try again.");
            return;
        }


        Transaction transaction = new Transaction(date, time, description, vendor, amount, type);


        transactionService.saveTransaction(transaction);
    }
}

