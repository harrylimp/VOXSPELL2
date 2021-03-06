package voxspell.scenes;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import voxspell.engine.DataIO;
import voxspell.engine.DesktopApi;
import voxspell.engine.LevelData;
import voxspell.engine.SceneManager;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * MainController class for the application entry / level selection screen (main.fxml)
 */
public class MainController implements Initializable {

    // static fields that store the settings and colours of the level buttons
    private static final String BASE = "-fx-border-color: rgb(31,65,9); -fx-border-width: 5px; -fx-border-radius: 1px; -fx-background-color: #b6e7c9; ";
    private static final String ON_HOVER = "-fx-background-color: #83B496;";
    private static final String ON_EXIT = "-fx-background-color: #b6e7c9;";

    private static final String NZ_VOICE_CHOICE = "NZ Voice";
    private static final String DEFAULT_VOICE_CHOICE = "Default Voice";
    private ArrayList<Button> buttons = new ArrayList<Button>();

    // data IO
    DataIO data = new DataIO();

    // initialize buttons from FXML
    @FXML
    private VBox vBox;
    @FXML
    private Button level1;
    @FXML
    private Button level2;
    @FXML
    private Button level3;
    @FXML
    private Button level4;
    @FXML
    private Button level5;
    @FXML
    private Button level6;
    @FXML
    private Button level7;
    @FXML
    private Button level8;
    @FXML
    private Button level9;
    @FXML
    private Button level10;
    @FXML
    private Button viewStatsButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button helpButton;

    /**
     * parse button text into level number
     */
    private int getLevelNumber(String text) {
        String number = text.substring(text.length() - 1);
        if (number.equals("0")) {
            return 10;
        }
        return Integer.parseInt(number);
    }

    /**
     * level selection handler to handle level selection button presses
     */
    class levelSelect implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            String text = ((Button)event.getSource()).getText();
            int level = getLevelNumber(text);
            LevelData.setLevel(level);
            SceneManager.goTo("spelling.fxml");
        }
    }

    /**
     * settings handler which changes the stage to the "settings.fxml"
     */
    class enableSettingsHandler implements  EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            SceneManager.goTo("settings.fxml");
        }
    }

    /**
     * game handler which changes the stage to the "game.fxml"
     */
    class gameHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            SceneManager.goTo("game.fxml");
        }
    }

    /**
     * help handler which opens up the README.md file
     */
    class helpHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {

            // open up the readme for the user to get help
            try {
                DesktopApi.open(new File("README.md"));
            }
            catch (Exception e){
                // do nothing
            }
        }
    }
    /**
     * handle change to stats scene
     */
    class statsSelectHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            SceneManager.goTo("stats.fxml");
        }
    }

    /**
     * onHover handler for button styling
     */
    class hoverHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            ((Button)event.getSource()).setStyle(BASE + ON_HOVER);
        }
    }

    /**
     * onExit handler for button styling
     */
    class exitHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            ((Button)event.getSource()).setStyle(BASE + ON_EXIT);
        }
    }

    /**
     * disables all the levels that are higher than the specified maxLevel
     */
    public void disable(int maxLevel) {
        for (int i = 9; i > maxLevel - 1; i--) {
            buttons.get(i).setDisable(true);
        }
    }

    /**
     * enables all ten levels
     */
    public void enableAll() {
        for (int i = 0; i < 10; i++) {
            buttons.get(i).setDisable(false);
        }
    }

    /**
     * Initializes the main menu stage
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the background
        vBox.setBackground(SceneManager.makeBackground());

        // create listeners
        EventHandler<MouseEvent> levelSelectionHandler = new levelSelect();
        EventHandler<MouseEvent> hoverHandler = new hoverHandler();
        EventHandler<MouseEvent> exitHandler = new exitHandler();
        EventHandler<MouseEvent> statsSelectHandler = new statsSelectHandler();

        // add buttons to list, then iterate through list assigning listeners
        buttons.add(level1);
        buttons.add(level2);
        buttons.add(level3);
        buttons.add(level4);
        buttons.add(level5);
        buttons.add(level6);
        buttons.add(level7);
        buttons.add(level8);
        buttons.add(level9);
        buttons.add(level10);

        // set the event handlers for each of the buttons
        for (Button button : buttons) {
            button.setStyle(BASE);
            button.setOnMouseClicked(levelSelectionHandler);
            button.setOnMouseEntered(hoverHandler);
            button.setOnMouseExited(exitHandler);
        }

        // disable all the levels the user has not yet reached
        disable(data.highestLevelEnabled());

        // if the user has asked to reset the levels, disable all but level 1
        if (LevelData.isReset()) {
            disable(1);
            LevelData.setIsReset(false);
        }

        // if the user has asked to unlock all levels, enable all ten levels
        if (LevelData.isEnable()) {
            enableAll();
            LevelData.setIsEnable(false);
        }

        // add event handlers for each button
        viewStatsButton.setOnMouseClicked(statsSelectHandler);
        settingsButton.setOnMouseClicked(new enableSettingsHandler());
        gameButton.setOnMouseClicked(new gameHandler());
        helpButton.setOnMouseClicked(new helpHandler());
    }

}
