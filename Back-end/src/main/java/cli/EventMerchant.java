package cli;

public class EventMerchant implements Runnable {
    /**
     * EventMerchant class to mimic vendor in this process
     */
    private int maxProTicket;
    private int producingTime;
    private TicketBuffer ticketpool;
    private String eventName;
    private double price;

    public EventMerchant(int maxProTicket, TicketBuffer ticketpool, int producingTime, String eventName, double price) {
        this.maxProTicket = maxProTicket;
        this.ticketpool = ticketpool;
        this.producingTime = producingTime;
        this.eventName = eventName;
        this.price = price;
    }

    /**
     * Run method to start the customer thread
     */
    @Override
    public void run() {
        for (int i = 0; i < maxProTicket; i++) {
            // Check if the exitRequested flag is true, meaning we should stop producing tickets
            if (Threading.exitRequested) {
                break;
            }

            // Create a new ticket
            EventTicket ticket = new EventTicket(eventName, price);

            // Produce the ticket and add it to the buffer
            ticketpool.produceTicket(ticket);

            try {
                // Sleep for the specified producing time before creating the next ticket
                Thread.sleep(producingTime * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Handle interruption gracefully
                System.out.println("Exception handled during ticket production!");
            }
        }
    }
}
