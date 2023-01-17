package com.example.filmaficionado.ControlObjects;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

    void deleteCategoryFromMovie(Category category) throws SQLException;

    void addCategory(Category category) throws SQLException;



    List getAllCategories();


    void deleteMovieBestCategory(Category category) throws SQLException;

    void addMovieBestCategory(Movie movie, Category category) throws SQLException;

    void addCategoryToMovie(Movie movie, Category category) throws SQLException;

    void deleteCategory(Category category) throws SQLException;

}
