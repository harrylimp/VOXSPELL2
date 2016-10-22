package voxspell.scenes;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import voxspell.engine.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by harrylimp on 22/10/16.
 */
public class WarningController implements Initializable {

    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label warningLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        noButton.setOnMouseClicked(new spellingHandler());
        yesButton.setOnMouseClicked(new menuHandler());
        warningLabel.setText("Are you sure you want to Exit? \n ALl progress will be lost?");

    }

    class menuHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {

            Stage stage = (Stage)yesButton.getScene().getWindow();
            stage.close();
            SceneManager.goTo("main.fxml");

        }
    }

    class spellingHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {

            Stage stage = (Stage)noButton.getScene().getWindow();
            stage.close();

        }
    }
}
