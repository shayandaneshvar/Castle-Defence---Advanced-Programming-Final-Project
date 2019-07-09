package ir.ac.kntu.model;

import ir.ac.kntu.model.Soldiers.Soldier;
import ir.ac.kntu.model.Soldiers.SoldierKind;
import ir.ac.kntu.model.Towers.Tower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TOP
 */
public class Player implements Serializable {
    private static final long serialVersionUID = -5574751126725342833L;
    private final String name;
    private final List<SoldierKind> kinds;
    private int energy;
    private int health;
    private int energyRate;
    private List<Soldier> soldiers;
    private List<Tower> towers;

    /**
     * @param name
     * @param energy
     * @param health
     * @param kinds
     * @param towers
     */
    public Player(String name, int energy, int health, List<SoldierKind> kinds,
                  List<Tower> towers) {
        this.name = name;
        this.kinds = kinds;
        this.energy = energy;
        this.health = health;
        this.towers = towers;
        soldiers = new ArrayList<>();
        energyRate = 8;
    }

    /**
     * @return
     */
    public int getEnergyRate() {
        return energyRate;
    }

    /**
     * @param energyRate
     */
    public void setEnergyRate(int energyRate) {
        this.energyRate = energyRate;
    }

    /**
     * @param player
     */
    public void updatePlayer(Player player) {
        energy = player.getEnergy();
        health = player.getHealth();
        soldiers = player.getSoldiers();
        towers = player.getTowers();
        energyRate = player.getEnergyRate();
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public int getEnergy() {
        return energy;
    }

    private void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     *
     */
    public synchronized void energize() {
        if (energy != 100) {
            energy += energyRate;
            if (energy > 100) {
                energy = 100;
            }
        }
    }

    /**
     * @param amount
     */
    public synchronized void decreaseEnergy(int amount) {
        setEnergy(energy - amount);
    }

    /**
     * @return
     */
    public synchronized int getHealth() {
        return health;
    }

    /**
     * @param painAmount
     */
    public synchronized void getHurt(int painAmount) {
        health -= painAmount;
    }

    /**
     * @return
     */
    public List<SoldierKind> getKinds() {
        return kinds;
    }

    /**
     * @return
     */
    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    /**
     * @return
     */
    public List<Tower> getTowers() {
        return towers;
    }

}
