package Util;

import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.User;

import java.io.File;
import java.io.PipedReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean handleRegister(String email, String password) throws SQLException {
        conn = DatabaseConnect.getConnection();
        String checkEmailSql = "SELECT 1 FROM user WHERE email = ?";
        PreparedStatement checkEmailPs = conn.prepareStatement(checkEmailSql);
        checkEmailPs.setString(1, email);

        ResultSet rs = checkEmailPs.executeQuery();
        if (rs.next()) {
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

    public static void deleteUser(User user) {
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            conn = DatabaseConnect.getConnection();
            String sql = "SELECT id, username, email,phone FROM user where role = 'User'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("phone"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void saveImagePathToDatabase(File selectedFile, User user) {
        String imagePath = selectedFile.getAbsolutePath();

        String insertQuery = "update user set imagePath = ? where id = ?";

        try (Connection conn = DatabaseConnect.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, imagePath);
            ps.setInt(2, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadImageFromDatabase(User user, ImageView imageView) {
        String query = "SELECT imagePath FROM user WHERE id = ?";

        try (Connection conn = DatabaseConnect.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, user.getId());  // userId
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String imagePath = rs.getString("imagePath");
                File file = new File(imagePath);

                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
