package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.datastore.Datastore;
import net.slc.ef.fla.qualif.model.data.RestaurantData;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

import java.io.IOException;

public class RestaurantEndingState extends RestaurantState {

    public RestaurantEndingState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        Datastore datastore = Datastore.getInstance();

        RestaurantData restaurantData = RestaurantData.builder()
                .setName(restaurant.getName())
                .setScore(restaurant.getScore())
                .build();

        datastore.registerRestaurantData(restaurantData);
        try {
            datastore.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        restaurant.getRestaurantFacade().end();
    }

    @Override
    public void processInput(String string) {

    }
}
