package ir.ac.kntu.controller;

import ir.ac.kntu.model.Board;
import ir.ac.kntu.model.Player;
import ir.ac.kntu.model.Soldiers.SoldierKind;
import ir.ac.kntu.model.Towers.*;
import ir.ac.kntu.view.Audio;
import ir.ac.kntu.view.View;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author TOP
 */
public class Controller {
    private View view;

    /**
     * @param root
     * @param scene
     */
    public Controller(Group root, Scene scene) {
        this.view = new View(root, scene);
    }

    /**
     * @return
     */
    public static List<Tower> towerGenerator() {
        List<Tower> towers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BlackTower blackTower = new BlackTower();
            towers.add(blackTower);
        }
        for (int i = 0; i < 3; i++) {
            ElectricTower electricTower = new ElectricTower();
            towers.add(electricTower);
        }
        for (int i = 0; i < 3; i++) {
            InfernoTower infernoTower = new InfernoTower();
            towers.add(infernoTower);
        }
        SoldierTower soldierTower = new SoldierTower();
        towers.add(soldierTower);
        return towers;
    }

    /**
     * @param choice
     * @param kinds
     */
    public static void addKinds(TextField choice, List<SoldierKind> kinds) {
        kinds.add(SoldierKind.values()[String.valueOf(choice.
                getCharacters()).trim().charAt(0) - '1']);
        kinds.add(SoldierKind.values()[String.valueOf(choice.
                getCharacters()).trim().charAt(1) - '1']);
        kinds.add(SoldierKind.values()[String.valueOf(choice.
                getCharacters()).trim().charAt(2) - '1']);
    }

    /**
     *
     */
    public void gameLoader() {
        try {
            FileInputStream fileOutputStream = new FileInputStream("save");
            ObjectInputStream input = new ObjectInputStream(fileOutputStream);
            Board board = (Board) input.readObject();
            long cycle = input.readLong();
            board.addObserver(view);
            GameController.gameHandler(board, view, cycle);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void initialize() {
        AtomicReference<UserChoice> result =
                new AtomicReference<>(UserChoice.NON);
        view.drawDecisionMenu(result);
        Thread thread = new Thread(() -> {
            while (result.get() == UserChoice.NON) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> {
                initialize();
                UserChoice.choiceFactory(result.get(), this);
            });
        });
        thread.start();
    }

    /**
     *
     */
    public void newGame() {
        TextField attacker = new TextField();
        TextField defender = new TextField();
        final String[] names = new String[2];
        AtomicBoolean atk = new AtomicBoolean(false);
        AtomicBoolean def = new AtomicBoolean(false);
        view.drawInitBoard(attacker, defender);
        attacker.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE && !atk.get()) {
                names[0] = String.valueOf(attacker.getCharacters());
                atk.set(true);
                if (def.get()) {
                    try {
                        handleCardsMenu(names[0], names[1]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        defender.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !def.get()) {
                names[1] = String.valueOf(defender.getCharacters());
                def.set(true);
                if (atk.get()) {
                    try {
                        handleCardsMenu(names[0], names[1]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void handleCardsMenu(String atk, String def)
            throws FileNotFoundException {
        Audio.preparationAudio();
        TextField atkChoice = new TextField();
        TextField defChoice = new TextField();
        List<SoldierKind> atkKinds = new ArrayList<>();
        List<SoldierKind> defKinds = new ArrayList<>();
        view.drawSelectionBoard(atkChoice, defChoice);
        atkChoice.setOnKeyPressed(event -> {
            if ((event.getCode() == KeyCode.SPACE) && String.valueOf(atkChoice.
                    getCharacters()).trim().matches("([1234567]){3}\\d*")
                    && atkKinds.size() < 3) {
                addKinds(atkChoice, atkKinds);
                if (defKinds.size() > 2) {
                    GameController.gameStarter(new Player(atk, 0, 1000,
                            atkKinds, towerGenerator()), new Player(def, 0,
                            1000,
                            defKinds, towerGenerator()), view);
                }
            }
        });
        defChoice.setOnKeyPressed(event -> {
            if ((event.getCode() == KeyCode.ENTER) && String.valueOf(defChoice.
                    getCharacters()).trim().matches("([1234567]){3}\\d*")
                    && defKinds.size() < 3) {
                addKinds(defChoice, defKinds);
                if (atkKinds.size() > 2) {
                    GameController.gameStarter(new Player(atk, 0, 1000,
                            atkKinds, towerGenerator()), new Player(def, 0,
                            1000, defKinds, towerGenerator()), view);
                }
            }
        });
    }

    /**
     * @return
     */
    public View getView() {
        return view;
    }
}