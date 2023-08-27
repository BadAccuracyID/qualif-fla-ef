package net.slc.ef.fla.qualif.model.restaurant;

import net.slc.ef.fla.qualif.model.person.cook.Cook;
import net.slc.ef.fla.qualif.model.person.server.Server;
import net.slc.ef.fla.qualif.model.restaurant.chair.Chair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Restaurant {

    private final String name;
    private final List<Chair> chairs;
    private final List<Server> servers;
    private final List<Cook> cooks;

    private final RestaurantFacade restaurantFacade;

    private final List<ExecutorService> executorServices;

    private int money;
    private int score;

    public Restaurant(String name, List<ExecutorService> executorServices) {
        this.name = name;
        this.chairs = new ArrayList<>();
        this.servers = new ArrayList<>();
        this.cooks = new ArrayList<>();
        this.restaurantFacade = new RestaurantFacade(this);
        this.executorServices = executorServices;

        this.money = 0;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public List<Chair> getChairs() {
        return chairs;
    }

    public List<Server> getServers() {
        return servers;
    }

    public List<Cook> getCooks() {
        return cooks;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public RestaurantFacade getRestaurantFacade() {
        return restaurantFacade;
    }

    public List<ExecutorService> getExecutorServices() {
        return executorServices;
    }

}
