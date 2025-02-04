package cli;

import com.google.gson.*;
import org.example.try5sevi.services.TicketService;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * The  class handles user input for configuring ticket details.
 * It provides methods to get valid inputs from the user, create a {@code TicketConfig} object,
 * and save the configuration to a JSON file.
 */

public class TicketInputManager {
    static Scanner scan = new Scanner(System.in); // Reuse the Scanner instance

    static String yellow = "\u001B[33m"; // Yellow
    static String cyan = "\u001B[36m";  // Cyan
    static String reset = "\u001B[0m";  // Reset color
    static String bul = String.valueOf('\u2022'); // Bullet point
    static String green = "\u001B[32m";
    static String red = "\u001B[31m";

    /**
     * Prompts the user to input the details of the event and creates a object.
     *
     * @return the created {@code TicketConfig} object
     */
    public static TicketConfig getInputs() {
        System.out.println('\u2022' + yellow + "Configure the Details of the Event -" + reset);

        // Event name input
        String eventName = getValidStringInput( '\u2022' + "Enter the event name: ", "Event name cannot be empty!");

        // Ensure event name is not all digits
        while (eventName.chars().allMatch(Character::isDigit)) {
            System.out.println("Event name cannot be numeric. Please enter a valid event name.");
            eventName = getValidStringInput("\n" + '\u2022' + "Enter the event name: ", "Event name cannot be empty!");
        }

        // Ticket price input
        double ticketPrice = getValidDoubleInput('\u2022' + "Enter the ticket price Rs: ", "Ticket price must be a positive number!", 0);

        System.out.println('\u2022' + yellow + "Configure the Details of Vendor and Customer -" + red+"\nNumber of Merchants & Buyers: Minimum '1' and Maximum '200'"+reset);

        // Number of merchants and buyers
        int noOfMerch = getValidIntInputMB('\u2022' + "Enter the total number of merchants of this Event : ", "Number of merchants must be a positive number!", 0);
        int noOfBuyers = getValidIntInputMB('\u2022' + "Enter the total number of buyers of this Event : ", "Number of buyers must be a positive number!", 0);

        System.out.println('\u2022' + yellow + "Configure the Details of the Tickets -" + red+"\nNumber of Buffer size & A Customer or Producer can Consume or Produce: Minimum '1' " +
                "and Maximum '100'"+reset);

        // Total tickets, max tickets per customer, producing and consuming times, max tickets per merchant
        int totalTicket = getValidIntInputBuffer('\u2022' + "Enter the Maximum capacity of the buffer  : ", "maximum must be a positive number!", 0);
        int maxCusTicket = getValidIntInput('\u2022' + "Enter the maximum number of tickets a customer can buy: ", "Max customer tickets must be a positive number!", 0);
        int consumingTime = getValidIntInput('\u2022' + "Enter the consuming time (seconds per ticket): ", "Consuming time must be a positive number!", 0);
        int maxProTicket = getValidIntInput('\u2022' + "Enter the maximum number of tickets a merchant can produce: ", "Max tickets per merchant must be a positive number!", 0);
        int producingTime = getValidIntInput('\u2022' + "Enter the producing time (seconds per ticket): ", "Producing time must be a positive number!", 0);

        // Save config to JSON
        while (true) {
            String choice = getValidStringInput('\u2022' + " Do you want to save the config details (y/n):", "Input only 'y' or 'n'");

            if (choice.equalsIgnoreCase("y")) {

                saveConfigToJson(new TicketConfig(totalTicket, maxCusTicket, producingTime, consumingTime, maxProTicket, eventName, ticketPrice, noOfBuyers, noOfMerch));
                break;
            } else if (choice.equalsIgnoreCase("n")) {
                System.out.println(cyan + "Continuing Without Saving..." + reset);
                break;
            } else {
                System.out.println("Input should be 'y' or 'n'. Please try again.");
            }
        }

        // Validate config before proceeding
        TicketConfig config = new TicketConfig(totalTicket, maxCusTicket, producingTime, consumingTime, maxProTicket, eventName, ticketPrice, noOfBuyers, noOfMerch);
        System.out.println(cyan + "\n" + '\u2022' + "Validating the configurations before starting the MultiThreading.." + reset);


        try {
            config.validateConfig();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println(cyan + "Good to go :)" + reset);

        // Multithreading decision
        String threadper = getValidStringInput("\n" + '\u2022' + "Do you want to start the Multithreading (y/n): ", "Enter valid input");
        if (threadper.equalsIgnoreCase("y")) {
            return config;
        } else {
            System.out.println('\u2022' + "Exiting...");
            System.exit(0);
        }


        return config;
    }

    /**
     * Saves the configuration to a JSON file with a dynamic name based on the current date and time.
     *
     * @param config the {@code TicketConfig} object to be saved
     */
    // Method to save the configuration to a JSON file with a dynamic name based on date-time
    public static void saveConfigToJson(TicketConfig config) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Get current date and time
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = config.getEventName() + timestamp + ".json";

        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(config, writer);
            System.out.println("Details saved to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving to the JSON file: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for a valid string input.
     *
     * @param prompt      the prompt message to display
     * @param errorMessage the error message to display if the input is invalid
     * @return the valid string input
     */

    // Utility methods for user input validation
    public static String getValidStringInput(String prompt, String errorMessage) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scan.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\t" + bul + errorMessage);
            } else {
                break;
            }
        }
        return input;
    }

    /**
     * Prompts the user for a valid integer input.
     *
     * @param prompt      the prompt message to display
     * @param errorMessage the error message to display if the input is invalid
     * @param minValue    the minimum valid value
     * @return the valid integer input
     */
    public static int getValidIntInput(String prompt, String errorMessage, int minValue) {
        int input ;

        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scan.nextLine().trim());

                if (input <= minValue) {
                    System.out.println("\t" + bul + errorMessage);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("\n\t" + bul + "Invalid input! Please enter a valid integer.");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return input;
    }
    /**
     * Prompts the user for a valid integer input with a maximum buffer size restriction.
     *
     * @param prompt      the prompt message to display
     * @param errorMessage the error message to display if the input is invalid
     * @param minValue    the minimum valid value
     * @return the valid integer input
     */
    public static int getValidIntInputBuffer(String prompt, String errorMessage, int minValue) {
        int input ;

        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scan.nextLine().trim());
                if(input>100){
                    System.out.println("\t"+bul+"To make the system efficient Maximum amount Restricted to '100'\n");
                    continue;
                }

                if (input <= minValue) {
                    System.out.println("\n\t" + bul + errorMessage);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("\n\t" + bul + "Invalid input! Please enter a valid integer.\n");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return input;
    }
    /**
     * Prompts the user for a valid integer input with a maximum merchants and buyers restriction.
     *
     * @param prompt      the prompt message to display
     * @param errorMessage the error message to display if the input is invalid
     * @param minValue    the minimum valid value
     * @return the valid integer input
     */

    public static int getValidIntInputMB(String prompt, String errorMessage, int minValue) {
        int input ;

        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scan.nextLine().trim());
                if(input>200){
                    System.out.println("\t"+bul+"To make the system efficient Maximum amount Restricted to '200'\n");
                    continue;
                }

                if (input <= minValue) {
                    System.out.println("\t" + bul + errorMessage);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("\n\t" + bul + "Invalid input! Please enter a valid integer.");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return input;
    }

    /**
     * Prompts the user for a valid double input.
     *
     * @param prompt      the prompt message to display
     * @param errorMessage the error message to display if the input is invalid
     * @param minValue    the minimum valid value
     * @return the valid double input
     */
    public static double getValidDoubleInput(String prompt, String errorMessage, double minValue) {
        double input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Double.parseDouble(scan.nextLine().trim());
                if (input <= minValue) {
                    System.out.println("\t" + bul + errorMessage);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("\n\t" + bul + "Invalid input! Please enter a valid number.");

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return input;
    }
}
