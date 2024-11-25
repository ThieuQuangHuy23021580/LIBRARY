package controller.libraryapp;

import model.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String URL = "jdbc:mysql://localhost:3307/library";
    private static final String USER = "root";  // Your MySQL username
    private static final String PASSWORD = "123";  // Your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertBook(Book book) {
        String query = "INSERT INTO books (title, author, publisher, description, imageUrl, quantity, category) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setString(4, book.getDescription());
            stmt.setString(5, book.getImageUrl());
            stmt.setInt(6, book.getQuantity());
            stmt.setString(7, book.getCategory());  // Insert the category

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

