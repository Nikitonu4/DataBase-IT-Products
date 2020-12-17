package sample.models;

import sample.entities.Providers;

import java.sql.SQLException;

public class Provider extends Providers {

    private long id;
    private String name;

    public Provider(long id, String name) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
