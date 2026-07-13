package com.pluralsight.accountingledger.ui;

import com.pluralsight.accountingledger.model.Transaction;
import com.pluralsight.accountingledger.service.TransactionService;

import java.util.Scanner;

public class LedgerScreen {
    //Make methods that print out how screen interface and are interactive on the ledger screen
    private static TransactionService transactionService;
    private static Scanner scanner;

    public void ledgerMenu(){
        System.out.println("Ledger");
        System.out.println("Choose an option:");
        System.out.println("A) All");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");
    }

    public void ledgerChoiceMenu(){
        ledgerMenu();
        String input = scanner.nextLine();
        switch (input.toUpperCase()) {
            case "A" -> getAllTransactions();
            case "D" -> displayDeposits();
            case "P" -> displayPayments();
            case "R" -> reportsMenu(scanner);
            case "H" -> running = false;
            default -> System.out.println("Invalid option");
        }
    }

    public void getAllTransactions(){
        System.out.println(transactionService.allTransactions());
    }

    public void getAllDeposits(){
        System.out.println(transactionService.getAllDeposits());
    }

    public void getAllPayments(){
        System.out.println(transactionService.getAllPayments());
    }

}
