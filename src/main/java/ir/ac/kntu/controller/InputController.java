package ir.ac.kntu.controller;

import ir.ac.kntu.model.Board;
import ir.ac.kntu.model.Player;
import ir.ac.kntu.model.Soldiers.Soldier;
import ir.ac.kntu.model.Soldiers.SoldierKind;
import ir.ac.kntu.model.Towers.SoldierTower;
import ir.ac.kntu.model.Towers.Tower;
import ir.ac.kntu.view.View;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author TOP
 */
public final class InputController {

    private InputController() {
    }

    /**
     * @param attacker
     * @param defender
     * @param view
     * @param board
     */
    public static void towerHandler(Player attacker, Player defender,
                                    View view, Board board) {
        AtomicReference<KeyCode> atkInitCode = new AtomicReference<>();
        AtomicReference<KeyCode> defInitCode = new AtomicReference<>();
        AtomicInteger atkXCoord = new AtomicInteger();
        AtomicInteger defXCoord = new AtomicInteger();
        AtomicBoolean atkEnd = new AtomicBoolean();
        AtomicBoolean defEnd = new AtomicBoolean();
        atkEnd.set(false);
        defEnd.set(false);
        atkInitCode.set(KeyCode.K);
        defInitCode.set(KeyCode.INSERT);
        atkXCoord.set(-1);
        defXCoord.set(-1);
        Scene scene = view.getScene();
        scene.getRoot().requestFocus();
        //for Attacker
        scene.getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.MINUS || event.getCode() ==
                    KeyCode.EQUALS && !atkEnd.get()) {
                atkInitCode.set(event.getCode());
                System.out.println(atkInitCode.get());
                atkXCoord.set(-102);
            }
        });
        scene.setOnKeyReleased(event -> {
            if ((atkInitCode.get() == KeyCode.MINUS || atkInitCode.get() ==
                    KeyCode.EQUALS) && (event.getCode() != KeyCode.MINUS &&
                    event.getCode() != KeyCode.EQUALS) && atkXCoord.get() < 0) {
                System.out.println("entered X");
                boolean doNothing = false;
                switch (event.getCode()) {
                    case DIGIT0:
                        atkXCoord.set(0);
                        break;
                    case DIGIT1:
                        atkXCoord.set(1);
                        break;
                    case DIGIT2:
                        atkXCoord.set(2);
                        break;
                    case DIGIT3:
                        atkXCoord.set(3);
                        break;
                    case DIGIT4:
                        atkXCoord.set(4);
                        break;
                    case DIGIT5:
                        atkXCoord.set(5);
                        break;
                    case DIGIT6:
                        atkXCoord.set(6);
                        break;
                    case DIGIT7:
                        atkXCoord.set(7);
                        break;
                    case DIGIT8:
                        atkXCoord.set(8);
                        break;
                    case DIGIT9:
                        atkXCoord.set(9);
                        break;
                    case NUMPAD0:
                    case NUMPAD1:
                    case NUMPAD2:
                    case NUMPAD3:
                    case NUMPAD4:
                    case NUMPAD5:
                    case NUMPAD6:
                    case NUMPAD7:
                    case NUMPAD8:
                    case NUMPAD9:
                    case MULTIPLY:
                    case DIVIDE:
                        doNothing = true;
                        break;
                    default:
                        atkXCoord.set(-100);
                        atkInitCode.set(KeyCode.K);
                        System.err.println("Bad Input from Attacker (x)");
                }
                if (atkInitCode.get() == KeyCode.EQUALS && !doNothing) {
                    atkXCoord.set(atkXCoord.get() + 10);
                }
                if (atkXCoord.get() < 0 && !doNothing) {
                    atkInitCode.set(KeyCode.K);
                }
            }
        });

        scene.getRoot().setOnKeyPressed(event -> {
            if ((atkInitCode.get() == KeyCode.MINUS || atkInitCode.get() ==
                    KeyCode.EQUALS) && !(event.getCode() == KeyCode.MINUS ||
                    event.getCode() == KeyCode.EQUALS) && atkXCoord.get() >= 0
                    && !atkEnd.get()) {
                System.out.println("entered Y");
                int atkYCoord = 0;
                boolean doNothing = false;
                switch (event.getCode()) {
                    case DIGIT0:
                        atkYCoord = 0;
                        break;
                    case DIGIT1:
                        atkYCoord = 1;
                        break;
                    case DIGIT2:
                        atkYCoord = 2;
                        break;
                    case DIGIT3:
                        atkYCoord = 3;
                        break;
                    case DIGIT4:
                        atkYCoord = 4;
                        break;
                    case DIGIT5:
                        atkYCoord = 5;
                        break;
                    case DIGIT6:
                        atkYCoord = 6;
                        break;
                    case DIGIT7:
                        atkYCoord = 7;
                        break;
                    case DIGIT8:
                        atkYCoord = 8;
                        break;
                    case DIGIT9:
                        atkYCoord = 9;
                        break;
                    case NUMPAD0:
                    case NUMPAD1:
                    case NUMPAD2:
                    case NUMPAD3:
                    case NUMPAD4:
                    case NUMPAD5:
                    case NUMPAD6:
                    case NUMPAD7:
                    case NUMPAD8:
                    case NUMPAD9:
                    case MULTIPLY:
                    case DIVIDE:
                        doNothing = true;
                        break;
                    default:
                        atkYCoord = -1000;
                        atkXCoord.set(-101);
                        atkInitCode.set(KeyCode.K);
                        System.err.println("Bad Input from Attacker (y)");
                        break;
                }
                if (atkYCoord >= 0 && !doNothing) {
                    System.out.println(atkInitCode.get() + " " + atkXCoord.get()
                            + " " + atkYCoord);
                    atkInitCode.set(KeyCode.K);
                    if ((atkXCoord.get() < board.getMap()[0].length) &&
                            (atkYCoord < board.getMap().length) && board.
                            getMap()[atkYCoord][atkXCoord.get()] == 'b' && board
                            .getElements()[atkYCoord][atkXCoord.get()] == '0') {
                        choiceFinalizer(attacker, view, board, atkXCoord, defEnd
                                , atkEnd, atkYCoord);
                    }
                }
            }
        });

        //for Defender
        scene.getRoot().addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.DIVIDE || event.getCode() ==
                    KeyCode.MULTIPLY && !defEnd.get()) {
                defInitCode.set(event.getCode());
                System.out.println(defInitCode.get());
                defXCoord.set(-102);
            }
        });
        scene.getRoot().setOnKeyReleased(event -> {
            if ((defInitCode.get() == KeyCode.DIVIDE || defInitCode.get() ==
                    KeyCode.MULTIPLY) && (event.getCode() != KeyCode.DIVIDE &&
                    event.getCode() != KeyCode.MULTIPLY) && defXCoord.get() < 0
            ) {
                System.out.println("entered X - def");
                boolean doNothing = false;
                switch (event.getCode()) {
                    case NUMPAD0:
                        defXCoord.set(0);
                        break;
                    case NUMPAD1:
                        defXCoord.set(1);
                        break;
                    case NUMPAD2:
                        defXCoord.set(2);
                        break;
                    case NUMPAD3:
                        defXCoord.set(3);
                        break;
                    case NUMPAD4:
                        defXCoord.set(4);
                        break;
                    case NUMPAD5:
                        defXCoord.set(5);
                        break;
                    case NUMPAD6:
                        defXCoord.set(6);
                        break;
                    case NUMPAD7:
                        defXCoord.set(7);
                        break;
                    case NUMPAD8:
                        defXCoord.set(8);
                        break;
                    case NUMPAD9:
                        defXCoord.set(9);
                        break;
                    case DIGIT0:
                    case DIGIT1:
                    case DIGIT2:
                    case DIGIT3:
                    case DIGIT4:
                    case DIGIT5:
                    case DIGIT6:
                    case DIGIT7:
                    case DIGIT8:
                    case DIGIT9:
                    case MINUS:
                    case EQUALS:
                        doNothing = true;
                        break;
                    default:
                        defXCoord.set(-100);
                        defInitCode.set(KeyCode.INSERT);
                        System.err.println("Bad Input from Defender (x)");
                }
                if (defInitCode.get() == KeyCode.MULTIPLY && !doNothing) {
                    defXCoord.set(defXCoord.get() + 10);
                }
                if (defXCoord.get() < 0 && !doNothing) {
                    defInitCode.set(KeyCode.INSERT);
                }
            }
        });

        scene.setOnKeyPressed(event -> {
            if ((defInitCode.get() == KeyCode.DIVIDE || defInitCode.get() ==
                    KeyCode.MULTIPLY) && !(event.getCode() == KeyCode.DIVIDE ||
                    event.getCode() == KeyCode.MULTIPLY) && defXCoord.get() >=
                    0 && !defEnd.get()) {
                System.out.println("entered Y - def");
                int defYCoord = 0;
                boolean doNothing = false;
                switch (event.getCode()) {
                    case NUMPAD0:
                        defYCoord = 0;
                        break;
                    case NUMPAD1:
                        defYCoord = 1;
                        break;
                    case NUMPAD2:
                        defYCoord = 2;
                        break;
                    case NUMPAD3:
                        defYCoord = 3;
                        break;
                    case NUMPAD4:
                        defYCoord = 4;
                        break;
                    case NUMPAD5:
                        defYCoord = 5;
                        break;
                    case NUMPAD6:
                        defYCoord = 6;
                        break;
                    case NUMPAD7:
                        defYCoord = 7;
                        break;
                    case NUMPAD8:
                        defYCoord = 8;
                        break;
                    case NUMPAD9:
                        defYCoord = 9;
                        break;
                    case DIGIT0:
                    case DIGIT1:
                    case DIGIT2:
                    case DIGIT3:
                    case DIGIT4:
                    case DIGIT5:
                    case DIGIT6:
                    case DIGIT7:
                    case DIGIT8:
                    case DIGIT9:
                    case MINUS:
                    case EQUALS:
                        doNothing = true;
                        break;
                    default:
                        defYCoord = -1000;
                        defXCoord.set(-101);
                        defInitCode.set(KeyCode.K);
                        System.err.println("Bad Input from Defender (y)");
                        break;
                }
                if (defYCoord >= 0 && !doNothing) {
                    defYCoord += board.getMap().length / 2;
                    System.out.println(defInitCode.get() + " " + defXCoord.get()
                            + " " + defYCoord);
                    defInitCode.set(KeyCode.INSERT);
                    if ((defXCoord.get() < board.getMap()[0].length) &&
                            (defYCoord < board.getMap().length) && board.
                            getMap()[defYCoord][defXCoord.get()] == 'B' && board
                            .getElements()[defYCoord][defXCoord.get()] == '0') {
                        choiceFinalizer(defender, view, board, defXCoord, atkEnd
                                , defEnd, defYCoord);
                    }
                }
            }
        });
    }

    private static void choiceFinalizer(Player player, View view, Board board,
                                        AtomicInteger xCoord, AtomicBoolean
                                                atkEnd, AtomicBoolean defEnd,
                                        int yCoord) {
        boolean end = GameController.addNextTower(board,
                xCoord.get(), yCoord, player);
        board.updateElements();
        board.updateAllObservers();
        if (end) {
            defEnd.set(true);
            if (atkEnd.get()) {
                GameController.gameHandler(board, view, 0);
            }
        }
    }

    /**
     * @param board
     * @param view
     */
    public static void soldierHandler(Board board, View view) {
        Scene scene = view.getScene();
        scene.getRoot().requestFocus();
        AtomicReference<SoldierKind> atkSoldierKind = new AtomicReference<>();
        AtomicReference<SoldierKind> defSoldierKind = new AtomicReference<>();
        scene.getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case A:
                    atkSoldierKind.set(board.getAttacker().getKinds().get(0));
                    break;
                case S:
                    atkSoldierKind.set(board.getAttacker().getKinds().get(1));
                    break;
                case D:
                    atkSoldierKind.set(board.getAttacker().getKinds().get(2));
                    break;
                case W:
                    atkSoldierKind.set(SoldierKind.NON);
                    break;
                case DIGIT0:
                case DIGIT1:
                case DIGIT2:
                case DIGIT3:
                case DIGIT4:
                case DIGIT5:
                case DIGIT6:
                case DIGIT7:
                case DIGIT8:
                case DIGIT9:
                    System.out.println("Ignoring Atk Choice in Filter");
                    break;
                default:
                    System.err.println("Bad Input");
            }
        });

        scene.setOnKeyReleased(event -> {
            int order = -10;
            boolean doNothing = false;
            switch (event.getCode()) {
                case DIGIT1:
                    order = 1;
                    break;
                case DIGIT2:
                    order = 2;
                    break;
                case DIGIT3:
                    order = 3;
                    break;
                case DIGIT4:
                    order = 4;
                    break;
                case DIGIT5:
                    order = 5;
                    break;
                case DIGIT6:
                    order = 6;
                    break;
                case DIGIT7:
                    order = 7;
                    break;
                case DIGIT8:
                    order = 8;
                    break;
                case DIGIT9:
                    order = 9;
                    break;
                case A:
                case S:
                case D:
                case W:
                case NUMPAD0:
                case NUMPAD1:
                case NUMPAD2:
                case NUMPAD3:
                case NUMPAD4:
                case NUMPAD5:
                case NUMPAD6:
                case NUMPAD7:
                case NUMPAD8:
                case NUMPAD9:
                case LEFT:
                case DOWN:
                case RIGHT:
                case UP:
                    doNothing = true;
                    break;
                default:
                    System.out.println("Bad Input order from ATK");
            }
            if (!doNothing) {
                atkConditionsHandler(board, atkSoldierKind, order);
            }
        });

        scene.getRoot().addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            switch (event.getCode()) {
                case LEFT:
                    defSoldierKind.set(board.getDefender().getKinds().get(0));
                    break;
                case DOWN:
                    defSoldierKind.set(board.getDefender().getKinds().get(1));
                    break;
                case RIGHT:
                    defSoldierKind.set(board.getDefender().getKinds().get(2));
                    break;
                case UP:
                    defSoldierKind.set(SoldierKind.NON);
                    break;
                case NUMPAD0:
                case NUMPAD1:
                case NUMPAD5:
                case NUMPAD7:
                case NUMPAD2:
                case NUMPAD3:
                case NUMPAD4:
                case NUMPAD6:
                case NUMPAD8:
                case NUMPAD9:
                    System.out.println("Ignoring Def Choice in Filter ");
                    break;
                default:
                    System.err.println("Bad Input");
            }
        });
        scene.setOnKeyPressed(event -> {
            int order = -12;
            boolean doNothing = false;
            switch (event.getCode()) {
                case NUMPAD1:
                    order = 1;
                    break;
                case NUMPAD2:
                    order = 2;
                    break;
                case NUMPAD3:
                    order = 3;
                    break;
                case NUMPAD4:
                    order = 4;
                    break;
                case NUMPAD5:
                    order = 5;
                    break;
                case NUMPAD6:
                    order = 6;
                    break;
                case NUMPAD7:
                    order = 7;
                    break;
                case NUMPAD8:
                    order = 8;
                    break;
                case NUMPAD9:
                    order = 9;
                    break;
                case LEFT:
                case DOWN:
                case RIGHT:
                case UP:
                case W:
                case A:
                case S:
                case D:
                case DIGIT0:
                case DIGIT1:
                case DIGIT2:
                case DIGIT3:
                case DIGIT4:
                case DIGIT5:
                case DIGIT6:
                case DIGIT7:
                case DIGIT8:
                case DIGIT9:
                    doNothing = true;
                    break;
                default:
                    System.out.println("Bad Input order from Def");
            }
            if (!doNothing) {
                defConditionsHandler(board, defSoldierKind, order);
            }
        });
    }

    /**
     * @param player
     * @param board
     * @param view
     */
    public static void atkTowerHandler(Player player, Board board, View view) {
        AtomicReference<KeyCode> atkInitCode = new AtomicReference<>();
        AtomicInteger atkXCoord = new AtomicInteger();
        AtomicBoolean atkEnd = new AtomicBoolean();
        atkXCoord.set(-2);
        atkInitCode.set(KeyCode.K);
        atkEnd.set(false);
        Scene scene = view.getScene();
        System.out.println("ATK TOWER Handler!");
        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY || event.getButton() ==
                    MouseButton.SECONDARY && !atkEnd.get()) {

                if (event.getButton() == MouseButton.PRIMARY) {
                    atkInitCode.set(KeyCode.MINUS);
                } else {
                    atkInitCode.set(KeyCode.EQUALS);
                }
                System.out.println(atkInitCode.get());
                atkXCoord.set(-102);
            }
        });
        scene.setOnKeyReleased(event -> {
            if ((atkInitCode.get() == KeyCode.MINUS || atkInitCode.get() ==
                    KeyCode.EQUALS) && (event.getCode() != KeyCode.MINUS &&
                    event.getCode() != KeyCode.EQUALS) && atkXCoord.get() < 0) {
                System.out.println("entered X");
                switch (event.getCode()) {
                    case DIGIT0:
                        atkXCoord.set(0);
                        break;
                    case DIGIT1:
                        atkXCoord.set(1);
                        break;
                    case DIGIT2:
                        atkXCoord.set(2);
                        break;
                    case DIGIT3:
                        atkXCoord.set(3);
                        break;
                    case DIGIT4:
                        atkXCoord.set(4);
                        break;
                    case DIGIT5:
                        atkXCoord.set(5);
                        break;
                    case DIGIT6:
                        atkXCoord.set(6);
                        break;
                    case DIGIT7:
                        atkXCoord.set(7);
                        break;
                    case DIGIT8:
                        atkXCoord.set(8);
                        break;
                    case DIGIT9:
                        atkXCoord.set(9);
                        break;
                    default:
                        atkXCoord.set(-100);
                        atkInitCode.set(KeyCode.K);
                        System.err.println("Bad Input from Player");
                }
                if (atkInitCode.get() == KeyCode.EQUALS) {
                    atkXCoord.set(atkXCoord.get() + 10);
                }
                if (atkXCoord.get() < 0) {
                    atkInitCode.set(KeyCode.K);
                }
            }
        });
        AtomicBoolean finished = new AtomicBoolean(false);
        scene.setOnKeyPressed(event -> {
            if ((atkInitCode.get() == KeyCode.MINUS || atkInitCode.get() ==
                    KeyCode.EQUALS) && !(event.getCode() == KeyCode.MINUS ||
                    event.getCode() == KeyCode.EQUALS) && atkXCoord.get() >= 0
                    && !atkEnd.get()) {
                System.out.println("entered Y");
                int atkYCoord = 0;
                switch (event.getCode()) {
                    case DIGIT0:
                        atkYCoord = 0;
                        break;
                    case DIGIT1:
                        atkYCoord = 1;
                        break;
                    case DIGIT2:
                        atkYCoord = 2;
                        break;
                    case DIGIT3:
                        atkYCoord = 3;
                        break;
                    case DIGIT4:
                        atkYCoord = 4;
                        break;
                    case DIGIT5:
                        atkYCoord = 5;
                        break;
                    case DIGIT6:
                        atkYCoord = 6;
                        break;
                    case DIGIT7:
                        atkYCoord = 7;
                        break;
                    case DIGIT8:
                        atkYCoord = 8;
                        break;
                    case DIGIT9:
                        atkYCoord = 9;
                        break;
                    default:
                        atkYCoord = -1000;
                        atkXCoord.set(-101);
                        atkInitCode.set(KeyCode.K);
                        System.err.println("Bad Input from Attacker (y)");
                        break;
                }
                if (atkYCoord >= 0) {
                    System.out.println(atkInitCode.get() + " " + atkXCoord.get()
                            + " " + atkYCoord);
                    atkInitCode.set(KeyCode.K);
                    if ((atkXCoord.get() < board.getMap()[0].length) &&
                            (atkYCoord < board.getMap().length) && board.
                            getMap()[atkYCoord][atkXCoord.get()] == 'b' && board
                            .getElements()[atkYCoord][atkXCoord.get()] == '0') {
                        if (!finished.get()) {
                            finished.set(GameController.addNextTower(board,
                                    atkXCoord.get(), atkYCoord, player));
                            board.updateElements();
                            board.updateAllObservers();
                            if (finished.get()) {
                                return;
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * @param player
     * @param board
     * @param view
     */
    public static void defTowerHandler(Player player, Board board, View view) {
        AtomicReference<KeyCode> defInitCode = new AtomicReference<>();
        AtomicInteger defXCoord = new AtomicInteger();
        AtomicBoolean defEnd = new AtomicBoolean();
        defEnd.set(false);
        defInitCode.set(KeyCode.INSERT);
        defXCoord.set(-1);
        Scene scene = view.getScene();
        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY || event.getButton() ==
                    MouseButton.SECONDARY && !defEnd.get()) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    defInitCode.set(KeyCode.DIVIDE);
                } else {
                    defInitCode.set(KeyCode.MULTIPLY);
                }
                System.out.println(defInitCode.get());
                defXCoord.set(-103);
            }
        });
        scene.setOnKeyReleased(event -> {
            if ((defInitCode.get() == KeyCode.DIVIDE || defInitCode.get() ==
                    KeyCode.MULTIPLY) && (event.getCode() != KeyCode.DIVIDE &&
                    event.getCode() != KeyCode.MULTIPLY) && defXCoord.get() < 0
            ) {
                System.out.println("entered X - def");
                switch (event.getCode()) {
                    case NUMPAD0:
                        defXCoord.set(0);
                        break;
                    case NUMPAD1:
                        defXCoord.set(1);
                        break;
                    case NUMPAD3:
                        defXCoord.set(3);
                        break;
                    case NUMPAD4:
                        defXCoord.set(4);
                        break;
                    case NUMPAD2:
                        defXCoord.set(2);
                        break;
                    case NUMPAD5:
                        defXCoord.set(5);
                        break;
                    case NUMPAD6:
                        defXCoord.set(6);
                        break;
                    case NUMPAD7:
                        defXCoord.set(7);
                        break;
                    case NUMPAD8:
                        defXCoord.set(8);
                        break;
                    case NUMPAD9:
                        defXCoord.set(9);
                        break;
                    default:
                        defXCoord.set(-100);
                        defInitCode.set(KeyCode.INSERT);
                        System.err.println("Bad Input from Defender (x)");
                }
                if (defInitCode.get() == KeyCode.MULTIPLY) {
                    defXCoord.set(defXCoord.get() + 10);
                }
                if (defXCoord.get() < 0) {
                    defInitCode.set(KeyCode.INSERT);
                }
            }
        });
        AtomicBoolean finished = new AtomicBoolean(false);
        scene.setOnKeyPressed(event -> {
            if ((defInitCode.get() == KeyCode.DIVIDE || defInitCode.get() ==
                    KeyCode.MULTIPLY) && !(event.getCode() == KeyCode.DIVIDE ||
                    event.getCode() == KeyCode.MULTIPLY) && defXCoord.get() >=
                    0 && !defEnd.get()) {
                System.out.println("entered Y - def");
                int defYCoord = 0;
                switch (event.getCode()) {
                    case NUMPAD0:
                        defYCoord = 0;
                        break;
                    case NUMPAD1:
                        defYCoord = 1;
                        break;
                    case NUMPAD2:
                        defYCoord = 2;
                        break;
                    case NUMPAD3:
                        defYCoord = 3;
                        break;
                    case NUMPAD4:
                        defYCoord = 4;
                        break;
                    case NUMPAD5:
                        defYCoord = 5;
                        break;
                    case NUMPAD6:
                        defYCoord = 6;
                        break;
                    case NUMPAD7:
                        defYCoord = 7;
                        break;
                    case NUMPAD8:
                        defYCoord = 8;
                        break;
                    case NUMPAD9:
                        defYCoord = 9;
                        break;
                    default:
                        defYCoord = -1001;
                        defXCoord.set(-104);
                        defInitCode.set(KeyCode.K);
                        System.err.println("Bad Input from Defender (y)");
                        break;
                }
                if (defYCoord >= 0) {
                    defYCoord += board.getMap().length / 2;
                    System.out.println(defInitCode.get() + " " + defXCoord.get()
                            + " " + defYCoord);
                    defInitCode.set(KeyCode.INSERT);
                    if ((defXCoord.get() < board.getMap()[0].length) &&
                            (defYCoord < board.getMap().length) && board.
                            getMap()[defYCoord][defXCoord.get()] == 'B' && board
                            .getElements()[defYCoord][defXCoord.get()] == '0') {
                        if (!finished.get()) {
                            finished.set(GameController.addNextTower(board,
                                    defXCoord.get(), defYCoord, player));
                            board.updateElements();
                            board.updateAllObservers();
                            if (finished.get()) {
                                return;
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * @param board
     * @param view
     * @param update
     */
    public static void atkSoldierHandler(Board board, View view,
                                         AtomicBoolean update) {
        Scene scene = view.getScene();
        AtomicReference<SoldierKind> atkSoldierKind = new AtomicReference<>();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    atkSoldierKind.set(board.getAttacker().getKinds().get(0));
                    break;
                case S:
                    atkSoldierKind.set(board.getAttacker().getKinds().get(1));
                    break;
                case D:
                    atkSoldierKind.set(board.getAttacker().getKinds().get(2));
                    break;
                case W:
                    atkSoldierKind.set(SoldierKind.NON);
                    break;
                default:
                    System.err.println("Bad Input");
            }
        });
        scene.setOnKeyReleased(event -> {
            int order = -10;
            switch (event.getCode()) {
                case DIGIT1:
                    order = 1;
                    break;
                case DIGIT2:
                    order = 2;
                    break;
                case DIGIT3:
                    order = 3;
                    break;
                case DIGIT4:
                    order = 4;
                    break;
                case DIGIT5:
                    order = 5;
                    break;
                case DIGIT6:
                    order = 6;
                    break;
                case DIGIT7:
                    order = 7;
                    break;
                case DIGIT8:
                    order = 8;
                    break;
                case DIGIT9:
                    order = 9;
                    break;
                default:
                    System.out.println("Bad Input");
            }
            atkConditionsHandler(board, atkSoldierKind, order, update);
        });
    }

    private static void atkConditionsHandler(Board board, AtomicReference
            <SoldierKind> soldierKind, int order, AtomicBoolean update) {
        if (soldierKind.get() == SoldierKind.NON) {
            atkConditionHandler(board, order);
        } else if (soldierKind.get() != null) {
            Soldier soldier =
                    SoldierKind.soldierFactory(soldierKind.get());
            Pair coord = GameController.coordinator(order, board, 'r');
            if ((int) coord.getKey() >= 0 && board.getElements()[(int)
                    coord.getValue()][(int) coord.getKey()] == '0' &&
                    board.getAttacker().getEnergy() >= soldier.getCost()) {
                board.getAttacker().decreaseEnergy(soldier.getCost());
                board.getAttacker().getSoldiers().add(soldier);
                GameController.addElement(board, soldier, coord);
                update.set(true);
            }
        }
        soldierKind.set(null);
    }

    private static void atkConditionsHandler(Board board,
                                             AtomicReference<SoldierKind>
                                                     soldierKind, int order) {
        if (soldierKind.get() == SoldierKind.NON) {
            atkConditionHandler(board, order);
        } else if (soldierKind.get() != null) {
            Soldier soldier =
                    SoldierKind.soldierFactory(soldierKind.get());
            Pair coord = GameController.coordinator(order, board, 'r');
            if ((int) coord.getKey() >= 0 && board.getElements()[(int)
                    coord.getValue()][(int) coord.getKey()] == '0' &&
                    board.getAttacker().getEnergy() >= soldier.getCost()) {
                board.getAttacker().decreaseEnergy(soldier.getCost());
                board.getAttacker().getSoldiers().add(soldier);
                GameController.addElement(board, soldier, coord);
            }
        }
        soldierKind.set(null);
    }

    private static void atkConditionHandler(Board board, int order) {
        List<Tower> towers = board.getAttacker().getTowers()
                .stream().filter(x -> !x.isActive() && x instanceof
                        SoldierTower).collect(Collectors.toList());
        if (!towers.isEmpty()) {
            Pair coord = GameController.coordinator(order, board, 'g');
            if ((int) coord.getKey() >= 0 && board.getElements()[(int)
                    coord.getValue()][(int) coord.getKey()] == '0') {
                GameController.addElement(board, towers.get(0), coord);
                board.getAttacker().setEnergyRate(board.getAttacker
                        ().getEnergyRate() / 4);
            }
        }
    }

    /**
     * @param board
     * @param view
     * @param update
     */
    public static void defSoldierHandler(Board board, View view,
                                         AtomicBoolean update) {
        Scene scene = view.getScene();
        AtomicReference<SoldierKind> defSoldierKind = new AtomicReference<>();
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    defSoldierKind.set(board.getDefender().getKinds().get(0));
                    break;
                case DOWN:
                    defSoldierKind.set(board.getDefender().getKinds().get(1));
                    break;
                case RIGHT:
                    defSoldierKind.set(board.getDefender().getKinds().get(2));
                    break;
                case UP:
                    defSoldierKind.set(SoldierKind.NON);
                    break;
                default:
                    System.err.println("Bad Input");
            }
        });
        scene.setOnKeyPressed(event -> {
            int order = -12;
            switch (event.getCode()) {
                case NUMPAD1:
                    order = 1;
                    break;
                case NUMPAD2:
                    order = 2;
                    break;
                case NUMPAD3:
                    order = 3;
                    break;
                case NUMPAD4:
                    order = 4;
                    break;
                case NUMPAD5:
                    order = 5;
                    break;
                case NUMPAD6:
                    order = 6;
                    break;
                case NUMPAD7:
                    order = 7;
                    break;
                case NUMPAD8:
                    order = 8;
                    break;
                case NUMPAD9:
                    order = 9;
                    break;
                default:
                    System.out.println("Bad Input order");
            }
            defConditionsHandler(board, defSoldierKind, order, update);
        });
    }

    private static void defConditionsHandler(Board board,
                                             AtomicReference<SoldierKind>
                                                     soldierKind, int order,
                                             AtomicBoolean update) {
        if (soldierKind.get() == SoldierKind.NON) {
            defConditionHandler(board, order);
        } else if (soldierKind.get() != null) {
            Soldier soldier =
                    SoldierKind.soldierFactory(soldierKind.get());
            Pair coord = GameController.coordinator(order, board, 'R');
            if ((int) coord.getKey() >= 0 && board.getElements()[(int)
                    coord.getValue()][(int) coord.getKey()] == '0' &&
                    board.getDefender().getEnergy() >=
                            soldier.getCost()) {
                board.getDefender().decreaseEnergy(soldier.getCost());
                board.getDefender().getSoldiers().add(soldier);
                GameController.addElement(board, soldier, coord);
                update.set(true);
            }
        }
        soldierKind.set(null);
    }

    private static void defConditionHandler(Board board, int order) {
        List<Tower> towers = board.getDefender().getTowers().
                stream().filter(x -> !x.isActive() && x instanceof
                SoldierTower).collect(Collectors.toList());
        if (!towers.isEmpty()) {
            Pair coord = GameController.coordinator(order, board,
                    'G');
            if ((int) coord.getKey() >= 0 && board.getElements()[
                    (int) coord.getValue()][(int) coord.getKey()] ==
                    '0') {
                GameController.addElement(board, towers.get(0),
                        coord);
                board.getDefender().setEnergyRate(board.getDefender
                        ().getEnergyRate() / 4);
            }
        }
    }


    private static void defConditionsHandler(Board board,
                                             AtomicReference<SoldierKind>
                                                     soldierKind, int order) {
        if (soldierKind.get() == SoldierKind.NON) {
            defConditionHandler(board, order);
        } else if (soldierKind.get() != null) {
            Soldier soldier =
                    SoldierKind.soldierFactory(soldierKind.get());
            Pair coord = GameController.coordinator(order, board, 'R');
            if ((int) coord.getKey() >= 0 && board.getElements()[(int)
                    coord.getValue()][(int) coord.getKey()] == '0' &&
                    board.getDefender().getEnergy() >=
                            soldier.getCost()) {
                board.getDefender().decreaseEnergy(soldier.getCost());
                board.getDefender().getSoldiers().add(soldier);
                GameController.addElement(board, soldier, coord);
            }
        }
        soldierKind.set(null);
    }
}