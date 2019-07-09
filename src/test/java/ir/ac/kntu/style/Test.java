package ir.ac.kntu.style;

import ir.ac.kntu.controller.GameController;
import ir.ac.kntu.model.Map;
import org.junit.Assert;

/**
 *
 * @author TOP
 */
public class Test {
    private char[][] sampleMap = {
            {'0', '0', '0', '0', 'b', 'r', 'b', '0', 'b', 'r', 'b', '0', 'b',
                    'r', 'b', '0', '0', '0', '0'},
            {'b', 'b', 'b', 'b', 'b', 'y', 'b', '0', 'b', 'y', 'b', '0', 'b',
                    'y', 'b', 'b', 'b', 'b', 'b'},
            {'b', 'y', 'y', 'y', 'y', 'y', 'b', '0', 'b', 'y', 'b', '0', 'b',
                    'y', 'y', 'y', 'y', 'y', 'b'},
            {'b', 'y', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'y', 'b', 'b', 'b'
                    , 'b', 'b', 'b', 'b', 'y', 'b'},
            {'b', 'y', 'b', '0', 'b', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y'
                    , 'y', 'b', '0', 'b', 'y', 'b'},
            {'b', 'y', 'b', '0', 'b', 'y', 'b', 'b', 'b', 'g', 'b', 'b', 'b'
                    , 'y', 'b', '0', 'b', 'y', 'b'},
            {'b', 'y', 'b', '0', 'b', 'y', 'b', '0', '0', '0', '0', '0', 'b'
                    , 'y', 'b', '0', 'b', 'y', 'b'},
            {'b', 'y', 'b', '0', 'b', 'y', 'b', '0', '0', '0', '0', '0', 'b'
                    , 'y', 'b', '0', 'b', 'y', 'b'},
            {'0', 'y', '0', '0', '0', 'y', '0', '0', '0', '0', '0', '0', '0'
                    , 'y', '0', '0', '0', 'y', '0'},
            {'0', 'y', '0', '0', '0', 'y', '0', '0', '0', '0', '0', '0', '0'
                    , 'y', '0', '0', '0', 'y', '0'},
            {'0', 'Y', '0', '0', '0', 'Y', '0', '0', '0', '0', '0', '0', '0'
                    , 'Y', '0', '0', '0', 'Y', '0'},
            {'0', 'Y', '0', '0', '0', 'Y', '0', '0', '0', '0', '0', '0', '0'
                    , 'Y', '0', '0', '0', 'Y', '0'},
            {'B', 'Y', 'B', '0', 'B', 'Y', 'B', '0', '0', '0', '0', '0', 'B'
                    , 'Y', 'B', '0', 'B', 'Y', 'B'},
            {'B', 'Y', 'B', '0', 'B', 'Y', 'B', '0', '0', '0', '0', '0', 'B'
                    , 'Y', 'B', '0', 'B', 'Y', 'B'},
            {'B', 'Y', 'B', '0', 'B', 'Y', 'B', 'B', 'B', 'G', 'B', 'B', 'B'
                    , 'Y', 'B', '0', 'B', 'Y', 'B'},
            {'B', 'Y', 'B', '0', 'B', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'
                    , 'Y', 'B', '0', 'B', 'Y', 'B'},
            {'B', 'Y', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'Y', 'B', 'B', 'B'
                    , 'B', 'B', 'B', 'B', 'Y', 'B'},
            {'B', 'Y', 'Y', 'Y', 'Y', 'Y', 'B', '0', 'B', 'Y', 'B', '0', 'B'
                    , 'Y', 'Y', 'Y', 'Y', 'Y', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'Y', 'B', '0', 'B', 'Y', 'B', '0', 'B'
                    , 'Y', 'B', 'B', 'B', 'B', 'B'},
            {'0', '0', '0', '0', 'B', 'R', 'B', '0', 'B', 'R', 'B', '0', 'B'
                    , 'R', 'B', '0', '0', '0', '0'},
    };

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void mapSaverLoaderTester() {
        Map.mapSaver(sampleMap, "TEST-MAP");
        char[][] map = Map.mapLoader("TEST-MAP");
        boolean error = false;
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] != sampleMap[j][i]) {
                    error = true;
                    System.out.println(sampleMap[j][i]);
                    System.out.println(map[j][i]);
                }
            }
        }
        if (!error) {
            System.err.println("$$$ Passed $$$ - MapSaverLoader");
        }
        Assert.assertFalse(error);
    }

    /**
     *
     */
    @org.junit.jupiter.api.Test
    public void isInRangeTester() {
        boolean state;
        state = GameController.isInRange(1, 2, 2, 3, 3);
        Assert.assertFalse(String.valueOf(state), !state);
        state = GameController.isInRange(2, 2, 3, 2, 4);
        Assert.assertFalse(String.valueOf(state), !state);
        state = GameController.isInRange(2, 2, 3, 3, 4);
        Assert.assertFalse(String.valueOf(state), !state);
        state = GameController.isInRange(2, 2, 3, 4, 4);
        Assert.assertFalse(String.valueOf(state), !state);
        state = GameController.isInRange(2, 2, 3, 4, 5);
        Assert.assertFalse(String.valueOf(state), !state);
        state = GameController.isInRange(3, 5, 4, 2, 1);
        Assert.assertFalse(String.valueOf(state), !state);
        state = GameController.isInRange(3, 5, 4, 2, 2);
        Assert.assertFalse(String.valueOf(state), !state);
        state = GameController.isInRange(3, 5, 4, 2, 0);
        Assert.assertFalse(String.valueOf(state), state);
        state = GameController.isInRange(3, 5, 4, 0, 0);
        Assert.assertFalse(String.valueOf(state), state);
    }


}
