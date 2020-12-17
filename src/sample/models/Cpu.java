package sample.models;

import sample.entities.CpuList;

import java.sql.SQLException;

public class Cpu extends CpuList {

    private long id;
    private String name;
    private String manufacturer;
    private String year;
    private String frequency;
    private String price;

    public Cpu(long id, String name, String manufacturer, String year, String frequency, String price) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.year = year;
        this.frequency = frequency;
        this.price = price;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return manufacturer + " " + name + " " + frequency +" ГГц";
    }
}
