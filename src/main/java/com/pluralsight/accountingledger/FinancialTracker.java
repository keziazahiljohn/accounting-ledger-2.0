package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/*
 * Capstone skeleton – personal finance tracker.
 * ------------------------------------------------
 * File format  (pipe-delimited)
 *     yyyy-MM-dd|HH:mm:ss|description|vendor|amount
 * A deposit has a positive amount; a payment is stored
 * as a negative amount.
 */
public class FinancialTracker {

    /* ------------------------------------------------------------------
       Shared data and formatters
       ------------------------------------------------------------------ */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    /* ------------------------------------------------------------------
       Main menu
       ------------------------------------------------------------------ */
    public static void main(String[] args) {
        loadTransactions(FILE_NAME);

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addDeposit(scanner);
                case "P" -> addPayment(scanner);
                case "L" -> ledgerMenu(scanner);
                case "X" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
        scanner.close();
    }

    /* ------------------------------------------------------------------
       File I/O
       ------------------------------------------------------------------ */

    /**
     * Load transactions from FILE_NAME.
     * • If the file doesn’t exist, create an empty one so that future writes succeed.
     * • Each line looks like: date|time|description|vendor|amount
     */
    public static void loadTransactions(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                LocalDate date = LocalDate.parse(tokens[0], DATE_FMT);
                LocalTime time = LocalTime.parse(tokens[1], TIME_FMT);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactions.add(transaction);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    /* ------------------------------------------------------------------
       Add new transactions
       ------------------------------------------------------------------ */

    /**
     * Prompt for ONE date+time string in the format
     * "yyyy-MM-dd HH:mm:ss", plus description, vendor, amount.
     * Validate that the amount entered is positive.
     * Store the amount as-is (positive) and append to the file.
     */
    private static void addDeposit(Scanner scanner) {
        System.out.println("Enter date and time (yyyy-MM-dd HH:mm:ss):");
        String dateTimeInput = scanner.nextLine();
        LocalTime time;
        LocalDate date;
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, DATETIME_FMT);
            date = dateTime.toLocalDate();
            time = dateTime.toLocalTime();
        } catch (Exception e) {
            System.out.println("Invalid date format, try again.");
            return;
        }


        System.out.println("Description of transaction:");
        String description = scanner.nextLine();

        System.out.println("Enter the Vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter Amount:");
        double amount = Math.abs(scanner.nextDouble()); // handle negative
        scanner.nextLine();

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        transactions.add(transaction);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(String.format("%s|%s|%s|%s|%.2f",
                    date, time, description, vendor, amount));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    /**
     * Same prompts as addDeposit.
     * Amount must be entered as a positive number,
     * then converted to a negative amount before storing.
     */

    private static void addPayment(Scanner scanner) {
        System.out.println("Enter date and time (yyyy-MM-dd HH:mm:ss):");
        String dateTimeInput = scanner.nextLine();
        LocalTime time;
        LocalDate date;
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, DATETIME_FMT);
            date = dateTime.toLocalDate();
            time = dateTime.toLocalTime();
        } catch (Exception e) {
            System.out.println("Invalid format, try again.");
            return;
        }

        System.out.println("Description of transaction:");
        String description = scanner.nextLine();

        System.out.println("Enter the Vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter Amount:");
        double amount = scanner.nextDouble() * -1;
        scanner.nextLine();

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        transactions.add(transaction);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(String.format("%s|%s|%s|%s|%.2f",
                    date, time, description, vendor, amount));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> displayLedger();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> reportsMenu(scanner);
                case "H" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Display helpers: show data in neat columns
       ------------------------------------------------------------------ */
    private static void displayLedger() {
        transactions.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        System.out.printf("%-12s %-10s %-20s %-15s %10s%n", "date", "time", "description", "vendor", "amount");
        System.out.println("----------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            System.out.printf("%-12s %-10s %-20s %-15s $%8.2f%n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());
        }
    }

    private static void displayDeposits() {
        transactions.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        System.out.println("Deposits:");
        System.out.printf("%-12s %-10s %-20s %-15s %10s%n", "date", "time", "description", "vendor", "amount");
        System.out.println("----------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.printf("%-12s %-10s %-20s %-15s $%8.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

    private static void displayPayments() {
        transactions.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        System.out.println("Payments:");
        System.out.printf("%-12s %-10s %-20s %-15s %10s%n", "date", "time", "description", "vendor", "amount");
        System.out.println("----------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.printf("%-12s %-10s %-20s %-15s $%8.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            LocalDate today = LocalDate.now();
            LocalDate firstDayOfMonth = today.withDayOfMonth(1);
            LocalDate firstOfTheYear = LocalDate.ofYearDay(LocalDate.now().getYear(), 1);

            switch (input) {
                case "1" -> {
                    System.out.println("Month to Date Report:");

                    LocalDate startofMonth = today.withDayOfMonth(1);
                    filterTransactionsByDate(startofMonth, today);
                }
                case "2" -> {
                    System.out.println("Previous Month Report");

                    LocalDate firstPreviousMonth = firstDayOfMonth.minusMonths(1);
                    LocalDate lastPreviousMonth = firstDayOfMonth.minusDays(1);
                    filterTransactionsByDate(firstPreviousMonth, lastPreviousMonth);
                }
                case "3" -> {
                    System.out.println("Year to Date Report:");

                    filterTransactionsByDate(firstOfTheYear, today);
                }
                case "4" -> {
                    LocalDate firstOfPreviousYear = firstOfTheYear.minusYears(1);
                    LocalDate lastOfLastYear = firstOfTheYear.minusDays(1);

                    filterTransactionsByDate(firstOfPreviousYear, lastOfLastYear);
                }
                case "5" -> {
                    System.out.println("Search Vendor:");
                    String vendor = scanner.nextLine();
                    String formatted = vendor.substring(0, 1).toUpperCase() + vendor.substring(1);

                    System.out.println(formatted + " Vendor Report:");
                    filterTransactionsByVendor(vendor);
                }
                case "6" -> customSearch(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */
    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {
        transactions.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        System.out.printf("%-12s %-10s %-20s %-15s %10s%n", "date", "time", "description", "vendor", "amount");
        System.out.println("----------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            if (!transaction.getDate().isBefore(start) && !transaction.getDate().isAfter(end)) {
                System.out.printf("%-12s %-10s %-20s %-15s $%8.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

    private static void filterTransactionsByVendor(String vendor) {
        transactions.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        System.out.printf("%-12s %-10s %-20s %-15s %10s%n", "date", "time", "description", "vendor", "amount");
        System.out.println("----------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                System.out.printf("%-12s %-10s %-20s %-15s $%8.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

    private static void customSearch(Scanner scanner) {
        transactions.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        System.out.println("Custom Search:");
        System.out.println("Enter Start Date (" + DATE_PATTERN + "):");
        String startDateInput = scanner.nextLine();
        LocalDate startDate = startDateInput.isEmpty() ? null : parseDate(startDateInput);

        System.out.println("Enter End Date:");
        String endDateInput = scanner.nextLine();
        LocalDate endDate = endDateInput.isEmpty() ? null : parseDate(endDateInput);

        System.out.println("Enter Description:");
        String descriptionInput = scanner.nextLine();

        System.out.println("Enter Vendor:");
        String vendorInput = scanner.nextLine();

        System.out.println("Enter Amount:");
        String amountInput = scanner.nextLine();
        Double amount = amountInput.isEmpty() ? null : Math.abs(parseDouble(amountInput));

        boolean found = false;

        System.out.printf("%-12s %-10s %-20s %-15s %10s%n", "date", "time", "description", "vendor", "amount");
        System.out.println("----------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            boolean match = true;

            if (startDate != null && transaction.getDate().isBefore(startDate)) {
                match = false;
            }

            if (endDate != null && transaction.getDate().isAfter(endDate)) {
                match = false;
            }

            if (!descriptionInput.isEmpty() && !transaction.getDescription().equalsIgnoreCase(descriptionInput)) {
                match = false;
            }

            if (!vendorInput.isEmpty() && !transaction.getVendor().equalsIgnoreCase(vendorInput)) {
                match = false;
            }

            if (amount != null && !(amount == transaction.getAmount())) {
                match = false;
            }

            if (match) {
                System.out.printf("%-12s %-10s %-20s %-15s $%8.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Nothing found.\n");
            return;
        }
    }

    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
    private static LocalDate parseDate(String s) {
        try {
            return LocalDate.parse(s, DATE_FMT);
        } catch (Exception e) {
            return null;
        }
    }

    private static Double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }
}
