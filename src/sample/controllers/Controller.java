package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.entities.Categories;
import sample.entities.Computers;
import sample.entities.Products;
import sample.entities.Providers;
import sample.models.DbManager;
import sample.models.Product;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    protected DbManager db;
    protected Categories categories;
    protected Computers computers;
    protected Products products;
    protected Providers providers;
    private ObservableList<Product> products_list_view = FXCollections.observableArrayList();

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
    private TextField name_search;

    @FXML
    private TextField category_search;

    @FXML
    private TextField provider_search;

    @FXML
    void SimularProducts() {
        Product pr;
        pr = products_table.getSelectionModel().getSelectedItem();
        if (pr != null)
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/simularProducts.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                SimularController simc = loader.getController();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.show();
                simc.showSimular(pr);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    @FXML
    void addProductButton() {
        newWindow("addProduct");
    }

    @FXML
    void addCategoryButton() {
        newWindow("addCategory");
    }

    @FXML
    void addProviderButton() {
        newWindow("addProvider");
    }

    @FXML
    void findButton() {
        boolean flag = false;

        if (name_search.getText().isEmpty()) {
            name_search.setStyle("-fx-border-color: red;");
            flag = true;
        } else {
            name_search.setStyle("-fx-border-color: none;");
        }

        if (provider_search.getText().isEmpty()) {
            provider_search.setStyle("-fx-border-color: red;");
            flag = true;

        } else {
            provider_search.setStyle("-fx-border-color: none;");
        }

        if (category_search.getText().isEmpty()) {
            category_search.setStyle("-fx-border-color: red;");
            flag = true;
        } else {
            category_search.setStyle("-fx-border-color: none;");
        }

        if (!flag) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/informationProduct.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                InformationController infoc = loader.getController();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.show();
                infoc.info(name_search.getText().trim(), provider_search.getText().trim(), category_search.getText().trim());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void searchProducts() throws SQLException, ClassNotFoundException {
        newWindow("searchProducts");
    }

    @FXML
    void initialize() throws Exception {
        reOpen();
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

//        addProduct_button.setOnAction(event -> {
//        });

//        addCategory_button.setOnAction(event -> {
//            newWindow("addCategory");
//        });

//        addProvider_button.setOnAction(event -> {
//            newWindow("addProvider");
//        });

//        find_button.setOnAction(event -> {
//            boolean flag = false;
//            if (name_search.getText().isEmpty()) {
//                name_search.setStyle("-fx-border-color: red;");
//                flag = true;
//            } else {
//                name_search.setStyle("-fx-border-color: none;");
//            }
//            if (provider_search.getText().isEmpty()) {
//                provider_search.setStyle("-fx-border-color: red;");
//                flag = true;
//
//            } else {
//                provider_search.setStyle("-fx-border-color: none;");
//            }
//            if (category_search.getText().isEmpty()) {
//                category_search.setStyle("-fx-border-color: red;");
//                flag = true;
//            } else {
//                category_search.setStyle("-fx-border-color: none;");
//            }
//
//            if (!flag) {
//                try {
//                    FXMLLoader loader = new FXMLLoader();
//                    loader.setLocation(getClass().getResource("../view/informationProduct.fxml"));
//                    loader.load();
//                    Parent root = loader.getRoot();
//                    Stage stage = new Stage();
//                    InformationController infoc = loader.getController();
//                    stage.initModality(Modality.APPLICATION_MODAL);
//                    stage.setScene(new Scene(root));
//                    stage.show();
//                    infoc.info(name_search.getText().trim(), provider_search.getText().trim(), category_search.getText().trim());
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    protected void newWindow(String windowName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/" + windowName + ".fxml"));
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
    }

    private void setCellsMain() {
        products_name_table.setCellValueFactory(new PropertyValueFactory<>("name"));
        products_provider_table.setCellValueFactory(new PropertyValueFactory<>("provider"));
        products_price_table.setCellValueFactory(new PropertyValueFactory<>("price"));
        products_category_table.setCellValueFactory(new PropertyValueFactory<>("category"));
        products_disk_table.setCellValueFactory(new PropertyValueFactory<>("disk"));
    }

    protected void updateMainTable() throws SQLException, ClassNotFoundException {
        setCellsMain();
        reOpen();
        products_list_view.clear();

        ResultSet selected = products.selectAll();

        while (selected.next()) {
            long provider_id = selected.getLong("provider");
            String providerName = providers.findNamebyId(provider_id);

            long category_id = selected.getLong("category");
            String categoryName = categories.findNamebyId(category_id);

            products_list_view.add(new Product(
                    selected.getString("name"),
                    providerName,
                    categoryName,
                    selected.getString("disk") + ":",
                    selected.getString("price") + " руб."
            ));
        }
        products_table.setItems(products_list_view);
    }

    protected void reOpen() throws SQLException, ClassNotFoundException {
        this.db = new DbManager();
        this.categories = db.getCategories();
        this.computers = db.getComputers();
        this.products = db.getProducts();
        this.providers = db.getProviders();
    }

}