package ir.ac.kntu.controller;

import ir.ac.kntu.model.Board;
import ir.ac.kntu.model.Map;
import ir.ac.kntu.model.Player;
import ir.ac.kntu.model.Soldiers.SoldierKind;
import ir.ac.kntu.view.Audio;
import ir.ac.kntu.view.View;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author TOP
 */
public final class Network {
    private static final int PORT = 54322;
    private static ServerSocket serverSocket = null;
    private static Socket socket = null;
    private static Board board = null;
    private static View view = null;
    private static AtomicBoolean update = new AtomicBoolean(false);

    private Network() {
    }

    /**
     * @param controller
     * @param kinds
     * @throws IOException
     * @throws InterruptedException
     */
    public static void player1Starter(Controller controller, List<SoldierKind>
            kinds) throws IOException, InterruptedException {
        view = controller.getView();
        Player player1 = new Player("Player 1", 0, 1000, kinds,
                Controller.towerGenerator());
        AtomicReference<Player> player2 = new AtomicReference<>();
        serverInitializer();
        Thread starter = new Thread(() -> {
            try {
                ObjectInputStream ois =
                        new ObjectInputStream(socket.getInputStream());
                player2.set((Player) ois.readObject());
                System.out.println("Player2 Received");
                Audio.starterAudio();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

        });
        starter.start();
        starter.join();
        board = new Board(Map.mapLoader("map"), player1, player2.get());
        board.addObserver(controller.getView());
        Thread towerInit = new Thread(() -> {
            try {
                ObjectOutputStream oos =
                        new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(board);
                oos.flush();
                InputController.atkTowerHandler(board.getAttacker(), board,
                        controller.getView());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        towerInit.start();
        towerInit.join();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handlePlayer1();
            }
        };
        timer.schedule(task, 35 * 1000);
    }

    /**
     * @param controller
     * @param kinds
     * @throws IOException
     * @throws InterruptedException
     */
    public static void player2Starter(Controller controller, List<SoldierKind>
            kinds) throws IOException, InterruptedException {
        view = controller.getView();
        Player player2 = new Player("Player 2", 0, 1000, kinds,
                Controller.towerGenerator());
        clientInitializer();
        Thread starter = new Thread(() -> {
            try {
                ObjectOutputStream oos =
                        new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(player2);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        starter.start();
        starter.join();
        Audio.starterAudio();
        Thread towerInit = new Thread(() -> {
            try {
                ObjectInputStream objectInputStream =
                        new ObjectInputStream(socket.getInputStream());
                board = (Board) objectInputStream.readObject();
                Platform.runLater(() ->
                        board.addObserver(controller.getView()));
                InputController.defTowerHandler(board.getDefender(), board,
                        controller.getView());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        towerInit.start();
        towerInit.join();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handlePlayer2();
            }
        };
        timer.schedule(task, 35 * 1000);
    }

    private static void clientInitializer() throws IOException {
        socket = new Socket("127.0.0.1", PORT);
        System.out.println("Client Connected To Server Successfully!");
    }

    private static void serverInitializer() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server Is Waiting...");
        socket = serverSocket.accept();
        System.out.println("Server Accepted Client..");

    }

    private static void handlePlayer2() {
        Thread initReceiver = new Thread(() -> {
            try {
                ObjectInputStream ois =
                        new ObjectInputStream(socket.getInputStream());
                Player atk = (Player) ois.readObject();
                System.out.println("Client Received Update");
                board.getAttacker().updatePlayer(atk);
                System.out.println("Client Handled The Update");
                System.out.println(atk);
                System.out.println(board.getAttacker());
                board.updateElements();
                Platform.runLater(() -> board.updateAllObservers());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        initReceiver.start();
        try {
            initReceiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread initSender = new Thread(() -> {
            try {
                ObjectOutputStream oos =
                        new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(board);
                System.out.println("Sending Updated board to Server");
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        initSender.start();
        try {
            initSender.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InputController.defSoldierHandler(board, view, update);
        updater(1300, 600);
//        receiver(600);
//        updater(1000);
    }

    private static void handlePlayer1() {
        Thread initSender = new Thread(() -> {
            try {
                ObjectOutputStream oos =
                        new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(board.getAttacker());
                System.out.println("Server Is Sending Update");
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        initSender.start();
        try {
            initSender.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread initReceiver = new Thread(() -> {
            try {
                ObjectInputStream ois =
                        new ObjectInputStream(socket.getInputStream());
                System.out.println("Server Received The Update");
                board = (Board) ois.readObject();
                Platform.runLater(() -> board.addObserver(view));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        initReceiver.start();
        try {
            initReceiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameCycle cycle = new GameCycle(1400, board, view, 0);
        InputController.atkSoldierHandler(board, view, update);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                cycle.start();
            }
        };
        timer.schedule(task, 10);
        updater(600, 1300);
//        sender(1300);//sending changed cycle
//        updater(1000);
//        receiver(1200);
    }

    private static void sender(int sendingDelay) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };
        timer.scheduleAtFixedRate(task, 0, sendingDelay);
    }

    private static void receiver(int receivingDelay) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Thread receiver = new Thread(() -> {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(socket
                                .getInputStream());
                        Board updatedBoard = (Board) ois.readObject();
                        board.updateBoard(updatedBoard);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                receiver.start();
            }
        };
        timer.scheduleAtFixedRate(task, 400, receivingDelay);
    }

    //
    private static void updater(int sendingDelay, int receivingDelay) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (/*update.get() || serverSocket != null*/true) {
                    update();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, sendingDelay);
        Timer timer1 = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                if (/*!update.get()*/true) {
                    Thread receiver = new Thread(() -> {
                        try {
                            ObjectInputStream ois = new ObjectInputStream(socket
                                    .getInputStream());
                            Board updatedBoard = (Board) ois.readObject();
                            board.updateBoard(updatedBoard);
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                    receiver.start();
                }
            }
        };
        timer1.scheduleAtFixedRate(task1, 0, receivingDelay);
    }

    private static void update() {
        Thread sender = new Thread(() -> {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(socket
                        .getOutputStream());
                oos.writeObject(board);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sender.start();
    }
}
