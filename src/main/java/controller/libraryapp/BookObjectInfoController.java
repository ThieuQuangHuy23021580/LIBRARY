package controller.libraryapp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.util.List;

import model.Book;

import java.awt.*;

public class BookObjectInfoController {

    @FXML
    private Label titleBook;
    @FXML
    private TextField authorConfigTextField;
    @FXML
    private TextField publishConfigTextField;
    @FXML
    private TextField quantityConfigTextField;
    @FXML
    private TextField descriptionConfigTextField;
    @FXML
    private ImageView bookImage;
// TextField for book quantity in stock

    // Method to load book information from a database or any other source
    public void displayBookDetails(Book book) {
        if (book != null) {
            // Set the details in the UI
            titleBook.setText(book.getTitle());
            authorConfigTextField.setText("Author: " + book.getAuthor());
            publishConfigTextField.setText("Published: " + book.getPublisher());
            quantityConfigTextField.setText("Quantity: " + book.getQuantity());
            descriptionConfigTextField.setText("Description: " + book.getDescription());

            // Set book image
            String imageUrl = book.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                bookImage.setImage(new Image(imageUrl));
            } else {
                // Set a placeholder or clear the image if URL is not available
                bookImage.setImage(null);
            }
        } else {
            // Handle case where no book is selected or found
            titleBook.setText("No book selected");
            authorConfigTextField.setText("");
            publishConfigTextField.setText("");
            quantityConfigTextField.setText("");
            descriptionConfigTextField.setText("");
            bookImage.setImage(null); // Clear the image
        }
    }

    @FXML
    private AnchorPane bookObjectInfo; // Root pane of the FXML file

    @FXML
    private void handleCloseButtonAction() {
        // Remove this AnchorPane from its parent
        ((StackPane) bookObjectInfo.getParent()).getChildren().remove(bookObjectInfo);
    }

}
