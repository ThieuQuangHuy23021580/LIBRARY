package Util;

import model.User;

import java.sql.*;

public class UserDAO {
    private static Connection conn = null;

    public static User authenticator(String email, String password) throws SQLException {
        conn = DatabaseConnect.getConnection();

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            String userName = rs.getString("username");
            String phone = rs.getString("phone");
            String role = rs.getString("role");
            return new User(id, userName, email, phone, password, role);
        }
        return null;
    }

    public static void handleRegister(String email, String password) throws SQLException {
        conn = DatabaseConnect.getConnection();

        String sql = "INSERT INTO user (email, password, role) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);
        ps.setString(3, User.USER);

        ps.executeUpdate();
    }

}
