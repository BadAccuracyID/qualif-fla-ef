package net.slc.ef.fla.qualif.model.person.cook;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.state.TickableState;

public class Cook extends AbstractPerson {

    private final CookFacade cookFacade;
    private TickableState state;

    private int skillLevel;
    private Customer servingCustomer;

    public Cook(Restaurant restaurant, String name) {
        super(restaurant, name);
        this.cookFacade = new CookFacade(this);
    }

    @Override
    public void tick() {

    }

    public CookFacade getCookFacade() {
        return cookFacade;
    }

    public TickableState getState() {
        return state;
    }

    public void setState(TickableState state) {
        this.state = state;
    }

    public Customer getServingCustomer() {
        return servingCustomer;
    }

    public void setServingCustomer(Customer servingCustomer) {
        this.servingCustomer = servingCustomer;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
}
