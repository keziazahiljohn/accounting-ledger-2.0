package com.pluralsight.accountingledger.ui;

import java.util.Scanner;

public class HomeScreen {

    //Make methods that print out how screen interface and are interactive on the home screen
    private static Scanner scanner;

    public void printHomeScreen(){
        System.out.println("Welcome to TransactionApp");
        System.out.println("Choose an option:");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
    }

    public void useHomeScreen(){
        while(true){
            printHomeScreen();
            String input = scanner.nextLine();
            switch (input.toUpperCase()){
                case "D" -> System.out.println();
                case "P" -> System.out.println();
                case "L" -> System.out.println();
                case "X" -> {
                    return;
                }
                default -> System.out.println("Invalid input please try again");
            }
        }
    }
}
