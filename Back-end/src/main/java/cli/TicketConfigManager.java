package cli;

import org.example.try5sevi.services.TicketService;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * The  class manages the configuration of tickets for an event.
 * It provides a command-line interface (CLI) for users to upload a JSON file or input configuration details manually.
 * The class also handles the threading to simulate the ticketing process.
 */

public class TicketConfigManager {
    static String bul = String.valueOf('\u2022');
    static Scanner scan = new Scanner(System.in);
    private static boolean exitRequested = false;

    /**
     * The main method that starts the ticket configuration process.
     */

    public static void main() {
        heading();
    }

    /**
     * Displays the heading and menu options for the CLI.
     * Handles user input for configuring ticket details.
     */
    static void heading() {
        String purpleBold = "\u001B[35m\u001B[1m"; // Purple and Bold
        String yellow = "\u001B[33m"; // Yellow
        String reset = "\u001B[0m"; // Reset color
        String cyan = "\u001B[36m";
        String green = "\u001B[32m";

        LocalDate date = LocalDate.now();

        System.out.println("\n\t\t\t\t" + purpleBold + "Zeaus Event Management System\n\n"
                + green + "Continuing in CLI ......\n" + reset
                + "\n" + cyan + "Date : " + date.toString() + reset);

        while (!exitRequested) {
            // Get user input for the menu
            String op = TicketInputManager.getValidStringInput(
                    "\nTo configure the details\n\t1.Upload json file\n\t2.Input configuration\n\t3.To exit\nEnter the index:",
                    "Please enter a valid option (1, 2, or 3):");

            if (op.equals("empty")) {
                continue; // Skip if the user entered "empty"
            }

            // Convert input to an integer
            int option;
            try {
                option = Integer.parseInt(op);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input, please enter a valid number.");
                continue; // Skip if the input is not a valid number
            }

            TicketConfig config = null;
            switch (option) {
                case 1:
                    try {
                        System.out.println("Enter the JsonFile path (Absolute path is good) to upload:");
                        String jsonName = scan.nextLine();
                        config = JsonInputManager.readJsonConfig(jsonName);

                        if (config == null) {
                            System.out.println("Error: Invalid configuration data received. Please try again.");
                            continue; // Skip if the config is null
                        }
                        System.out.println(purpleBold + "\t__Threading Started to Stimulate !!!__" + reset);
                        Threading.beginThread(config);
                    } catch (Exception e) {
                        System.err.println("Error uploading the JSON file. Please check the file path and format.");
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    config = TicketInputManager.getInputs();

                    System.out.println(purpleBold + "\t__Threading Started to Stimulate !!!__" + reset);
                    Threading.beginThread(config);
                    break;

                case 3:
                    System.out.println("Exiting....\nThank you ");
                    exitRequested = true; // Set the flag to exit
                    exit();
                    break;

                default:
                    System.out.println("Invalid option!!! Please enter a valid option.");
            }
        }
    }

    /**
     * Exits the program.
     */
    private static void exit() {
        System.out.println("Exiting program.");

    }
}
