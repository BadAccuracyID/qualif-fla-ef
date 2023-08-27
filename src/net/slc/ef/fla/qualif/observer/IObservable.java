package net.slc.ef.fla.qualif.observer;

public interface IObservable {

    void addObserver(IObserver observer);

    void removeObserver(IObserver observer);

    void notifyObservers();

}
