package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CompleteController extends Controller {

    @FXML
    private Button close_button;

    @FXML
    void closeButtonAction(){
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
    }
}
