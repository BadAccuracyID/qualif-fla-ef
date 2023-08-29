package net.slc.ef.fla.qualif.model.restaurant;

import net.slc.ef.fla.qualif.async.ASExecutorManager;
import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.chair.Chair;
import net.slc.ef.fla.qualif.model.restaurant.mediator.RestaurantMediator;
import net.slc.ef.fla.qualif.model.restaurant.state.RestaurantInitializationState;
import net.slc.ef.fla.qualif.model.restaurant.state.RestaurantState;
import net.slc.ef.fla.qualif.observer.IObservable;
import net.slc.ef.fla.qualif.observer.IObserver;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements IObservable<Restaurant> {

    // we use WeakReference to avoid memory leaks
    // let's hope that the GC will not be too aggressive
    private static WeakReference<Restaurant> instance;

    private final String name;
    private final List<Chair> chairs;
    private final List<Waiter> waiters;
    private final List<Chef> chefs;

    private final List<IObserver<Restaurant>> observers;

    private final RestaurantFacade restaurantFacade;
    private final RestaurantMediator restaurantMediator;

    private final ASExecutorManager asExecutorManager;
    private RestaurantState state;
    private int money;
    private int score;

    private Restaurant(String name) {
        this.name = name;
        this.chairs = new ArrayList<>();
        this.waiters = new ArrayList<>();
        this.chefs = new ArrayList<>();
        this.observers = new ArrayList<>();

        this.restaurantFacade = new RestaurantFacade(this);
        this.restaurantMediator = new RestaurantMediator(this);
        this.asExecutorManager = new ASExecutorManager();
        this.state = new RestaurantInitializationState(this);
        this.state.onEnter();

        this.money = 0;
        this.score = 0;
    }

    public static Restaurant getInstance(String name) {
        if (instance == null || instance.get() == null) {
            instance = new WeakReference<>(new Restaurant(name));
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

    public List<Waiter> getWaiters() {
        return waiters;
    }

    public List<Chef> getChefs() {
        return chefs;
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

    public RestaurantMediator getRestaurantMediator() {
        return restaurantMediator;
    }

    @Override
    public void addObserver(IObserver<Restaurant> observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver<Restaurant> observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach(observer -> observer.update(this));
    }
}
