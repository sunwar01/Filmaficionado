package com.example.filmaficionado.ControlObjects;



public class Category {

    int id;

    String name;



    public Category(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public Integer getID() {
        return id;
    }



    @Override
    public String toString() {
        return name;
    }


}
