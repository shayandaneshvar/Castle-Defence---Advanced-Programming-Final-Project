package ir.ac.kntu.model.Soldiers;

import ir.ac.kntu.model.UnknownTypeException;

import java.io.Serializable;

/**
 * @author TOP
 */
public enum SoldierKind implements Serializable {

    /**
     *
     */
    GOBLIN,

    /**
     *
     */
    SHIELD,

    /**
     *
     */
    SWORDSMAN,

    /**
     *
     */
    KNIGHT,

    /**
     *
     */
    ARCHER,

    /**
     *
     */
    WIZARD,

    /**
     *
     */
    DRAGON,

    /**
     *
     */
    NON;

    /**
     * @param soldier
     * @param toUppercase
     * @return
     */
    public static char getSymbol(Soldier soldier, boolean toUppercase) {
        char symbol = ' ';
        try {
            if (soldier instanceof Archer) {
                symbol = 'A';
            } else if (soldier instanceof Goblin) {
                symbol = 'G';
            } else if (soldier instanceof Dragon) {
                symbol = 'D';
            } else if (soldier instanceof Knight) {
                symbol = 'K';
            } else if (soldier instanceof Shield) {
                symbol = 'P';
            } else if (soldier instanceof Swordsman) {
                symbol = 'S';
            } else if (soldier instanceof Wizard) {
                symbol = 'W';
            } else {
                throw new UnknownTypeException(soldier);
            }
        } catch (UnknownTypeException e) {
            e.printStackTrace();
        }
        if (!toUppercase) {
            symbol += 32;
        }
        return symbol;
    }

    /**
     * @param kind
     * @return
     */
    public static Soldier soldierFactory(SoldierKind kind) {
        switch (kind) {
            case SWORDSMAN:
                return new Swordsman();
            case WIZARD:
                return new Wizard();
            case KNIGHT:
                return new Knight();
            case SHIELD:
                return new Shield();
            case GOBLIN:
                return new Goblin();
            case DRAGON:
                return new Dragon();
            case ARCHER:
                return new Archer();
            default:
                System.err.println("Something Went Wrong In Soldier Factory");
                return null;
        }
    }


    /*
    Archer A
    Goblin G
    Dragon D
    Knight K
    Shield P
    Swordsman S
    Wizard W
     */
}
