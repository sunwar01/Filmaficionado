package com.example.filmaficionado.ControlObjects;

import java.sql.*;
import java.util.LinkedList;
import java.util.Objects;



public class MovieDaoImplementation implements MovieDao {


    private static Connection con; // forbindelsen til databasen

    public MovieDaoImplementation() throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://swcheats1.database.windows.net:1433;database=SWcheats;user=sunwar01@swcheats1;password=lukasersej123!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
        } catch (SQLException e) {
            System.err.println("can not create connection");
            System.out.println(e.getMessage());
        }


    }




    @Override
    public LinkedList<Movie> getAllMovies() {
        LinkedList<Movie> movies = new LinkedList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie;");
            ResultSet rs = ps.executeQuery();

            Movie movie;
            while (rs.next()) {
                String id = rs.getString(1);
                String title = rs.getString(2);
                String personalRating = rs.getString(3);
                String imdbRating = rs.getString(4);
                String category = rs.getString(5);
                String director = rs.getString(6);
                String actors = rs.getString(7);
                String trailer = rs.getString(8);
                String releaseDate = rs.getString(9);
                String lastViewed = rs.getString(10);
                String picture = rs.getString(11);
                String description = rs.getString(12);


                movie = new Movie(Integer.parseInt(id), title, Double.parseDouble(personalRating), Double.parseDouble(imdbRating), category, director, actors, trailer, Integer.parseInt(releaseDate), Integer.parseInt(lastViewed), picture, description);

                movies.add(movie);



            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    @Override
    public LinkedList<Movie> getLowestRatedMovies() {
        LinkedList<Movie> movies = new LinkedList<>();
        try {

            //Her siger vi at den skal vælge de 5 lavest ratings. ASC er Ascending og DESC er Descending
            PreparedStatement ps = con.prepareStatement("SELECT TOP 5 * FROM Movie ORDER BY personalRating ASC" );
            ResultSet rs = ps.executeQuery();

            Movie movie;
            while (rs.next()) {
                String id = rs.getString(1);
                String title = rs.getString(2);
                String personalRating = rs.getString(3);
                String imdbRating = rs.getString(4);
                String category = rs.getString(5);
                String director = rs.getString(6);
                String actors = rs.getString(7);
                String trailer = rs.getString(8);
                String releaseDate = rs.getString(9);
                String lastViewed = rs.getString(10);
                String picture = rs.getString(11);
                String description = rs.getString(12);


                movie = new Movie(Integer.parseInt(id), title, Double.parseDouble(personalRating), Double.parseDouble(imdbRating), category, director, actors, trailer, Integer.parseInt(releaseDate), Integer.parseInt(lastViewed), picture, description);

                movies.add(movie);



            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }






    @Override
    public void deleteMovie(Movie movie) throws SQLException {


        PreparedStatement ps = con.prepareStatement("DELETE FROM Combined WHERE MovieID=" + getIDFromMovie(movie) + ";");
        ps.executeUpdate();

        PreparedStatement ps2 = con.prepareStatement("DELETE FROM BestCategory WHERE MovieID=" + getIDFromMovie(movie) + ";");
        ps2.executeUpdate();

        PreparedStatement ps3 = con.prepareStatement("DELETE FROM Movie WHERE MovieID=" + getIDFromMovie(movie) + ";");
        ps3.executeUpdate();



    }


    @Override
    public void editMovie(Movie movie, String newTrailerLink, String newPersonalRating, String newImdbRating, String newCategories, String newDirector, String newActors, String newReleaseDate, String newPicture, String newDescription) throws SQLException {

        String SQLMovieTitle = "'%s'".formatted(movie.getTitle());


        //Vi er nødt til at formatere vores string, så der er ' på hver side af vores string. Ellers kan databasen ikke finde ud af at ændre det.

        String SQLMovieTrailerLink = "'%s'".formatted(movie.getTrailer());
        String SQLMoviePersonalRating = "'%s'".formatted(movie.getPersonalRating());
        String SQLMovieImdbRating = "'%s'".formatted(movie.getImdbRating());
        String SQLMovieCategory = "'%s'".formatted(movie.getCategory());
        String SQLMovieDirector = "'%s'".formatted(movie.getDirector());
        String SQLMovieActors = "'%s'".formatted(movie.getActors());
        String SQLMovieReleaseDate = "'%s'".formatted(movie.getReleaseDate());
        String SQLMoviePicture = "'%s'".formatted(movie.getPicture());
        String SQLMovieDescription = "'%s'".formatted(movie.getDescription());




        PreparedStatement ps = con.prepareStatement("UPDATE Movie SET trailer=" + newTrailerLink + " WHERE trailer=" + SQLMovieTrailerLink + " AND title=" + SQLMovieTitle + ";");
        PreparedStatement ps2 = con.prepareStatement("UPDATE Movie SET personalRating=" + newPersonalRating + " WHERE personalRating=" + SQLMoviePersonalRating + " AND title=" + SQLMovieTitle +  ";");
        PreparedStatement ps3 = con.prepareStatement("UPDATE Movie SET imdbRating=" + newImdbRating + " WHERE imdbRating=" + SQLMovieImdbRating + " AND title=" + SQLMovieTitle +  ";");
        PreparedStatement ps4 = con.prepareStatement("UPDATE Movie SET director=" + newDirector + " WHERE director=" + SQLMovieDirector  + " AND title=" + SQLMovieTitle +  ";");
        PreparedStatement ps5 = con.prepareStatement("UPDATE Movie SET actors=" + newActors + " WHERE actors=" + SQLMovieActors + " AND title=" + SQLMovieTitle +  ";");
        PreparedStatement ps6 = con.prepareStatement("UPDATE Movie SET releaseDate=" + newReleaseDate + " WHERE releaseDate=" + SQLMovieReleaseDate + " AND title=" + SQLMovieTitle +  ";");
        PreparedStatement ps7 = con.prepareStatement("UPDATE Movie SET category=" + newCategories + " WHERE category=" + SQLMovieCategory + " AND title=" + SQLMovieTitle +  ";");
        PreparedStatement ps8 = con.prepareStatement("UPDATE Movie SET picture=" + newPicture + " WHERE picture=" + SQLMoviePicture + " AND title=" + SQLMovieTitle +  ";");
        PreparedStatement ps9 = con.prepareStatement("UPDATE Movie SET description=" + newDescription + " WHERE description=" + SQLMovieDescription + " AND title=" + SQLMovieTitle +  ";");


        ps.executeUpdate();
        ps2.executeUpdate();
        ps3.executeUpdate();
        ps4.executeUpdate();
        ps5.executeUpdate();
        ps6.executeUpdate();
        ps7.executeUpdate();
        ps8.executeUpdate();
        ps9.executeUpdate();


    }

    @Override
    public void addMovie(Movie movie) throws SQLException {


        PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie;");
        ResultSet rs = ps.executeQuery();


        boolean isduplicate = false;

        while (rs.next()) {
            if (Objects.equals(rs.getString("title"), movie.getTitle())){
                isduplicate = true;
                System.out.println(movie.getTitle() + " is already in database");

            }
        }



            if (!isduplicate) {

            try {

                int currentID = 100;


                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    if (rs2.getInt("MovieID") == currentID) {
                        currentID++;


                    }


                }


                PreparedStatement ps2 = con.prepareStatement("INSERT INTO Movie VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");

                ps2.setInt(1, currentID);
                ps2.setString(2, movie.getTitle());
                ps2.setDouble(3, movie.getPersonalRating());
                ps2.setDouble(4, movie.getImdbRating());
                ps2.setString(5, movie.getCategory());
                ps2.setString(6, movie.getDirector());
                ps2.setString(7, movie.getActors());
                ps2.setString(8, movie.getTrailer());
                ps2.setInt(9, movie.getReleaseDate());
                ps2.setInt(10, movie.getLastViewed());
                ps2.setString(11, movie.getPicture());
                ps2.setString(12, movie.getDescription());





                ps2.executeUpdate();
                System.out.println(movie.getTitle() + " has been added to database");


            } catch (SQLException e) {

                System.err.println(e.getErrorCode() + " : " + e.getMessage());
            }


        }
    }

    public String getCategoryThatMovieIsBestIn(Movie movie) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM BestCategory WHERE MovieID=?;");

        ps.setInt(1, getIDFromMovie(movie));

        ResultSet rs = ps.executeQuery();

        int CategoryID = 0;

        while (rs.next()) {
            CategoryID = rs.getInt("CategoryID");
        }

        System.out.println(CategoryID);

        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM Category WHERE CategoryID=?;");

        ps2.setInt(1, CategoryID);

        ResultSet rs2 = ps2.executeQuery();

        String CategoryName = null;

        while (rs2.next()) {
            CategoryName = rs2.getString("name");
        }

        System.out.println(CategoryName);

        return CategoryName;

    }




    public int getIDFromMovie(Movie movie) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie WHERE title=?;");

        ps.setString(1, movie.getTitle());


        ResultSet rs = ps.executeQuery();

        int id = -1;

        while (rs.next()) {
            id = rs.getInt("MovieID");
        }

        return id;

    }


}
