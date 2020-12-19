package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.models.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimularController extends Controller {
    private ObservableList<Product> simularProducts = FXCollections.observableArrayList();

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
    private TableColumn<Product, String> ram_simular;

    @FXML
    private TableColumn<Product, String> videocard_simular;

    @FXML
    private TableColumn<Product, String> windows_simular;

    @FXML
    private TableColumn<Product, String> cpu_frequency_simular;

    @FXML
    private Button close_button;

    @FXML
    void closeButtonAction() {
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
        ram_simular.setCellValueFactory(new PropertyValueFactory<>("ram"));
        videocard_simular.setCellValueFactory(new PropertyValueFactory<>("videocard"));
        windows_simular.setCellValueFactory(new PropertyValueFactory<>("windows"));
        cpu_frequency_simular.setCellValueFactory(new PropertyValueFactory<>("cpu_frequency"));
    }

    protected void showSimular(Product product) throws SQLException, ClassNotFoundException {
        setCells();
        reOpen();
        ResultSet res = products.selectAll();
        simularProducts.clear();

        long pr_category = categories.findIdByName(product.getCategory());

        while (res.next()) {
            if (res.getLong("category") == pr_category) {

                long provider_id = res.getLong("provider");
                String providerName = providers.findNamebyId(provider_id);

                long category_id = res.getLong("category");
                String categoryName = categories.findNamebyId(category_id);

                simularProducts.add(new Product(res.getString("id"),
                        res.getString("name"),
                        providerName,
                        categoryName,
                        res.getString("disk") + ":",
                        res.getString("price") + " руб.",
                        res.getString("memory") + " Мб",
                        res.getString("ram") + " Мб",
                        res.getString("videocard") + " Мб",
                        res.getString("windows"),
                        res.getString("cpu_frequency") + " Ггц"
                ));
            }
        }
        products_table.setItems(simularProducts);
    }
}
