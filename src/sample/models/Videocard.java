package sample.models;

import sample.entities.VideocardsList;

import java.sql.SQLException;

public class Videocard extends VideocardsList {

    private int id;
    private String manufacturer;
    private String year;
    private String model;
    private String ram;
    private String price;

    public Videocard(int id, String manufacturer, String year, String model, String ram, String price) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.manufacturer = manufacturer;
        this.year = year;
        this.model = model;
        this.ram = ram;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return manufacturer+ " "+ model + " " + ram + " Гб";
    }
}
