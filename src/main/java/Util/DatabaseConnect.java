package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    public static final String url = "jdbc:mysql://127.0.0.1:3307/library";
    public static final String user = "root";
    public static final String password = "";

    public static Connection getConnection()  throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
