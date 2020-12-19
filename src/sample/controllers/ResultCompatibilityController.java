package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.models.Product;

public class ResultCompatibilityController {

    @FXML
    private Label error;

    @FXML
    private Button close_button;

    @FXML
    private Label memory_resultCheck;

    @FXML
    private Label ram_resultCheck;

    @FXML
    private Label videocard_resultCheck;

    @FXML
    private Label windows_resultCheck;

    @FXML
    private Label cpuFrequency_resultCheck;

    @FXML
    void closeButtonAction() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

    }

    public void checkCompatibility(Product product, boolean memoryPass, boolean ramPass, boolean videocardPass, boolean windowsPass, boolean cpuFrequencyPass){
        if (memoryPass){
            memory_resultCheck.setTextFill(Color.rgb(51, 234, 51));
            memory_resultCheck.setText("совместимо");
        }
        else{
            memory_resultCheck.setTextFill(Color.RED);
            memory_resultCheck.setText("не совместимо, требуется не меньше "+product.getMemory()+" Мб");

        }

        if (ramPass){
            ram_resultCheck.setTextFill(Color.rgb(51, 234, 51));
            ram_resultCheck.setText("совместимо");
        }
        else{
            ram_resultCheck.setTextFill(Color.RED);
            ram_resultCheck.setText("не совместимо, требуется не меньше "+product.getRam()+" Мб");
        }

        if (videocardPass){
            videocard_resultCheck.setTextFill(Color.rgb(51, 234, 51));
            videocard_resultCheck.setText("совместимо");
        }
        else{
            videocard_resultCheck.setTextFill(Color.RED);
            videocard_resultCheck.setText("не совместимо, требуется не меньше "+product.getVideocard()+" Мб");

        }

        if (windowsPass){
            windows_resultCheck.setTextFill(Color.rgb(51, 234, 51));
            windows_resultCheck.setText("совместимо");
        }
        else{
            windows_resultCheck.setTextFill(Color.RED);
            windows_resultCheck.setText("не совместимо, версия windows должна быть не ниже "+product.getWindows());
        }

        if (cpuFrequencyPass){
            cpuFrequency_resultCheck.setTextFill(Color.rgb(51, 234, 51));
            cpuFrequency_resultCheck.setText("совместимо");
        }
        else{
            cpuFrequency_resultCheck.setTextFill(Color.RED);
            cpuFrequency_resultCheck.setText("не совместимо, требуется не меньше "+product.getCpu_frequency()+" Ггц");
        }

    }
}
