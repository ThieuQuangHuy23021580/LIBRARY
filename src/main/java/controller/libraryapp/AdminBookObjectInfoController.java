package controller.libraryapp;

import Util.BookDAO;
import Util.DatabaseConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AdminBookObjectInfoController {

    @FXML
    private Label titleBook;
    @FXML
    private TextField authorConfigTextField;
    @FXML
    private Label authorConfigLabel;
    @FXML
    private TextField publishConfigTextField;
    @FXML
    private Label publishConfigLabel;
    @FXML
    private TextField quantityConfigTextField;
    @FXML
    private Label quantityConfigLabel;
    @FXML
    private TextField descriptionConfigTextField;
    @FXML
    private Label descriptionConfigLabel;
    @FXML
    private ImageView bookImage;
    @FXML
    private AnchorPane adminBookObjectInfo;
    @FXML
    private StackPane mainStackPane;
    @FXML
    private TextField addQuantityTextField;



    public void setMainStackpane(StackPane mainStackpane) {
        this.mainStackPane = mainStackpane;
    }

    private String isbn;
    private Book book;
    private Connection connection;

    // Constructor with Book and String isbn
    public AdminBookObjectInfoController(Book book, String isbn, Connection connection) {
        this.isbn = isbn;
        this.book = book;
        this.connection = connection;
    }

    public void displayBookDetails(Book book) {
        if (book != null) {
            titleBook.setText(book.getTitle());
            authorConfigLabel.setText("Author: " + book.getAuthor());
            publishConfigLabel.setText("Publisher: " + book.getPublisher());
            quantityConfigLabel.setText("Quantity: " + book.getQuantity());
            descriptionConfigLabel.setText(book.getDescription());

            // Set book image
            String imageUrl = book.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                bookImage.setImage(new Image(imageUrl));
            } else {
                bookImage.setImage(null); // Clear image if URL is not available
            }
        } else {
            titleBook.setText("No book selected");
            clearTextFields();
            bookImage.setImage(null); // Clear image
        }
    }

    private void clearTextFields() {
        authorConfigTextField.setText("");
        publishConfigTextField.setText("");
        quantityConfigTextField.setText("");
        descriptionConfigTextField.setText("");
    }

    @FXML
    private void handleCloseButtonAction() {
        ((StackPane) adminBookObjectInfo.getParent()).getChildren().remove(adminBookObjectInfo);
    }

    @FXML
    private void handleAuthorConfig() {
        toggleConfig(authorConfigLabel, authorConfigTextField);
    }

    @FXML
    private void handlePublishConfig() {
        toggleConfig(publishConfigLabel, publishConfigTextField);
    }

    @FXML
    private void handleQuantityConfig() {
        toggleConfig(quantityConfigLabel, quantityConfigTextField);
    }

    @FXML
    private void handleDescriptionConfig() {
        toggleConfig(descriptionConfigLabel, descriptionConfigTextField);
    }

    private void toggleConfig(Label label, TextField textField) {
        boolean isLabelVisible = label.isVisible();

        // Toggle visibility of the label and text field
        label.setVisible(!isLabelVisible);
        textField.setVisible(isLabelVisible);

        if (!isLabelVisible) {
            // If the label is not visible, update the label with the value from the TextField
            label.setText(label.getText().split(": ", 2)[0] + ": " + textField.getText().trim());
        } else {
            // Pre-fill the TextField with the current value of the Label
            textField.setText(label.getText().split(": ", 2)[1].trim());
        }
    }


    @FXML
    public void configButtonPress(ActionEvent actionEvent) {
        // Get the current text from the labels
        String newAuthor = authorConfigLabel.getText().split(": ", 2)[1].trim();
        String newPublisher = publishConfigLabel.getText().split(": ", 2)[1].trim();
        String newQuantity = quantityConfigLabel.getText().split(": ", 2)[1].trim();
        String newDescription = descriptionConfigLabel.getText().trim();

        // Validate that no field is empty
        if (newAuthor.isEmpty() || newPublisher.isEmpty() || newQuantity.isEmpty() || newDescription.isEmpty()) {
            System.err.println("Please fill in all fields.");
            return; // Or show an error message to the user
        }

        // Update the database for all fields using the current text from the labels
        updateBookInDatabase("author", newAuthor);
        updateBookInDatabase("publisher", newPublisher);
        updateBookInDatabase("quantity", newQuantity);
        updateBookInDatabase("description", newDescription);

        // After updating the database, update the labels with the new values
        authorConfigLabel.setText("Author: " + newAuthor);
        publishConfigLabel.setText("Publisher: " + newPublisher);
        quantityConfigLabel.setText("Quantity: " + newQuantity);
        descriptionConfigLabel.setText(newDescription);

        // Hide text fields and show labels again after updating
        authorConfigTextField.setVisible(false);
        publishConfigTextField.setVisible(false);
        quantityConfigTextField.setVisible(false);
        descriptionConfigTextField.setVisible(false);

        authorConfigLabel.setVisible(true);
        publishConfigLabel.setVisible(true);
        quantityConfigLabel.setVisible(true);
        descriptionConfigLabel.setVisible(true);
    }


    // Update the database with the new value
    private void updateBookInDatabase(String columnName, String newValue) {
        String sql = "UPDATE books SET " + columnName + " = ? WHERE isbn = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newValue);
            statement.setString(2, isbn);
            statement.executeUpdate();
            System.out.println("Database updated successfully: " + columnName + " = " + newValue);
        } catch (SQLException e) {
            System.err.println("Error updating the database: " + e.getMessage());
        }
    }

    // Placeholder for delete button action
    @FXML
    public void deleteButtonPress(ActionEvent actionEvent) {
        if (book == null || book.getIsbn() == null || book.getIsbn().isEmpty()) {
            System.out.println("No book selected to delete.");
            return;
        }

        // Get the quantity to be reduced from the TextField or other source
        String quantityText = addQuantityTextField.getText(); // Assuming you have a TextField for quantity input
        int quantityToDelete;

        try {
            quantityToDelete = Integer.parseInt(quantityText);
            if (quantityToDelete <= 0) {
                System.out.println("Invalid quantity to delete. It must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity format.");
            return;
        }

        String checkQuery = "SELECT quantity FROM books WHERE isbn = ?";
        String updateQuery = "UPDATE books SET quantity = quantity - ? WHERE isbn = ?";
        String deleteQuery = "DELETE FROM books WHERE isbn = ?";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            // Check if the book exists
            checkStmt.setString(1, book.getIsbn());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");

                if (currentQuantity > quantityToDelete) {
                    // If the quantity is greater than the deletion amount, update the quantity
                    updateStmt.setInt(1, quantityToDelete);
                    updateStmt.setString(2, book.getIsbn());
                    updateStmt.executeUpdate();
                    System.out.println("Reduced quantity of book: " + book.getTitle() + " by " + quantityToDelete);
                } else {
                    // If the quantity is less than or equal to the deletion amount, delete the book
                    deleteStmt.setString(1, book.getIsbn());
                    deleteStmt.executeUpdate();
                    System.out.println("Deleted book: " + book.getTitle());
                }
            } else {
                System.out.println("No book found with ISBN: " + book.getIsbn());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in deleteButtonPress for ISBN: " + book.getIsbn());
        }
    }


    // Placeholder for add button action
    public void addBookButtonPress(ActionEvent actionEvent) {
        try {
            // Parse the quantity from the TextField
            int quantity = Integer.parseInt(addQuantityTextField.getText());

            if (quantity <= 0) {
                // Display an error if the quantity is invalid
                System.out.println("Quantity must be greater than 0.");
                return;
            }

            // Use the database method to insert or update the book with the given quantity
            BookDAO.insertBook(book, quantity);

            // Feedback to the user
            System.out.println("Added " + quantity + " copies of the book: " + book.getTitle());
        } catch (NumberFormatException e) {
            // Handle invalid number format
            System.out.println("Invalid quantity entered. Please enter a valid number.");
        }
    }

    public void minusQuantityButtonPressed(ActionEvent actionEvent) {
        int addQuantity = Integer.parseInt(addQuantityTextField.getText());
        if (addQuantity >= 1) {
            addQuantityTextField.setText(String.valueOf(addQuantity - 1));
        }
    }

    public void plusQuantityButtonPressed(ActionEvent actionEvent) {
        int addQuantity = Integer.parseInt(addQuantityTextField.getText());
        addQuantityTextField.setText(String.valueOf(addQuantity + 1));
    }
}
