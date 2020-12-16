package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.entities.Categories;
import sample.models.DbManager;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCategoryController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField category_name;

    @FXML
    private Button add_category_button;

    @FXML
    private Button close_button;

    @FXML
    private Label error;

    @FXML
    void closeButtonAction() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize(){
        add_category_button.setOnAction(event -> {
            try {
                DbManager db;
                String categoryName = category_name.getText();
                db = new DbManager();
                boolean flag = false;
                Categories categories = db.getCategories();
                ResultSet res = categories.selectAll();
                if (categoryName == null || categoryName == "") {
                    category_name.setStyle("-fx-border-color: red;");
                    error.setText("Категория пуста");
                } else {
                    while (res.next()) {
                        if (res.getString("name").equals(categoryName)) {
                            category_name.setStyle("-fx-border-color: red;");
                            error.setText("Такая категория уже есть");
                            flag = true;
                        }
                    }
                    if (!flag) {
                        categories.addCategory(categoryName);
                        closeButtonAction();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
