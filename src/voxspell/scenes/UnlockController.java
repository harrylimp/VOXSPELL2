package voxspell.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import voxspell.engine.LevelData;
import voxspell.engine.SceneManager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by harrylimp on 22/09/16.
 */
public class UnlockController implements Initializable {

    @FXML
    private Button submitButton;
    @FXML
    private TextField inputTextField;
    @FXML
    private Label output;

    /**
     * listener to handle enter key submits
     */
    class enterSubmitHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            submit();
        }
    }

    /**
     * listener to handle submits with the "Go!" button
     */
    class submitHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent mouseEvent) {
            submit();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submitButton.setOnMouseClicked(new submitHandler());
        inputTextField.setOnAction(new enterSubmitHandler());
    }

    private void submit() {
        String userInput = inputTextField.getText().trim();
        inputTextField.clear();
        inputTextField.requestFocus();
        if (LevelData.checkPassword(userInput)) {
            LevelData.setIsEnable(true);
            LevelData.setIsReset(false);
            output.setText("Correct! All levels have been unlocked");
        } else {
            output.setText("Incorrect! Please re-enter");
        }
    }
}
