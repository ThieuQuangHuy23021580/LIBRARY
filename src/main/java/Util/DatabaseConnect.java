package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    public static final String url = "jdbc:mysql://127.0.0.1:3306/library";
    public static final String user = "root";
    public static final String password = "huy104967";

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Kết nối đã được đóng.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
