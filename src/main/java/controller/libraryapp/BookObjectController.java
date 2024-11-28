package controller.libraryapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.Book;

import java.io.IOException;

public class BookObjectController {
    @FXML
    private Label bookName;

    @FXML
    private Label author;

    @FXML
    private ImageView imageView;

    @FXML
    private Button moreInfobutton;

    @FXML
    private StackPane mainStackPane;

    public void setMainStackPane(StackPane stackPane) {
        this.mainStackPane = stackPane;
    }


    // Method to set the details for a book
    public void setBookDetails(Book book) {
        bookName.setText(book.getTitle());
        author.setText(book.getAuthor());

        // Set the image if available, otherwise use a placeholder
        String imageUrl = book.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageView.setImage(new Image(imageUrl));
        } else {
            imageView.setImage(new Image(book.getImageUrl()));
        }

        // Add event handler for "More Info" button if needed
        moreInfobutton.setOnAction(event -> displayBookInfo(book));
    }

    private void displayBookInfo(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/BookObjectInfor.fxml"));
            AnchorPane bookInfoPane = loader.load();

            // Get the controller of the detailed view
            BookObjectInfoController infoController = loader.getController();
            infoController.displayBookDetails(book);

            // Add the detailed view to the main StackPane
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
