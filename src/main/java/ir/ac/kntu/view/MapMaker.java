package ir.ac.kntu.view;

import ir.ac.kntu.model.Map;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * @author TOP
 */
public final class MapMaker {

    private MapMaker() {
    }

    /**
     * @param mainScene
     */
    public static void sizeGetter(Scene mainScene) {
        Group root = new Group();
        root.getChildren().add(new ImageView(new Image("ir/ac/kntu/image" +
                "/bvs.jpg")));
        VBox vBox = new VBox();
        root.getChildren().addAll(vBox);
        Label info = GameBoard.labelMaker("Enter Width & Height of Your Map:",
                18, "transparent", "DarkBlue");
        TextField width = new TextField();
        TextField height = new TextField();
        width.setMaxWidth(200);
        height.setFocusTraversable(false);
        width.setFocusTraversable(false);
        height.setMaxWidth(200);
        width.setAlignment(Pos.CENTER);
        height.setAlignment(Pos.CENTER);
        GridPane.setHalignment(width, HPos.CENTER);
        GridPane.setHalignment(height, HPos.CENTER);
        width.setPromptText("Width..");
        height.setPromptText("Height..");
        View.MenuItem submit = new View.MenuItem("Submit", 199, 30);
        Stage getSize = new Stage();
        vBox.getChildren().addAll(info, width, height, submit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        Scene scene = new Scene(root, 286, 140, true,
                SceneAntialiasing.BALANCED);
        getSize.setScene(scene);
        getSize.setResizable(false);
        getSize.setTitle("Map Maker");
        getSize.show();
        submit.setOnMousePressed(event -> {
            int x = 0, y = 0;
            if (height.getText().trim().matches("([123456789])\\d*") && width
                    .getText().trim().matches("([123456789])\\d*") && (y =
                    Integer.parseInt(height.getText().trim())) < 21 && (x =
                    Integer.parseInt(width.getText().trim())) < 19) {
                getSize.close();
                Stage maker = new Stage();
                Scene newScene = new Scene(new Group(), 32.85 * x,
                        45 * y + 30);
                maker.setScene(newScene);
                maker.setResizable(false);
                maker.setTitle("Map Maker");
                maker.show();
                initializeMapMaker(maker, x, y);
            }
        });
    }

    private static void initializeMapMaker(Stage stage, int x, int y) {
        Group root = (Group) stage.getScene().getRoot();
        root.getChildren().clear();
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        GridPane grid = new GridPane();
        vBox.getChildren().addAll(grid, hBox);
        grid.setPadding(new Insets(8, 5, 8, 5));
        Label[][] labels = new Label[y][x];
        grid.setHgap(2d);
        grid.setVgap(1.5d);
        char[][] map = new char[y][x];
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                if (j < y / 2) {
                    labels[j][i] = GameBoard.labelMaker("0", 18, "DimGrey",
                            "DimGrey");
                } else {
                    labels[j][i] = GameBoard.labelMaker("0", 18, "DarkGrey",
                            "DarkGrey");
                }
                map[j][i] = '0';
            }
        }
        Platform.runLater(() -> {
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    int finalJ = j, finalI = i;
                    grid.add(labels[j][i], i, j);
                    labels[j][i].setOnMouseClicked(event -> {
                        System.out.println(Arrays.deepToString(map));
                        if (map[finalJ][finalI] == '0') {
                            map[finalJ][finalI] = 'y';
                            labelStyler(labels[finalJ][finalI], "yellow");
                        } else if (map[finalJ][finalI] == 'y') {
                            map[finalJ][finalI] = 'b';
                            labelStyler(labels[finalJ][finalI], "royalblue");
                        } else if (map[finalJ][finalI] == 'b') {
                            map[finalJ][finalI] = 'r';
                            labelStyler(labels[finalJ][finalI], "crimson");
                        } else if (map[finalJ][finalI] == 'r') {
                            map[finalJ][finalI] = 'g';
                            labelStyler(labels[finalJ][finalI], "Green");
                        } else {
                            if (finalJ < y / 2) {
                                map[finalJ][finalI] = '0';
                                labelStyler(labels[finalJ][finalI], "dimgrey");
                            } else {
                                map[finalJ][finalI] = '0';
                                labelStyler(labels[finalJ][finalI], "darkgrey");
                            }
                        }
                    });
                }
            }
        });
        TextField saveName = new TextField();
        saveName.setMinWidth(200);
        saveName.setFocusTraversable(false);
        saveName.setMinHeight(30);
        hBox.setPadding(new Insets(5));
        vBox.setSpacing(2);
        hBox.setSpacing(10);
        saveName.setPromptText("Name Of This Map..");
        View.MenuItem submit = new View.MenuItem("Finalize", 120, 30);
        submit.requestFocus();
        hBox.getChildren().addAll(saveName, submit);
        root.getChildren().addAll(new ImageView(new Image("ir/ac/kntu/image" +
                "/bvs.jpg")), vBox);
        submit.setOnMousePressed(event -> {
            if (saveName.getText().trim().length() > 1) {
                for (int j = 0; j < y; j++) {
                    for (int i = 0; i < x; i++) {
                        if (j >= y / 2 && map[j][i] != 0) {
                            map[j][i] = Character.toUpperCase(map[j][i]);
                        }
                    }
                }
                Map.mapSaver(map, "NEW-" + saveName.getText().trim());
                System.out.println("Map Created and Saved Successfully!!");
                stage.close();
            }
        });
    }

    /**
     * @param label
     * @param color
     */
    public static void labelStyler(Label label, String color) {
        label.setStyle("-fx-background-color:" + color + ";-fx-padding: 10px" +
                ";-fx-background-radius: 7%;-fx-text-fill:" + color);
    }
}
