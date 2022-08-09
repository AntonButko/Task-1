package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String url = "jdbc:mysql://127.0.0.1:3306";
    private static final String user = "root";
    private static final String password = "root";


    private static final String sqlCreateTable = "CREATE TABLE Task1.User (" +
            "user_id BIGINT NOT NULL AUTO_INCREMENT," +
            "name VARCHAR(30) NOT NULL," +
            "lastName VARCHAR(30) NOT NULL," +
            "age TINYINT NOT NULL," +
            "PRIMARY KEY (user_id))";

    private static final String sqlDropTable = "DROP TABLE Task1.User";


    public static String getSqlCreateTable() {
        return sqlCreateTable;
    }

    public static String getSqlDropTable() {
        return sqlDropTable;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    public static boolean tableExists(Connection connection) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null,
                "Task1.User", new String[]{"TABLE"});
        return resultSet.next();
    }


}
