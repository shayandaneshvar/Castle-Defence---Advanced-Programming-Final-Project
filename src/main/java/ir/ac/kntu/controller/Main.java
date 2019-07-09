package ir.ac.kntu.controller;

import ir.ac.kntu.view.Audio;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author TOP
 */
public class Main extends Application {

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 994, 964, true,
                SceneAntialiasing.BALANCED);
        primaryStage.setTitle("Castle Defence");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Controller controller = new Controller(root, scene);
        scene.setFill(Color.LIGHTGRAY);
        Audio.initAudio();
        controller.initialize();
    }
}