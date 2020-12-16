package sample.models;
import sample.entities.*;

import java.sql.*;

public class DbManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/itProducts1";
    private static final String DB_User = "postgres";
    private static final String DB_Password = "1111";
    private static final String DB_Driver = "org.postgresql.Driver";

    public Connection connection;
    public PreparedStatement ps;
    public ResultSet resultSet;

    private Categories categories;
    private Computers computers;
    private CpuList cpuList;
    private Products products;
    private Providers providers;
    private VideocardsList videocardsList;

    public Categories getCategories() {
        return categories;
    }

    public Computers getComputers() {
        return computers;
    }

    public CpuList getCpuList() {
        return cpuList;
    }

    public Products getProducts() {
        return products;
    }

    public Providers getProviders() {
        return providers;
    }

    public VideocardsList getVideocardsList() {
        return videocardsList;
    }

    /** Подключение к БД */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(DB_URL, DB_User, DB_Password);
    }

    public DbManager() throws SQLException, ClassNotFoundException{
        Class.forName(DB_Driver);
        categories = new Categories();
        computers = new Computers();
        cpuList = new CpuList();
        products = new Products();
        providers = new Providers();
        videocardsList = new VideocardsList();
    }

    public void createTablesAndForeignKeys() throws SQLException, ClassNotFoundException {
        //создание таблиц
        categories.createTable();
        cpuList.createTable();
        videocardsList.createTable();
        computers.createTable();
        providers.createTable();
        products.createTable();
    }
//    public void addProviders(String name) throws Exception{
//        connection = getConnection();
//        String addStr = "INSERT INTO " + DbConst.PROVIDERS_TABLE +"(\"name\")" + "VALUES (?)";
//        PreparedStatement ps = connection.prepareStatement(addStr);
//        ps.setString(1, name);
//        ps.executeUpdate();
//    }

//
//    /** Добавление в таблицу информации */
//    public void postTable() throws Exception {
//        String hello_key = "привет";
//        String hello_value = "hello";
//
//        try {
//            connection = getConnection();
//            ps = connection.prepareStatement("INSERT INTO chatbot(our_key, our_value)" +
//                    "VALUES ('"+hello_key+"','"+hello_value+"')");
//            ps.executeUpdate();
//        } catch (Exception e){
//            System.out.println(e);
//        } finally {
//            System.out.println("Таблица заполнена.");
//        }
//    }
//
//    /** Выбор и вывод данных */
//    public ArrayList<String> get() throws Exception {
//        try {
//            connection = getConnection();
//            ps = connection.prepareStatement("SELECT our_key, our_value FROM chatbot");
//            resultSet = ps.executeQuery();
//
//            ArrayList<String> array = new ArrayList<>();
//
//            while (resultSet.next()) {
//                System.out.print(resultSet.getString("our_key"));
//                System.out.print(" ");
//                System.out.println(resultSet.getString("our_value"));
//
//                array.add(resultSet.getString("our_key"));
//                array.add(resultSet.getString("our_value"));
//            }
//            System.out.println("Все записи выбраны!");
//            return array;
//
//        } catch (Exception e){
//            System.out.println(e);
//        }
//        return null;
//    }
}
