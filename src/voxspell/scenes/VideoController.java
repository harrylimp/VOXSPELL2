package voxspell.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import voxspell.engine.SceneManager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by harrylimp on 22/09/16.
 */
public class VideoController implements Initializable {

    @FXML
    private MediaView mv;
    private MediaPlayer mp;
    private Media me;

    @FXML
    private Button exitButton;

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vBox.setBackground(SceneManager.makeBackground());

        String path = new File("./lib/PC10.mp4").getAbsolutePath();
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        mv.setFitWidth(600);
        mv.setPreserveRatio(true);
        mp.setAutoPlay(true);


        exitButton.setOnMouseClicked(new VideoController.returnHandler());

    }

    class returnHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent mouseEvent) {
            mp.stop();
            SceneManager.goTo("endSession.fxml");
        }
    }

    public void play(ActionEvent event) {
        mp.play();
        mp.setRate(1);
    }

    public void pause(ActionEvent event) {
        mp.pause();
    }

    public void fast(ActionEvent event) {
        mp.setRate(2); // two times faster
    }

    public void slow(ActionEvent event) {
        mp.setRate(0.5); // two times slower
    }

    public void reload(ActionEvent event) {
        mp.seek(mp.getStartTime());
        mp.play();
    }

}
