package cli;

public class TicketConfig {
    private int totalTicket;
    private int maxCusTicket;
    private int producingTime;
    private int consumingTime;
    private int maxProTicket;
    private String eventName;
    private double ticketPrice;
    private int noOfBuyers;
    private int noOfMerch;

    /**
     * Constructs a new {@code TicketConfig} object with the specified parameters.
     * Validates the configuration values upon creation.
     *
     * @param totalTicket   the total number of tickets
     * @param maxCusTicket  the maximum number of tickets per customer
     * @param producingTime the time taken to produce tickets
     * @param consumingTime the time taken to consume tickets
     * @param maxProTicket  the maximum number of tickets per merchant
     * @param eventName     the name of the event
     * @param ticketPrice   the price of each ticket
     * @param noOfBuyers    the number of buyers
     * @param noOfMerch     the number of merchants
     * @throws IllegalArgumentException if any of the configuration values are invalid
     */
    public TicketConfig(int totalTicket, int maxCusTicket, int producingTime, int consumingTime,
                        int maxProTicket, String eventName, double ticketPrice, int noOfBuyers, int noOfMerch) {
        this.totalTicket = totalTicket;
        this.maxCusTicket = maxCusTicket;
        this.producingTime = producingTime;
        this.consumingTime = consumingTime;
        this.maxProTicket = maxProTicket;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.noOfBuyers = noOfBuyers;
        this.noOfMerch = noOfMerch;

        // Validate configuration when the object is created
        validateConfig();
    }

    // Getter methods
    public int getTotalTicket() {
        return totalTicket;
    }
    public int getMaxCusTicket() {
        return maxCusTicket;
    }
    public int getProducingTime() {
        return producingTime;
    }
    public int getConsumingTime() {
        return consumingTime;
    }
    public int getNoOfBuyers() {
        return noOfBuyers;
    }
    public int getMaxProTicket() {
        return maxProTicket;
    }
    public String getEventName() {
        return eventName;
    }
    public double getTicketPrice() {
        return ticketPrice;
    }
    public int getNoOfMerch() {
        return noOfMerch;
    }

    public void setTotalTicket(int totalTicket) {
        this.totalTicket = totalTicket;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setMaxProTicket(int maxProTicket) {
        this.maxProTicket = maxProTicket;
    }

    public void setConsumingTime(int consumingTime) {
        this.consumingTime = consumingTime;
    }

    public void setProducingTime(int producingTime) {
        this.producingTime = producingTime;
    }

    public void setMaxCusTicket(int maxCusTicket) {
        this.maxCusTicket = maxCusTicket;
    }

    public void setNoOfBuyers(int noOfBuyers) {
        this.noOfBuyers = noOfBuyers;
    }

    public void setNoOfMerch(int noOfMerch) {
        this.noOfMerch = noOfMerch;
    }

    /**
     * Validates the configuration values to ensure they are valid.
     *
     * @throws IllegalArgumentException if any of the configuration values are invalid
     */
    public void validateConfig() {
        // Checking for null or empty event name
        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be null or empty");
        }

        // Checking if ticket price and other fields are positive
        if (ticketPrice <= 0) {
            throw new IllegalArgumentException("Ticket price must be a positive number");
        }
        if (noOfMerch <= 0) {
            throw new IllegalArgumentException("Number of merchants must be a positive number");
        }
        if (noOfBuyers <= 0) {
            throw new IllegalArgumentException("Number of buyers must be a positive number");
        }
        if (totalTicket <= 0) {
            throw new IllegalArgumentException("Total tickets must be a positive number");
        }
        if (maxCusTicket <= 0) {
            throw new IllegalArgumentException("Maximum tickets per customer must be a positive number");
        }
        if (consumingTime <= 0) {
            throw new IllegalArgumentException("Consuming time must be a positive number");
        }
        if (maxProTicket <= 0) {
            throw new IllegalArgumentException("Max tickets per merchant must be a positive number");
        }
        if (producingTime <= 0) {
            throw new IllegalArgumentException("Producing time must be a positive number");
        }
    }


    @Override
    public String toString() {
        return "TicketConfig{" +
                "totalTicket=" + totalTicket +
                ", maxCusTicket=" + maxCusTicket +
                ", producingTime=" + producingTime +
                ", consumingTime=" + consumingTime +
                ", maxProTicket=" + maxProTicket +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", noOfBuyers=" + noOfBuyers +
                ", noOfMerch=" + noOfMerch +
                '}';
    }


}
