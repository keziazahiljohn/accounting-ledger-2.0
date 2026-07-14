package com.pluralsight.accountingledger.ui;

import com.pluralsight.accountingledger.model.Transaction;
import com.pluralsight.accountingledger.service.TransactionService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class LedgerScreen {

    private final TransactionService transactionService;
    private final ReportsScreen reportsScreen;


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_YELLOW = "\u001B[33m";


    public LedgerScreen(TransactionService transactionService,
                        ReportsScreen reportsScreen) {
        this.transactionService = transactionService;
        this.reportsScreen = reportsScreen;
    }

    public void ledgerMenu() {
        System.out.println(ANSI_PURPLE + "Ledger");
        System.out.println("Choose an option:" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "1)" + ANSI_RESET + ANSI_PURPLE + " All");
        System.out.println(ANSI_YELLOW + "2)" + ANSI_RESET + ANSI_PURPLE + " Deposits");
        System.out.println(ANSI_YELLOW + "3) " + ANSI_RESET + ANSI_PURPLE + "Payments");
        System.out.println(ANSI_YELLOW + "4)" + ANSI_RESET + ANSI_PURPLE + " Reports");
        System.out.println(ANSI_RED + "0)" + ANSI_RESET + ANSI_PURPLE + " Home"+ ANSI_RESET);
    }

    public void ledgerChoiceMenu(Scanner scanner, LocalDate date) {
        while (true) {
            ledgerMenu();
            if (!scanner.hasNextLine()) {
                return;
            }
            String input = scanner.nextLine();
            switch (input.toUpperCase()) {
                case "1" -> getAllTransactions();
                case "2" -> getAllDeposits();
                case "3" -> getAllPayments();
                case "4" -> reportsScreen.reportsActionMenu(scanner, date);
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    public void getAllTransactions() {
        printTransactions(transactionService.allTransactions());
    }

    public void getAllDeposits() {
        printTransactions(transactionService.getAllDeposits());
    }

    public void getAllPayments() {
        printTransactions(transactionService.getAllPayments());
    }

    public void printTransactions(List<Transaction> transactions) {
        System.out.printf(
                "%-4s %-12s %-10s %-10s %-20s %-25s %11s%n",
                ANSI_PURPLE, "ID", "Date", "Time", "Type", "Vendor", "Description", "Amount", ANSI_RESET);

        System.out.println(ANSI_YELLOW + "---------------------------------------------------------------------------------------------------------------" + ANSI_RESET);

        transactions.forEach(transaction -> {
            String type = String.valueOf(transaction.getType());
            String color;

            if (type.equalsIgnoreCase("DEPOSIT")) {
                color = ANSI_GREEN;
            } else if (type.equalsIgnoreCase("PAYMENT")) {
                color = ANSI_RED;
            } else {
                color = ANSI_RESET; // no color for unknown types
            }
            System.out.println(color + transaction + ANSI_RESET);

        });
        System.out.println(ANSI_YELLOW + "---------------------------------------------------------------------------------------------------------------" + ANSI_RESET);
    }
}
