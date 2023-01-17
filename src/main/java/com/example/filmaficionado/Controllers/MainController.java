package com.example.filmaficionado.Controllers;

import com.example.filmaficionado.ControlObjects.Movie;
import com.example.filmaficionado.ControlObjects.MovieDaoImplementation;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;





public class MainController {



    private final MovieDaoImplementation mDI = new MovieDaoImplementation();
    private final MediaViewController mVC = new MediaViewController();
    private final EditMovieMenuController eMMC = new EditMovieMenuController();

    private final DescriptionMovieMenuController dMMC = new DescriptionMovieMenuController();

    private final AddMovieMenuController aMMC = new AddMovieMenuController();

    private final LowestRatedMoviesPopupController lRMPC = new LowestRatedMoviesPopupController();





    @FXML
    private TableColumn<Movie, String> tableViewColumnActors;

    @FXML
    private TableColumn<Movie, String> tableViewColumnCategory;

    @FXML
    private TableColumn<Movie, String> tableViewColumnDirector;

    @FXML
    private TableColumn<Movie, Integer> tableViewColumnIMDBRating;


    @FXML
    private TableColumn<Movie, Integer> tableViewColumnPersonalRating;

    @FXML
    private TableColumn<Movie, Integer> tableViewColumnReleaseDate;

    @FXML
    private TableColumn<Movie, String> tableViewColumnTitle;

    @FXML
    public TableView<Movie> tableViewMovies;

    @FXML
    private TextField textFieldSearch;


    public static Movie currentMovie;

    public static TableView<Movie> tableViewMoviesShare;



    @FXML
    public void menuItemDeleteClicked() throws  SQLException {

        Movie movieToDelete;
        movieToDelete = tableViewMovies.getSelectionModel().getSelectedItem();

        tableViewMovies.getItems().remove(movieToDelete);

        mDI.deleteMovie(movieToDelete);

    }




    public static void openWebPage(String url) {

        try {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI(url);
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @FXML
    public void menuItemPlayTrailerClicked()   {

        Movie movieToGetDataFrom;
        movieToGetDataFrom = tableViewMovies.getSelectionModel().getSelectedItem();

        openWebPage(movieToGetDataFrom.getTrailer());




    }


    @FXML
    public void menuItemEditMovieClicked() throws IOException {

        currentMovie = tableViewMovies.getSelectionModel().getSelectedItem();

        tableViewMoviesShare = tableViewMovies;

        eMMC.createEditMovieWindow();

    }

    @FXML
    public void menuItemDescriptionClicked() throws IOException {

        currentMovie = tableViewMovies.getSelectionModel().getSelectedItem();

        tableViewMoviesShare = tableViewMovies;

        dMMC.createDescriptionMovieWindow();

    }





    @FXML
    public void menuItemPlayMovieClicked() throws IOException {

        currentMovie = tableViewMovies.getSelectionModel().getSelectedItem();

        mVC.createMediaViewWindow();

    }


    @FXML
    public void addMovieButtonClicked() throws IOException {

        tableViewMoviesShare = tableViewMovies;

        aMMC.createAddMovieWindow();




    }





    public MainController() throws SQLException {


    }


    public void updateMoviesInTableView(){

        tableViewMovies.getItems().clear();

        for (Movie movie : mDI.getAllMovies()) {

            tableViewMovies.getItems().add(movie);

        }


    }


    public void initialize() throws IOException {




       tableViewColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        tableViewColumnPersonalRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));

        tableViewColumnReleaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

        tableViewColumnIMDBRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));

        tableViewColumnDirector.setCellValueFactory(new PropertyValueFactory<>("director"));

        tableViewColumnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        tableViewColumnActors.setCellValueFactory(new PropertyValueFactory<>("actors"));



        updateMoviesInTableView();


        ObservableList<Movie> data =  tableViewMovies.getItems();
        textFieldSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableViewMovies.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Movie> MoviesThatContainsWhatSearchedFor = FXCollections.observableArrayList();

            long count = tableViewMovies.getColumns().size();
            for (int i = 0; i < tableViewMovies.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableViewMovies.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        MoviesThatContainsWhatSearchedFor.add(tableViewMovies.getItems().get(i));
                        break;
                    }
                }
            }
            tableViewMovies.setItems(MoviesThatContainsWhatSearchedFor);
        });


        tableViewMoviesShare = tableViewMovies;

        lRMPC.createLowedRatedMoviesWindow();







    }


}