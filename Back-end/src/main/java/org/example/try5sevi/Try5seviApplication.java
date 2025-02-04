package org.example.try5sevi;

import cli.TicketConfigManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.net.URI;
import java.util.Scanner;

/**
 * The  class is the entry point for the Zeaus Event Management System application.
 * It provides options for running the application in CLI or GUI mode.
 */
@SpringBootApplication
public class Try5seviApplication {

    /**
     * The main method that starts the Spring Boot application and provides a menu for the user to choose between CLI and GUI interfaces.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String purpleBold = "\u001B[35m\u001B[1m"; // Purple and Bold
        String reset = "\u001B[0m"; // Reset color
        String cyan = "\u001B[36m";

        SpringApplication.run(Try5seviApplication.class, args);
        System.out.println("\n\t\t\t\t" + purpleBold + "Zeaus Event Management System\n\n" + reset);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(cyan + "1. CLI" + reset);
            System.out.println(cyan + "2. GUI" + reset);
            System.out.println(cyan + "3. Exit" + reset);
            System.out.println("Choose an interface:");

            String input = scanner.nextLine(); // Read input as a string
            int choice;

            try {
                choice = Integer.parseInt(input); // Parse input to integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
                continue;
            }

            switch (choice) {
                case 1:
                    runCLI(); // Run CLI logic
                    break;
                case 2:
                    runGUI(); // Run GUI logic
                    break;
                case 3:
                    System.out.println("Exiting program. Goodbye!");
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, or 3.");
                    break;
            }
        }
    }
    /**
     * Runs the CLI-specific logic.
     */

    private static void runCLI() {
        // Run the CLI-specific logic here
        System.out.println("Starting CLI...");
        try {
            TicketConfigManager.main();
        } catch (Exception e) {
            System.out.println("Error running CLI: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Runs the GUI-specific logic.
     */

    private static void runGUI() {
        // Run the GUI-specific logic here
        System.out.println("Starting GUI...");
        openBrowser();
    }
    /**
     * Opens the default web browser to the specified URL.
     */
    private static void openBrowser() {
        try {

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:5173/"));
            } else {
                System.out.println("Desktop browsing not supported. Please open the URL manually: http://localhost:5173/");
            }
        } catch (Exception e) {
            System.out.println("Error opening browser: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
