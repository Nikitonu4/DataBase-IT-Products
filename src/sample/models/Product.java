package sample.models;

import sample.entities.Products;

import java.sql.SQLException;

public class Product extends Products {

    private String id;
    private String name;
    private String provider;
    private String category;
    private String disk;
    private String price;
    private String memory;
    private String cpu;
    private String ram;
    private String videocard;
    private String windows;

    public Product(String id, String name, String provider, String category, String disk, String price, String memory, String cpu, String ram, String videocard, String windows) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.category = category;
        this.disk = disk;
        this.price = price;
        this.memory = memory;
        this.cpu = cpu;
        this.ram = ram;
        this.videocard = videocard;
        this.windows = windows;
    }

    public Product(String name, String provider, String category, String disk, String price) throws SQLException, ClassNotFoundException {
        this.name = name;
        this.provider = provider;
        this.category = category;
        this.disk = disk;
        this.price = price;
    }

    public String getVideocard() {
        return videocard;
    }

    public void setVideocard(String videocard) {
        this.videocard = videocard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getWindows() {
        return windows;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }
}
