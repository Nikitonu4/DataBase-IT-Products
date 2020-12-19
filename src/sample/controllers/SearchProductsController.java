package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sample.models.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchProductsController extends Controller {
    private ObservableList<Product> seachedProducts = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> products_searchTable;

    @FXML
    private TableColumn<Product, String> id_searchTable;

    @FXML
    private TableColumn<Product, String> name_searchTable;

    @FXML
    private TableColumn<Product, String> provider_searchTable;

    @FXML
    private TableColumn<Product, String> category_searchTable;

    @FXML
    private TableColumn<Product, String> disk_searchTable;

    @FXML
    private TableColumn<Product, String> price_searchTable;

    @FXML
    private TableColumn<Product, String> memory_searchTable;

    @FXML
    private TableColumn<Product, String> ram_searchTable;

    @FXML
    private TableColumn<Product, String> videocard_searchTable;

    @FXML
    private TableColumn<Product, String> windows_searchTable;

    @FXML
    private TableColumn<Product, String> cpu_frequency_searchTable;

    @FXML
    private TextField name_searchFiled;

    @FXML
    private TextField provider_searchFiled;

    @FXML
    private TextField category_searchFiled;

    @FXML
    private TextField disk_searchFiled;

    @FXML
    private TextField price_searchFiled;

    @FXML
    private TextField memory_searchFiled;

    @FXML
    private TextField ram_searchFiled;

    @FXML
    private TextField videocard_searchFiled;

    @FXML
    private TextField windows_searchFiled;

    @FXML
    private TextField cpuFrequency_searchFiled;

    @FXML
    private Button close_button;

    @FXML
    private Circle status;

    @FXML
    private Label statusM;

    @FXML
    void closeButtonAction() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    //НАЖАТИЕ НА КНОПКУ ПОИСКА
    @FXML
    void searchProducts() throws SQLException, ClassNotFoundException {
        seachedProducts.clear();
        long id_provider = 0;
        long id_category = 0;
        if (!(provider_searchFiled.getText() == null || provider_searchFiled.getText() == ""))
            id_provider = providers.findIdByName(provider_searchFiled.getText());

        if (!(category_searchFiled.getText() == null || category_searchFiled.getText() == ""))
            id_category = categories.findIdByName(category_searchFiled.getText());

        if (name_searchFiled.getText().isEmpty() &&
                id_provider == 0 &&
                id_category == 0 &&
                disk_searchFiled.getText().isEmpty() &&
                price_searchFiled.getText().isEmpty() &&
                memory_searchFiled.getText().isEmpty() &&
                ram_searchFiled.getText().isEmpty() &&
                videocard_searchFiled.getText().isEmpty() &&
                windows_searchFiled.getText().isEmpty() &&
                cpuFrequency_searchFiled.getText().isEmpty()) {
            showAll();
            status.setFill(Color.rgb(51, 234, 51));
            statusM.setText("Выполнено");
        } else {
            ResultSet res = products.selectWhere(
                    name_searchFiled.getText(),
                    id_provider,
                    id_category,
                    disk_searchFiled.getText(),
                    price_searchFiled.getText(),
                    memory_searchFiled.getText(),
                    ram_searchFiled.getText(),
                    videocard_searchFiled.getText(),
                    windows_searchFiled.getText(),
                    cpuFrequency_searchFiled.getText()
            );

            if (res == null) {
                status.setFill(Color.RED);
                statusM.setText("Ошибка");


            } else {
                status.setFill(Color.rgb(51, 234, 51));
                statusM.setText("Выполнено");
                while (res.next()) {
                    String categoryName = categories.findNamebyId(res.getLong("category"));
                    String providerName = providers.findNamebyId(res.getLong("provider"));

                    seachedProducts.add(new Product(res.getString("id"),
                            res.getString("name"),
                            providerName,
                            categoryName,
                            res.getString("disk"),
                            res.getString("price") + " руб.",
                            res.getString("memory") + " Мб",
                            res.getString("ram") + " Мб",
                            res.getString("videocard") + " Мб",
                            res.getString("windows"),
                            res.getString("cpu_frequency") + " Ггц"
                    ));
                }
                products_searchTable.setItems(seachedProducts);
            }
        }
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        setCells();
        reOpen();
        showAll();
    }

    private void showAll() throws SQLException, ClassNotFoundException {
        ResultSet res = products.selectAll();
        while (res.next()) {
            String categoryName = categories.findNamebyId(res.getLong("category"));

            String providerName = providers.findNamebyId(res.getLong("provider"));

            seachedProducts.add(new Product(res.getString("id"),
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
        products_searchTable.setItems(seachedProducts);
    }

    private void setCells() {
        id_searchTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_searchTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        provider_searchTable.setCellValueFactory(new PropertyValueFactory<>("provider"));
        category_searchTable.setCellValueFactory(new PropertyValueFactory<>("category"));
        disk_searchTable.setCellValueFactory(new PropertyValueFactory<>("disk"));
        price_searchTable.setCellValueFactory(new PropertyValueFactory<>("price"));
        memory_searchTable.setCellValueFactory(new PropertyValueFactory<>("memory"));
        ram_searchTable.setCellValueFactory(new PropertyValueFactory<>("ram"));
        videocard_searchTable.setCellValueFactory(new PropertyValueFactory<>("videocard"));
        windows_searchTable.setCellValueFactory(new PropertyValueFactory<>("windows"));
        cpu_frequency_searchTable.setCellValueFactory(new PropertyValueFactory<>("cpu_frequency"));
    }
}