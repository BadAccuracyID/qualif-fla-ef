package net.slc.ef.fla.qualif.model.restaurant.chair;

import net.slc.ef.fla.qualif.model.person.customer.Customer;

public class Chair {

    private final int number;
    private ChairStatus status;
    private Customer customer;

    public Chair(int number) {
        this.number = number;
        this.status = ChairStatus.AVAILABLE;
    }

    public int getNumber() {
        return number;
    }

    public ChairStatus getStatus() {
        return status;
    }

    public void setStatus(ChairStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
