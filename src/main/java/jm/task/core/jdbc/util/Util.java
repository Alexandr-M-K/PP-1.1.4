package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            connection.setAutoCommit (false);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Не удалось подключиться к базе");
        }
        return connection;
    }
}
