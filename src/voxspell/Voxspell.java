package voxspell;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import voxspell.engine.SceneManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * This is the main class used to initially launch the application. This loads the background
 * music to be played, and loads the "main.fxml" as the first screen to be displayed.
 */
public class Voxspell extends Application {

    // Defining the size of the window
    private static int WINDOW_WIDTH = 600;
    private static int WINDOW_HEIGHT = 400;

    /**
     * Called when launching the application. This is where the stage is set
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // set the stage as "main.fxml" which is the main menu
        SceneManager.setStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("scenes/main.fxml"));
        primaryStage.setTitle("VOXSPELL");
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();

        // enable background music - open throughout the entire program
        File file = new File("./lib/sounds/song.mp3");
        Media musicFile = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.15);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        // plays on repeat
        mediaPlayer.onRepeatProperty();

    }

    /**
     * Launches the application
     */
    public static void main(String[] args) {

        try {
            Class<?> macFontFinderClass = Class.forName("com.sun.t2k.MacFontFinder");
            Field psNameToPathMap = macFontFinderClass.getDeclaredField("psNameToPathMap");
            psNameToPathMap.setAccessible(true);
            psNameToPathMap.set(null, new HashMap<String, String>());
        } catch (Exception e) {
            // ignore
        }

        launch(args);
    }
}
