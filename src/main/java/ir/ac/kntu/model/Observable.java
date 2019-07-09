package ir.ac.kntu.model;

import ir.ac.kntu.view.Observer;

import java.io.Serializable;

/**
 * @author TOP
 */
public interface Observable extends Serializable {

    /**
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     *
     */
    void updateAllObservers();
}
