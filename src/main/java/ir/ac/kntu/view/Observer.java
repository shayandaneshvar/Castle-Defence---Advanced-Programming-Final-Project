package ir.ac.kntu.view;

import ir.ac.kntu.model.Observable;

/**
 * @author TOP
 */
public interface Observer {

    /**
     * @param changedObservable
     */
    void update(Observable changedObservable);
}
