package ir.ac.kntu.controller;

import ir.ac.kntu.model.Board;
import ir.ac.kntu.model.Player;
import ir.ac.kntu.model.Soldiers.Soldier;
import ir.ac.kntu.model.Soldiers.SoldierKind;
import ir.ac.kntu.model.Towers.SoldierTower;
import ir.ac.kntu.model.Towers.Tower;
import ir.ac.kntu.view.GameBoard;
import ir.ac.kntu.view.View;
import javafx.application.Platform;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author TOP
 */
public class GameCycle {
    private final long gameCycle;
    private final Board board;
    private View view;
    private long cycle;

    /**
     * @param gameCycle
     * @param board
     * @param view
     * @param cycleNumber
     */
    public GameCycle(long gameCycle, Board board, View view, long cycleNumber) {
        this.gameCycle = gameCycle;
        this.board = board;
        this.view = view;
        cycle = cycleNumber;
    }

    private static Integer findCellularDistance(int i, int j, int x, int y) {
        return Math.abs(i - x) + Math.abs(j - y);
    }

    /**
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return
     */
    public static Double findDistance(int x1, int x2, int y1, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * @return
     */
    public long getCycle() {
        return cycle;
    }

    /**
     *
     */
    public void start() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (gameOverHandler()) {
                    timer.cancel();
                    timer.purge();
                    return;
                }
                towerActor();
                soldierActor();
                playerHandler();
                energizer();
                if (cycle % 5 == 4) {
                    soldierTowerActuator(board.getAttacker(), board.getDefender(
                    ).getKinds(), 'r');
                    soldierTowerActuator(board.getDefender(), board.getAttacker(
                    ).getKinds(), 'R');
                }
                cycle++;
            }
        };
        timer.scheduleAtFixedRate(task, 0, gameCycle);
    }

    private void playerHandler() {
        defHandler();
        atkHandler();
    }

    private void atkHandler() {
        List<Soldier> martyrs = new ArrayList<>();
        for (Soldier soldier : board.getAttacker().getSoldiers()) {
            if (board.getMap()[soldier.getLocation().getValue() + 1]
                    [soldier.getLocation().getKey()] == 'R') {
                board.getDefender().getHurt(soldier.getDamage());
                martyrs.add(soldier);
            }
        }
        board.getAttacker().getSoldiers().removeAll(martyrs);
    }

    private void defHandler() {
        List<Soldier> martyrs = new ArrayList<>();
        for (Soldier soldier : board.getDefender().getSoldiers()) {
            if (board.getMap()[soldier.getLocation().getValue() - 1][soldier.
                    getLocation().getKey()] == 'r') {
                board.getAttacker().getHurt(soldier.getDamage());
                martyrs.add(soldier);
            }
        }
        board.getDefender().getSoldiers().removeAll(martyrs);
    }

    private boolean gameOverHandler() {
        if (board.getAttacker().getHealth() <= 0 && board.getDefender().
                getHealth() <= 0) {
            GameBoard.gameOver(view.getScene(), "Draw");
            return true;
        } else if (board.getAttacker().getHealth() <= 0) {
            GameBoard.gameOver(view.getScene(),
                    board.getDefender().getName() + " Has Won !");
            return true;
        } else if (board.getDefender().getHealth() <= 0) {
            GameBoard.gameOver(view.getScene(),
                    board.getAttacker().getName() + " Has Won !");
            return true;
        }
        return false;
    }

    private void soldierTowerActuator(Player player, List<SoldierKind> kinds,
                                      char init) {
        List<Tower> soldierTowers = player.getTowers().stream().filter(x -> x
                instanceof SoldierTower).collect(Collectors.toList());
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        for (int j = 0; j < board.getMap().length; j++) {
            for (int i = 0; i < board.getMap()[0].length; i++) {
                if (board.getMap()[j][i] == init) {
                    pairs.add(new Pair<>(i, j));
                }
            }
        }
        for (Tower tower : soldierTowers) {
            if (tower.isActive()) {
                int rand = Math.abs(new Random().nextInt()) % kinds.size();
                Soldier soldier = SoldierKind.soldierFactory(kinds.get(rand));
                if (board.getElements()[pairs.get(rand).getValue()][pairs.
                        get(rand).getKey()] == '0') {
                    soldier.setLocation(pairs.get(rand).getKey(),
                            pairs.get(rand).getValue());
                    player.getSoldiers().add(soldier);
                }
            }
        }
    }

    private void soldierActor() {
        List<Soldier> atkIn = board.getAttacker().getSoldiers().stream().filter
                (x -> Character.isLowerCase(board.getMap()[x.getLocation().
                        getValue()][x.getLocation().getKey()])).
                collect(Collectors.toList());
        List<Soldier> atkOut = board.getAttacker().getSoldiers().stream().filter
                (x -> Character.isUpperCase(board.getMap()[x.getLocation()
                        .getValue()][x.getLocation().getKey()]))
                .collect(Collectors.toList());
        List<Soldier> defIn = board.getDefender().getSoldiers().stream().filter
                (x -> Character.isUpperCase(board.getMap()[x.getLocation()
                        .getValue()][x.getLocation().getKey()]))
                .collect(Collectors.toList());
        List<Soldier> defOut = board.getDefender().getSoldiers().stream().filter
                (x -> Character.isLowerCase(board.getMap()[x.getLocation()
                        .getValue()][x.getLocation().getKey()]))
                .collect(Collectors.toList());
        Iterator<Soldier> atkInIterator = atkIn.iterator();
        while (atkInIterator.hasNext()) {
            Soldier soldier = atkInIterator.next();
            ATK_IN:
            for (int j = 0; j < board.getMap().length; j++) {
                for (int i = 0; i < board.getMap()[0].length; i++) {
                    board.updateElements();
                    if (Character.isUpperCase(board.getElements()[j][i]) &&
                            GameController.isInRange(soldier.getRange(), i, j,
                                    soldier.getLocation().getKey(), soldier.
                                            getLocation().getValue(),
                                    soldier.getSoldierType())) {
                        shooter(soldier, i, j);
                        atkInIterator.remove();
                        break ATK_IN;
                    }
                }
            }
        }
        Iterator<Soldier> defInIterator = defIn.iterator();
        while (defInIterator.hasNext()) {
            Soldier soldier = defInIterator.next();
            DEF_IN:
            for (int j = 0; j < board.getMap().length; j++) {
                for (int i = 0; i < board.getMap()[0].length; i++) {
                    board.updateElements();
                    if (Character.isLowerCase(board.getElements()[j][i]) &&
                            GameController.isInRange(soldier.getRange(), i, j,
                                    soldier.getLocation().getKey(), soldier.
                                            getLocation().getValue(),
                                    soldier.getSoldierType())) {
                        defInIterator.remove();
                        shooter(soldier, i, j);
                        break DEF_IN;
                    }
                }
            }
        }
        /////////// Writing The Stupid Thing using Iterator ^
        Iterator<Soldier> atkOutIterator = atkOut.iterator();
        while (atkOutIterator.hasNext()) {
            Soldier soldier = atkOutIterator.next();
            ATK_OUT:
            for (int j = 0; j < board.getMap().length; j++) {
                for (int i = 0; i < board.getMap()[0].length; i++) {
                    board.updateElements();
                    if (Character.isUpperCase(board.getElements()[j][i]) &&
                            GameController.isInRange(soldier.getRange(), i, j,
                                    soldier.getLocation().getKey(), soldier
                                            .getLocation().getValue(),
                                    soldier.getSoldierType())) {
                        atkOutIterator.remove();
                        shooter(soldier, i, j);
                        break ATK_OUT;
                    }
                }
            }
        }
        Iterator<Soldier> defOutIterator = defOut.iterator();
        while (defOutIterator.hasNext()) {
            Soldier soldier = defOutIterator.next();
            DEF_OUT:
            for (int j = 0; j < board.getMap().length; j++) {
                for (int i = 0; i < board.getMap()[0].length; i++) {
                    board.updateElements();
                    if (Character.isLowerCase(board.getElements()[j][i]) &&
                            GameController.isInRange(soldier.getRange(), i, j,
                                    soldier.getLocation().getKey(), soldier.
                                            getLocation().getValue(),
                                    soldier.getSoldierType())) {
                        shooter(soldier, i, j);
                        defOutIterator.remove();
                        break DEF_OUT;
                    }
                }
            }
        }
        soldierPoker(defIn, false);
        soldierPoker(atkIn, true);
        soldierPoker(atkOut, true);
        soldierPoker(defOut, false);
    }

    private void soldierPoker(List<Soldier> soldiers, boolean goDown) {
        soldiers.stream().forEach(x -> {
            if (goDown) {
                pokeAttacker(x);
            } else {
                pokeDefender(x);
            }
        });
    }

    private void pokeDefender(Soldier soldier) {
        List<Pair<Integer, Integer>> moves = new ArrayList<>();
        for (int j = 0; j < board.getMap().length; j++) {
            for (int i = 0; i < board.getMap()[0].length; i++) {
                if (j <= soldier.getLocation().getValue() &&
                        findCellularDistance(i, j, soldier.getLocation().getKey(
                        ), soldier.getLocation().getValue()) == 1
                        && Character.toLowerCase(board.getMap()[j][i]) == 'y'
                        && !Character.isUpperCase(board.getElements()[j][i])) {
                    moves.add(new Pair<>(i, j));
                }
            }
        }
        Pair<Integer, Integer> finalMove = null;
        finalMove = findBestDefMove(moves, soldier.getSpeed(), soldier
                .getLocation().getValue() < board.getMap().length / 2);

        if (finalMove != null) {
            soldier.setLocation(finalMove.getKey(), finalMove.getValue());
        }
        board.updateElements();
    }

    private void pokeAttacker(Soldier soldier) {
        List<Pair<Integer, Integer>> moves = new ArrayList<>();
        for (int j = 0; j < board.getMap().length; j++) {
            for (int i = 0; i < board.getMap()[0].length; i++) {
                if (j >= soldier.getLocation().getValue() &&
                        findCellularDistance(i, j, soldier.getLocation().getKey(
                        ), soldier.getLocation().getValue()) == 1
                        && Character.toLowerCase(board.getMap()[j][i]) == 'y'
                        && !Character.isLowerCase(board.getElements()[j][i])) {
                    moves.add(new Pair<>(i, j));
                }
            }
        }
        Pair<Integer, Integer> finalMove = null;

        finalMove = findBestAtkMove(moves, soldier.getSpeed(),
                soldier.getLocation().getValue() < board.getMap().length / 2);

        if (finalMove != null) {
            soldier.setLocation(finalMove.getKey(), finalMove.getValue());
        }
        board.updateElements();
    }

    private Pair<Integer, Integer> findBestDefMove(List<Pair<Integer, Integer>>
                                                           moves, int speed,
                                                   boolean isUp) {
        if (speed == 1) {
            moves = moves.stream().filter(x -> board.getElements()[x.getValue()]
                    [x.getKey()] == '0').collect(Collectors.toList());
            if (!moves.isEmpty()) {
                if (moves.size() == 2 && findDistance(moves.get(0).getKey(),
                        board.getMap()[0].length / 2, moves.get(0).getValue(
                        ), 0) == findDistance(moves.get(1).getKey(),
                        board.getMap()[0].length / 2, moves.get(1).getValue()
                        , 0)) {
                    return moves.get(Math.abs(new Random().nextInt()) % 2);
                }
                Optional<Pair<Integer, Integer>> move;
                if (isUp) {
                    move = moves.stream().reduce((y, x) -> findDistance(x.getKey
                            (), board.getMap()[0].length / 2, x.getValue(), 0)
                            > findDistance(y.getKey(), board.getMap()[0].length
                            / 2, y.getValue(), 0) ? y : x);
                } else {
                    move = moves.stream().reduce((y, x) -> findDistance(x.getKey
                            (), board.getMap()[0].length / 2, x.
                            getValue(), board.getMap().length) > findDistance(y.
                            getKey(), board.getMap()[0].length / 2, y.getValue
                            (), board.getMap().length) ? x : y);
                }
                return move.get();
            } else {
                return null;
            }
        } else {
            Iterator<Pair<Integer, Integer>> iterator = moves.iterator();
            List<Pair<Integer, Integer>> newMoves = new ArrayList<>();
            while (iterator.hasNext()) {
                boolean changed = false;
                Pair<Integer, Integer> pair = iterator.next();
                for (int j = 0; j < board.getMap().length; j++) {
                    for (int i = 0; i < board.getMap()[0].length; i++) {
                        if (j <= pair.getValue() && findCellularDistance(i, j,
                                pair.getKey(), pair.getValue()) == 1 &&
                                Character.toLowerCase(board.getMap()[j][i]) ==
                                        'y' && !Character.isLowerCase(board.
                                getElements()[j][i])) {
                            newMoves.add(new Pair<>(i, j));
                            if (board.getElements()[j][i] == '0') {
                                changed = true;
                            }
                        }
                    }
                }
                if (changed) {
                    iterator.remove();
                }
            }
            moves.addAll(newMoves);
        }
        return findBestDefMove(moves, speed - 1, isUp);
    }

    private Pair<Integer, Integer> findBestAtkMove(List<Pair<Integer, Integer>>
                                                           moves, int speed,
                                                   boolean isUp) {
        if (speed == 1) {
            moves = moves.stream().filter(x -> board.getElements()[x.getValue()]
                    [x.getKey()] == '0').collect(Collectors.toList());
            if (!moves.isEmpty()) {
                if (moves.size() == 2 && findDistance(moves.get(0).getKey(),
                        board.getMap()[0].length / 2, moves.get(0).getValue()
                        , 0) == findDistance(moves.get(1).getKey(), board
                        .getMap()[0].length / 2, moves.get(1).getValue(), 0)) {
                    return moves.get(Math.abs(new Random().nextInt()) % 2);
                }
                Optional<Pair<Integer, Integer>> move;
                if (isUp) {
                    move = moves.stream().reduce((x, y) -> findDistance(x.getKey
                                    (), board.getMap()[0].length / 2,
                            x.getValue(), 0) > findDistance(y.getKey(),
                            board.getMap()[0].length / 2, y.getValue(), 0)
                            ? x : y);
                } else {
                    move = moves.stream().reduce((x, y) -> findDistance(x.getKey
                            (), board.getMap()[0].length / 2, x.getValue
                            (), board.getMap().length) > findDistance(y.getKey()
                            , board.getMap()[0].length / 2, y.getValue(),
                            board.getMap().length) ? y : x);
                }
                return move.get();
            } else {
                return null;
            }
        } else {
            Iterator<Pair<Integer, Integer>> iterator = moves.iterator();
            List<Pair<Integer, Integer>> newMoves = new ArrayList<>();
            while (iterator.hasNext()) {
                boolean changed = false;
                Pair<Integer, Integer> pair = iterator.next();
                for (int j = 0; j < board.getMap().length; j++) {
                    for (int i = 0; i < board.getMap()[0].length; i++) {
                        if (j >= pair.getValue() && findCellularDistance(i, j,
                                pair.getKey(), pair.getValue()) == 1 &&
                                Character.toLowerCase(board.getMap()[j][i]) ==
                                        'y' && !Character.isUpperCase(board.
                                getElements()[j][i])) {
                            newMoves.add(new Pair<>(i, j));
                            if (board.getElements()[j][i] == '0') {
                                changed = true;
                            }
                        }
                    }
                }
                if (changed) {
                    iterator.remove();
                }
            }
            moves.addAll(newMoves);
        }
        return findBestAtkMove(moves, speed - 1, isUp);
    }

    private void shooter(Soldier soldier, int x, int y) {
        List<Soldier> soldiers = new ArrayList<>();
        List<Tower> towers = new ArrayList<>();
        board.updateElements();
        int sSize = 0, tSize = 0;
        if (board.getAttacker().getSoldiers().stream().anyMatch(s ->
                s.getLocation().getKey() == x && s.getLocation().getValue() ==
                        y)) {
            soldiers.addAll(board.getAttacker().getSoldiers().stream().filter
                    (s -> s.getLocation().getKey() == x && s.getLocation().
                            getValue() == y).collect(Collectors.toList()));
        }
        sSize = soldiers.size();
        if (board.getDefender().getSoldiers().stream().anyMatch(s -> s.
                getLocation().getKey() == x && s.getLocation().getValue() ==
                y)) {
            soldiers.addAll(board.getDefender().getSoldiers().stream().filter
                    (s -> s.getLocation().getKey() == x && s.getLocation().
                            getValue() == y).collect(Collectors.toList()));
        }
        if (board.getAttacker().getTowers().stream().anyMatch(s -> !(s
                instanceof SoldierTower) && s.getLocation().getKey() == x &&
                s.getLocation().getValue() == y)) {
            towers.addAll(board.getAttacker().getTowers().stream().filter(s ->
                    !(s instanceof SoldierTower) && s.getLocation().getKey() ==
                            x && s.getLocation().getValue() == y).collect(
                    Collectors.toList()));
        }
        tSize = towers.size();
        if (board.getDefender().getTowers().stream().anyMatch(s ->
                !(s instanceof SoldierTower) && s.getLocation().getKey() == x &&
                        s.getLocation().getValue() == y)) {
            towers.addAll(board.getDefender().getTowers().stream().filter(s ->
                    !(s instanceof SoldierTower) && s.getLocation().getKey() ==
                            x && s.getLocation().getValue() == y)
                    .collect(Collectors.toList()));
        }
        if (towers.isEmpty() && !soldiers.isEmpty()) {
            Soldier victim = soldiers.get(0);
            victim.setHealth(victim.getHealth() - soldier.getDamage());
            if (victim.getHealth() <= 0) {
                if (sSize > 0) {
                    board.getAttacker().getSoldiers().remove(victim);
                } else {
                    board.getDefender().getSoldiers().remove(victim);
                }
                board.updateElements();
            }
        } else if (!towers.isEmpty()) {
            Tower target = towers.get(0);
            target.setHealth(target.getHealth() - soldier.getDamage());
            if (target.getHealth() <= 0) {
                if (tSize > 0) {
                    board.getAttacker().getTowers().remove(target);
                } else {
                    board.getDefender().getTowers().remove(target);
                }
                board.updateElements();
            }
        }
    }

    private void towerActor() {
        List<Tower> atkTowers = board.getAttacker().getTowers().stream().filter
                (x -> x.isActive() && !(x instanceof SoldierTower)).collect
                (Collectors.toList());
        List<Tower> defTowers =
                board.getDefender().getTowers().stream().filter(x -> x.isActive
                        () && !(x instanceof SoldierTower)).collect
                        (Collectors.toList());
        // attacker towers
        for (Tower tower : atkTowers) {
            ATK:
            for (int j = 0; j < board.getMap().length; j++) {
                for (int i = 0; i < board.getMap()[0].length; i++) {
                    board.updateElements();
                    if (Character.isUpperCase(board.getElements()[j][i]) &&
                            GameController.isInRange(tower.getRange(), i, j,
                                    tower.getLocation().getKey(), tower.
                                            getLocation().getValue())) {
                        AtomicInteger x = new AtomicInteger(i);
                        AtomicInteger y = new AtomicInteger(j);
                        Soldier victim = board.getDefender().getSoldiers().
                                stream().filter(z -> z.getLocation().getKey() ==
                                x.get() && z.getLocation().getValue() == y.get()
                        ).collect(Collectors.toList()).get(0);
                        System.out.println(tower + " is Attacking " + victim);
                        victim.setHealth(victim.getHealth() -
                                tower.getDamage());
                        if (victim.getHealth() <= 0) {
                            board.getDefender().getSoldiers().remove(victim);
                            board.updateElements();
                        }
                        break ATK;
                    }
                }
            }
        }
        // Defender Towers
        for (Tower tower : defTowers) {
            DEF:
            for (int j = 0; j < board.getMap().length; j++) {
                for (int i = 0; i < board.getMap()[0].length; i++) {
                    board.updateElements();
                    if (Character.isLowerCase(board.getElements()[j][i]) &&
                            GameController.isInRange(tower.getRange(),
                                    i, j, tower.getLocation().getKey(),
                                    tower.getLocation().getValue())) {
                        AtomicInteger x = new AtomicInteger(i);
                        AtomicInteger y = new AtomicInteger(j);
                        Soldier victim = board.getAttacker().getSoldiers().
                                stream().filter(z -> z.getLocation().getKey() ==
                                x.get() && z.getLocation().getValue() == y.get()
                        ).collect(Collectors.toList()).get(0);
                        System.out.println(tower + " is Attacking " + victim);
                        victim.setHealth(victim.getHealth() -
                                tower.getDamage());
                        if (victim.getHealth() <= 0) {
                            board.getAttacker().getSoldiers().remove(victim);
                            board.updateElements();
                        }
                        break DEF;
                    }
                }
            }
        }
    }

    private void energizer() {
        board.getAttacker().energize();
        board.getDefender().energize();
        Platform.runLater(board::updateAllObservers);
    }
}