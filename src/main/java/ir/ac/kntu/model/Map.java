package ir.ac.kntu.model;

import java.io.*;

/**
 * @author S.Shayan Daneshvar
 * Utility Class For Map Related Stuff
 */
public final class Map {
    private Map() {
    }

    /**
     * Used for Saving A map
     *
     * @param map  an Array
     * @param name name Of the map
     */
    public static void mapSaver(char[][] map, String name) {
        File file = new File("src/main/resources/ir/ac/kntu/" + name + ".txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(file))) {

            bufferedWriter.write(map.length + "\n");
            bufferedWriter.write(map[0].length + "\n");

            for (int j = 0; j < map.length; j++) {
                for (int i = 0; i < map[0].length; i++) {
                    bufferedWriter.write((int) map[j][i] + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param name map name
     * @return the whole map which is a 2D Array of Characters
     */
    public static char[][] mapLoader(String name) {
        char[][] map;
        File file = new File("src/main/resources/ir/ac/kntu/" + name + ".txt");
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(file))) {
            map = new char[Integer.parseInt(bufferedReader.readLine())]
                    [Integer.parseInt(bufferedReader.readLine())];
            for (int j = 0; j < map.length; j++) {
                for (int i = 0; i < map[0].length; i++) {
                    map[j][i] =
                            (char) Integer.parseInt(bufferedReader.readLine());
                }
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
