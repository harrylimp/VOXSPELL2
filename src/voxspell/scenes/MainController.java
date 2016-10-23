package voxspell.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import voxspell.Voxspell;
import voxspell.engine.DataIO;
import voxspell.engine.Festival;
import voxspell.engine.LevelData;
import voxspell.engine.SceneManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * MainController class for the application entry / level selection screen (main.fxml)
 */
public class MainController implements Initializable {
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
    private Button resetButton;
    @FXML
    private MenuButton voiceMenuButton;
    @FXML
    private Button viewStatsButton;
    @FXML
    private Button unlockAllButton;
    @FXML
    private Button changeListButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button gameButton;

    /**
     * parse button text into level number
     * @param text
     * @return level
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
     * handle change to stats scene
     */
    class statsSelectHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            SceneManager.goTo("stats.fxml");
        }
    }

    /**
     * voice MenuButton handler for voice changing
     */
    class voiceMenuButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            switch((String)((MenuItem)actionEvent.getSource()).getUserData()) { // userData is voice type
                case Festival.DEFAULT:
                    LevelData.setVoice(Festival.DEFAULT);
                    voiceMenuButton.setText(DEFAULT_VOICE_CHOICE);
                    break;
                case Festival.NZ:
                    LevelData.setVoice(Festival.NZ);
                    voiceMenuButton.setText(NZ_VOICE_CHOICE);
                    break;
                default:
                    LevelData.setVoice(Festival.DEFAULT); // set to default if invalid voice
                    voiceMenuButton.setText(DEFAULT_VOICE_CHOICE);
                    break;
            }
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
     * handler for resetting labels
     */
    class resetHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            data.delete();
            disable(1); // disable all levels except the first
        }
    }

    class enableAllHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            enableAll();
        }
    }

    class enableSettingsHandler implements  EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            //popUp();
            SceneManager.goTo("settings.fxml");
        }
    }

    class gameHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            SceneManager.goTo("game.fxml");
        }
    }

    class fileHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            final FileChooser fileChooser = new FileChooser();
            Stage stage = (Stage)changeListButton.getScene().getWindow();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
            File file = fileChooser.showOpenDialog(stage);
            String chosenFile = file.getName();
            LevelData.setWordlist(chosenFile);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vBox.setBackground(SceneManager.makeBackground());
        // create listeners
        EventHandler<MouseEvent> levelSelectionHandler = new levelSelect();
        EventHandler<MouseEvent> hoverHandler = new hoverHandler();
        EventHandler<MouseEvent> exitHandler = new exitHandler();
        //EventHandler<MouseEvent> resetHandler = new resetHandler();
        EventHandler<MouseEvent> statsSelectHandler = new statsSelectHandler();
        //EventHandler<MouseEvent> enableAllHandler = new enableAllHandler();

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

        for (Button button : buttons) {
            button.setStyle(BASE);
            button.setOnMouseClicked(levelSelectionHandler);
            button.setOnMouseEntered(hoverHandler);
            button.setOnMouseExited(exitHandler);
        }

        if (LevelData.isReset()) {
            disable(1);
            LevelData.setIsReset(false);
        }
        if (LevelData.isEnable()) {
            enableAll();
            LevelData.setIsEnable(false);
        }

        // initialise buttons
        //resetButton.setOnMouseClicked(resetHandler);
        viewStatsButton.setOnMouseClicked(statsSelectHandler);
        //unlockAllButton.setOnMouseClicked(enableAllHandler);


        // disable locked levels
        //disable(data.highestLevelEnabled());

        // initialise voice menu
        voiceMenuButton.setText("Change voice");
        EventHandler<ActionEvent> voiceMenuButtonHandler = new voiceMenuButtonHandler();
        MenuItem defaultVoice = new MenuItem(DEFAULT_VOICE_CHOICE);
        defaultVoice.setUserData(Festival.DEFAULT);
        MenuItem nzVoice = new MenuItem(NZ_VOICE_CHOICE);
        nzVoice.setUserData(Festival.NZ);
        voiceMenuButton.getItems().setAll(defaultVoice, nzVoice);

        defaultVoice.setOnAction(voiceMenuButtonHandler);
        nzVoice.setOnAction(voiceMenuButtonHandler);

        changeListButton.setOnAction(new fileHandler());

        // enable background music
        File file = new File("./lib/song.mp3");
        Media musicFile = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.05);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() { mediaPlayer.seek(Duration.ZERO); }
        });
        mediaPlayer.onRepeatProperty();

        // enable settings button
        settingsButton.setOnMouseClicked(new enableSettingsHandler());

        // enable game button
        gameButton.setOnMouseClicked(new gameHandler());

    }

    /*public void popUp() {
        Stage stage;
        Parent root = null;
        stage = new Stage();

        try {
            root = FXMLLoader.load(Voxspell.class.getResource("scenes/" + "settings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }*/

    public void disable(int maxLevel) {
        for (int i = 9; i > maxLevel - 1; i--) {
            buttons.get(i).setDisable(true);
        }
    }

    public void enableAll() {
        for (int i = 0; i < 10; i++) {
            buttons.get(i).setDisable(false);
        }
    }
}
