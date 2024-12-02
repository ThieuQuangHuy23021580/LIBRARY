package controller.libraryapp;

import model.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {

    private static final String URL = "jdbc:mysql://127.0.0.1:3307/library";
    private static final String USER = "root";  // Your MySQL username
    private static final String PASSWORD = "123";  // Your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Insert book into the database
    public static void insertBook(Book book) {
        if (isBookExists(book.getIsbn())) {  // Check if book exists by ISBN
            System.out.println("Book already exists: " + book.getTitle() + " by " + book.getAuthor());
            return; // Skip inserting duplicates
        }

        String query = "INSERT INTO books (title, author, publisher, description, imageUrl, quantity, category, isbn) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setString(4, book.getDescription());
            stmt.setString(5, book.getImageUrl());
            stmt.setInt(6, book.getQuantity());
            stmt.setString(7, book.getCategory()); // Insert the category
            stmt.setString(8, book.getIsbn()); // Insert ISBN

            stmt.executeUpdate();
            System.out.println("Inserted book: " + book.getTitle() + " by " + book.getAuthor());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting book: " + book.getTitle());
        }
    }

    // Check if a book already exists in the database based on ISBN
    public static boolean isBookExists(String isbn) {
        String query = "SELECT COUNT(*) FROM books WHERE isbn = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, isbn);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if count > 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error checking existence of book with ISBN: " + isbn);
        }

        return false; // Default to false if error occurs
    }

    // Get the current number of books in a specific category
    public static int getBookCountByCategory(String category) {
        String query = "SELECT COUNT(*) FROM books WHERE category = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the count of books
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting book count for category: " + category);
        }

        return 0; // Return 0 if error occurs
    }
    public static List<Book> getBooksByTitle(String searchTerm) {
        // Ensure the search term is not null or empty
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            System.out.println("Search term cannot be null or empty.");
            return new ArrayList<>();
        }

        // SQL query for partial matching
        String query = "SELECT * FROM books WHERE title LIKE ?";
        String likeSearchTerm = "%" + searchTerm.trim() + "%";
        List<Book> books = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the search term with wildcards
            stmt.setString(1, likeSearchTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("publisher"),
                            rs.getString("description"),
                            rs.getString("imageUrl"),
                            rs.getInt("quantity"),
                            rs.getString("category"),
                            rs.getString("isbn")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while searching for books with term: " + searchTerm);
        }

        return books;
    }



}


