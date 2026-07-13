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

    public HomeScreen(TransactionService transactionService,
                      LedgerScreen ledgerScreen) {
        this.transactionService = transactionService;
        this.ledgerScreen = ledgerScreen;
    }


    //Make methods that print out how screen interface and are interactive on the home screen

    public void printHomeScreen() {
        System.out.println("Welcome to TransactionApp");
        System.out.println("Choose an option:");
        System.out.println("1) Add Deposit");
        System.out.println("2) Make Payment (Debit)");
        System.out.println("3) Ledger");
        System.out.println("0) Exit");
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
                default -> System.out.println("Invalid input please try again");
            }
        }
    }

    public void addTransactions(Scanner scanner, TransactionType type, DateTimeFormatter dateTimeFormatter) {
            System.out.println("Enter date and time (yyyy-MM-dd HH:mm:ss):");
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
                System.out.println("Invalid format, try again.");
                return;
            }

            System.out.println("Description of transaction:");
            if (!scanner.hasNextLine()) {
                return;
            }
            String description = scanner.nextLine();

            System.out.println("Enter the Vendor: ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String vendor = scanner.nextLine();

            System.out.println("Enter Amount:");
            if (!scanner.hasNextLine()) {
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount, try again.");
                return;
            }


            Transaction transaction = new Transaction(date, time, description, vendor, amount, type);


            transactionService.saveTransaction(transaction);
        }
    }

