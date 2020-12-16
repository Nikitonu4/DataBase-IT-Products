package sample.models;

import sample.entities.Categories;

import java.sql.SQLException;

public class Category extends Categories {

    private int id;
    private String name;

    public Category(int id, String name) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
