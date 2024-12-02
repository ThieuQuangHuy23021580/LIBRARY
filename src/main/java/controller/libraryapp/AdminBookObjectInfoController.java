package controller.libraryapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    private Label successMessageLabel;

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
        // Implement delete button logic here
    }

    // Placeholder for add button action
    @FXML
    public void addBookButtonPress(ActionEvent actionEvent) {
        // Implement add book button logic here
    }
}
