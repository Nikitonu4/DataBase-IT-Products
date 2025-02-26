package sample.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Providers extends BaseTable implements TableOperations {

    public Providers() throws SQLException, ClassNotFoundException {
        super("providers");
    }

    @Override
    public void createTable() throws SQLException, ClassNotFoundException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS providers(" +
                "    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +
                "    name character varying(150) COLLATE pg_catalog.default NOT NULL);", "Обновлена таблица " + tableName);
    }

    public long findIdByName(String name) throws SQLException, ClassNotFoundException {
        reopenConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM providers WHERE \"name\" ~* ?;");
        ps.setString(1, name);
        ResultSet result = ps.executeQuery();
        long providerId = 0;
        while (result.next()) {
            providerId = result.getLong("id");
        }
        return providerId;
    }

    public String findNamebyId(long id) throws SQLException, ClassNotFoundException {
        reopenConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM providers WHERE \"id\" = ?;");
        ps.setLong(1, id);
        ResultSet result = ps.executeQuery();
        String provider = null;
        while (result.next()) {
            provider = result.getString("name");
        }
        return provider;
    }

    public void addProvider(String name) throws SQLException, ClassNotFoundException {
        reopenConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO providers (name) VALUES (?);");
        ps.setString(1, name);
        ps.executeUpdate();
        connection.close();
        System.out.println("Производитель добавлен!");
    }
}
