package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.models.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InformationController extends Controller {

    @FXML
    private Button close_button;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label provider;

    @FXML
    private Label category;

    @FXML
    private Label disk;

    @FXML
    private Label price;

    @FXML
    private Label memory;

    @FXML
    private Label cpu_frequency;

    @FXML
    private Label ram;

    @FXML
    private Label videocard;

    @FXML
    private Label windows;

    @FXML
    void closeButtonAction() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

    }


    protected void info(String name_search, String provider_search, String category_search) throws SQLException, ClassNotFoundException {
        reOpen();
        ResultSet res = products.selectAll();
        Product product = null;

        long id_provider = providers.findIdByName(provider_search);
        long id_category = categories.findIdByName(category_search);

        while (res.next()) {
            if ((res.getString("name").equalsIgnoreCase(name_search)) &&
                    res.getLong("provider") == id_provider &&
                    res.getLong("category") == id_category) {
                product = new Product(res.getString("id"),
                        res.getString("name"),
                        provider_search,
                        category_search,
                        res.getString("disk"),
                        res.getString("price"),
                        res.getString("memory"),
                        res.getString("ram"),
                        res.getString("videocard"),
                        res.getString("windows"),
                        res.getString("cpu_frequency")
                );
            }
        }

        if (product == null) {
            closeButtonAction();
            newWindow("notFoundProduct");
        } else {
            id.setText(product.getId());
            name.setText(product.getName());
            provider.setText(product.getProvider());
            category.setText(product.getCategory());
            disk.setText(product.getDisk());
            price.setText(product.getPrice() + " руб");
            memory.setText(product.getMemory() + " Мб");
            cpu_frequency.setText(product.getCpu_frequency() + " Ггц");
            ram.setText(product.getRam() + " Мб");
            videocard.setText(product.getVideocard() + " Мб");
            windows.setText(product.getWindows());
        }
    }
}