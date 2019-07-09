package ir.ac.kntu.view;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A utility class for the game's sounds
 */
public final class Audio {
    private static List<Clip> clipList = new ArrayList<>();
    private static MediaPlayer mediaPlayer;

    private Audio() {
    }

    private static void playSound(String name) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream
                    (new File("src/main/resources/ir/ac/kntu/audio/" + name));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clipList.add(clip);
            clip.start();
        } catch (IOException | LineUnavailableException |
                UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param name
     */
    public static void playMedia(String name) {
        new Thread(() -> {
            Media hit = new Media(new File("src/main/resources/ir/ac/kntu/" +
                    "audio/" + name).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }).start();
    }

    /**
     *
     */
    public static void stopAllSounds() {
        clipList.stream().forEach(x -> x.stop());
    }

    /**
     *
     */
    public static void initAudio() {
        if (new Random().nextInt() % 3 != 0) {
            playSound("arena.wav");
        } else {
            playSound("aght.wav");
        }
    }

    /**
     *
     */
    public static void choice() {
        playSound("choice.wav");
    }

    /**
     *
     */
    public static void preparationAudio() {
        playSound("prepare.wav");
    }

    /**
     *
     */
    public static void starterAudio() {
        playSound("prepare4.wav");
    }

    /**
     *
     */
    public static void gameOver() {
        playSound("blade1.wav");
    }

    /**
     *
     */
    public static void counterAudio() {
        long start = System.currentTimeMillis();
        Thread one = new Thread(() -> {
            playSound("one.wav");
        });
        Thread two = new Thread(() -> {
            playSound("two.wav");
            while (System.currentTimeMillis() - start < 2000) {
                System.out.println("Counting 2");
            }
            one.start();
        });
        Thread three = new Thread(() -> {
            playSound("three.wav");
            while (System.currentTimeMillis() - start < 1400) {
                System.out.println("Counting 3");
            }
            two.start();
        });
        three.start();
    }

    /**
     *
     */
    public static void mainAudio() {
        playMedia("TreadofWar.mp3");
    }
}
