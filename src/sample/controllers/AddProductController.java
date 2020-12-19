package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.Category;
import sample.models.Provider;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProductController extends Controller {

    private ResultSet res;
    private ObservableList<Provider> providersList = FXCollections.observableArrayList();
    private ObservableList<Category> categoriesList = FXCollections.observableArrayList();
    private boolean flag = true;

    @FXML
    private TextField name_addTable;

    @FXML
    private TextField price_addTable;

    @FXML
    private Button addButton_addTable;

    @FXML
    private ComboBox<Provider> provider_addTable;

    @FXML
    private ComboBox<Category> category_addTable;

    @FXML
    private ComboBox<String> windows_addTable;

    @FXML
    private TextField disk_addTable;

    @FXML
    private TextField memory_addTable;

    @FXML
    private TextField videocard_addTable;

    @FXML
    private TextField ram_addTable;

    @FXML
    private TextField cpu_frequency_addTable;

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
    void initialize() throws SQLException, ClassNotFoundException {
        reOpen();
        windows_addTable.getItems().addAll("2000", "XP", "7", "8", "10");
        selectProviders();
        selectCategories();
    }

    @FXML
    void addButton() throws SQLException, ClassNotFoundException {
        String name = name_addTable.getText();
        String disk = disk_addTable.getText();
        double price = 0;
        int memory = 0;
        int ram = 0;
        int videocard = 0;
        String windows = windows_addTable.getValue();
        double cpu_frequency = 0;

        flag = true;

        //Проверка имени
        if (!checkName(name)) {
            flag = false;
        } else
            name_addTable.setStyle("-fx-border-color: none;");

        //Проверка месторасположения на диске
        if (!checkDisk(disk)) {
            flag = false;
        } else
            disk_addTable.setStyle("-fx-border-color: none;");

        //Проверка цены
        if (!checkPrice(price_addTable.getText())) {
            flag = false;
        } else {
            price = Double.parseDouble(price_addTable.getText());
            price_addTable.setStyle("-fx-border-color: none;");
        }

        //Проверка необходимой памяти
        if (!checkMemory(memory_addTable.getText())) {
            flag = false;
        } else {
            memory = Integer.parseInt(memory_addTable.getText());
            memory_addTable.setStyle("-fx-border-color: none;");
        }

        //Проверка оперативной памяти
        if (!checkRam(ram_addTable.getText())) {
            flag = false;
        } else {
            ram = Integer.parseInt(ram_addTable.getText());
            ram_addTable.setStyle("-fx-border-color: none;");
        }

        //Проверка видеопамяти
        if (!checkVideocard(videocard_addTable.getText())) {
            flag = false;
        } else {
            videocard = Integer.parseInt(videocard_addTable.getText());
            videocard_addTable.setStyle("-fx-border-color: none;");
        }

        //Проверка версии windows
        if (!checkWindows(windows)) {
            flag = false;
        } else {
            windows_addTable.setStyle("-fx-border-color: none;");
        }

        //Проверка минимальной частоты процессора
        if (!checkCpuFrequency(cpu_frequency_addTable.getText())) {
            flag = false;
        } else {
            cpu_frequency = Double.parseDouble(cpu_frequency_addTable.getText());
            cpu_frequency_addTable.setStyle("-fx-border-color: none;");
        }

        if (!checkExist())
            flag = false;
        //Если прошли все проверки
        if (flag) {
            try {
                long providerId = provider_addTable.getValue().getId();
                long categoryId = category_addTable.getValue().getId();
                products.addProduct(name, providerId, categoryId, disk, price, memory, ram, videocard, windows, cpu_frequency);
                closeButtonAction();
                newWindow("complete");
//                updateMainTable();

            } catch (SQLException throwables) {
                error.setText("Не все поля заполнены корректно");
            } catch (ClassNotFoundException e) {
                error.setText("Не все поля заполнены корректно");
            } catch (Exception e) {
                error.setText("Не все поля заполнены корректно");
            }
        }
    }

    private void selectProviders() throws SQLException, ClassNotFoundException {
        res = providers.selectAll();
        while (res.next()) {
            providersList.add(new Provider(res.getLong("id"),
                    res.getString("name")));
        }

        provider_addTable.setItems(providersList);
        provider_addTable.setConverter(new StringConverter<>() {
            @Override
            public String toString(Provider obj) {
                return obj == null ? "" : obj.getName();
            }

            @Override
            public Provider fromString(String s) {
                if (s == null || s.isEmpty()) {
                    flag = false;
                    provider_addTable.setStyle("-fx-border-color: red;");
                    error.setText("Название производителя пусто");
                    return null;
                }
                for (Provider obj : provider_addTable.getItems()) {
                    if (obj.getName().equalsIgnoreCase(s)) {
                        provider_addTable.setStyle("-fx-border-color: none;");
                        flag = false;
                        return obj;
                    }
                }
                return null;
            }
        });
    }

    private void selectCategories() throws SQLException, ClassNotFoundException {
        res = categories.selectAll();
        while (res.next()) {
            categoriesList.add(new Category(res.getLong("id"),
                    res.getString("name")));
        }
        category_addTable.setItems(categoriesList);
        category_addTable.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category obj) {
                return obj == null ? "" : obj.getName();
            }

            @Override
            public Category fromString(String s) {
                if (s == null || s.isEmpty()) {
                    flag = false;
                    category_addTable.setStyle("-fx-border-color: red;");
                    error.setText("Название категории пусто");
                    return null;
                }
                for (Category obj : category_addTable.getItems()) {
                    if (obj.getName().equalsIgnoreCase(s)) {
                        category_addTable.setStyle("-fx-border-color: none;");
                        flag = true;
                        return obj;
                    }
                }
                return null;
            }
        });
    }

    private boolean checkName(String name) {
        if (name_addTable.getText() == "" || name_addTable.getText() == null) {
            name_addTable.setStyle("-fx-border-color: red;");
            error.setText("Имя продукта пусто");
            return false;
        }
        return true;
    }

    private boolean checkDisk(String disk) {
        if (disk_addTable.getText() == "" || disk_addTable.getText() == null) {
            disk_addTable.setStyle("-fx-border-color: red;");
            error.setText("Месторасположение на диске пусто");
            return false;
        }

        if (disk.length() != 1) {
            disk_addTable.setStyle("-fx-border-color: red;");
            error.setText("Место на диск должно быть одной буквой(A, B, C ...)");
            disk_addTable.clear();
            return false;
        }
        return true;
    }

    private boolean checkPrice(String price) {
        try {
            if (price_addTable.getText() == "" || price_addTable.getText() == null) {
                price_addTable.setStyle("-fx-border-color: red;");
                error.setText("Цена пуста");
                return false;
            }

            if (Double.parseDouble(price) < 0) {
                price_addTable.setStyle("-fx-border-color: red;");
                error.setText("Цена должна быть больше или равна 0");
                price_addTable.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            price_addTable.setStyle("-fx-border-color: red;");
            error.setText("Цена должна быть числом");
            price_addTable.clear();
            return false;
        }
        return true;
    }

    private boolean checkMemory(String memory) {
        try {
            if (memory_addTable.getText() == "" || memory_addTable.getText() == null) {
                memory_addTable.setStyle("-fx-border-color: red;");
                error.setText("Необходимая память пуста");
                return false;
            }

            if (Integer.parseInt(memory) <= 0) {
                memory_addTable.setStyle("-fx-border-color: red;");
                error.setText("Необходимая память должна быть положительной");
                memory_addTable.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            memory_addTable.setStyle("-fx-border-color: red;");
            error.setText("Необходимая память должна быть числом");
            memory_addTable.clear();
            return false;
        }
        return true;
    }

    private boolean checkRam(String ram) {
        try {
            if (ram_addTable.getText() == "" || ram_addTable.getText() == null) {
                ram_addTable.setStyle("-fx-border-color: red;");
                error.setText("Оперативная память пуста");
                return false;
            }

            if (Integer.parseInt(ram) <= 0) {
                ram_addTable.setStyle("-fx-border-color: red;");
                error.setText("Оперативная память должна быть положительной");
                ram_addTable.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            ram_addTable.setStyle("-fx-border-color: red;");
            error.setText("Оперативная память должна быть числом");
            ram_addTable.clear();
            return false;
        }
        return true;
    }

    private boolean checkVideocard(String videocard) {
        try {
            if (videocard_addTable.getText() == "" || videocard_addTable.getText() == null) {
                videocard_addTable.setStyle("-fx-border-color: red;");
                error.setText("Видеопамять пуста");
                return false;
            }

            if (Integer.parseInt(videocard) <= 0) {
                videocard_addTable.setStyle("-fx-border-color: red;");
                error.setText("Видеопамять должна быть положительной");
                videocard_addTable.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            videocard_addTable.setStyle("-fx-border-color: red;");
            error.setText("Видеопамять должна быть числом");
            videocard_addTable.clear();
            return false;
        }
        return true;
    }

    private boolean checkWindows(String windows) {
        if (windows == null || windows.isEmpty()) {
            windows_addTable.setStyle("-fx-border-color: red;");
            error.setText("Версия windows пуста");
            return false;
        }
        return true;
    }

    private boolean checkCpuFrequency(String cpuFrequency) {
        try {
            if (cpu_frequency_addTable.getText() == "" || cpu_frequency_addTable.getText() == null) {
                cpu_frequency_addTable.setStyle("-fx-border-color: red;");
                error.setText("Минимальная частота процессора пуста");
                return false;
            }

            if (Double.parseDouble(cpuFrequency) <= 0 || Double.parseDouble(cpuFrequency) >= 9) {
                cpu_frequency_addTable.setStyle("-fx-border-color: red;");
                error.setText("Частота должна быть больше 0 и меньше 9");
                cpu_frequency_addTable.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            cpu_frequency_addTable.setStyle("-fx-border-color: red;");
            error.setText("Частота должна быть числом");
            cpu_frequency_addTable.clear();
            return false;
        }
        return true;
    }

    private boolean checkExist() throws SQLException, ClassNotFoundException {
        //Если совпадает имя и производитель - false
        res = products.selectAll();
        while (res.next()) {
            if (res.getString("name").equalsIgnoreCase(name_addTable.getText()) &&
                    res.getInt("provider") == provider_addTable.getValue().getId()
            ) {
                error.setText("Такой продукт уже существует");
                name_addTable.clear();
                return false;
            }
        }
        return true;
    }
}
