package org.example.try5sevi.services;

import cli.EventTicket;
import cli.Threading;
import cli.TicketConfig;
import org.example.try5sevi.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private TicketRepository ticketRepository;



    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * Start the ticket configuration.
     *
     * @param ticketConfigData the ticket configuration data to start
     */
    public void startTicketConfig(TicketConfig ticketConfigData) {
        ticketRepository.save(ticketConfigData);
        System.out.println("Data Saved successfully");
    }

    /**
     * Stop the ticket configuration.
     *
     * @param msg the message to stop the ticket configuration
     * @return the response from the threading stop method
     */
    public String stopTicketConfig(String msg) {
        String text =Threading.stopThreading(msg);
        return (text);
    }

    /**
     * Get all ticket configurations.
     *
     * @return a list of ticket configurations
     */
    public List<TicketConfig> getTicketConfig() {
        // Fetch all ticket configuration data from the repository
        List<TicketConfig> ticketConfigs = ticketRepository.findAll();

        // Iterate through each ticket configuration and calculate required variables
        for (TicketConfig ticketConfig : ticketConfigs) {
            // Extract necessary fields
            int noOfBuyers = ticketConfig.getNoOfBuyers();
            int maxCusTicket = ticketConfig.getMaxCusTicket();
            int maxProTicket = ticketConfig.getMaxProTicket();

            // Calculate the number of producers (just an assumption for this example)
            int noOfProducers = noOfBuyers / maxCusTicket; // Simple logic, can be adjusted
            int totalProducedTickets = noOfProducers * maxProTicket;

        }

        // Return the list of ticket configurations
        return ticketConfigs;
    }

    /**
     * Send data.
     *
     * @return the ticket counter value minus one
     */
    public int sendData() {
        EventTicket t1 = new EventTicket();

        return ((t1.getTicketCounter())-1);
    }

    /**
     * Get the price.
     *
     * @return the total price based on the ticket counter and ticket price
     */
    public double getPrice() {
        EventTicket t1 = new EventTicket();
        int price = (int) (((t1.getTicketCounter()) - 1) * t1.getTicketPrice());
        return price;
    }
}
