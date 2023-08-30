package net.slc.ef.fla.qualif.menu.state;

import net.slc.ef.fla.qualif.menu.Menu;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.utils.Utils;

public class MenuPlayState extends MenuState {

    protected MenuPlayState(Menu menu) {
        super(menu);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void onEnter() {
        Utils.clearScreen();
        System.out.print("Enter restaurant name: ");

        String input = menu.getMenuFacade().readString();
        Restaurant restaurant = Restaurant.getInstance(input);
        restaurant.getRestaurantFacade().start();

        restaurant.getRestaurantFacade().await().join();
        this.switchState(new MenuMainState(menu));
    }

    @Override
    public void onExit() {

    }
}
