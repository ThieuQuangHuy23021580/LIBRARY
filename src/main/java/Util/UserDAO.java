package Util;

import javafx.scene.control.Alert;
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

    public static void showAlert(String content, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static boolean handleRegister(String email, String password) throws SQLException {
        conn = DatabaseConnect.getConnection();
        String checkEmailSql = "SELECT 1 FROM user WHERE email = ?";
        PreparedStatement checkEmailPs = conn.prepareStatement(checkEmailSql);
        checkEmailPs.setString(1, email);

        ResultSet rs = checkEmailPs.executeQuery();
        if (rs.next()) {
            showAlert("Tai khoan da ton tai", "dang ki khong thanh cong");
            return false;
        }
        String sql = "INSERT INTO user (email, password, role) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);
        ps.setString(3, User.USER);

        ps.executeUpdate();
        return true;
    }

    public static void handleUpdateInfo(User user) throws SQLException {
        conn = DatabaseConnect.getConnection();

        String sql = "update user set username = ?, phone = ?, password = ? where id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user.getUserName());
        ps.setString(2, user.getPhone());
        ps.setString(3, user.getPassword());
        ps.setInt(4, user.getId());
        ps.executeUpdate();
    }

}
