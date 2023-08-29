package net.slc.ef.fla.qualif.observer;

public interface IObservable<T> {

    void addObserver(IObserver<T> observer);

    void removeObserver(IObserver<T> observer);

    void notifyObservers();

}
