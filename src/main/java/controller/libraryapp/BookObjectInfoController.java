package controller.libraryapp;
import Util.LoanDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.util.List;

import model.Book;
import model.Loan;
import model.User;

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
    @FXML
    private TextField borrowQuantityTextField;

// TextField for book quantity in stock
    int borrowQuantity = 0;
    public void initialize() {
        borrowQuantityTextField.setText(String.valueOf(borrowQuantity));
    }
    private User user;
    private Book book;
    public void setBook(Book book) {
        this.book = book;
        displayBookDetails();
    }
    public void setUser(User user) {
        this.user = user;
    }
    // Method to load book information from a database or any other source
    public void displayBookDetails() {
        if (book != null) {
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
        ((StackPane) bookObjectInfo.getParent()).getChildren().remove(bookObjectInfo);
    }

    public void configButtonPress(ActionEvent actionEvent) {
    }

    public void deleteButtonPress(ActionEvent actionEvent) {
    }

    public void borrowButtonPress() {
        borrowQuantity = Integer.parseInt(borrowQuantityTextField.getText());
        if(borrowQuantity <= book.getQuantity()) {
           Loan loan = new Loan(user,book, LocalDate.now(),borrowQuantity);
           book.setQuantity(book.getQuantity() - borrowQuantity);
           LoanDAO.addLoan(loan);
        }
    }
    public void minusQuantity() {
        if(borrowQuantity < 0)
          return;
        borrowQuantity-= 1;
        borrowQuantityTextField.setText(String.valueOf(borrowQuantity));
    }
    public void plusQuantity() {
        borrowQuantity += 1;
        borrowQuantityTextField.setText(String.valueOf(borrowQuantity));
    }


    public void returnButtonPress(ActionEvent actionEvent) {
    }
}
