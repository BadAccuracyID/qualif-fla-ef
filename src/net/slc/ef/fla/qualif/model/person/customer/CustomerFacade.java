package net.slc.ef.fla.qualif.model.person.customer;

import net.slc.ef.fla.qualif.state.TickableState;

public class CustomerFacade {

    private final Customer customer;

    public CustomerFacade(Customer customer) {
        this.customer = customer;
    }

    public void decreaseTolerance() {
        this.customer.setTolerance(this.customer.getTolerance() - 1);
    }

    public void increaseTolerance() {
        this.customer.setTolerance(this.customer.getTolerance() + 1);
    }

    public void switchState(TickableState state) {
        this.customer.getState().onExit();

        this.customer.setState(state);
        this.customer.getState().onEnter();
    }
}
