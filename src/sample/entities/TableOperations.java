package sample.entities;

import java.sql.SQLException;

// Операции с таблицами
public interface TableOperations {
    void createTable() throws SQLException, ClassNotFoundException; // создание таблицы
    void insertBaseDate(String sql) throws SQLException, ClassNotFoundException; // наполнение первичным контентом
//    ResultSet selectProducts() throws SQLException, ClassNotFoundException;
}

