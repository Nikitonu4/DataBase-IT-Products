package sample.entities;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Products extends BaseTable implements TableOperations{

    public Products() throws SQLException, ClassNotFoundException {
        super("products");
    }

    @Override
    public void createTable() throws SQLException, ClassNotFoundException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS products(" +
                "    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +
                "    name character varying(150) NOT NULL," +
                "    provider integer NOT NULL," +
                "    category integer NOT NULL," +
                "    disk character varying(1) NOT NULL, " +
                "    price numeric(10,2) NOT NULL," +
                "    memory numeric(50,2) NOT NULL," +
                "    cpu integer NOT NULL," +
                "    ram integer NOT NULL," +
                "    videocard integer NOT NULL," +
                "CONSTRAINT fk_category FOREIGN KEY (category)" +
                "    REFERENCES public.categories (id) MATCH SIMPLE, " +
                "CONSTRAINT fk_cpu_pc FOREIGN KEY (cpu)" +
                "    REFERENCES public.cpu_list (id) MATCH SIMPLE," +
                "    CONSTRAINT fk_provider FOREIGN KEY (provider)" +
                "    REFERENCES public.providers (id) MATCH SIMPLE);", "Обновлена таблица " + tableName);
    }

//TODO ДОБАВИТЬ ВЕРСИЮ windows
    @Override
    public void insertBaseDate(String sql) throws SQLException, ClassNotFoundException {
        super.executeSqlStatement(sql);
    }

    public void addProduct(String name, long providerId, long categoryId, String disk, double price, double memory, long cpuId, int ram, int videocard) throws SQLException, ClassNotFoundException {
        reopenConnection();
        String sql = "INSERT INTO products (name, provider, category, disk, price, memory, cpu, ram, videocard) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setLong(2, providerId);
        ps.setLong(3, categoryId);
        ps.setString(4, disk);
        ps.setDouble(5, price);
        ps.setDouble(6, memory);
        ps.setLong(7, cpuId);
        ps.setInt(8, ram);
        ps.setInt(9, videocard);
        ps.executeUpdate();
        System.out.println("Добавлено!");
    }
}
