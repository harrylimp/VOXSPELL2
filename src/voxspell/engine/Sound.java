package voxspell.engine;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

/**
 * Static ('singleton') class used to play correct and incorrect sound effects
 *
 * Created by harrylimp on 24/10/16.
 */
public class Sound {

    /**
     * The file name of the sound effect is passed in as a string, and the method plays it
     */
    public static void playSoundEffect(String sound) {
        // enable the sound effect - only play it once
        File file = new File("./lib/sounds/" + sound);
        Media musicFile = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.setVolume(0.20);
        mediaPlayer.play();
    }

}
