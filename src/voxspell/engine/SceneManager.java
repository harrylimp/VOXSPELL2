package voxspell.engine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import voxspell.Voxspell;

import java.io.IOException;

/**
 * It is a static ('singleton') class with methods used to change the scene,
 * and manage scene background.
 */
public class SceneManager {
    private static Stage currentStage;

    /**
     * Set stage to edit
     */
    public static void setStage(Stage stage) {
        currentStage = stage;
    }

    /**
     * This is the standard background used throughout the application.
     * Calling this method sets it to this background.
     */
    public static Background makeBackground() {
        Image image = new Image(Voxspell.class.getResource("scenes/assets/trees.png").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        return background;
    }

    /**
     * This is the background used in the "Finding the Bee" game puzzle.
     */
    public static Background makeBeeBackground() {
        Image image = new Image(Voxspell.class.getResource("scenes/assets/bee.jpeg").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        return background;
    }

    /**
     * By specifying the fxmlDestination that you want to go to, the goTo method will
     * set the stage to the new stage defined by the destination.
     */
    public static void goTo(String fxmlDestination) {
        if (currentStage != null) {
            Stage stage;
            Parent root = null;
            stage = currentStage;

            try {
                root = FXMLLoader.load(Voxspell.class.getResource("scenes/" + fxmlDestination));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
