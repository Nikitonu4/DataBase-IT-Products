package sample.entities;

import java.sql.SQLException;

public class Computers extends BaseTable implements TableOperations {

    public Computers() throws SQLException, ClassNotFoundException {
        super("computers");
    }

    @Override
    public void createTable() throws SQLException, ClassNotFoundException {
//        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS computers(" +
//                "    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +
//                "    cpu integer NOT NULL," +
//                "    videocard integer NOT NULL," +
//                "    ram integer NOT NULL," +
//                " CONSTRAINT fk_cpu_pc FOREIGN KEY (cpu) REFERENCES public.cpu_list (id) MATCH SIMPLE);", "Обновлена таблица " + tableName);
    }
}


