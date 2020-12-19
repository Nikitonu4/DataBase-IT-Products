package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProviderController extends Controller {

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
    void initialize() {
        add_provider_button.setOnAction(event -> {
            try {
                reOpen();
                String providerName = provider_name.getText();
                ResultSet res = providers.selectAll();
                boolean flag = false;
                if (providerName.isEmpty()) {
                    provider_name.setStyle("-fx-border-color: red;");
                    error.setText("Название производителя пусто");
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
