package ir.ac.kntu.controller;

import ir.ac.kntu.view.MapMaker;
import ir.ac.kntu.view.View;
import javafx.application.Platform;

/**
 * @author TOP
 */
public enum UserChoice {

    /**
     *
     */
    NEWGAME,

    /**
     *
     */
    CONTINUE,

    /**
     *
     */
    MAPMAKER,

    /**
     *
     */
    NETWORK,

    /**
     *
     */
    NON,

    /**
     *
     */
    EXIT;

    /**
     * @param userChoice
     * @param controller
     */
    public static void choiceFactory(UserChoice userChoice,
                                     Controller controller) {
        switch (userChoice) {
            case NEWGAME:
                controller.newGame();
                break;
            case CONTINUE:
                controller.gameLoader();
                break;
            case MAPMAKER:
                MapMaker.sizeGetter(controller.getView().getScene());
                break;
            case NETWORK:
                View.networkStarter(controller);
                break;
            case EXIT:
                Platform.exit();
                System.exit(1);
                break;
            default:
        }
    }
}
