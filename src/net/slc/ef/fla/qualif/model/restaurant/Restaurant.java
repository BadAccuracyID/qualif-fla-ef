package net.slc.ef.fla.qualif.model.restaurant;

import net.slc.ef.fla.qualif.async.ASExecutorManager;
import net.slc.ef.fla.qualif.model.person.cook.Cook;
import net.slc.ef.fla.qualif.model.person.server.Server;
import net.slc.ef.fla.qualif.model.restaurant.chair.Chair;
import net.slc.ef.fla.qualif.model.restaurant.state.RestaurantInitializationState;
import net.slc.ef.fla.qualif.model.restaurant.state.RestaurantState;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Restaurant {

    // we use WeakReference to avoid memory leaks
    // let's hope that the GC will not be too aggressive
    private static WeakReference<Restaurant> instance;

    private final String name;
    private final List<Chair> chairs;
    private final List<Server> servers;
    private final List<Cook> cooks;

    private final RestaurantFacade restaurantFacade;

    private final List<ExecutorService> executorServices;
    private final ASExecutorManager asExecutorManager;
    private RestaurantState state;
    private int money;
    private int score;

    private Restaurant(String name, List<ExecutorService> executorServices) {
        this.name = name;
        this.chairs = new ArrayList<>();
        this.servers = new ArrayList<>();
        this.cooks = new ArrayList<>();
        this.restaurantFacade = new RestaurantFacade(this);
        this.executorServices = executorServices;
        this.asExecutorManager = new ASExecutorManager();
        this.state = new RestaurantInitializationState(this);

        this.money = 0;
        this.score = 0;
    }

    public static Restaurant getInstance(String name, List<ExecutorService> executorServices) {
        if (instance == null || instance.get() == null) {
            instance = new WeakReference<>(new Restaurant(name, executorServices));
        }

        return instance.get();
    }

    public static Restaurant getInstance() {
        return instance.get();
    }

    public static void destroy() {
        instance = null;
    }

    public ASExecutorManager getAsExecutorManager() {
        return asExecutorManager;
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

    public RestaurantState getState() {
        return state;
    }

    public void setState(RestaurantState state) {
        this.state = state;
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
