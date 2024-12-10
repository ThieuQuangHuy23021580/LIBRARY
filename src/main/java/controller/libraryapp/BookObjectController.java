package controller.libraryapp;

import Util.DatabaseConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.Book;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class BookObjectController {
    @FXML
    private Label bookName;

    @FXML
    private Label author;

    @FXML
    private ImageView imageView;

    @FXML
    private StackPane mainStackPane;
    private Book book;
    private User user;
    private LoanController controller;

    public void setMainStackPane(StackPane stackPane) {
        this.mainStackPane = stackPane;
    }

    public void setBook(Book book) {
        this.book = book;
        setBookDetails(book);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLoanController(LoanController loanController) {
        this.controller = loanController;
    }

    public void setBookDetails(Book book) {
        bookName.setText(book.getTitle());
        author.setText(book.getAuthor());

        String imageUrl = book.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageView.setImage(new Image(imageUrl));
        } else {
            imageView.setImage(new Image(book.getImageUrl()));
        }

    }

    public void displayBookInfo() {

        String fxmlPath = user.getRole().equals("Manager")
                ? "/controller/fxml_designs/AdminBookObjectInfo.fxml"
                : "/controller/fxml_designs/BookObjectInfor.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (user.getRole().equals("Manager")) {
                loader.setControllerFactory(param -> {
                    try {
                        return new AdminBookObjectInfoController(book, book.getIsbn(), DatabaseConnect.getConnection());
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
            }

            AnchorPane bookInfoPane = loader.load();
            if (user.getRole().equals("Manager")) {
                AdminBookObjectInfoController adminController = loader.getController();
                adminController.setMainStackpane(mainStackPane);
                adminController.displayBookDetails(book);
            } else {
                BookObjectInfoController infoController = loader.getController();
                infoController.setUp(book, user);
                infoController.setLoanController(controller);
            }

            mainStackPane.getChildren().add(bookInfoPane);

        } catch (IOException e) {
            System.err.println("Error loading detailed book info view: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
