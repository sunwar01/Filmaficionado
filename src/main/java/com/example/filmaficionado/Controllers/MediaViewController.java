package com.example.filmaficionado.Controllers;

import java.io.File;
import java.io.IOException;
import com.example.filmaficionado.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.media.MediaPlayer.Status.*;

public class MediaViewController {

    @FXML
    public MediaView mediaView;

    @FXML
    private Button playAndPauseButton;

    @FXML
    private Slider progressSlider;

    @FXML
    private Button seekBackButton;

    @FXML
    private Button seekForwardButton;

    @FXML
    private Slider volumeSlider;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;






    public void createMediaViewWindow() throws IOException {

        Stage primaryStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Filmaficionado-mediaview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Movie");
        primaryStage.show();
        primaryStage.setFullScreen(true);

    }






    public void playSelectedMovie(){


        file = new File("src/main/resources/Movies/" + MainController.currentMovie.getTitle());

        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);


        if (mediaPlayer != null) {
            this.mediaView.setMediaPlayer(mediaPlayer);
        }


    }


    public void initialize()  {

        playSelectedMovie();

}


    @FXML
    public void playAndPauseButtonClicked() {




        System.out.println(mediaPlayer.getStatus());
        if (mediaPlayer.getStatus().equals(READY) || mediaPlayer.getStatus().equals(PAUSED) ) {
            mediaPlayer.play();
            playAndPauseButton.setText("⏸");
        } else {
            mediaPlayer.pause();
            playAndPauseButton.setText("▶");
        }

    }



    @FXML
    public void seekForwardButtonClicked()  {

    mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(15)));


    }

    @FXML
    public void seekBackButtonClicked()  {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-15)));
    }

    @FXML
    public void volumeSlider() {



    }

    @FXML
    public void progressSlider()  {



    }





}
