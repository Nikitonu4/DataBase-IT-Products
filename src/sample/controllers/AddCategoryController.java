package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCategoryController extends Controller {

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
    void initialize() {
        add_category_button.setOnAction(event -> {
            try {
                reOpen();
                ResultSet res = categories.selectAll();
                String categoryName = category_name.getText();
                boolean flag = false;
                if (categoryName.isEmpty()) {
                    category_name.setStyle("-fx-border-color: red;");
                    error.setText("Название категории пусто");
                } else {
                    while (res.next()) {
                        if (res.getString("name").equalsIgnoreCase(categoryName)) {
                            category_name.setStyle("-fx-border-color: red;");
                            error.setText("Такая категория уже есть");
                            flag = true;
                        }
                    }
                    if (!flag) {
                        categories.addCategory(categoryName);
                        closeButtonAction();
                        newWindow("complete");
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
