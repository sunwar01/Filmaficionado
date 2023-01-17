package com.example.filmaficionado.Controllers;


import com.example.filmaficionado.ControlObjects.Movie;
import com.example.filmaficionado.ControlObjects.MovieDaoImplementation;
import com.example.filmaficionado.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

import static com.example.filmaficionado.Controllers.MainController.tableViewMoviesShare;

public class LowestRatedMoviesPopupController {
    private final MovieDaoImplementation mDI = new MovieDaoImplementation();

    @FXML
    private TableColumn<Movie, Double> popupImdbRating;

    @FXML
    private TableColumn<Movie, Double> popupPersonalRating;

    @FXML
    private TableColumn<Movie, String> popupTitleColumn;


    @FXML
    private TableView<Movie> tableviewLowRatedMovies;

    @FXML
    private Button popupNoButton;


    @FXML
    private Button popupYesButton;




    public LowestRatedMoviesPopupController() throws SQLException {
    }

    public void createLowedRatedMoviesWindow() throws IOException {

        Stage primaryStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Filmaficionado-lowestratedmoviepopup.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("5 lowest rated movies on your list.");
        primaryStage.show();
        primaryStage.setAlwaysOnTop(true);

    }


    @FXML
    public void noButtonClicked()  {

        Stage stage = (Stage) popupNoButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void yesButtonClicked() throws SQLException {

        for (Movie movie : mDI.getLowestRatedMovies()) {

            tableviewLowRatedMovies.getItems().remove(movie);
            mDI.deleteMovie(movie);

        }

        tableViewMoviesShare.getItems().clear();

        for (Movie movie : mDI.getAllMovies()) {

            tableViewMoviesShare.getItems().add(movie);

        }

        Stage stage = (Stage) popupYesButton.getScene().getWindow();
        stage.close();



    }



    public void initialize()  {

        popupTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        popupImdbRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));

        popupPersonalRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));



        tableviewLowRatedMovies.getItems().clear();

        for (Movie movie : mDI.getLowestRatedMovies()) {

            tableviewLowRatedMovies.getItems().add(movie);

        }




    }


}
