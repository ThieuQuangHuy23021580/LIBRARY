package controller.libraryapp;

import Util.BookDAO;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.Book;

import java.io.IOException;


public class AddBookInforController {
    @FXML
    private Label titleBook;
    @FXML
    private Label authorConfigTextField;
    @FXML
    private Label publishConfigTextField;
    @FXML
    private Label descriptionConfigTextField;
    @FXML
    private ImageView bookImage;
    @FXML
    private Book book;
    @FXML
    private TextField addQuantity;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane AddBookInfor;

    public void setBook(Book book) {
        this.book = book;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void setBookDetails(Book book) {
        titleBook.setText(book.getTitle());
        authorConfigTextField.setText("Author: " + book.getAuthor());
        publishConfigTextField.setText("Publish: " + book.getPublisher());
        descriptionConfigTextField.setText(book.getDescription());

        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            bookImage.setImage(new Image(book.getImageUrl()));
        } else {
            bookImage.setImage(null); // Or set a placeholder image
        }
    }

    @FXML
    public void handleAddButtonAction(ActionEvent event) {
        try {
            // Parse the quantity from the TextField
            int quantity = Integer.parseInt(addQuantity.getText());

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

    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
       stackPane.getChildren().remove(AddBookInfor);
    }

}


