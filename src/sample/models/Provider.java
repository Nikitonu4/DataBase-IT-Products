package sample.models;

import sample.entities.Providers;

import java.sql.SQLException;

public class Provider extends Providers {

    private int id;
    private String name;

    public Provider(int id, String name) throws SQLException, ClassNotFoundException {
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
