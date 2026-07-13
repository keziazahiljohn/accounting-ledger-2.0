package com.pluralsight.accountingledger.ui;

import com.pluralsight.accountingledger.model.Transaction;
import com.pluralsight.accountingledger.service.TransactionService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class ReportsScreen {
    private final TransactionService transactionService;

    public ReportsScreen(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void reportsMenu() {
        System.out.println("Reports");
        System.out.println("Choose an option:");
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("6) Custom Search");
        System.out.println("0) Back");
    }

    public void reportsActionMenu(Scanner scanner, LocalDate date) {
        while (true) {
            reportsMenu();
            if (!scanner.hasNextLine()) {
                return;
            }
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.println("Month to Date Report:");

                    LocalDate startofMonth = date.withDayOfMonth(1);
                    printTransactions(transactionService.searchByDateRange(startofMonth, date));
                }
                case "2" -> {
                    System.out.println("Previous Month Report");

                    LocalDate firstPreviousMonth = date.minusMonths(1).withDayOfMonth(1);
                    LocalDate lastPreviousMonth = date.withDayOfMonth(1).minusDays(1);

                    printTransactions(transactionService.searchByDateRange(firstPreviousMonth, lastPreviousMonth));
                }
                case "3" -> {
                    System.out.println("Year to Date Report:");

                    LocalDate startOfYear = date.withDayOfYear(1);

                    printTransactions(transactionService.searchByDateRange(startOfYear, date));
                }
                case "4" -> {
                    System.out.println("Previous Year Report:");

                    LocalDate firstOfPreviousYear = date.minusYears(1).withDayOfYear(1);
                    LocalDate lastOfLastYear = date.withDayOfYear(1).minusDays(1);

                    printTransactions(transactionService.searchByDateRange(firstOfPreviousYear, lastOfLastYear));
                }
                case "5" -> {
                    System.out.println("Search Vendor:");
                    if (!scanner.hasNextLine()) {
                        return;
                    }
                    String vendor = scanner.nextLine().trim();
                    if (vendor.isEmpty()) {
                        System.out.println("Vendor is required.");
                        break;
                    }
                    String formatted = vendor.substring(0, 1).toUpperCase() + vendor.substring(1);

                    System.out.println(formatted + " Vendor Report:");
                    printTransactions(transactionService.findByVendor(formatted));
                }
                case "6" -> System.out.println("place holder");
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void printTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.printf(
                "%-4s %-12s %-10s %-10s %-20s %-25s %11s%n",
                "ID", "Date", "Time", "Type", "Vendor", "Description", "Amount");

        System.out.println("---------------------------------------------------------------------------------------------------------------");

        transactions.forEach(System.out::println);
    }
}


