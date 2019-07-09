package ir.ac.kntu.model.Soldiers;

import javafx.util.Pair;

import java.io.Serializable;

/**
 * @author TOP
 */
public class Soldier implements Serializable {
    private static final long serialVersionUID = -3134206080606141917L;
    private final int cost;
    private final int speed;
    private final int damage;
    private final int range;
    private final SoldierType soldierType;
    private int health;
    private boolean isActive;
    private Pair<Integer, Integer> location;

    /**
     * @param health
     * @param soldierType
     * @param cost
     * @param speed
     * @param damage
     * @param range
     */
    public Soldier(int health, SoldierType soldierType, int cost, int speed,
                   int damage, int range) {
        this.soldierType = soldierType;
        this.health = health;
        this.cost = cost;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        isActive = false;
    }

    /**
     * @return
     */
    public int getCost() {
        return cost;
    }

    /**
     * @return
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @return
     */
    public int getRange() {
        return range;
    }

    /**
     * @return
     */
    public SoldierType getSoldierType() {
        return soldierType;
    }

    /**
     * @return a String
     */
    @Override
    public String toString() {
        return "Soldier{ health =" + health + ", isActive=" + isActive +
                ", location=" + location + "} instance Of " + this.getClass();
    }

    /**
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * @return
     */
    public Pair<Integer, Integer> getLocation() {
        return location;
    }

    /**
     * @param xCoord
     * @param yCoord
     */
    public void setLocation(int xCoord, int yCoord) {
        this.location = new Pair<>(xCoord, yCoord);
        setActive(true);
    }

    /**
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

}