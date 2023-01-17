package com.example.filmaficionado.Controllers;

import com.example.filmaficionado.ControlObjects.Category;
import com.example.filmaficionado.ControlObjects.CategoryDaoImplementation;
import com.example.filmaficionado.ControlObjects.Movie;
import com.example.filmaficionado.ControlObjects.MovieDaoImplementation;
import com.example.filmaficionado.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import static com.example.filmaficionado.Controllers.MainController.tableViewMoviesShare;

public class EditMovieMenuController {

    private final MovieDaoImplementation mDI = new MovieDaoImplementation();

    private final CategoryDaoImplementation cDI = new CategoryDaoImplementation();

    @FXML
    private Button saveButton;
    @FXML
    private TextField editActors;

    @FXML
    private TextField editDirector;

    @FXML
    private TextField editImdbRating;

    @FXML
    private TextField editPersonalRating;

    @FXML
    private TextField editReleaseDate;

    @FXML
    private TextField editTrailerLink;

    Category selectCategory;

    @FXML
    private ListView<Category> listviewEditAllCategories;

    @FXML
    private ListView<Category> listviewEditCategoriesForMovie;


    @FXML
    private TextArea editDescription;


    @FXML
    private TextField editPicture;

    public EditMovieMenuController() throws SQLException {
    }


    @FXML
    public void addEditCategoryButtonClicked()  {


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
                listviewEditAllCategories.getItems().add(newCategory);
                cDI.addCategory(newCategory);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            primaryStage.close();


        });
    }


    public void bestInCategoryContextMenuClicked() throws SQLException {
        selectCategory = listviewEditCategoriesForMovie.getSelectionModel().getSelectedItem();

        cDI.addMovieBestCategory(MainController.currentMovie, selectCategory);

    }


    @FXML
    public void deleteEditCategoryButtonClicked() throws  SQLException {

        selectCategory = listviewEditAllCategories.getSelectionModel().getSelectedItem();

        listviewEditAllCategories.getItems().remove(selectCategory);

        cDI.deleteCategory(selectCategory);

    }


    @FXML
    public void deleteEditCategoryFromMovieButtonClicked() throws SQLException {

        selectCategory = listviewEditCategoriesForMovie.getSelectionModel().getSelectedItem();

        listviewEditCategoriesForMovie.getItems().remove(selectCategory);

        cDI.deleteCategoryFromMovie(selectCategory);

    }


    @FXML
    public void addEditCategoryToMovieButtonClicked() throws SQLException {

        selectCategory = listviewEditAllCategories.getSelectionModel().getSelectedItem();

        listviewEditCategoriesForMovie.getItems().add(selectCategory);

        cDI.addCategoryToMovie(MainController.currentMovie, selectCategory);

    }


    public void createEditMovieWindow() throws IOException {

        Stage primaryStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Filmaficionado-editmoviemenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Edit Movie");
        primaryStage.show();


    }

    public void setTextFieldInfo(){

        editActors.setText(MainController.currentMovie.getActors());
        editDirector.setText(MainController.currentMovie.getDirector());
        editImdbRating.setText(String.valueOf(MainController.currentMovie.getImdbRating()));
        editPersonalRating.setText(String.valueOf(MainController.currentMovie.getPersonalRating()));
        editReleaseDate.setText(String.valueOf(MainController.currentMovie.getReleaseDate()));
        editTrailerLink.setText(MainController.currentMovie.getTrailer());
        editPicture.setText(MainController.currentMovie.getPicture());
        editDescription.setText(MainController.currentMovie.getDescription());




    }


    public void initialize() throws SQLException, IOException {


        setTextFieldInfo();

        for (Category category : cDI.getAllCategories()) {

            listviewEditAllCategories.getItems().add(category);

        }


        for (Category category : cDI.getMovieCategories(MainController.currentMovie)) {

            listviewEditCategoriesForMovie.getItems().add(category);

        }


    }

    @FXML
    public void saveChangesButtonClicked() throws SQLException {

        //Vi er nødt til at formatere vores string, så der er ' på hver side af vores string. Ellers kan databasen ikke finde ud af at ændre det.

        LinkedList<Category> categorylist = new LinkedList<>();
        for (Category category : listviewEditCategoriesForMovie.getItems()) {
            categorylist.add(category);

        }

        mDI.editMovie(MainController.currentMovie, "'%s'".formatted(editTrailerLink.getText()), "'%s'".formatted(editPersonalRating.getText()), "'%s'".formatted(editImdbRating.getText()), "'%s'".formatted(categorylist),  "'%s'".formatted(editDirector.getText()), "'%s'".formatted(editActors.getText()), "'%s'".formatted(editReleaseDate.getText()),  "'%s'".formatted(editPicture.getText()),  "'%s'".formatted(editDescription.getText()));


        tableViewMoviesShare.getItems().clear();

        for (Movie movie : mDI.getAllMovies()) {

            tableViewMoviesShare.getItems().add(movie);

        }

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();


    }


}
