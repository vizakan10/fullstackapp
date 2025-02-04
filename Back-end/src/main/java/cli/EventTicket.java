package cli;

public class EventTicket {

    private static int ticketCounter = 1; // counter for ticket UID
    private String eventName;
    private String ticketUID;
    private static double ticketPrice;

    /**
     * Constructor to initialize an EventTicket with the specified event name and ticket price.
     *
     * @param eventName the name of the event
     * @param ticketPrice the price of the ticket
     */
    public EventTicket(String eventName, double ticketPrice) {
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;


    }

    /**
     * Default constructor for EventTicket.
     */

    public EventTicket() {
    }

    /**
     * Get the current ticket counter value.
     *
     * @return the current ticket counter value
     */
    public static int getTicketCounter() {
        return ticketCounter;//
    }

    /**
     * Get the name of the event.
     *
     * @return the event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Get the unique ticket ID.
     *
     * @return the unique ticket ID
     */
    public String getTicketUID() {
        ticketUID= eventName+"#"+ticketCounter++;
    return ticketUID;
    }


    /**
     * Get the price of the ticket.
     *
     * @return the ticket price
     */
    public static double getTicketPrice() {
        return ticketPrice;
    }



}
