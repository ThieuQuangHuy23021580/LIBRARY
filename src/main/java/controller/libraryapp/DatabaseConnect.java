package controller.libraryapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    public static final String url = "jdbc:mariadb://localhost:3306/account";
    public static final String user = "root";
    public static final String password = "071205";

    public Connection getConnection()  throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
