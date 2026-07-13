package com.pluralsight.accountingledger.ui;

import com.pluralsight.accountingledger.model.Transaction;
import com.pluralsight.accountingledger.service.TransactionService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class LedgerScreen {

    private final TransactionService transactionService;
    private final ReportsScreen reportsScreen;

    public LedgerScreen(TransactionService transactionService,
                        ReportsScreen reportsScreen) {
        this.transactionService = transactionService;
        this.reportsScreen = reportsScreen;
    }

    public void ledgerMenu(){
        System.out.println("Ledger");
        System.out.println("Choose an option:");
        System.out.println("1) All");
        System.out.println("2) Deposits");
        System.out.println("3) Payments");
        System.out.println("4) Reports");
        System.out.println("0) Home");
    }

    public void ledgerChoiceMenu(Scanner scanner, LocalDate date){
        while(true) {
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

    public void getAllTransactions(){
        printTransactions(transactionService.allTransactions());
    }

    public void getAllDeposits(){
        printTransactions(transactionService.getAllDeposits());
    }

    public void getAllPayments(){
        printTransactions(transactionService.getAllPayments());
    }

    public void printTransactions(List<Transaction> transactions) {

        System.out.printf(
                "%-4s %-12s %-10s %-10s %-20s %-25s %11s%n",
                "ID", "Date", "Time", "Type", "Vendor", "Description", "Amount");

        System.out.println("---------------------------------------------------------------------------------------------------------------");

        transactions.forEach(System.out::println);
    }

}
