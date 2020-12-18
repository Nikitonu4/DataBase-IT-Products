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
import sample.entities.*;
import sample.models.Category;
import sample.models.Cpu;
import sample.models.DbManager;
import sample.models.Provider;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddProductController extends Controller {

    private Categories categories;
    private Computers computers;
    private CpuList cpuList;
    private Products products;
    private Providers providers;
    ResultSet res;
    ObservableList<Provider> providers_list = FXCollections.observableArrayList();
    ObservableList<Category> categories_list = FXCollections.observableArrayList();
    ObservableList<Cpu> cpu_list = FXCollections.observableArrayList();
    boolean flag = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TextField disk_addTable;

    @FXML
    private TextField memory_addTable;

    @FXML
    private ComboBox<Cpu> cpu_addTable;

    @FXML
    private TextField videocard_addTable;

    @FXML
    private TextField ram_addTable;

    @FXML
    private ComboBox<String> windows_addTable;

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
        DbManager db = new DbManager();
        this.categories = db.getCategories();
        this.computers = db.getComputers();
        this.cpuList = db.getCpuList();
        this.products = db.getProducts();
        this.providers = db.getProviders();
        windows_addTable.getItems().addAll("XP", "7", "8", "10");
        selectCategories();
        selectProviders();
        selectCpu();
        addButton_addTable.setOnAction(event -> {
            //TODO проверки!!!!!
            double price = 0;
            double memory = 0;
            int ram = 0;
            int videocard = 0;
            String name = name_addTable.getText();
            String disk = disk_addTable.getText();
            String windows = windows_addTable.getValue();
            flag = false;

            if (windows.isEmpty()) {
                windows_addTable.setStyle("-fx-border-color: red;");
                error.setText("Минимальная версия Windows некорректна");
                flag = true;
            } else
                name_addTable.setStyle("-fx-border-color: none;");

            if (name == null || name == "") {
                name_addTable.setStyle("-fx-border-color: red;");
                error.setText("Имя неккоректно");
                flag = true;
            } else {
                try {
                    res = products.selectAll();
                    while (res.next()) {
                        if (res.getString("name").equalsIgnoreCase(name)) {
                            name_addTable.setStyle("-fx-border-color: red;");
                            name_addTable.clear();
                            error.setText("Такой продукт уже есть");
                            flag = true;
                        }
                    }
                } catch (SQLException | ClassNotFoundException throwables) {
                    error.setText("Ошибка в имени продукта");
                    name_addTable.clear();
                    throwables.printStackTrace();
                }
//                name_addTable.setStyle("-fx-border-color: none;");
//                flag = false;
            }
            if (disk == null || disk == "") {
                disk_addTable.setStyle("-fx-border-color: red;");
                error.setText("Месторасположение на диске неккоректно");
                disk_addTable.clear();

                flag = true;
            } else if (disk.length() != 1) {
                disk_addTable.setStyle("-fx-border-color: red;");
                error.setText("Диск должен быть из одной буквы(A, B, C, D ...)");
                disk_addTable.clear();
                flag = true;
            }

            try {
                price = Double.parseDouble(price_addTable.getText());

            } catch (NumberFormatException e) {
                price_addTable.setStyle("-fx-border-color: red;");
                price_addTable.clear();
                error.setText("Цена неккоректна");
                flag = true;
            }

            try {
                memory = Double.parseDouble(memory_addTable.getText());
                if (memory <= 0) {
                    memory_addTable.setStyle("-fx-border-color: red;");
                    error.setText("Количество памяти должно быть больше 0");
                    memory_addTable.clear();
                    flag = true;
                }
            } catch (NumberFormatException e) {
                memory_addTable.setStyle("-fx-border-color: red;");
                memory_addTable.clear();
                error.setText("Количество памяти неккоректно");
                flag = true;
            }

            try {
                ram = Integer.parseInt(ram_addTable.getText());
                if (ram <= 0) {
                    ram_addTable.setStyle("-fx-border-color: red;");
                    error.setText("Оперативная память должна быть больше 0");
                    ram_addTable.clear();
                    flag = true;
                }
            } catch (NumberFormatException e) {
                ram_addTable.setStyle("-fx-border-color: red;");
                error.setText("Оперативная память неккоректна");
                ram_addTable.clear();

                flag = true;
            }

            try {
                videocard = Integer.parseInt(videocard_addTable.getText());
                if (videocard <= 0) {
                    videocard_addTable.setStyle("-fx-border-color: red;");
                    error.setText("Видеопамять должна быть больше 0");
                    videocard_addTable.clear();
                    flag = true;
                }
            } catch (NumberFormatException e) {
                videocard_addTable.setStyle("-fx-border-color: red;");
                error.setText("Видеопамять неккоректна");
                videocard_addTable.clear();

                flag = true;
            }

            if (!flag) {
                long providerId = provider_addTable.getValue().getId();
                long categoryId = category_addTable.getValue().getId();
                long cpuId = cpu_addTable.getValue().getId();

                try {
                    products.addProduct(name, providerId, categoryId, disk, price, memory, cpuId, ram, videocard, windows);
                    closeButtonAction();
                    newWindow("complete");
//                    updateMainTable();

                } catch (SQLException throwables) {
                    error.setText("Ошибка ввода");
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    error.setText("Ошибка ввода");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void selectProviders() throws SQLException, ClassNotFoundException {
        res = providers.selectAll();
        while (res.next()) {
            providers_list.add(new Provider(res.getLong("id"),
                    res.getString("name")));
        }

        provider_addTable.setItems(providers_list);
        provider_addTable.setConverter(new StringConverter<>() {
            @Override
            public String toString(Provider obj) {
                return obj == null ? "" : obj.getName();
            }

            @Override
            public Provider fromString(String s) {
                if (s == null || s.isEmpty()) {
                    flag = true;
                    provider_addTable.setStyle("-fx-border-color: red;");
                    error.setText("Выберите производителя");
                    return null;
                }
                for (Provider obj : provider_addTable.getItems()) {
                    if (obj.getName().equalsIgnoreCase(s)) {
                        return obj;
                    }
                }
                flag = true;
                provider_addTable.setStyle("-fx-border-color: red;");
                error.setText("Выберите производителя");
                return null;
            }
        });
    }

    void selectCategories() throws SQLException, ClassNotFoundException {
        res = categories.selectAll();
        while (res.next()) {
            categories_list.add(new Category(res.getLong("id"),
                    res.getString("name")));
        }
        category_addTable.setItems(categories_list);
        category_addTable.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category obj) {
                return obj == null ? "" : obj.getName();
            }

            @Override
            public Category fromString(String s) {
                if (s == null || s.isEmpty()) {
                    flag = true;
                    category_addTable.setStyle("-fx-border-color: red;");
                    error.setText("Выберите категорию");
                    return null;
                }
                for (Category obj : category_addTable.getItems()) {
                    if (obj.getName().equalsIgnoreCase(s)) {
                        return obj;
                    }
                }
                category_addTable.setStyle("-fx-border-color: red;");
                error.setText("Выберите категорию");
                flag = true;
                return null;
            }
        });
    }

    void selectCpu() throws SQLException, ClassNotFoundException {
        res = cpuList.selectAll();
        while (res.next()) {
            cpu_list.add(new Cpu(res.getLong("id"),
                    res.getString("name"),
                    res.getString("manufacturer"),
                    res.getString("generation"),
                    res.getString("frequency"),
                    res.getString("cores")));
        }
        cpu_addTable.setItems(cpu_list);
        cpu_addTable.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cpu obj) {
                return obj == null ? "" : obj.toString();
            }

            @Override
            public Cpu fromString(String s) {
                if (s == null || s.isEmpty()) {
                    flag = true;
                    cpu_addTable.setStyle("-fx-border-color: red;");
                    error.setText("Выберите минимальный процессор");
                    return null;
                }
                for (Cpu obj : cpu_addTable.getItems()) {
                    if (obj.toString().equalsIgnoreCase(s)) {
                        return obj;
                    }
                }
                cpu_addTable.setStyle("-fx-border-color: red;");
                error.setText("Выберите минимальный процессор");
                flag = true;
                return null;
            }
        });
    }
}
