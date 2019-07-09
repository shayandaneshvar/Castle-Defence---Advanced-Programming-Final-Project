package ir.ac.kntu.model;

import ir.ac.kntu.model.Soldiers.Soldier;
import ir.ac.kntu.model.Soldiers.SoldierKind;
import ir.ac.kntu.model.Towers.Tower;
import ir.ac.kntu.view.Observer;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TOP
 */
public class Board implements Observable, Serializable {
    private static final long serialVersionUID = 5193411289324161050L;
    private final char[][] map;
    private final Player attacker;
    private final Player defender;
    private char[][] elements;
    private transient List<Observer> observers;

    /**
     * @param map
     * @param attacker
     * @param defender
     */
    public Board(char[][] map, Player attacker, Player defender) {
        this.map = map;
        elements = new char[map.length][map[0].length];
        this.attacker = attacker;
        this.defender = defender;
        observers = new ArrayList<>();
        updateElements();
        updateAllObservers();
    }

    /**
     * @return
     */
    public char[][] getMap() {
        return map;
    }

    /**
     * @return
     */
    public char[][] getElements() {
        return elements;
    }

    /**
     * @return
     */
    public Player getAttacker() {
        return attacker;
    }

    /**
     * @return
     */
    public Player getDefender() {
        return defender;
    }

    /**
     *
     */
    public void updateElements() {
        for (int j = 0; j < elements.length; j++) {
            for (int i = 0; i < elements[0].length; i++) {
                elements[j][i] = '0';
            }
        }
        attacker.getSoldiers().stream().filter(Soldier::isActive).forEach(x ->
                elements[x.getLocation().getValue()][x.getLocation().getKey()] =
                        SoldierKind.getSymbol(x, false));
        defender.getSoldiers().stream().filter(Soldier::isActive).forEach(x ->
                elements[x.getLocation().getValue()][x.getLocation().getKey()] =
                        SoldierKind.getSymbol(x, true));
        attacker.getTowers().stream().filter(Tower::isActive).forEach(x ->
                elements[x.getLocation().getValue()][x.getLocation().getKey()] =
                        Tower.getSymbol(x, false));
        defender.getTowers().stream().filter(Tower::isActive).forEach(x ->
                elements[x.getLocation().getValue()][x.getLocation().getKey()] =
                        Tower.getSymbol(x, true));
//        updateAllObservers(); not good to be put here
    }
    //uppercase letters for the attacker and lowercase letters for the defender

    /**
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
        updateAllObservers();
    }

    /**
     *
     */
    @Override
    public void updateAllObservers() {
        observers.stream().forEach(x -> x.update(this));
    }

    /**
     * @param updatedBoard
     */
    public void updateBoard(Board updatedBoard) {
        attacker.updatePlayer(updatedBoard.getAttacker());
        defender.updatePlayer(updatedBoard.getDefender());
        updateElements();
        Platform.runLater(this::updateAllObservers);
    }
}