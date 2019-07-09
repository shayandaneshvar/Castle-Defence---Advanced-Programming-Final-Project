package ir.ac.kntu.view;

import ir.ac.kntu.controller.Controller;
import ir.ac.kntu.controller.Network;
import ir.ac.kntu.controller.UserChoice;
import ir.ac.kntu.model.Board;
import ir.ac.kntu.model.Observable;
import ir.ac.kntu.model.Soldiers.SoldierKind;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author TOP
 */
public class View implements Observer {
    private Group root;
    private Scene scene;

    /**
     * @param root
     * @param scene
     */
    public View(Group root, Scene scene) {
        this.root = root;
        this.scene = scene;
    }

    private static GridPane cardsLoader() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setTranslateX(-10);
        grid.setPadding(new Insets(2, 5, 5, 5));
        grid.setVgap(2.5d);
        grid.setHgap(2d);
        grid.setMinSize(390, 940);
        Text text1 = new Text(" Melee Soldiers: 1.Goblin, 2.Shield, " +
                "3.Swordsman, 4.Knight");
        Text text2 = new Text(" Range Soldiers: 5.Archer, 6.Wizard, 7.Dragon");
        Text text3 = new Text("\t\t   Cost \t Health \t   Speed  \t    Damage" +
                "   \tRange\n\n Towers : Black, Electric, Inferno, " +
                "SoldierMaker");
        text2.setFont(Font.font("Pristina", 24));
        text1.setFont(Font.font("Pristina", 24));
        text2.setFill(Color.DARKRED);
        text1.setFill(Color.DARKRED);
        text3.setFill(Color.DARKRED);
        text3.setFont(Font.font("Pristina", 24));
        grid.add(text1, 0, 0, 6, 1);
        grid.add(soldierCardImageFactory(SoldierKind.GOBLIN), 0, 1, 1, 1);
        grid.add(soldierCardImageFactory(SoldierKind.SHIELD), 0, 2, 1, 1);
        grid.add(soldierCardImageFactory(SoldierKind.SWORDSMAN), 0, 3, 1, 1);
        grid.add(soldierCardImageFactory(SoldierKind.KNIGHT), 0, 4, 1, 1);
        grid.add(text2, 0, 5, 6, 1);
        grid.add(soldierCardImageFactory(SoldierKind.ARCHER), 0, 6, 1, 1);
        grid.add(soldierCardImageFactory(SoldierKind.WIZARD), 0, 7, 1, 1);
        grid.add(soldierCardImageFactory(SoldierKind.DRAGON), 0, 8, 1, 1);
        grid.add(text3, 0, 9, 6, 1);
        grid.add(textGenerator("10", 20), 1, 1);
        grid.add(textGenerator("200", 20), 2, 1);
        grid.add(textGenerator("3", 20), 3, 1);
        grid.add(textGenerator("250", 20), 4, 1);
        grid.add(textGenerator("1", 20), 5, 1);
        grid.add(textGenerator("10", 20), 1, 2);
        grid.add(textGenerator("1000", 20), 2, 2);
        grid.add(textGenerator("1", 20), 3, 2);
        grid.add(textGenerator("150", 20), 4, 2);
        grid.add(textGenerator("1", 20), 5, 2);
        grid.add(textGenerator("20", 20), 1, 3);
        grid.add(textGenerator("500", 20), 2, 3);
        grid.add(textGenerator("1", 20), 3, 3);
        grid.add(textGenerator("350", 20), 4, 3);
        grid.add(textGenerator("1", 20), 5, 3);
        grid.add(textGenerator("30", 20), 1, 4);
        grid.add(textGenerator("600", 20), 2, 4);
        grid.add(textGenerator("2", 20), 3, 4);
        grid.add(textGenerator("400", 20), 4, 4);
        grid.add(textGenerator("1", 20), 5, 4);
        grid.add(textGenerator("15", 20), 1, 6);
        grid.add(textGenerator("300", 20), 2, 6);
        grid.add(textGenerator("1", 20), 3, 6);
        grid.add(textGenerator("200", 20), 4, 6);
        grid.add(textGenerator("2", 20), 5, 6);
        grid.add(textGenerator("20", 20), 1, 7);
        grid.add(textGenerator("300", 20), 2, 7);
        grid.add(textGenerator("1", 20), 3, 7);
        grid.add(textGenerator("300", 20), 4, 7);
        grid.add(textGenerator("2", 20), 5, 7);
        grid.add(textGenerator("35", 20), 1, 8);
        grid.add(textGenerator("500", 20), 2, 8);
        grid.add(textGenerator("2", 20), 3, 8);
        grid.add(textGenerator("350", 20), 4, 8);
        grid.add(textGenerator("3", 20), 5, 8);
        grid.add(new ImageView(new Image("ir/ac/kntu/image/BlackTCard.png"))
                , 1, 10);
        grid.add(new ImageView(new Image("ir/ac/kntu/image/TeslaCard.png")), 2,
                10);
        grid.add(new ImageView(new Image("ir/ac/kntu/image/InfernoTCard.png"))
                , 3, 10);
        grid.add(new ImageView(new Image("ir/ac/kntu/image/SoldierCard.png")),
                4, 10);
        return grid;
    }

    /**
     * @param content
     * @param size
     * @return
     */
    public static Text textGenerator(String content, int size) {
        Text text = new Text("  " + content + "   ");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Pristina", size));
        return text;
    }

    /**
     * @param kind
     * @return
     */
    public static ImageView soldierCardImageFactory(SoldierKind kind) {
        ImageView imageView = new ImageView();
        switch (kind) {
            case ARCHER:
                imageView.setImage(new Image("ir/ac/kntu/image/ArcherCard" +
                        ".png"));
                break;
            case DRAGON:
                imageView.setImage(new Image("ir/ac/kntu/image/DragonCard" +
                        ".png"));
                break;
            case GOBLIN:
                imageView.setImage(new Image("ir/ac/kntu/image/GoblinsCard" +
                        ".png"));
                break;
            case KNIGHT:
                imageView.setImage(new Image("ir/ac/kntu/image/KnightCard" +
                        ".png"));
                break;
            case SHIELD:
                imageView.setImage(new Image("ir/ac/kntu/image/ShieldCard" +
                        ".png"));
                break;
            case WIZARD:
                imageView.setImage(new Image("ir/ac/kntu/image/WizardCard" +
                        ".png"));
                break;
            case SWORDSMAN:
                imageView.setImage(new Image("ir/ac/kntu/image/SwordCard" +
                        ".png"));
                break;
            default:
                try {
                    throw new ir.ac.kntu.model.UnknownTypeException(kind);
                } catch (ir.ac.kntu.model.UnknownTypeException e) {
                    e.printStackTrace();
                }
        }
        return imageView;
    }

    /**
     * @param controller
     */
    public static void networkStarter(Controller controller) {
        Group root = new Group();
        VBox vBox = new VBox();
        root.getChildren().addAll(new ImageView(new Image("ir/ac/kntu/image" +
                "/simpleG.jpg")), vBox);
        Stage stage = new Stage();
        Scene scene = new Scene(root, 234, 96, true,
                SceneAntialiasing.BALANCED);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Network Panel");
        stage.show();
        vBox.setPadding(new Insets(5));
        vBox.setSpacing(4);
        MenuItem player1 = new MenuItem("First Player", 234, 45);
        MenuItem player2 = new MenuItem("Second Player", 234, 45);
        vBox.getChildren().addAll(player1, player2);
        TextField textField = new TextField();
        textField.setPromptText("Goblin Shield Sword Knight Arch Wiz Dragon");
        textField.setFocusTraversable(false);
        Label label = GameBoard.labelMaker("Choose Your Soldiers:", 24,
                "transparent", "Navy");
        VBox vBox1 = new VBox();
        vBox1.setPadding(new Insets(5));
        vBox1.setSpacing(5);
        vBox1.getChildren().addAll(label, textField);
        List<SoldierKind> kinds = new ArrayList<>();
        player1.setOnMousePressed(event -> {
            root.getChildren().clear();
            root.getChildren().addAll(new ImageView(new Image("ir/ac/kntu" +
                    "/image/simpleG.jpg")), vBox1);
            stage.setWidth(256);
            stage.setX(100);
            root.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER && textField.getText().
                        trim().matches("([1234567]){3}\\d*")) {
                    stage.close();
                    Controller.addKinds(textField, kinds);
                    try {
                        Network.player1Starter(controller, kinds);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        player2.setOnMousePressed(event -> {
            root.getChildren().clear();
            root.getChildren().addAll(new ImageView(new Image("ir/ac/kntu" +
                    "/image/simpleG.jpg")), vBox1);
            stage.setWidth(256);
            stage.setHeight(120);
            root.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER && textField.getText().
                        trim().matches("([1234567]){3}\\d*")) {
                    stage.close();
                    Controller.addKinds(textField, kinds);
                    try {
                        Network.player2Starter(controller, kinds);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    /**
     * @param attacker
     * @param defender
     */
    public void drawInitBoard(TextField attacker, TextField defender) {
        root.getChildren().clear();
        ImageView backgroundImage = new ImageView(new Image("ir/ac/kntu" +
                "/image/bb.jpg"));
        Line line = new Line(0, 965, 0, 30);
        line.setTranslateX(485d);
        line.setStroke(Color.BROWN.darker());
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStrokeWidth(50);
        Label labelA = new Label("First Player");
        Label labelD = new Label("Second Player");
        labelD.setLayoutX(640d);
        labelA.setLayoutX(130d);
        ImageView keyboard = new ImageView(new Image("ir/ac/kntu/image" +
                "/keyboard.png"));
        keyboard.setX(45);
        keyboard.setY(712);
        keyboard.setScaleX(1.11d);
        keyboard.setScaleY(1.11d);
        labelA.setFont(Font.font("NPITerme", FontWeight.BOLD,
                FontPosture.ITALIC, 55));
        labelD.setFont(Font.font("NPITerme", FontWeight.BOLD,
                FontPosture.ITALIC, 55));
        attacker.setMinWidth(250d);
        defender.setMinWidth(250d);
        attacker.setTranslateX(110);
        attacker.setTranslateY(640);
        defender.setTranslateX(640);
        defender.setTranslateY(640);
        Text atkName = new Text("Enter Your Name here:\n\tUse Space to Submit");
        Text defName = new Text("Enter Your Name here:\n\tUse Enter to Submit");
        atkName.setFont(Font.font("Pristina", FontWeight.BOLD,
                FontPosture.ITALIC, 30));
        defName.setFont(Font.font("Pristina", FontWeight.BOLD,
                FontPosture.REGULAR, 30));
        atkName.setTranslateX(100);
        atkName.setTranslateY(560);
        defName.setTranslateX(620);
        defName.setTranslateY(560);
        Label atkInstructions = new Label(" To Submit Anything Use Space" +
                "\n To Enter Coordinates Use Numbers,\n To Enter the X " +
                "Coordinate\n You Should First Press - or =,\n Then you can " +
                "Enter a Number.\n Use WSAD Keys To Choose Cards\n\n Ex) - " +
                "and 0 means 0,\n     - and 9 means 9\n and = and 0 means 10," +
                "\n  also = and 3 means 13.");
        atkInstructions.setFont(Font.font("Bauhaus LT Medium",
                FontWeight.SEMI_BOLD, FontPosture.REGULAR, 24));
        Label defInstructions = new Label(" To Submit Anything Use Enter" +
                "\n To Enter Coordinates Use Numbers,\n To Enter the X " +
                "Coordinate\n You Should First Press / or *,\n Then you can " +
                "Enter a Number.\n Use Arrow Keys To Choose Cards\n\n Ex) / " +
                "and 0 means 0,\n     / and 9 means 9\n and * and 0 means 10," +
                "\n  also * and 3 means 13.");
        atkInstructions.setTextFill(Color.NAVY);
        defInstructions.setTextFill(Color.NAVY.darker());
        atkInstructions.setStyle("-fx-background-color: yellowgreen;" +
                "-fx-background-radius: 10%;-fx-padding: 8px;");
        defInstructions.setStyle("-fx-background-color: crimson;" +
                "-fx-background-radius: 10%;-fx-padding: 8px;");
        defInstructions.setFont(Font.font("Bauhaus LT Medium",
                FontWeight.SEMI_BOLD,
                FontPosture.REGULAR, 24));
        atkInstructions.setTranslateX(30);
        atkInstructions.setTranslateY(130);
        defInstructions.setTranslateX(560);
        defInstructions.setTranslateY(130);
        root.getChildren().addAll(backgroundImage, line, labelA, labelD,
                atkName, defName, attacker, defender, keyboard,
                atkInstructions, defInstructions);
    }

    /**
     * @param atkChoice
     * @param defChoice
     * @throws FileNotFoundException
     */
    public void drawSelectionBoard(TextField atkChoice, TextField defChoice)
            throws FileNotFoundException {
        root.getChildren().clear();
        HBox mainBox = new HBox();
        FileInputStream input = new FileInputStream("src/main/resources/ir" +
                "/ac/kntu/image/simpleG.jpg");
        Image image = new Image(input);
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainBox.setBackground(new Background(backgroundimage));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(15));
        mainBox.setMinSize(950d, 90d);
        VBox rightBox = new VBox();
        rightBox.setMinWidth(385d);
        Label label = new Label((" Enter Your Desired " +
                "Soldiers Numbers.\n Note:You Can Only Choose 3 Soldiers.\n " +
                "Examples) 123, 135, 267 \n After You Entered At Least 3 " +
                "Numbers\n Submit by Space/Enter. \n Space for 1st " +
                "Player.\n Enter for 2nd player."));
        label.setFont(Font.font("Bauhaus LT Medium", FontWeight.SEMI_BOLD,
                FontPosture.REGULAR, 24));
        label.setStyle("-fx-background-color: Cyan;-fx-background-radius: " +
                "8%;-fx-padding: 4px 8px 8px 8px;-fx-text-fill: Navy;");
        Label instructions = new Label("Instructions:\n Before The Game " +
                "Starts,\n You Have to Place All Of Your Towers\n On The map " +
                "except for the SoldierMaker,\n You Can put The soldierMaker " +
                "Tower\n on The Map After The game Starts,\n Note That By " +
                "Using The SoldierMaker \n Your Energy Points Making Speed \n" +
                " Decreases by 75%.\n You have One SoldierMaker Tower\n " +
                "Card,and 3 of Any Other Tower.\n For Making Any of The " +
                "Soldiers That\n You Chose,You Have to Spend The\n Required " +
                "Energy, But Soldiers Created\n By The SoldierMaker Are " +
                "Free!");
        instructions.setFont(Font.font("Bauhaus LT Medium",
                FontWeight.SEMI_BOLD, FontPosture.REGULAR, 24));
        instructions.setStyle("-fx-background-color: greenyellow;" +
                "-fx-background-radius: 8%;-fx-padding: 8px; -fx-text-fill: " +
                "Navy");
        Text creator = new Text("Created By S.Shayan Daneshvar");
        creator.setFill(Color.MAGENTA.darker());
        creator.setTextAlignment(TextAlignment.CENTER);
        creator.setFont(Font.font("NPITerme", 30));
        rightBox.getChildren().addAll(label, textGenerator("1st Play" +
                "er's Soldiers:", 24), atkChoice, textGenerator("2nd " +
                "Player's Soldiers:", 24), defChoice, instructions, creator);
        rightBox.setTranslateX(-70);
        rightBox.setSpacing(25d);
        VBox centerBox = new VBox();
        centerBox.setMinWidth(605d);
        GridPane centerGrid = cardsLoader();
        centerBox.getChildren().add(centerGrid);
        centerBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(centerBox, rightBox);
        root.getChildren().add(mainBox);
    }

    /**
     * @return
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * @param result
     */
    public void drawDecisionMenu(AtomicReference<UserChoice> result) {
        ImageView img = new ImageView(new Image("ir/ac/kntu/image/start.jpg"));
        root.getChildren().add(img);
        Title title = new Title("Castle Defence");
        title.setTranslateX(35);
        title.setTranslateY(200);
        MenuItem newGame = new MenuItem("New Game", 300, 45);
        MenuItem continueGame = new MenuItem("Continue", 300, 45);
        MenuItem mapMaker = new MenuItem("Map Maker", 300, 45);
        MenuItem network = new MenuItem("Network", 300, 45);
        MenuItem exit = new MenuItem("Exit", 300, 45);
        MenuBox vbox = new MenuBox(newGame, continueGame, mapMaker, network,
                exit);
        vbox.setTranslateX(70);
        vbox.setTranslateY(300);
        root.getChildren().addAll(title, vbox);
        newGame.setOnMouseReleased(event -> result.set(UserChoice.NEWGAME));
        continueGame.setOnMouseReleased(event ->
                result.set(UserChoice.CONTINUE));
        mapMaker.setOnMouseReleased(event -> result.set(UserChoice.MAPMAKER));
        network.setOnMouseReleased(event -> result.set(UserChoice.NETWORK));
        exit.setOnMouseReleased(event -> result.set(UserChoice.EXIT));
    }

    /**
     * @param changedObservable
     */
    @Override
    public void update(Observable changedObservable) {
        GameBoard.drawGameBoard(root, (Board) changedObservable);
    }

    private static class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(375, 60);
            bg.setStroke(Color.BLACK.darker());
            bg.setStrokeWidth(3);
            bg.setFill(null);
            Text text = new Text(name);
            text.setFill(Color.GRAY.darker());// TODO: 7/1/2019
            text.setFont(Font.font("NPITerme", FontWeight.SEMI_BOLD, 54));
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

    private static class MenuBox extends VBox {
        public MenuBox(MenuItem... items) {
            getChildren().add(createSeparator());
            for (MenuItem item : items) {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(210);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }

    /**
     *
     */
    public static class MenuItem extends StackPane {

        /**
         * @param name
         * @param width
         * @param height
         */
        public MenuItem(String name, int width, int height) {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true,
                    CycleMethod.NO_CYCLE, new Stop(0, Color.DARKBLUE),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.DARKRED));
            Rectangle bg = new Rectangle(width, height);
            bg.setOpacity(0.45);
            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("NPITerme", FontWeight.SEMI_BOLD,
                    28));
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                text.setFill(Color.WHITE);
                bg.setFill(gradient);

            });
            setOnMouseExited(event -> {
                text.setFill(Color.DARKGREY);
                bg.setFill(Color.BLACK);
            });
            setOnMousePressed(event -> {
                bg.setFill(Color.DARKVIOLET.darker());
                Audio.choice();
            });
            setOnMouseReleased(event -> bg.setFill(gradient));
        }
    }
}
