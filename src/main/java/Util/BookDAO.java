package Util;

import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public static void insertBook(Book book, int quantity) {
        String checkQuery = "SELECT quantity FROM books WHERE isbn = ?";
        String insertQuery = "INSERT INTO books (title, author, publisher, description, imageUrl, quantity, category, isbn) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE books SET quantity = quantity + ? WHERE isbn = ?";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            // Check if the book exists
            checkStmt.setString(1, book.getIsbn());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Book exists, update the quantity
                updateStmt.setInt(1, quantity); // Increment the quantity
                updateStmt.setString(2, book.getIsbn());
                updateStmt.executeUpdate();
                System.out.println("Updated quantity for book: " + book.getTitle() + ". Quantity increased by: " + quantity);
            } else {
                // Book doesn't exist, insert it
                insertStmt.setString(1, book.getTitle());
                insertStmt.setString(2, book.getAuthor());
                insertStmt.setString(3, book.getPublisher());
                insertStmt.setString(4, book.getDescription());
                insertStmt.setString(5, book.getImageUrl());
                insertStmt.setInt(6, quantity); // Insert the initial quantity
                insertStmt.setString(7, book.getCategory());
                insertStmt.setString(8, book.getIsbn());
                insertStmt.executeUpdate();
                System.out.println("Inserted new book: " + book.getTitle());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in insertOrUpdateBook for ISBN: " + book.getIsbn());
        }
    }

    // Get the current number of books in a specific category
    public static int getBookCountByCategory(String category) {
        String query = "SELECT COUNT(*) FROM books WHERE category = ?";

        try (Connection conn = DatabaseConnect.getConnection();
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

        try (Connection conn = DatabaseConnect.getConnection();
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

    public static List<Book> getBooksByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            System.out.println("Category cannot be null or empty.");
            return new ArrayList<>();
        }

        // SQL query to fetch books by category with a limit of 15
        String query = "SELECT * FROM books WHERE category = ? LIMIT 15";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the category parameter
            stmt.setString(1, category);

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
            System.out.println("Error occurred while fetching books for category: " + category);
        }

        return books;
    }


    // Get the current number of books in a specific category


    public static Book getBookByISBN(String isbn) {
        String query = "SELECT * FROM books where isbn = ?";
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("description"),
                        rs.getString("imageUrl"),
                        rs.getInt("quantity"),
                        rs.getString("category"),
                        rs.getString("isbn")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Book> getRandomBook() {

        String query = "SELECT * FROM books order by rand() limit 15";
        List<Book> books = new ArrayList<>();

        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static void updateBookQuantity(int borrowQuantity, String isbn, int type) {
        String sql = "update books set quantity = quantity + ? where isbn = ?";
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            if (type == 1) {
                ps.setInt(1, -borrowQuantity);
            } else {
                ps.setInt(1, borrowQuantity);
            }
            ps.setString(2, isbn);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}


