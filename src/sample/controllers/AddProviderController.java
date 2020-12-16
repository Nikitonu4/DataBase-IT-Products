package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.entities.Providers;
import sample.models.DbManager;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddProviderController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField provider_name;

    @FXML
    private Button add_provider_button;

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
        add_provider_button.setOnAction(event -> {
            try {
                DbManager db;
                String providerName = provider_name.getText();
                db = new DbManager();
                boolean flag = false;
                Providers providers = db.getProviders();
                ResultSet res = providers.selectAll();
                if (providerName == null || providerName == "") {
                    provider_name.setStyle("-fx-border-color: red;");
                    error.setText("Производитель пуст");
                } else {
                    while (res.next()) {
                        if (res.getString("name").equalsIgnoreCase(providerName)) {
                            provider_name.setStyle("-fx-border-color: red;");
                            error.setText("Такой производитель уже есть");
                            flag = true;
                        }
                    }
                    if (!flag) {
                        providers.addProvider(providerName);
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
