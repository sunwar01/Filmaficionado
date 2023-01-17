package com.example.filmaficionado.ControlObjects;

import java.sql.*;
import java.util.LinkedList;
import java.util.Objects;

public class CategoryDaoImplementation implements CategoryDao {

    private static Connection con; // forbindelsen til databasen

    private final MovieDaoImplementation mDI = new MovieDaoImplementation();

    public CategoryDaoImplementation() throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://swcheats1.database.windows.net:1433;database=SWcheats;user=sunwar01@swcheats1;password=lukasersej123!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
        } catch (SQLException e) {
            System.err.println("can not create connection");
            System.out.println(e.getMessage());
        }




    }

    public int getIDFromCategory(Category category) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Category WHERE name=?;");

        ps.setString(1, category.getName());


        ResultSet rs = ps.executeQuery();

        int id = -1;

        while (rs.next()) {
            id = rs.getInt("CategoryID");
        }

        return id;

    }


    public LinkedList<Category> getMovieCategories(Movie movie) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Combined WHERE MovieID=?;");
        ps.setInt(1, mDI.getIDFromMovie(movie));
        ResultSet rs = ps.executeQuery();

        LinkedList<Category> categoryIDList = new LinkedList<>();

        while (rs.next()) {
            categoryIDList.add(getCategoryfromID(rs.getInt("CategoryID")));
        }


        return categoryIDList;

    }

    @Override
    public void deleteMovieBestCategory(Category category) throws SQLException {

        PreparedStatement ps2 = con.prepareStatement("DELETE FROM BestCategory WHERE CategoryID=" + getIDFromCategory(category) + ";");
        ps2.executeUpdate();


    }


    @Override
    public void addMovieBestCategory(Movie movie, Category category) throws SQLException {


        int MovieID = mDI.getIDFromMovie(movie);
        int CategoryID = getIDFromCategory(category);

        PreparedStatement ps = con.prepareStatement("DELETE FROM BestCategory WHERE CategoryID=" + CategoryID + ";");
        ps.executeUpdate();

        boolean isduplicate = false;


        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM BestCategory;");
        ResultSet rs2 = ps2.executeQuery();


        while (rs2.next()) {
            if (Objects.equals(rs2.getString("CategoryID"), CategoryID) && Objects.equals(rs2.getString("MovieID"), MovieID)){

                System.out.println(movie.getTitle() + " is already best in category:  " + category.getName());
                isduplicate = true;

            }
        }



        if (!isduplicate) {

            try {

                int currentID = 100;


                ResultSet rs3 = ps2.executeQuery();
                while (rs3.next()) {
                    if (rs3.getInt("BestCategoryID") == currentID) {
                        currentID++;
                    }

                }

                PreparedStatement ps3 = con.prepareStatement("INSERT INTO BestCategory VALUES (?,?,?);");


                ps3.setInt(1, currentID);
                ps3.setInt(2, CategoryID);
                ps3.setInt(3, MovieID);


                ps3.executeUpdate();
                System.out.println(movie.getTitle() + " is best in category:  " + category.getName());




            } catch (SQLException e) {

                System.err.println(e.getErrorCode() + " : " + e.getMessage());


            }
        }
    }




    @Override
    public void addCategoryToMovie(Movie movie, Category category) throws SQLException {


        int MovieID = mDI.getIDFromMovie(movie);
        int CategoryID = getIDFromCategory(category);

        PreparedStatement ps = con.prepareStatement("SELECT * FROM Combined;");
        ResultSet rs = ps.executeQuery();

        boolean isduplicate = false;

        while (rs.next()) {
            if (Objects.equals(rs.getString("CategoryID"), CategoryID) && Objects.equals(rs.getString("MovieID"), MovieID)){
                isduplicate = true;
                System.out.println(category.getName() + " is already added to " + movie.getTitle());
            }
        }



        if (!isduplicate) {

            try {

                int currentID = 100;


                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    if (rs2.getInt("combinedID") == currentID) {
                        currentID++;
                    }

                }

                PreparedStatement ps2 = con.prepareStatement("INSERT INTO Combined VALUES (?,?,?);");


                ps2.setInt(1, currentID);
                ps2.setInt(2, CategoryID);
                ps2.setInt(3, MovieID);


                ps2.executeUpdate();
                System.out.println(category.getName() + " has been added to " + movie.getTitle());





            } catch (SQLException e) {

                System.err.println(e.getErrorCode() + " : " + e.getMessage());


            }
        }
    }


            @Override
    public void deleteCategory(Category category) throws SQLException {

        PreparedStatement ps = con.prepareStatement("DELETE FROM Combined WHERE CategoryID=" + getIDFromCategory(category) + ";");
        ps.executeUpdate();

        PreparedStatement ps2 = con.prepareStatement("DELETE FROM BestCategory WHERE CategoryID=" + getIDFromCategory(category) + ";");
        ps2.executeUpdate();

        PreparedStatement ps3 = con.prepareStatement("DELETE FROM Category WHERE CategoryID=" + getIDFromCategory(category) + ";");
        ps3.executeUpdate();


    }

    @Override
    public void deleteCategoryFromMovie(Category category) throws SQLException {

        PreparedStatement ps2 = con.prepareStatement("DELETE FROM Combined WHERE CategoryID=" + getIDFromCategory(category) + ";");
        ps2.executeUpdate();

    }




    @Override
    public void addCategory(Category category) throws SQLException {


        PreparedStatement ps = con.prepareStatement("SELECT * FROM Category;");
        ResultSet rs = ps.executeQuery();


        boolean isduplicate = false;

        while (rs.next()) {
            if (Objects.equals(rs.getString("name"), category.getName())){
                isduplicate = true;
                System.out.println(category.getName() + " is already in database");

            }
        }



        if (!isduplicate) {

            try {

                int currentID = 100;


                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    if (rs2.getInt("CategoryID") == currentID) {
                        currentID++;


                    }


                }


                PreparedStatement ps2 = con.prepareStatement("INSERT INTO Category VALUES (?,?);");

                ps2.setInt(1, currentID);
                ps2.setString(2, category.getName());






                ps2.executeUpdate();
                System.out.println(category.getName() + " has been added to database");


            } catch (SQLException e) {

                System.err.println(e.getErrorCode() + " : " + e.getMessage());
            }


        }
    }

    public Category getCategoryfromID(int id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Category WHERE CategoryID=?;");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        String categoryName = rs.getString("name");

        Category category;

        category = new Category(Integer.parseInt(String.valueOf(id)), categoryName);

        return category;
    }




    @Override
    public LinkedList<Category> getAllCategories() {
        LinkedList<Category> categories = new LinkedList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Category;");
            ResultSet rs = ps.executeQuery();

            Category category;
            while (rs.next()) {
                String CategoryID = rs.getString(1);
                String name = rs.getString(2);



                category = new Category(Integer.parseInt(CategoryID), name);

                categories.add(category);



            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

}
