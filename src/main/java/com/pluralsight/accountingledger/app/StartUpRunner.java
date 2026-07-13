package com.pluralsight.accountingledger.app;

import com.pluralsight.accountingledger.ui.HomeScreen;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class StartUpRunner implements CommandLineRunner {

    private static final DateTimeFormatter DATETIME_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final HomeScreen homeScreen;
    private final Scanner scanner = new Scanner(System.in);

    public StartUpRunner(HomeScreen homeScreen) {
        this.homeScreen = homeScreen;
    }

    @Override
    public void run(String... args) {
        homeScreen.useHomeScreen(
                scanner,
                DATETIME_FMT,
                LocalDate.now()
        );
    }
}
