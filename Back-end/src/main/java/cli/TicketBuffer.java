package cli;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketBuffer {

    private Queue<EventTicket> ticketpool;
    private int totalTickets;

    private final Lock lock = new ReentrantLock();
    private final Condition qFull = lock.newCondition();
    private final Condition qEmpty = lock.newCondition();

    private int totalProduced = 0;
    private int totalConsumed = 0;

    /**
     * Constructor to initialize the TicketBuffer with a specified total number of tickets.
     *
     * @param totalTickets the total number of tickets that can be produced
     */
    public TicketBuffer(int totalTickets) {
        this.totalTickets = totalTickets;
        this.ticketpool = new LinkedList<>();
    }

    /**
     * Produce a ticket and add it to the buffer.
     *
     * @param ticket the ticket to be produced
     */

    public void produceTicket(EventTicket ticket) {
        lock.lock();
        try {
            while (ticketpool.size() >= totalTickets) {
                System.out.println("Buffer is full, please wait until consumer buys a ticket!");
                qFull.await();
            }
            ticketpool.add(ticket);
            totalProduced++;
            System.out.println(TicketInputManager.yellow + "Ticket produced by - " + Thread.currentThread().getName() + TicketInputManager.reset +
                    "\tAvailable Tickets count: " + ticketpool.size());
            qEmpty.signalAll();  // Signal one consumer
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Handle thread interruption gracefully
            System.out.println("Exception handled during ticket production!");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Consume a ticket from the buffer.
     *
     * @return the consumed ticket
     */

    public EventTicket consumeTicket() {
        lock.lock();

        try {
            while (ticketpool.isEmpty()) {
                System.out.println("Buffer is empty (No tickets), please wait until a merchant produces tickets!");
                qEmpty.await();
            }

            EventTicket ticket = ticketpool.poll();
            totalConsumed++;
            System.out.println(TicketInputManager.green + "Ticket bought by - " + Thread.currentThread().getName() + TicketInputManager.reset +
                    "\tAvailable Tickets count: " + ticketpool.size() + " Bought ticket ID: " + ticket.getTicketUID());
            qFull.signalAll();  // Signal one producer
            return ticket;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Handle thread interruption gracefully
            System.out.println("Exception handled during ticket consumption!");
        } finally {
            lock.unlock();
        }

        return null;
    }

    public boolean allTicketsConsumed() {
        return totalProduced == totalConsumed;//Check if all tickets have been consumed.
    }

    public int getTotalProduced() {
        return totalProduced;// Get the total number of tickets produced.
    }

    public int getTotalConsumed() {
        return totalConsumed;//Get the total number of tickets consumed.
    }

    public int getTotalTickets() {
        return totalTickets;//Get the total number of tickets.
    }


}
