package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.entities.*;
import sample.models.DbManager;
import sample.models.Product;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller {
    DbManager db;
    private Categories categories;
    private Computers computers;
    private CpuList cpuList;
    private Products products;
    private Providers providers;
    private VideocardsList videocardsList;
    ObservableList<Product> products_list_view = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Product> products_table;

    @FXML
    private TableColumn<Product, String> products_name_table;

    @FXML
    private TableColumn<Product, String> products_provider_table;

    @FXML
    private TableColumn<Product, Double> products_price_table;

    @FXML
    private TableColumn<Product, String> products_category_table;

    @FXML
    private TableColumn<Product, String> products_disk_table;

    @FXML
    private Button update_button;

    @FXML
    private MenuItem addProduct_button;

    @FXML
    private MenuItem addCategory_button;

    @FXML
    private MenuItem addProvider_button;

    @FXML
    private MenuItem addCpu_button;

    @FXML
    private MenuItem addVideocard_button;

    @FXML
    void initialize() throws Exception {
        db = new DbManager();
        db.createTablesAndForeignKeys();
        this.categories = db.getCategories();
        this.computers = db.getComputers();
        this.cpuList = db.getCpuList();
        this.products = db.getProducts();
        this.providers = db.getProviders();
        this.videocardsList = db.getVideocardsList();
        updateMainTable();

        update_button.setOnAction(event -> {
            try {
                updateMainTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        addProduct_button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/addProduct.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
        addCategory_button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/addCategory.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        addProvider_button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/addProvider.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

    }

    protected void updateMainTable() throws SQLException, ClassNotFoundException {
        products_list_view.clear();
        products_name_table.setCellValueFactory(new PropertyValueFactory<>("name"));
        products_provider_table.setCellValueFactory(new PropertyValueFactory<>("provider"));
        products_price_table.setCellValueFactory(new PropertyValueFactory<>("price"));
        products_category_table.setCellValueFactory(new PropertyValueFactory<>("category"));
        products_disk_table.setCellValueFactory(new PropertyValueFactory<>("disk"));

        ResultSet selected = products.selectAll();

        while (selected.next()) {
            int provider_id = selected.getInt("provider");
            String providerName = providers.findNamebyId(provider_id);

            int category_id = selected.getInt("category");
            String categoryName = categories.findNamebyId(category_id);

            products_list_view.add(new Product(
                    selected.getString("name"),
                    providerName,
                    categoryName,
                    selected.getString("disk"),
                    selected.getString("price")
            ));
        }
        products_table.setItems(products_list_view);
    }
}