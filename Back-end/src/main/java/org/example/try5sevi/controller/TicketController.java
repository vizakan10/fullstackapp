package org.example.try5sevi.controller;

import cli.Threading;
import cli.TicketConfig;
import org.example.try5sevi.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/zeus")
public class TicketController {

    private final TicketService ticketService;
    private final Map<String, TicketConfig> realTimeData = new HashMap<>(); // Store real-time data for updates

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Stop the ticket configuration.
     *
     * @param payload the request payload containing the message
     * @return the response from the ticket service
     */
    @PostMapping("/stop")
    public String stop(@RequestBody Map<String, String> payload) {
        try {
            String msg = payload.get("msg");
            String response = ticketService.stopTicketConfig(msg);
            return response;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "Error processing the stop request!";
        }
    }

    /**
     * Get all ticket configurations.
     *
     * @return a list of ticket configurations
     */
    @GetMapping("/getChart")
    public List<TicketConfig> getTicketConfig() {
        return ticketService.getTicketConfig();
    }

    /**
     * Receive ticket configuration data.
     *
     * @param payload the request payload containing ticket configuration data
     * @return a success message if data is processed successfully
     */
    @PostMapping("/data")
    public String receiveData(@RequestBody Map<String, String> payload) {
        try {
            // Extract and convert each field from the payload
            int totaltickets = Integer.parseInt(payload.get("totaltickets"));
            int maxcus = Integer.parseInt(payload.get("maxcus"));
            int protime = Integer.parseInt(payload.get("protime"));
            int contime = Integer.parseInt(payload.get("contime"));
            String eventname = payload.get("eventname");
            double ticketprice = Double.parseDouble(payload.get("ticketprice"));
            int maxpro = Integer.parseInt(payload.get("maxpro"));
            int buyercount = Integer.parseInt(payload.get("buyercount"));
            int merchantcount = Integer.parseInt(payload.get("merchantcount"));

            // Create TicketConfig with the parsed values
            TicketConfig config = new TicketConfig(totaltickets, maxcus, protime, contime, maxpro, eventname, ticketprice, buyercount, merchantcount);

            ticketService.startTicketConfig(config);
            Threading.beginThread(config);

            return "Data received and processed successfully!";
        } catch (NumberFormatException e) {
            // Handle errors for invalid number formats
            System.out.println("Error: " + e.getMessage());
            return "Invalid data format received!";
        }
    }

    /**
     * Get data and price.
     *
     * @return a map containing data and price
     */
    @GetMapping("/getdataprice")
    public Map<String, String> getDataAndPrice() {
        Map<String, String> response = new HashMap<>();
        response.put("data", String.valueOf(ticketService.sendData()));
        response.put("price", String.valueOf(ticketService.getPrice()));
        return response;
    }







}
