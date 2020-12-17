package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.entities.Categories;
import sample.entities.CpuList;
import sample.entities.Products;
import sample.entities.Providers;
import sample.models.DbManager;
import sample.models.Product;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InformationController extends Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private Label cpu;

    @FXML
    private Label ram;

    @FXML
    private Label videocard;

    @FXML
    void closeButtonAction() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

    }


    public void info(String name_search, String provider_search, String category_search) throws SQLException, ClassNotFoundException {
        DbManager db = new DbManager();

        Products products = db.getProducts();
        Providers providers = db.getProviders();
        Categories categories = db.getCategories();
        CpuList cpus = db.getCpuList();
        ResultSet res = products.selectAll();
        Product product = null;
        boolean flag = false;

//        long id_products = products.findIdByName(name_search);
//        if (id_products == 0){
//            newWindow("notFoundProduct");
//        }

        long id_provider = providers.findIdByName(provider_search);
//        if (id_provider == 0){
//            closeButtonAction();
//            newWindow("notFoundProduct");
//        }

        long id_category = categories.findIdByName(category_search);
//        if (id_category == 0){
//            closeButtonAction();
//            newWindow("notFoundProduct");
//        }

        while (res.next()) {
            if ((res.getString("name").equalsIgnoreCase(name_search)) &&
                    res.getLong("provider") == id_provider &&
                    res.getLong("category") == id_category) {
                product = new Product(res.getString("id"),
                        res.getString("name"),
                        provider_search.toLowerCase(),
                        category_search.toLowerCase(),
                        res.getString("disk"),
                        res.getString("price"),
                        res.getString("memory"),
                        res.getString("cpu"),
                        res.getString("ram"),
                        res.getString("videocard")
                );
            }
        }

        if (product == null) {
            closeButtonAction();
            newWindow("notFoundProduct");
        } else {
            String cpuStr = cpus.allSpecificial(Long.parseLong(product.getCpu()));
            id.setText(product.getId());
            name.setText(product.getName());
            provider.setText(product.getProvider());
            category.setText(product.getCategory());
            disk.setText(product.getDisk());
            price.setText(product.getPrice() + " руб");
            memory.setText(product.getMemory() + " Мб");
            cpu.setText(cpuStr);
            ram.setText(product.getRam() + " Мб");
            videocard.setText(product.getVideocard() + " Мб");
        }
    }
}