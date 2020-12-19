package sample.models;

import sample.entities.*;

import java.sql.*;

public class DbManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/itProducts";
    private static final String DB_User = "postgres";
    private static final String DB_Password = "1111";
    private static final String DB_Driver = "org.postgresql.Driver";

    public Connection connection;
    public PreparedStatement ps;
    public ResultSet resultSet;

    private Categories categories;
    private Computers computers;
    private Products products;
    private Providers providers;

    public Categories getCategories() {
        return categories;
    }

    public Computers getComputers() {
        return computers;
    }

    public Products getProducts() {
        return products;
    }

    public Providers getProviders() {
        return providers;
    }

    /**
     * Подключение к БД
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(DB_URL, DB_User, DB_Password);
    }

    public DbManager() throws SQLException, ClassNotFoundException {
        Class.forName(DB_Driver);
        categories = new Categories();
        computers = new Computers();
        products = new Products();
        providers = new Providers();
    }

    public void createTablesAndForeignKeys() throws SQLException, ClassNotFoundException {
        //создание таблиц
        categories.createTable();
        computers.createTable();
        providers.createTable();
        products.createTable();
    }

}
