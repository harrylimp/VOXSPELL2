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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import voxspell.Voxspell;
import voxspell.engine.DataIO;
import voxspell.engine.Festival;
import voxspell.engine.LevelData;
import voxspell.engine.SceneManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * MainController class for the application entry / level selection screen (main.fxml)
 */
public class SettingsController implements Initializable {

    private static final String NZ_VOICE_CHOICE = "NZ Voice";
    private static final String DEFAULT_VOICE_CHOICE = "Default Voice";

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
    @FXML
    private Button menuButton;

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
     * handler for resetting labels
     */
    class resetHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            data.delete();
            popUp("warning.fxml");
            LevelData.setIsReset(true);
            LevelData.setIsEnable(false);
        }
    }

    class enableAllHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            popUp("unlock.fxml");
        }
    }

    class menuHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            SceneManager.goTo("main.fxml");
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

    public void popUp(String destination) {
        Stage stage;
        Parent root = null;
        stage = new Stage();

        try {
            root = FXMLLoader.load(Voxspell.class.getResource("scenes/" + destination));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        vBox.setBackground(SceneManager.makeBackground());

        // create listeners
        EventHandler<MouseEvent> resetHandler = new resetHandler();
        EventHandler<MouseEvent> enableAllHandler = new enableAllHandler();
        EventHandler<MouseEvent> menuHandler = new menuHandler();

        // initialise buttons
        resetButton.setOnMouseClicked(resetHandler);
        unlockAllButton.setOnMouseClicked(enableAllHandler);
        menuButton.setOnMouseClicked(menuHandler);

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
}
