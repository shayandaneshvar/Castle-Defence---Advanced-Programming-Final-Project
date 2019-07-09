package ir.ac.kntu.controller;

import ir.ac.kntu.model.Board;
import ir.ac.kntu.model.Map;
import ir.ac.kntu.model.Player;
import ir.ac.kntu.model.Soldiers.Soldier;
import ir.ac.kntu.model.Soldiers.SoldierType;
import ir.ac.kntu.model.Towers.SoldierTower;
import ir.ac.kntu.model.Towers.Tower;
import ir.ac.kntu.view.Audio;
import ir.ac.kntu.view.View;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TOP
 */
public final class GameController {

    private GameController() {
    }

    /**
     * @param attacker
     * @param defender
     * @param view
     */
    public static void gameStarter(Player attacker, Player defender,
                                   View view) {
        Audio.starterAudio();
        Board board = new Board(Map.mapLoader("map"), attacker, defender);
        board.addObserver(view);
        InputController.towerHandler(attacker, defender, view, board);
    }

    /**
     * @param board
     * @param xCoord
     * @param yCoord
     * @param player
     * @return
     */
    public static boolean addNextTower(Board board, int xCoord, int yCoord,
                                       Player player) {
        List<Tower> towers =
                player.getTowers().stream().filter(x -> !x.isActive()
                        && !(x instanceof SoldierTower))
                        .collect(Collectors.toList());
        addElement(board, towers.get(0), new Pair<>(xCoord, yCoord));
        return towers.size() == 1;
    }

    /**
     * @param board
     * @param view
     * @param cycleNumber
     */
    public static void gameHandler(Board board, View view, long cycleNumber) {
        Audio.stopAllSounds();
        Audio.mainAudio();
        Audio.counterAudio();
        GameCycle cycle = new GameCycle(1400, board, view, cycleNumber);
        cycle.start();
        InputController.soldierHandler(board, view);
        view.getScene().getRoot().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.HOME) {
                try {
                    FileOutputStream outStream = new FileOutputStream("save");
                    ObjectOutputStream output =
                            new ObjectOutputStream(outStream);
                    output.writeObject(board);
                    output.writeLong(cycle.getCycle());
                    output.close();
                    outStream.close();
                    System.out.println("Game Saved Successfully!");
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * This Method Checks if two Coordinates are in each others vicinity
     * Their Vicinity is a normal square therefor i wrote the equation
     * of normal square and used linear algebra to check if two coordinates are
     * in each others vicinity - i wrote the cartesian equation in this way :
     * |x|+|y| = 2k - then changed it (rotated it by 45deg) and i got:
     * |x|+|y|+||x|-|y|| = 0.5a so in my case i found out:
     * ### ||x|| + ||y|| + | ||x|| - ||y|| | == 2 * range ### and Bingo!
     *
     * @param range   range
     * @param firstX  x1
     * @param firstY  y1
     * @param secondX x2
     * @param secondY y2
     * @return are two coordinates in Range?
     */
    public static boolean isInRange(int range, int firstX, int firstY,
                                    int secondX, int secondY) {
        int normX = Math.abs(firstX - secondX);//  ||x||
        int normY = Math.abs(firstY - secondY);//  ||y||
        return normX + normY + Math.abs(normX - normY) <= range * 2;
    }

    /**
     * same As the Other isInRange
     *
     * @param range   range
     * @param firstX  x1
     * @param firstY  y1
     * @param secondX x2
     * @param secondY y2
     * @param type    SoldierType
     * @return are two coordinates in Range?
     * @see GameController
     */
    public static boolean isInRange(int range, int firstX, int firstY, int
            secondX, int secondY, SoldierType type) {
        if (type == SoldierType.RANGE) {
            return isInRange(range, firstX, firstY, secondX, secondY);
        }
        int normY = Math.abs(firstY - secondY);//  ||y||
        int normX = Math.abs(firstX - secondX);//  ||x||
        return normX * normY == 0 && normX + normY <= range;
    }

    /**
     * @param order
     * @param board
     * @param color
     * @return
     */
    public static Pair<Integer, Integer> coordinator(int order, Board board,
                                                     char color) {
        int k = 0;
        for (int j = 0; j < board.getMap().length; j++) {
            for (int i = 0; i < board.getMap()[0].length; i++) {
                if (board.getMap()[j][i] == color) {
                    k++;
                    if (order == k) {
                        return new Pair<>(i, j);
                    }
                }
            }
        }
        return new Pair<>(-10, -10);
    }

    /**
     * @param board
     * @param object
     * @param coord
     */
    public static void addElement(Board board, Object object, Pair coord) {
        if (object instanceof Tower) {
            Tower tower = (Tower) object;
            tower.setLocation((int) coord.getKey(), (int) coord.getValue());
        } else if (object instanceof Soldier) {
            Soldier soldier = (Soldier) object;
            soldier.setLocation((int) coord.getKey(), (int) coord.getValue());
        } else {
            System.err.println("Something Went Wrong!!!");
        }
        board.updateElements();
    }
}