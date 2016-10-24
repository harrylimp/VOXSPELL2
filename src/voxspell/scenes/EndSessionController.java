package voxspell.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import voxspell.engine.LevelData;
import voxspell.engine.SceneManager;
import voxspell.engine.Word;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by harrylimp on 21/09/16.
 */
public class EndSessionController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button reviewButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button nextLevelButton;
    @FXML
    private Button viewStatsButton;
    @FXML
    private Button retryLevelButton;
    @FXML
    private Button playVideoButton;
    @FXML
    private PieChart piechart;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label endMessage;

    private static ArrayList<String> correctList = new ArrayList<>();

    private int correct;
    private int incorrect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the background to the nature theme
        anchorPane.setBackground(SceneManager.makeBackground());

        // check whether the session was in review mode - if so change the button text
        if (LevelData.isReview()) {
            reviewButton.setText("Review again");
        }

        // check how many words were correct or incorrect in the session
        ArrayList<Word> currentWords = LevelData.getCurrentWordList();
        correct = 0;
        incorrect = 0;
        for (Word word : currentWords) {
            if (word.getMastered() == 1) {
                correct++;
            } else {
                incorrect++;
            }
        }

        // set the handlers when the buttons are clicked
        returnButton.setOnMouseClicked(new returnHandler());
        viewStatsButton.setOnMouseClicked(new statsHandler());
        retryLevelButton.setOnMouseClicked(new retryLevelHandler());

        // only enable the review button if there is more than one word incorrect
        if (incorrect > 0) {
            reviewButton.setOnMouseClicked(new reviewHandler());
        } else {
            reviewButton.setDisable(true);
        }

        // enable the video and next player buttons depending on the session
        if (correct >= 9 && !LevelData.isReview() || currentWords.size() == 1 && LevelData.isReview()) {
            // only enable reward/next level if original quiz was 9/10 and they reviewed 1 word only (or no review)
            playVideoButton.setOnMouseClicked(new videoHandler());
            if (!(LevelData.getLevel() == 10)) {
                nextLevelButton.setOnMouseClicked(new nextLevelHandler());
            } else {
                // disable next level on 10
                nextLevelButton.setDisable(true);
            }
        } else {
            nextLevelButton.setDisable(true);
            playVideoButton.setVisible(false);
        }

        // update everything on the pie chart
        displayText();
        LevelData.setIsReview(false);
        showPieChart();
        showListView();
    }

    class returnHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent mouseEvent) {
            SceneManager.goTo("main.fxml");
        }
    }

    class reviewHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            LevelData.setIsReview(true);
            SceneManager.goTo("spelling.fxml");
        }
    }

    class retryLevelHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            SceneManager.goTo("spelling.fxml");
        }
    }

    class nextLevelHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            LevelData.setLevel(LevelData.getLevel() + 1); // safe as button will be disabled if no next level
            SceneManager.goTo("spelling.fxml");
        }
    }

    class statsHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent mouseEvent) {
            SceneManager.goTo("stats.fxml");
        }
    }

    class videoHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            SceneManager.goTo("video.fxml");
        }
    }

    /**
     * Displays the text that should be provided after the session has finished
     * depending on whether it was in review mode or normal mode
     */
    public void displayText() {
        if (LevelData.isReview()) {
            endMessage.setText("Review results");
        } else {
            endMessage.setText("Well done!");
        }
    }

    /**
     * PieChart is generated depending on the percentage of correct and incorrect answers.
     */
    public void showPieChart() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data("Correct", correct * 10),
                new javafx.scene.chart.PieChart.Data("Incorrect", incorrect * 10)
        );
        piechart.setData(list);

        applyCustomColorSequence(
                list,
                "lightblue",
                "orange"
        );
        
    }

    private void applyCustomColorSequence(ObservableList<PieChart.Data> pieChartData, String... pieColors) {
        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: " + pieColors[i % pieColors.length] + ";");
            i++;
        }
    }

    public void showListView() {
        ArrayList<Word> currentWords = LevelData.getCurrentWordList();
        ObservableList<String> words = FXCollections.observableArrayList();
        for (Word word : currentWords) {
            words.add(word.toString());
            if (word.getMastered() == 1) {
                correctList.add(word.toString());
            }
        }
        listView.setItems(words);

        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new ColorRectCell();
            }
        });
    }

    static class ColorRectCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item);
                if (correctList.contains(item)) {
                    setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }
    }
}