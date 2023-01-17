package com.example.filmaficionado.Controllers;


import com.example.filmaficionado.ControlObjects.Movie;
import com.example.filmaficionado.ControlObjects.MovieDaoImplementation;
import com.example.filmaficionado.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class DescriptionMovieMenuController {

    private final MovieDaoImplementation mDI = new MovieDaoImplementation();

    @FXML
    private TextArea showDescription;

    @FXML
    private ImageView showPicture;


    @FXML
    private ImageView bestInCategoryIcon;

    @FXML
    private Label bestInCategoryLabel;

    public DescriptionMovieMenuController() throws SQLException {
    }


    public void checkIfCurrentMovieIsBestInACategory() throws SQLException {


       String bestCategory = mDI.getCategoryThatMovieIsBestIn(MainController.currentMovie);


        if (bestCategory != null){
            bestInCategoryLabel.setText(bestCategory);


            Image bestCategoryIcon = new Image("https://png.pngtree.com/element_our/20190602/ourlarge/pngtree-cartoon-yellow-trophy-illustration-image_1411253.jpg");

            bestInCategoryIcon.setImage(bestCategoryIcon);


        }





    }




    public void createDescriptionMovieWindow() throws IOException {

        Stage primaryStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Filmaficionado-descriptionmoviemenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("View Description");
        primaryStage.show();



    }

    public void setTextFieldInfo(){

        showDescription.setText(MainController.currentMovie.getDescription());

    }


    public void setImageView(){

        Image currentMovieImage = new Image(MainController.currentMovie.getPicture());

        showPicture.setImage(currentMovieImage);

    }

    public void initialize() throws SQLException {

        setTextFieldInfo();
        setImageView();
        checkIfCurrentMovieIsBestInACategory();

    }





}
