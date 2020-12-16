package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.entities.*;
import sample.models.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddProductController extends Controller{

    private Categories categories;
    private Computers computers;
    private CpuList cpuList;
    private Products products;
    private Providers providers;
    private VideocardsList videocardsList;
    ResultSet res;
    ObservableList<Provider> providers_list = FXCollections.observableArrayList();
    ObservableList<Category> categories_list = FXCollections.observableArrayList();
    ObservableList<Cpu> cpu_list = FXCollections.observableArrayList();
    ObservableList<Videocard> videocard_list = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name_addTable;

    @FXML
    private TextField price_addTable;

    @FXML
    private Button addButton_addTable;

    @FXML
    private ComboBox<Provider> provider_addTable;

    @FXML
    private ComboBox<Category> category_addTable;

    @FXML
    private TextField disk_addTable;

    @FXML
    private TextField memory_addTable;

    @FXML
    private ComboBox<Cpu> cpu_addTable;

    @FXML
    private ComboBox<Videocard> videocard_addTable;

    @FXML
    private TextField ram_addTable;

    @FXML
    private Button close_button;

    @FXML
    void closeButtonAction(){
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DbManager db = new DbManager();
        this.categories = db.getCategories();
        this.computers = db.getComputers();
        this.cpuList = db.getCpuList();
        this.products = db.getProducts();
        this.providers = db.getProviders();
        this.videocardsList = db.getVideocardsList();
        selectProviders();
        selectCategories();
        selectCpu();
        selectVideocard();

        addButton_addTable.setOnAction(event -> {
            //TODO проверки!!!!!
            String name = name_addTable.getText();
            long providerId = provider_addTable.getValue().getId();
            long categoryId = category_addTable.getValue().getId();
            String disk = disk_addTable.getText();
            double price = Double.parseDouble(price_addTable.getText());
            double memory = Double.parseDouble(memory_addTable.getText());
            long cpuId = cpu_addTable.getValue().getId();
            int ram = Integer.parseInt(ram_addTable.getText());
            long videocardId = videocard_addTable.getValue().getId();
            try {
                products.addProduct(name, providerId, categoryId, disk, price, memory, cpuId, ram, videocardId);
                closeButtonAction();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
//            try {
//                updateMainTable();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        });
    }

    void selectProviders() throws SQLException, ClassNotFoundException {
        res = providers.selectAll();
        while (res.next()) {
            providers_list.add(new Provider(res.getInt("id"),
                    res.getString("name")));
        }

        provider_addTable.setItems(providers_list);
        provider_addTable.setConverter(new StringConverter<>() {
            @Override
            public String toString(Provider obj) {
                return obj == null ? "" : obj.getName();
            }

            @Override
            public Provider fromString(String s) {
                if (s == null || s.isEmpty()) {
                    return null;
                }
                for (Provider obj : provider_addTable.getItems()) {
                    if (obj.getName().equalsIgnoreCase(s)) {
                        return obj;
                    }
                }
                return null;
            }
        });
    }

    void selectCategories() throws SQLException, ClassNotFoundException {
        res = categories.selectAll();
        while (res.next()) {
            categories_list.add(new Category(res.getInt("id"),
                    res.getString("name")));
        }
        category_addTable.setItems(categories_list);
        category_addTable.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category obj) {
                return obj == null ? "" : obj.getName();
            }

            @Override
            public Category fromString(String s) {
                if (s == null || s.isEmpty()) {
                    return null;
                }
                for (Category obj : category_addTable.getItems()) {
                    if (obj.getName().equalsIgnoreCase(s)) {
                        return obj;
                    }
                }
                return null;
            }
        });
    }

    void selectCpu() throws SQLException, ClassNotFoundException {
        res = cpuList.selectAll();
        while (res.next()) {
            cpu_list.add(new Cpu(res.getInt("id"),
                    res.getString("name"),
                    res.getString("manufacturer"),
                    res.getString("year"),
                    res.getString("frequency"),
                    res.getString("price")));
        }
        cpu_addTable.setItems(cpu_list);
        cpu_addTable.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cpu obj) {
                return obj == null ? "" : obj.toString();
            }

            @Override
            public Cpu fromString(String s) {
                if (s == null || s.isEmpty()) {
                    return null;
                }
                for (Cpu obj : cpu_addTable.getItems()) {
                    if (obj.toString().equalsIgnoreCase(s)) {
                        return obj;
                    }
                }
                return null;
            }
        });
    }

    void selectVideocard() throws SQLException, ClassNotFoundException {
        res = videocardsList.selectAll();
        while (res.next()) {
            videocard_list.add(new Videocard(res.getInt("id"),
                    res.getString("manufacturer"),
                    res.getString("year"),
                    res.getString("model"),
                    res.getString("ram"),
                    res.getString("price")));
        }
        videocard_addTable.setItems(videocard_list);
        videocard_addTable.setConverter(new StringConverter<>() {
            @Override
            public String toString(Videocard obj) {
                return obj == null ? "" : obj.toString();
            }

            @Override
            public Videocard fromString(String s) {
                if (s == null || s.isEmpty()) {
                    return null;
                }
                for (Videocard obj : videocard_addTable.getItems()) {
                    if (obj.toString().equalsIgnoreCase(s)) {
                        return obj;
                    }
                }
                return null;
            }
        });
    }


}
