package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.models.Product;

import java.io.IOException;
import java.sql.SQLException;

public class CheckCompatibilityController extends Controller {

    @FXML
    private TextField memory_checkCompatibility;

    @FXML
    private Button close_button;

    @FXML
    private Label error;

    @FXML
    private TextField ram_checkCompatibility;

    @FXML
    private TextField videocard_checkCompatibility;

    @FXML
    private ComboBox<String> windows_checkCompatibility;

    @FXML
    private TextField cpuFrequency_checkCompatibility;

    @FXML
    private Button checkCompatibilityButton;


    @FXML
    void closeButtonAction() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        windows_checkCompatibility.getItems().addAll("2000", "XP", "7", "8", "10");
        reOpen();
    }

    public void checkCompatibility(Product product) throws IOException {
        checkCompatibilityButton.setOnAction(event -> {
                    int memory = 0;
                    int ram = 0;
                    int videocard = 0;
                    String windows = null;
                    double cpu_frequency = 0;

                    boolean flag = true;

                    boolean memoryPass = false;
                    boolean ramPass = false;
                    boolean videocardPass = false;
                    boolean windowsPass = false;
                    boolean cpuFrequencyPass = false;

//            int productMemory = 0;
//            int productRam = 0;
//            int productVideocard = 0;
//            String productWindows = null;
//            double ProductCpuFrequency = 0;
//
//            long provider_id = 0;
//            long category_id = 0;
//            try {
//                provider_id = providers.findIdByName(product.getProvider());
//                category_id = categories.findIdByName(product.getCategory());
//
//
//                ResultSet res = products.selectNameProviderCategory(product.getName(), provider_id, category_id);
//
//                while (res.next()) {
//                    product.setMemory(res.getString("memory"));
//                    product.setRam(res.getString("ram"));
//                    product.setVideocard(res.getString("videocard"));
//                    product.setCpu_frequency(res.getString("cpu_frequency"));
//                    product.setWindows(res.getString("windows"));
//                }

                    //проверка памяти
                    if (!checkMemory(memory_checkCompatibility.getText())) {
                        flag = false;
                    } else {
                        memory_checkCompatibility.setStyle("-fx-border-color: none;");
                        memory = Integer.parseInt(memory_checkCompatibility.getText());
                    }

                    //проверка оперативной памяти
                    if (!checkRam(ram_checkCompatibility.getText())) {
                        flag = false;
                    } else {
                        ram_checkCompatibility.setStyle("-fx-border-color: none;");
                        ram = Integer.parseInt(ram_checkCompatibility.getText());
                    }

                    //проверка видеопамяти
                    if (!checkVideocard(videocard_checkCompatibility.getText())) {
                        flag = false;
                    } else {
                        videocard_checkCompatibility.setStyle("-fx-border-color: none;");
                        videocard = Integer.parseInt(videocard_checkCompatibility.getText());
                    }

                    //проверка windows
                    if (!checkWindows(windows_checkCompatibility.getValue())) {
                        flag = false;
                    } else {
                        windows_checkCompatibility.setStyle("-fx-border-color: none;");
                        windows = windows_checkCompatibility.getValue();
                    }

                    //проверка частоты процессора
                    if (!checkCpuFrequency(cpuFrequency_checkCompatibility.getText())) {
                        flag = false;
                    } else {
                        cpuFrequency_checkCompatibility.setStyle("-fx-border-color: none;");
                        cpu_frequency = Double.parseDouble(cpuFrequency_checkCompatibility.getText());
                    }

                    if (flag) {

                        if (Double.parseDouble(product.getMemory()) < memory) {
                            memoryPass = true;
                        }

                        if (Double.parseDouble(product.getRam()) < ram) {
                            ramPass = true;
                        }

                        if (Double.parseDouble(product.getVideocard()) < videocard) {
                            videocardPass = true;
                        }

                        //проверка на windows
                        int ourWindowsInt = 0;
                        switch (windows) {
                            case "2000":
                                ourWindowsInt = 5;
                                break;
                            case "XP":
                                ourWindowsInt = 6;
                                break;
                            case "7":
                                ourWindowsInt = 7;
                                break;
                            case "8":
                                ourWindowsInt = 8;
                                break;
                            case "10":
                                ourWindowsInt = 9;
                                break;
                        }

                        int productWindowsInt = 0;
                        switch (product.getWindows()) {
                            case "2000":
                                productWindowsInt = 5;
                                break;
                            case "XP":
                                productWindowsInt = 6;
                                break;
                            case "7":
                                productWindowsInt = 7;
                                break;
                            case "8":
                                productWindowsInt = 8;
                                break;
                            case "10":
                                productWindowsInt = 9;
                                break;
                        }

                        if (ourWindowsInt >= productWindowsInt) {
                            windowsPass = true;
                        }


                        if (Double.parseDouble(product.getCpu_frequency()) < cpu_frequency) {
                            cpuFrequencyPass = true;
                        }

                        try {
                            resultCheck(product, memoryPass, ramPass, videocardPass, windowsPass, cpuFrequencyPass);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    protected void clearAllFiled() {
        error.setText("");
        memory_checkCompatibility.clear();
        ram_checkCompatibility.clear();
        videocard_checkCompatibility.clear();
        cpuFrequency_checkCompatibility.clear();
    }

    private void resultCheck(Product product, boolean memoryPass, boolean ramPass, boolean videocardPass, boolean windowsPass, boolean cpuFrequencyPass) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/resultCompatibility.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        ResultCompatibilityController rcc = loader.getController();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
        rcc.checkCompatibility(product, memoryPass, ramPass, videocardPass, windowsPass, cpuFrequencyPass);
    }

    private boolean checkMemory(String memory) {
        try {
            if (memory == "" || memory == null) {
                memory_checkCompatibility.setStyle("-fx-border-color: red;");
                error.setText("Необходимая память пуста");
                return false;
            }

            if (Integer.parseInt(memory) <= 0) {
                memory_checkCompatibility.setStyle("-fx-border-color: red;");
                error.setText("Необходимая память должна быть положительной");
                memory_checkCompatibility.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            memory_checkCompatibility.setStyle("-fx-border-color: red;");
            error.setText("Необходимая память должна быть числом");
            memory_checkCompatibility.clear();
            return false;
        }
        return true;
    }

    private boolean checkRam(String ram) {
        try {
            if (ram == "" || ram == null) {
                ram_checkCompatibility.setStyle("-fx-border-color: red;");
                error.setText("Оперативная память пуста");
                return false;
            }

            if (Integer.parseInt(ram) <= 0) {
                ram_checkCompatibility.setStyle("-fx-border-color: red;");
                error.setText("Оперативная память должна быть положительной");
                ram_checkCompatibility.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            ram_checkCompatibility.setStyle("-fx-border-color: red;");
            error.setText("Оперативная память должна быть числом");
            ram_checkCompatibility.clear();
            return false;
        }
        return true;
    }

    private boolean checkVideocard(String videocard) {
        try {
            if (videocard == "" || videocard == null) {
                videocard_checkCompatibility.setStyle("-fx-border-color: red;");
                error.setText("Видеопамять пуста");
                return false;
            }

            if (Integer.parseInt(videocard) <= 0) {
                videocard_checkCompatibility.setStyle("-fx-border-color: red;");
                error.setText("Видеопамять должна быть положительной");
                videocard_checkCompatibility.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            videocard_checkCompatibility.setStyle("-fx-border-color: red;");
            error.setText("Видеопамять должна быть числом");
            videocard_checkCompatibility.clear();
            return false;
        }
        return true;
    }

    private boolean checkWindows(String windows) {
        if (windows == null || windows.isEmpty()) {
            windows_checkCompatibility.setStyle("-fx-border-color: red;");
            error.setText("Версия windows пуста");
            return false;
        }
        return true;
    }

    private boolean checkCpuFrequency(String cpuFrequency) {
        try {
            if (cpuFrequency == "" || cpuFrequency == null) {
                cpuFrequency_checkCompatibility.setStyle("-fx-border-color: red;");
                error.setText("Минимальная частота процессора пуста");
                return false;
            }

            if (Double.parseDouble(cpuFrequency) <= 0 || Double.parseDouble(cpuFrequency) >= 9) {
                cpuFrequency_checkCompatibility.setStyle("-fx-border-color: red;");
                error.setText("Частота должна быть больше 0 и меньше 9");
                cpuFrequency_checkCompatibility.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            cpuFrequency_checkCompatibility.setStyle("-fx-border-color: red;");
            error.setText("Частота должна быть числом");
            cpuFrequency_checkCompatibility.clear();
            return false;
        }
        return true;
    }
}
