package voxspell.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import voxspell.engine.DataIO;
import voxspell.engine.Festival;
import voxspell.engine.LevelData;
import voxspell.engine.SceneManager;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * MainController class for the application entry / level selection screen (main.fxml)
 */
public class SettingsController implements Initializable {
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
    private Button resetButton;
    @FXML
    private MenuButton voiceMenuButton;
    @FXML
    private Button unlockAllButton;
    @FXML
    private Button changeListButton;

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
        vBox.setBackground(SceneManager.makePopBackground());
        // create listeners
        EventHandler<MouseEvent> hoverHandler = new hoverHandler();
        EventHandler<MouseEvent> exitHandler = new exitHandler();
        EventHandler<MouseEvent> resetHandler = new resetHandler();
        EventHandler<MouseEvent> enableAllHandler = new enableAllHandler();

        // initialise buttons
        resetButton.setOnMouseClicked(resetHandler);
        unlockAllButton.setOnMouseClicked(enableAllHandler);

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

    }

    private void disable(int maxLevel) {
        for (int i = 9; i > maxLevel - 1; i--) {
            buttons.get(i).setDisable(true);
        }
    }

    private void enableAll() {
        for (int i = 0; i < 10; i++) {
            buttons.get(i).setDisable(false);
        }
    }
}
