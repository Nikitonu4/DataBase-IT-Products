package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.entities.CpuList;
import sample.models.DbManager;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCpuController extends Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name_addCpu;

    @FXML
    private TextField frequency_addCpu;

    @FXML
    private Button addButton_addCpu;

    @FXML
    private ComboBox<String> manufacturer_addCpu;

    @FXML
    private TextField generation_addCpu;

    @FXML
    private Button close_button;

    @FXML
    private Label error;

    @FXML
    private ComboBox<String> cores_addCpu;

    @FXML
    void closeButtonAction() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        manufacturer_addCpu.getItems().addAll("Intel", "AMD");
        cores_addCpu.getItems().addAll("1", "2", "4", "6", "8", "10", "12", "14", "16");
        addButton_addCpu.setOnAction(event -> {
            try {
                DbManager db = new DbManager();
                CpuList cpuList = db.getCpuList();
                ResultSet res = cpuList.selectAll();

                String name = name_addCpu.getText();
                String manufacturer = manufacturer_addCpu.getValue();
                boolean flag = false;
                int generation = 0;
                double frequency = 0;
                int cores = Integer.parseInt(cores_addCpu.getValue());

                try {
                    generation = Integer.parseInt(generation_addCpu.getText());
                    if (generation <= 0) {
                        generation_addCpu.setStyle("-fx-border-color: red;");
                        error.setText("Некорректное поколение");
                        flag = true;
                    }
                } catch (NumberFormatException e) {
                    generation_addCpu.setStyle("-fx-border-color: red;");
                    error.setText("Некорректное поколение");
                }

                try {
                    frequency = Double.parseDouble(frequency_addCpu.getText());
                    if (frequency <= 0 || frequency >= 6) {
                        frequency_addCpu.setStyle("-fx-border-color: red;");
                        error.setText("Некорректная частота");
                        flag = true;
                    }
                } catch (NumberFormatException e) {
                    frequency_addCpu.setStyle("-fx-border-color: red;");
                    error.setText("Некорректная частота");
                    flag = true;
                }


                if (name == null || name == "") {
                    name_addCpu.setStyle("-fx-border-color: red;");
                    error.setText("Заполните все поля");
                    flag = true;
                } else if (manufacturer == null || manufacturer == "") {
                    manufacturer_addCpu.setStyle("-fx-border-color: red;");
                    error.setText("Заполните все поля");
                    flag = true;
                } else {
                    while (res.next()) {
                        if (res.getString("name").equalsIgnoreCase(name)) {
                            name_addCpu.setStyle("-fx-border-color: red;");
                            error.setText("Такой процессор уже есть");
                            flag = true;
                        }
                    }
                }
                if (!flag) {
                    cpuList.addCpu(name, manufacturer, generation, frequency, cores);
                    closeButtonAction();
                    newWindow("complete");
                }
            } catch (SQLException throwables) {
                error.setText("ОШИБКА В ДАННЫХ");
            } catch (ClassNotFoundException e) {
                error.setText("ОШИБКА В ДАННЫХ");
            }
        });
    }
}
