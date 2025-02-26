package sample.entities;

import sample.models.DbManager;

import java.io.Closeable;
import java.sql.*;

// Сервисный родительский класс, куда вынесена реализация общих действий для всех таблиц
public class BaseTable implements Closeable {
    Connection connection;  // JDBC-соединение для работы с таблицей
    String tableName;       // Имя таблицы

    BaseTable(String tableName) throws SQLException, ClassNotFoundException { // Для реальной таблицы передадим в конструктор её имя
        this.tableName = tableName;
        this.connection = DbManager.getConnection(); // Установим соединение с СУБД для дальнейшей работы
    }

    // Закрытие
    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия SQL соединения!");
        }
    }

    // Выполнить SQL команду без параметров в СУБД, по завершению выдать сообщение в консоль
    void executeSqlStatement(String sql, String description) throws SQLException, ClassNotFoundException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        statement.execute(sql); // Выполняем statement - sql команду
        statement.close();      // Закрываем statement для фиксации изменений в СУБД
        if (description != null)
            System.out.println(description);
    }

    void executeSqlStatement(String sql) throws SQLException, ClassNotFoundException {
        executeSqlStatement(sql, null);
    }

    public ResultSet selectAll() throws SQLException, ClassNotFoundException {
        reopenConnection();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM " + tableName + " ORDER BY name ASC;");
        return res;
    }

    void reopenConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            connection = DbManager.getConnection();
        }
    }
}
