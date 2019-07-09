package ir.ac.kntu.model.Towers;

import ir.ac.kntu.model.UnknownTypeException;
import javafx.util.Pair;

import java.io.Serializable;

/**
 * @author TOP
 */
public class Tower implements Serializable {
    private static final long serialVersionUID = -1678915435882678851L;
    private final int cost;
    private final int damage;
    private final int range;
    private int health;
    private boolean isActive;
    private Pair<Integer, Integer> location;

    /**
     * @param health
     */
    public Tower(int health) {
        this(health, 0, 0, 0);
    }

    /**
     * @param health
     * @param cost
     * @param damage
     * @param range
     */
    public Tower(int health, int cost, int damage, int range) {
        this.health = health;
        this.cost = cost;
        this.damage = damage;
        this.range = range;
    }

    /**
     * @param tower
     * @param toUppercase
     * @return
     */
    public static char getSymbol(Tower tower, boolean toUppercase) {
        char symbol = ' ';
        if (tower instanceof BlackTower) {
            symbol = 'B';
        } else if (tower instanceof ElectricTower) {
            symbol = 'E';
        } else if (tower instanceof InfernoTower) {
            symbol = 'I';
        } else if (tower instanceof SoldierTower) {
            symbol = 'M';
        } else {
            try {
                throw new UnknownTypeException(tower);
            } catch (UnknownTypeException e) {
                e.printStackTrace();
            }
        }
        if (!toUppercase) {
            symbol += 32;
        }

        return symbol;
        /*
        BlackTower B
        Electric Tower E
        Inferno Tower I
        Soldier Tower M (Military)
         */
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
     * @return a String
     */
    @Override
    public String toString() {
        return "Tower{ health =" + health + ", isActive=" + isActive +
                ", location=" + location + "} Instance Of " + this.getClass();
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
    public boolean isActive() {
        return isActive;
    }

    private void setActive(boolean active) {
        isActive = active;
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
