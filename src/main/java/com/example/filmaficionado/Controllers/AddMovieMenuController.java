package com.example.filmaficionado.Controllers;

import com.example.filmaficionado.ControlObjects.*;
import com.example.filmaficionado.FilmaficionadoApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.LinkedList;

import static com.example.filmaficionado.Controllers.MainController.tableViewMoviesShare;


public class AddMovieMenuController {

    private final MovieDaoImplementation mDI = new MovieDaoImplementation();

    private final CategoryDaoImplementation cDI = new CategoryDaoImplementation();

    @FXML
    private MenuItem addMovieBestInCategory;

    @FXML
    private TextField actorsTextfield;


    @FXML
    private Button contextMenuAddMovieButton;

    @FXML
    private TextField directorTextfield;

    @FXML
    private Button filepathButton;

    @FXML
    private TextField imdbRatingTextfield;

    @FXML
    private TextField personalRatingTextfield;

    @FXML
    private TextField releaseDateTextfield;

    @FXML
    public  TextField titleTextfield;

    @FXML
    public  TextField trailerLinkTextfield;



    @FXML
    private ListView<Category> listviewAllCategories;

    @FXML
    private ListView<Category> listviewCategoriesForMovie;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private TextField pictureTextField;

    Category selectCategory;


    public AddMovieMenuController() throws SQLException {
    }



    public String getTitleTextField() {

        return titleTextfield.getText();
    }

    public String getPersonalRatingTextField() {

        return personalRatingTextfield.getText();
    }

    public String getImdbRatingTextField() {

        return imdbRatingTextfield.getText();
    }

    public String getDirector() {

        return directorTextfield.getText();
    }

    public String getActors() {

        return actorsTextfield.getText();
    }

    public String getReleaseDate() {

        return releaseDateTextfield.getText();
    }

    public String getTrailerLink() {

        return trailerLinkTextfield.getText();
    }

    public String getPictureLink() {

        return pictureTextField.getText();
    }

    public String getDescription() {

        return descriptionTextField.getText();
    }





public void initialize(){

    for (Category category : cDI.getAllCategories()) {

        listviewAllCategories.getItems().add(category);

    }


}

    public static boolean isNumericInt(String string) {
        int intValue;


        try {
            intValue = Integer.parseInt(string);
            return true;
            //hvis vi kan parse den som int, s?? returner vi true, da vi s?? ved at det er et tal.
        } catch (NumberFormatException e) {

            //Hvis vi ikke kan parse den som int, s?? er det ikke et tal
        }
        return false;
    }

    public static boolean isNumericDouble(String string) {
        double doubleValue;


        try {
            doubleValue = Double.parseDouble(string);
            return true;
            //hvis vi kan parse den som double, s?? returner vi true, da vi s?? ved at det er et tal.
        } catch (NumberFormatException e) {

            //Hvis vi ikke kan parse den som double, s?? er det ikke et tal
        }
        return false;
    }


public void addMovieErrorHandling(){


 if (personalRatingTextfield.getText().equals("")){
     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Error!");
     alert.setHeaderText("No rating is set in personal rating text-field");
     alert.setContentText("Please give the movie a personal rating.");
     alert.showAndWait();

 }

 if (imdbRatingTextfield.getText().equals("")){
     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Error!");
     alert.setHeaderText("No rating is set in imdb rating text-field");
     alert.setContentText("Please give the movie an imdb rating.");
     alert.showAndWait();

 }
 if (releaseDateTextfield.getText().equals("")){
     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Error!");
     alert.setHeaderText("No release date is set in release date text-field");
     alert.setContentText("Please give the movie a release date. (Only year of release)");
     alert.showAndWait();

 }

 String personalRatingString = personalRatingTextfield.getText();
 String imdbRatingString = imdbRatingTextfield.getText();
 String releaseDateString = releaseDateTextfield.getText();

 if (isNumericDouble(personalRatingString)) {
     //Do nothing
 }else{

     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Error!");
     alert.setHeaderText("A char or string is set in personal rating text-field");
     alert.setContentText("Please give the movie a number from 1-10 as a personal rating ");
     alert.showAndWait();

 }

 if (isNumericDouble(imdbRatingString)) {
     //Do nothing
 }else{

     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Error!");
     alert.setHeaderText("A char or string is set in imdb rating text-field");
     alert.setContentText("Please give the movie a number from 1-10 as a imdb rating ");
     alert.showAndWait();

 }

 if (isNumericInt(releaseDateString)) {
     //Do nothing
 }else{

     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Error!");
     alert.setHeaderText("A char or string is set in release date text-field");
     alert.setContentText("Please give the movie a number as release date. (Only year of release)");
     alert.showAndWait();

 }














}

    public void createAddMovieWindow() throws IOException {

        Stage primaryStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(FilmaficionadoApplication.class.getResource("Filmaficionado-addmoviemenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        primaryStage.setScene(scene);
        primaryStage.setTitle("Add new movie");
        primaryStage.show();

    }




    @FXML
    public void addCategoryButtonClicked() {


        Stage primaryStage = new Stage();

        Label label = new Label("Category name:");
        TextField tf = new TextField();
        Button btn = new Button("Add");


        HBox root = new HBox();
        root.setSpacing(20);
        root.getChildren().addAll(label, tf, btn);
        Scene scene = new Scene(root, 315, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add new category");
        primaryStage.show();

        btn.setOnAction(e ->
        {
            String userInput = tf.getText();


            Category newCategory = new Category(1, userInput);
            try {
                listviewAllCategories.getItems().add(newCategory);
                cDI.addCategory(newCategory);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            primaryStage.close();




        });
    }

    @FXML
    public void deleteCategoryButtonClicked() throws  SQLException {

       selectCategory = listviewAllCategories.getSelectionModel().getSelectedItem();

        listviewAllCategories.getItems().remove(selectCategory);

        cDI.deleteCategory(selectCategory);

    }



    @FXML
    public void addCategoryToMovieButtonClicked()  {

        selectCategory = listviewAllCategories.getSelectionModel().getSelectedItem();

        listviewCategoriesForMovie.getItems().add(selectCategory);

    }




    @FXML
    public void chooseFileButtonClicked() throws IOException {


        Stage primaryStage = new Stage();
        primaryStage.setTitle("Choose movie to add");


        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Movie Files", "*.mp4"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {


            Path copied = Paths.get("src/main/resources/Movies/" + selectedFile.getName());
            Path originalPath = selectedFile.toPath();
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);


            filepathButton.setText(selectedFile.getName());
            titleTextfield.setText(selectedFile.getName());



        }


    }






    @FXML
    public void addMovieButtonContextMenuClicked()  {

        addMovieErrorHandling();


        LinkedList<Category> categorylist = new LinkedList<>();
        for (Category category : listviewCategoriesForMovie.getItems()) {
            categorylist.add(category);

        }

        Movie newMovie = new Movie(100, getTitleTextField(), Double.parseDouble(getPersonalRatingTextField()), Double.parseDouble(getImdbRatingTextField()), categorylist.toString(), getDirector(), getActors(), getTrailerLink(), Integer.parseInt(getReleaseDate()), 2,getPictureLink(), getDescription());

        try {
            mDI.addMovie(newMovie);

            for (Category category : listviewCategoriesForMovie.getItems()) {

                cDI.addCategoryToMovie(newMovie, category);
            }




        } catch (SQLException ex) {
            throw new RuntimeException(ex);



        }



        tableViewMoviesShare.getItems().clear();

        for (Movie movie : mDI.getAllMovies()) {

            tableViewMoviesShare.getItems().add(movie);

        }

        Stage stage = (Stage) contextMenuAddMovieButton.getScene().getWindow();
        stage.close();






    }





}
