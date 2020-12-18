package sample.models;

import sample.entities.CpuList;

import java.sql.SQLException;

public class Cpu extends CpuList {

    private long id;
    private String name;
    private String manufacturer;
    private String generation;
    private String frequency;
    private String cores;

    public Cpu(long id, String name, String manufacturer, String generation, String frequency, String cores) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.generation = generation;
        this.frequency = frequency;
        this.cores = cores;
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
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

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }


    @Override
    public String toString() {
        return manufacturer + " " + name + " " + frequency +" ГГц";
    }
}
