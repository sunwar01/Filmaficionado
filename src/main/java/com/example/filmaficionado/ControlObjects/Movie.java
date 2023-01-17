package com.example.filmaficionado.ControlObjects;



public class Movie {

    int id;
    String title;

    double personalRating;

    double imdbRating;

    String category;

    String director;

    String actors;

    String trailer;

    int releaseDate;

    int lastViewed;

    String picture;
    String description;

    public Movie(int id, String title, double personalRating, double imdbRating, String category, String director, String actors, String trailer, int releaseDate , int lastViewed, String picture, String description){

        this.id = id;
        this.title = title;
        this.personalRating = personalRating;
        this.imdbRating = imdbRating;
        this.category = category;
        this.director = director;
        this.actors = actors;
        this.trailer = trailer;
        this.releaseDate = releaseDate;
        this.lastViewed = lastViewed;
        this.picture = picture;
        this.description = description;


    }

        //----------------------------
        //Getters!
        //----------------------------

    public String getTitle() {
        return title;
    }

    public String getActors() {
        return actors;
    }

    public String getDirector() {
        return director;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getCategory() {
        return category;
    }

    public Integer getReleaseDate() {
        return releaseDate;
    }

    public Integer getLastViewed() {
        return lastViewed;
    }

    public double getPersonalRating() {
        return personalRating;
    }


    public double getImdbRating() {
        return imdbRating;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    //----------------------------
    //Setters!
    //----------------------------

    public void setTitle(String title) {
        this.title = title;
    }








}
