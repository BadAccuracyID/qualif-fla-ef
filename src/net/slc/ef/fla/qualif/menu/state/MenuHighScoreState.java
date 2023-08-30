package net.slc.ef.fla.qualif.menu.state;

import net.slc.ef.fla.qualif.datastore.Datastore;
import net.slc.ef.fla.qualif.menu.Menu;
import net.slc.ef.fla.qualif.model.data.RestaurantData;
import net.slc.ef.fla.qualif.utils.Utils;

public class MenuHighScoreState extends MenuState {
    protected MenuHighScoreState(Menu menu) {
        super(menu);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void onEnter() {
        Utils.clearScreen();
        Datastore datastore = Datastore.getInstance();
        datastore.sort();

        if (datastore.getRestaurantData().isEmpty()) {
            System.out.println("No high score yet!");
            menu.getMenuFacade().promptEnterKey();

            this.switchState(new MenuMainState(menu));
            return;
        }

        System.out.println("         High Score");
        System.out.println("=================================");
        System.out.println(" Rank | Restaurant Name |  Score ");
        System.out.println("=================================");


        int i = 1;
        for (RestaurantData restaurantData : datastore.getRestaurantData()) {
            System.out.printf(" %3d. | %-15s | %6d \n", i++, restaurantData.getName(), restaurantData.getScore());
        }
        System.out.println("=================================");

        menu.getMenuFacade().promptEnterKey();
        this.switchState(new MenuMainState(menu));
    }

    @Override
    public void onExit() {

    }
}
