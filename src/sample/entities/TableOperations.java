package sample.entities;

import java.sql.SQLException;

// Операции с таблицами
public interface TableOperations {
    void createTable() throws SQLException, ClassNotFoundException; // создание таблицы
}

