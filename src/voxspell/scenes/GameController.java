package voxspell.scenes;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import voxspell.engine.Game;
import voxspell.engine.LevelData;
import voxspell.engine.SceneManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by harrylimp on 23/10/16.
 */
public class GameController implements Initializable {

    private static final String BASE = "-fx-border-color: rgb(31,65,9); -fx-border-width: 2px; -fx-background-color: #b6e7c9; ";
    private static final String ON_HOVER = "-fx-background-color: #83B496;";
    private static final String ON_EXIT = "-fx-background-color: #b6e7c9;";

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button menuButton;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button13;
    @FXML
    private Button button14;
    @FXML
    private Button button15;
    @FXML
    private Button button16;
    @FXML
    private Button button17;
    @FXML
    private Button button18;
    @FXML
    private Button button19;
    @FXML
    private Button button20;
    @FXML
    private Button button21;
    @FXML
    private Button button22;
    @FXML
    private Button button23;
    @FXML
    private Button button24;
    @FXML
    private Button button25;
    @FXML
    private Label pointLabel;

    ArrayList<Button> buttons = new ArrayList<>();

    class buttonRemoved implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            if (Game.canBuy()) {
                Game.buyButton();
                Button button = (Button)event.getSource();
                button.setVisible(false);
            }
            pointLabel.setText("Points Available: " + Game.getPoints());
        }
    }

    class exitHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            SceneManager.goTo("main.fxml");
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
    class leftHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            ((Button)event.getSource()).setStyle(BASE + ON_EXIT);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        anchorPane.setBackground(SceneManager.makeBeeBackground());

        // Add all the buttons to cover up the photo
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);
        buttons.add(button9);
        buttons.add(button10);
        buttons.add(button11);
        buttons.add(button12);
        buttons.add(button13);
        buttons.add(button14);
        buttons.add(button15);
        buttons.add(button16);
        buttons.add(button17);
        buttons.add(button18);
        buttons.add(button19);
        buttons.add(button20);
        buttons.add(button21);
        buttons.add(button22);
        buttons.add(button23);
        buttons.add(button24);
        buttons.add(button25);

        for (Button button : buttons) {
            button.setOnMouseClicked(new buttonRemoved());
            button.setStyle("-fx-background-color: #b6e7c9 ; -fx-border-color:black; -fx-border-width:2px; ");
            button.setOnMouseEntered(new hoverHandler());
            button.setOnMouseExited(new leftHandler());
        }

        pointLabel.setText("Points Available: " + Game.getPoints());
        menuButton.setOnMouseClicked(new exitHandler());
    }
}
