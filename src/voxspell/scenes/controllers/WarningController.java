package voxspell.scenes.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vBox.setBackground(SceneManager.makeBackground());
        noButton.setOnMouseClicked(new spellingHandler());
        yesButton.setOnMouseClicked(new menuHandler());
        warningLabel.setText("Are you sure?");
        warningLabel.setAlignment(Pos.CENTER);

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
