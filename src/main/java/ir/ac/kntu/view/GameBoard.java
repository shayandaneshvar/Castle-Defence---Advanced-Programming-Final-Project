package ir.ac.kntu.view;

import ir.ac.kntu.model.Board;
import ir.ac.kntu.model.Player;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * @author TOP
 */
public final class GameBoard {

    /**
     * @param root
     * @param board
     */
    public static void drawGameBoard(Group root, Board board) {
        root.getChildren().clear();
        HBox upperBox = new HBox();
        upperBox.setPadding(new Insets(62, 0, 0, 0));
        GridPane grid = new GridPane();
        grid.setHgap(2d);
        grid.setVgap(1.5d);
        grid.setStyle("-fx-background-color: Black;-fx-border-color: Red;" +
                "-fx-border-style:Dashed;-fx-padding: 4px;-fx-border-radius: " +
                "2%;-fx-background-radius:2% ");
        grid.add(labelMaker("X", 18, "Wheat", "Wheat"), 0, 0, 1, 1);
        for (int i = 0; i < board.getMap().length; i++) {
            Label number =
                    labelMaker(String.valueOf(i % (board.getMap().length / 2)),
                            18, "WHEAT", "NAVY");

            grid.add(number, 0, i + 1, 1, 1);
        }
        for (int i = 0; i < board.getMap()[0].length; i++) {
            Label number =
                    labelMaker(String.valueOf(i % 10),
                            18, "WHEAT", "NAVY");

            grid.add(number, i + 1, 0, 1, 1);
        }
        for (int j = 0; j < board.getMap().length; j++) {
            for (int i = 0; i < board.getMap()[0].length; i++) {
                grid.add(cellHandler(board, j, i), i + 1, j + 1, 1, 1);
            }
        }
        VBox choosingPanel = new VBox();
        GridPane atkPane = rightPanelCreator(board.getAttacker(), true);
        GridPane defPane = rightPanelCreator(board.getDefender(), false);
        GridPane towers = towerImageCollector();
        Label info = labelMaker("Player Panel", 20, "DarkViolet", "White");
        choosingPanel.setStyle("-fx-background-color: DarkViolet;" +
                "-fx-background-radius: 2%;-fx-border-style: DASHED;" +
                "-fx-border-color:RED;-fx-border-radius: 2%;");
        Label saveHelp = labelMaker(" You Can Save The Game Whenever  \n You" +
                        " Want After Selecting Your \n Desired Towers By " +
                        "Pressing The \n Home Key, You Can Load It Later " +
                        "In \n The Main Menu By Selecting\n Continue. Map " +
                        "Maker is \n Available As Well !  ", 20, "Magenta",
                "White");
        choosingPanel.setSpacing(13d);
        choosingPanel.getChildren().addAll(info, atkPane, defPane, towers,
                saveHelp);
        upperBox.getChildren().addAll(grid, choosingPanel);
        ImageView imageView = new ImageView(new Image("ir/ac/kntu/image/bv" +
                ".jpg"));
        Label name = labelMaker("CASTLE DEFENCE", 34,
                "Transparent", "WHITE");
        name.setTranslateX(394);
        root.getChildren().addAll(imageView, name, upperBox);
    }

    private static GridPane towerImageCollector() {
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color:DeepPink;-fx-border-radius:4%;-" +
                "fx-padding:2px;-fx-border-style: SOLID;-fx-border-" +
                "color:BLACK;-fx-background-radius: 4%;");
        Node node;
        grid.add(node = labelMaker("Main Towers", 18, "Cyan",
                "Navy"), 0, 0,
                4, 1);
        GridPane.setHalignment(node, HPos.CENTER);
        grid.add(new ImageView(new Image("ir/ac/kntu/image/BlackTCard.png"))
                , 0, 1, 1, 1);
        grid.add(new ImageView(new Image("ir/ac/kntu/image/TeslaCard.png"))
                , 1, 1, 1, 1);
        grid.add(new ImageView(new Image("ir/ac/kntu/image/InfernoTCard.png"))
                , 2, 1, 1, 1);
        grid.add(new ImageView(new Image("ir/ac/kntu/image/SoldierCard.png"))
                , 3, 1, 1, 1);
        grid.add(node = labelMaker("Black", 16, "GreenYellow", "Black" +
                " "), 0, 2, 1, 1);
        GridPane.setHalignment(node, HPos.CENTER);
        grid.add(node = labelMaker("Electric", 16, "GreenYellow", "BLUE" +
                " "), 1, 2, 1, 1);
        GridPane.setHalignment(node, HPos.CENTER);
        grid.add(node = labelMaker("Inferno", 16, "GreenYellow", "RED" +
                " "), 2, 2, 1, 1);
        GridPane.setHalignment(node, HPos.CENTER);
        grid.add(node = labelMaker("Military", 16, "GreenYellow", "Olive" +
                " "), 3, 2, 1, 1);
        GridPane.setHalignment(node, HPos.CENTER);
        grid.setVgap(5d);
        return grid;
    }

    private static GridPane rightPanelCreator(Player player, boolean up) {
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color:RoyalBlue;-fx-border-radius:4%;-" +
                "fx-padding:1px;-fx-border-style: SOLID;-fx-border-" +
                "color:BLACK;-fx-background-radius: 4%;");
        for (int i = 0; i < player.getKinds().size(); i++) {
            grid.add(View.soldierCardImageFactory(player.getKinds().get(i))
                    , i, 1, 1, 1);
        }
        grid.add(new ImageView(new Image("ir/ac/kntu/image/SoldierCard.png"))
                , player.getKinds().size(), 1, 1, 1);
        grid.setVgap(5d);
        ProgressBar progressBar =
                new ProgressBar((double) player.getEnergy() / 100d);
        Node node;
        if (up) {
            progressBar.setStyle("-fx-accent:YellowGreen");
            grid.add(node = labelMaker(player.getName(), 18, "Turquoise",
                    "Navy"), 0, 0,
                    player.getKinds().size() + 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
            grid.add(node = labelMaker("A", 16, "LightGreen", "Navy" +
                    " "), 0, 2, 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
            grid.add(node = labelMaker("S", 16, "LightGreen", "Navy" +
                    " "), 1, 2, 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
            grid.add(node = labelMaker("D", 16, "LightGreen", "Navy" +
                    " "), 2, 2, 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
            grid.add(node = labelMaker("W", 16, "LightGreen", "Navy" +
                    " "), 3, 2, 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
        } else {
            progressBar.setStyle("-fx-accent:MediumVioletRed");
            grid.add(node = labelMaker(player.getName(), 18, "Plum", "Navy" +
                            " "), 0, 0,
                    player.getKinds().size() + 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
            grid.add(node = labelMaker("Lf", 16, "PaleVioletRed", "Navy" +
                    " "), 0, 2, 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
            grid.add(node = labelMaker("Dn", 16, "PaleVioletRed", "Navy" +
                    " "), 1, 2, 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
            grid.add(node = labelMaker("Rt", 16, "PaleVioletRed", "Navy" +
                    " "), 2, 2, 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
            grid.add(node = labelMaker("Up", 16, "PaleVioletRed", "Navy" +
                    " "), 3, 2, 1, 1);
            GridPane.setHalignment(node, HPos.CENTER);
        }
        Rectangle life = new Rectangle(8 + 5 * player.getHealth() / 100d, 17);
        life.setArcWidth(10);
        life.setArcHeight(10);
        life.setTranslateX(20);
        life.setFill(Color.rgb(255 - Math.abs(2 * player.getHealth() / 10) %
                256, Math.abs(55 + 2 * player.getHealth() / 10) % 256, 5));
        progressBar.setScaleX(2.6d);
        grid.add(progressBar, 1, 3, player.getKinds().size() + 1, 1);
        grid.add(life, 3, 3, 1, 1);
        GridPane.setValignment(progressBar, VPos.CENTER);
        return grid;
    }

    private static Node cellHandler(Board board, int j, int i) {
        Node cell;
        if (board.getElements()[j][i] != '0') {
            switch (board.getElements()[j][i]) {
                case 'A':
                    cell = imageFinder("archer1.gif");
                    break;
                case 'a':
                    cell = imageFinder("archer2.gif");
                    break;
                case 'B':
                case 'b':
                    cell = imageFinder("BlackTower.png");
                    break;
                case 'D':
                    cell = imageFinder("dragon1.gif");
                    break;
                case 'd':
                    cell = imageFinder("dragon2.gif");
                    break;
                case 'E':
                case 'e':
                    cell = imageFinder("TeslaTower.png");
                    break;
                case 'G':
                    cell = imageFinder("goblin1.gif");
                    break;
                case 'g':
                    cell = imageFinder("goblin2.gif");
                    break;
                case 'I':
                case 'i':
                    cell = imageFinder("InfernoTower.png");
                    break;
                case 'K':
                    cell = imageFinder("Knight1.gif");
                    break;
                case 'k':
                    cell = imageFinder("Knight2.gif");
                    break;
                case 'P':
                    cell = imageFinder("Shield1.gif");
                    break;
                case 'p':
                    cell = imageFinder("Shield2.gif");
                    break;
                case 'M':
                case 'm':
                    cell = imageFinder("soldierTower.gif");
                    break;
                case 'S':
                    cell = imageFinder("Swordsman1.gif");
                    break;
                case 's':
                    cell = imageFinder("Swordsman2.gif");
                    break;
                case 'W':
                    cell = imageFinder("Wizard1.gif");
                    break;
                case 'w':
                    cell = imageFinder("Wizard2.gif");
                    break;
                default:
                    cell = labelMaker("Y", 18, "RED", "Cyan");
            }
        } else {
            switch (board.getMap()[j][i]) {
                case '0':
                    cell = labelMaker("0", 18, "DimGREY", "DimGrey");
                    break;
                case 'b':
                    cell = labelMaker("0", 18, "DeepSkyBlue", "DeepSkyBlue");
                    break;
                case 'B':
                    cell = labelMaker("0", 18, "DodgerBlue", "DodgerBlue");
                    break;
                case 'Y':
                    cell = labelMaker("0", 18, "Gold", "Gold");
                    break;
                case 'y':
                    cell = labelMaker("0", 18, "Yellow", "Yellow");
                    break;
                case 'r':
                    cell = labelMaker("0", 18, "FireBrick", "FireBrick");
                    break;
                case 'R':
                    cell = labelMaker("0", 18, "DarkRed", "DarkRed");
                    break;
                case 'g':
                    cell = labelMaker("0", 18, "Green", "Green");
                    break;
                case 'G':
                    cell = labelMaker("0", 18, "ForestGreen", "ForestGreen");
                    break;
                default:
                    cell = labelMaker("X", 18, "Magenta", "Violet");
            }
        }
        return cell;
    }

    private static ImageView imageFinder(String name) {
        Image image = new Image("ir/ac/kntu/image/" + name);
        ImageView imageView = new ImageView(image);
        if (!name.contains("Tower")) {
            imageView.setRotate(90);//270
        }
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    /**
     * @param scene
     * @param text
     */
    public static void gameOver(Scene scene, String text) {
        Audio.gameOver();
        Platform.runLater(() -> {
            Group root = (Group) scene.getRoot();
            root.getChildren().clear();
            scene.setFill(Color.BLACK);
            GridPane mainPane = new GridPane();
            Label gameOver = labelMaker("Game Over", 190, "transparent",
                    "Crimson");
            Label winner = labelMaker(text, 100, "transparent", "Navy");
            winner.setAlignment(Pos.CENTER);
            gameOver.setAlignment(Pos.CENTER);
            Image image = new Image("ir/ac/kntu/image/end.jpg");
            ImageView imageView = new ImageView(image);
            mainPane.add(gameOver, 0, 0);
            mainPane.add(winner, 0, 1);
            GridPane.setValignment(winner, VPos.CENTER);
            GridPane.setValignment(gameOver, VPos.CENTER);
            mainPane.setPadding(new Insets(5));
            root.getChildren().addAll(imageView, mainPane);
        });
    }

    /**
     * @param string
     * @param size
     * @param bColor
     * @param fColor
     * @return
     */
    public static Label labelMaker(String string, int size, String bColor,
                                   String fColor) {
        Label label = new Label(string);
        label.setFont(Font.font("Bauhaus LT Medium", FontWeight.SEMI_BOLD,
                FontPosture.REGULAR, size));
        label.setStyle("-fx-background-color:" + bColor + ";-fx-padding: 10px" +
                ";-fx-background-radius: 7%;-fx-text-fill:" + fColor);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private GameBoard() {
    }
}
