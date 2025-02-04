package cli;

import java.util.Scanner;
import java.util.logging.*;

public class Threading {

    static String bul = String.valueOf('\u2022'); // Bullet point
    static volatile boolean exitRequested = false; // volatile flag to be checked by all threads
    static String red = "\u001B[31m";
    static String reset = "\u001B[0m";

    private static final Logger logger = Logger.getLogger(Threading.class.getName()); // Logger initialization

    /**
     * Begin the threading process with the given ticket configuration.
     *
     * @param config the ticket configuration to use
     */
    public static void beginThread(TicketConfig config) {
        // Initialize TicketBuffer and other entities
        TicketBuffer ticketPool = new TicketBuffer(config.getTotalTicket());
        System.out.println("\n" + bul + "Number of Merchants: " + config.getNoOfMerch());
        System.out.println(bul + "Number of Buyers: " + config.getNoOfBuyers());
        System.out.println(bul + "Total Number of Tickets assigned for the event :" + (config.getMaxProTicket() * config.getNoOfMerch() + "\n"));
        System.out.print("\n" + red + bul + "Enter 'stop' to terminate the process: \n" + reset);

        EventMerchant[] merch = new EventMerchant[config.getNoOfMerch()];
        EventBuyer[] buyer = new EventBuyer[config.getNoOfBuyers()];

        // Create and start Merchant Threads
        Thread[] merchantThreads = new Thread[merch.length];
        for (int i = 0; i < merch.length; i++) {
            merch[i] = new EventMerchant(config.getMaxProTicket(), ticketPool, config.getProducingTime(), config.getEventName(), config.getTicketPrice());
            merchantThreads[i] = new Thread(merch[i], "Merchant ID : M#" + (i + 1));
            merchantThreads[i].start();  // Start each thread concurrently
        }

        // Create and start Buyer Threads
        Thread[] buyerThreads = new Thread[buyer.length];
        for (int i = 0; i < buyer.length; i++) {
            buyer[i] = new EventBuyer(config.getMaxCusTicket(), config.getConsumingTime(), ticketPool);
            buyerThreads[i] = new Thread(buyer[i], "Buyer ID  :  B#" + (i + 1));
            buyerThreads[i].start();  // Start each thread concurrently
        }

        // Start the thread for taking user input (e.g., to stop the process)
        Thread inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                stopThreading(input);  // Call stopThreading method to handle stopping logic
            }
        });
        inputThread.start();

        // Main thread waits for all threads (merchants and buyers) to finish
        try {
            // Wait for all merchant threads to finish
            for (Thread thread : merchantThreads) {
                thread.join();
            }

            // Wait for all buyer threads to finish
            for (Thread thread : buyerThreads) {
                thread.join();
            }

            // Check if all tickets are consumed and produced
            if (ticketPool.allTicketsConsumed()) {
                System.out.println(red + bul + "All tickets have been produced and consumed." + reset);
            } else {
                System.out.println(red + bul + "Tickets still available." + reset);
            }

            // Once all threads are finished, show total earnings
            System.out.println(red + bul + "All transactions completed." + reset);
            System.out.println("\t" + bul + "Total Tickets Produced: " + ticketPool.getTotalProduced());
            System.out.println("\t" + bul + "Total Tickets Consumed: " + ticketPool.getTotalConsumed());
            System.out.println("\t" + bul + "Total Earnings Rs: " + ((int) (ticketPool.getTotalConsumed() * config.getTicketPrice())));

            // When stop is entered, do not ask for configuration options anymore
            if (exitRequested) {
                System.out.println("Exiting. Please restart the program to configure details.");
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }

    // Method to handle stopping the threads and the program
    public static String stopThreading(String msg) {
        Scanner scanner = new Scanner(System.in);
        while (!exitRequested) {

            if ("stop".equalsIgnoreCase(msg)) {
                exitRequested = true;
                System.out.println(red+"Threading stopped...\n\t" + bul + "Restart the Programme !!!"+reset);
                System.exit(0);
                break;
            }
        }

        return "Stop";
    }

}
