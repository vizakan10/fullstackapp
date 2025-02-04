package cli;

public class EventBuyer implements Runnable {
    /**
     * Event buyer class to mimic customer in this process
     */

    //variable initialisations and declaration
    private int maxCusTicekts;
    private int consumingTime;
    private TicketBuffer ticketpool;

    public EventBuyer(int maxCusTicekts, int consumingTime, TicketBuffer ticketpool) {
        this.maxCusTicekts = maxCusTicekts;
        this.consumingTime = consumingTime;
        this.ticketpool = ticketpool;
    }

    /**
     * run method to start the buyerthread
     */
    @Override
    public void run() {
        for (int i = 0; i < maxCusTicekts; i++) {
            // Check if exitRequested is true
            if (Threading.exitRequested) {
                break;  // Exit the loop if the process has been stopped
            }

            EventTicket ticket = ticketpool.consumeTicket();


            // Sleep for the specified time before attempting to buy the next ticket
            try {
                Thread.sleep(consumingTime * 1000);  // Sleep for the consumer time interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Handle interruption
                System.out.println("Exception handled during ticket consumption!");
            }
        }
    }
}
