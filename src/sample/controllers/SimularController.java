package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.entities.*;
import sample.models.DbManager;
import sample.models.Product;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SimularController {
    private ObservableList<Product> simularProducts = FXCollections.observableArrayList();
    private DbManager db;
    private Categories categories;
    private Computers computers;
    private CpuList cpuList;
    private Products products;
    private Providers providers;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Product> products_table;

    @FXML
    private TableColumn<Product, String> id_simular;

    @FXML
    private TableColumn<Product, String> name_simular;

    @FXML
    private TableColumn<Product, String> provider_simular;

    @FXML
    private TableColumn<Product, String> category_simular;

    @FXML
    private TableColumn<Product, String> disk_simular;

    @FXML
    private TableColumn<Product, String> price_simular;

    @FXML
    private TableColumn<Product, String> memory_simular;

    @FXML
    private TableColumn<Product, String> cpu_simular;

    @FXML
    private TableColumn<Product, String> ram_simular;

    @FXML
    private TableColumn<Product, String> videocard_simular;

    @FXML
    private TableColumn<Product, String> windows_simular;

    @FXML
    private Button close_button;

    @FXML
    void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
    }

    private void setCells() {
        id_simular.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_simular.setCellValueFactory(new PropertyValueFactory<>("name"));
        provider_simular.setCellValueFactory(new PropertyValueFactory<>("provider"));
        category_simular.setCellValueFactory(new PropertyValueFactory<>("category"));
        disk_simular.setCellValueFactory(new PropertyValueFactory<>("disk"));
        price_simular.setCellValueFactory(new PropertyValueFactory<>("price"));
        memory_simular.setCellValueFactory(new PropertyValueFactory<>("memory"));
        cpu_simular.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        ram_simular.setCellValueFactory(new PropertyValueFactory<>("ram"));
        videocard_simular.setCellValueFactory(new PropertyValueFactory<>("videocard"));
        windows_simular.setCellValueFactory(new PropertyValueFactory<>("windows"));

    }

    protected void reOpen() throws SQLException, ClassNotFoundException {
        db = new DbManager();
        this.categories = db.getCategories();
        this.computers = db.getComputers();
        this.cpuList = db.getCpuList();
        this.products = db.getProducts();
        this.providers = db.getProviders();
    }

    protected void showSimular(Product product) throws SQLException, ClassNotFoundException {

        db = new DbManager();
        Products products = db.getProducts();
        ResultSet res = products.selectAll();
        setCells();
        reOpen();
        simularProducts.clear();

        long pr_category = categories.findIdByName(product.getCategory());

        while (res.next()) {
            if (res.getLong("category") == pr_category) {

                long provider_id = res.getLong("provider");
                String providerName = providers.findNamebyId(provider_id);

                long category_id = res.getLong("category");
            String categoryName = categories.findNamebyId(category_id);

                long cpu_id = res.getLong("cpu");
                String cpuName = cpuList.allSpecificial(cpu_id);

                simularProducts.add(new Product(res.getString("id"),
                        res.getString("name"),
                        providerName,
                        categoryName,
                        res.getString("disk"),
                        res.getString("price"),
                        res.getString("memory"),
                        cpuName,
                        res.getString("ram"),
                        res.getString("videocard"),
                        res.getString("windows")));
            }
        }
        products_table.setItems(simularProducts);
    }
}
