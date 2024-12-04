package controller.libraryapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.Book;
import model.User;

import java.io.IOException;

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

    public void setMainStackPane(StackPane stackPane) {
        this.mainStackPane = stackPane;
    }

    public void setBook(Book book) {
        this.book = book;
        setBookDetails();
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void setBookDetails() {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/BookObjectInfor.fxml"));
            AnchorPane bookInfoPane = loader.load();

            BookObjectInfoController infoController = loader.getController();
            infoController.setBook(book);
            infoController.setUser(user);
            mainStackPane.getChildren().add(bookInfoPane);

            // Configure the close button to remove the detailed view
            Button closeButton = (Button) bookInfoPane.lookup("#closeButton");
            closeButton.setOnAction(e -> mainStackPane.getChildren().remove(bookInfoPane));

        } catch (IOException e) {
            System.err.println("Error loading the detailed book info view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
