package controller.libraryapp;

import Util.BookDAO;
import Util.LoanDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

import model.Book;
import model.Loan;
import model.User;


public class BookObjectInfoController {

    @FXML
    private Label titleBook;
    @FXML
    private Label authorConfigTextField;
    @FXML
    private Label publishConfigTextField;
    @FXML
    private Label quantityConfigTextField;
    @FXML
    private Label descriptionConfigTextField;
    @FXML
    private ImageView bookImage;
    @FXML
    private TextField borrowQuantityTextField;
    @FXML
    private Button returnButton;
    @FXML
    Label userBorrowQuantity;

    int borrowQuantity = 0;
    int returnQuantity = 0;

    public void initialize() {
        borrowQuantityTextField.setText(String.valueOf(borrowQuantity));
    }

    private User user;
    private Book book;
    private LoanController controller;

    public void setUp(Book book, User user) {
        this.book = book;
        this.user = user;
        displayBookDetails();
        if (LoanDAO.getBorrowQuantity(user.getId(), book.getIsbn()) == 0) {
            returnButton.setDisable(true);
        } else
            userBorrowQuantity.setText("Borrowed: " + String.valueOf(LoanDAO.getBorrowQuantity(user.getId(), book.getIsbn())));
    }

    public void setLoanController(LoanController controller) {
        this.controller = controller;
    }

    public void displayBookDetails() {
        if (book != null) {
            titleBook.setText(book.getTitle());
            authorConfigTextField.setText("Author: " + book.getAuthor());
            publishConfigTextField.setText("Published: " + book.getPublisher());
            quantityConfigTextField.setText("Quantity: " + book.getQuantity());
            descriptionConfigTextField.setText("Description: " + book.getDescription());

            String imageUrl = book.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                bookImage.setImage(new Image(imageUrl));
            } else {
                bookImage.setImage(null);
            }
        } else {

            titleBook.setText("No book selected");
            authorConfigTextField.setText("");
            publishConfigTextField.setText("");
            quantityConfigTextField.setText("");
            descriptionConfigTextField.setText("");
            bookImage.setImage(null);
        }
    }

    @FXML
    private AnchorPane bookObjectInfo;

    @FXML
    private void handleCloseButtonAction() {
        ((StackPane) bookObjectInfo.getParent()).getChildren().remove(bookObjectInfo);
        if (controller != null && LoanDAO.getBorrowQuantity(user.getId(), book.getIsbn()) == 0) {
            controller.deleteStackPane(book.getIsbn());
        }
    }

    public void borrowButtonPress() {
        if (LoanDAO.getBorrowQuantity(user.getId(), book.getIsbn()) != 0) {
            return;
        }
        borrowQuantity = Integer.parseInt(borrowQuantityTextField.getText());
        if (borrowQuantity <= book.getQuantity() && borrowQuantity > 0) {
            Loan loan = new Loan(user, book, LocalDate.now(), borrowQuantity);
            LoanDAO.addLoan(loan);

            book.setQuantity(book.getQuantity() - borrowQuantity);

            userBorrowQuantity.setText("Borrowed: " + String.valueOf(borrowQuantity));
            BookDAO.updateBookQuantity(borrowQuantity, book.getIsbn(), 1);
            returnButton.setDisable(false);
        }
    }

    public void returnButtonPress() {
        returnQuantity = Integer.parseInt(borrowQuantityTextField.getText());
        int borrowQuantity1 = LoanDAO.getBorrowQuantity(user.getId(), book.getIsbn());
        if (returnQuantity < borrowQuantity1 && returnQuantity > 0) {
            userBorrowQuantity.setText("Borrowed: " + String.valueOf(borrowQuantity1 - returnQuantity));
            LoanDAO.updateLoan(user.getId(), book.getIsbn(), returnQuantity);
        } else if (returnQuantity == borrowQuantity1) {
            userBorrowQuantity.setText("Borrowed: 0");
            LoanDAO.deleteLoan(user.getId(), book.getIsbn());
        }
    }

    public void minusQuantity() {
        if (borrowQuantity > 0) {
            borrowQuantity -= 1;
        }
        borrowQuantityTextField.setText(String.valueOf(borrowQuantity));
    }

    public void plusQuantity() {
        borrowQuantity += 1;
        borrowQuantityTextField.setText(String.valueOf(borrowQuantity));
    }


}
