package com.example.filmaficionado.ControlObjects;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public interface MovieDao {




    List getAllMovies();


    LinkedList<Movie> getLowestRatedMovies();

    void deleteMovie(Movie movie) throws SQLException;




    void editMovie(Movie movie, String newTrailerLink, String newPersonalRating, String newImdbRating, String newCategories, String newDirector, String newActors, String newReleaseDate, String newPicture, String newDescription) throws SQLException;

    void addMovie(Movie movie) throws SQLException;






}
