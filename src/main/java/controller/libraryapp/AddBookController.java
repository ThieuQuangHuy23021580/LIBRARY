package controller.libraryapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import model.Book;

import java.io.IOException;

public class AddBookController {
    @FXML
    private TextField isbnAddBookTextField;
    @FXML
    private StackPane stackPane;

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void addBookButtonPress(ActionEvent actionEvent) {
        String isbn = isbnAddBookTextField.getText();

        if (!isbn.isEmpty()) {
            // Call GoogleBooksAPI to search for the book by ISBN
            GoogleBooksAPI googleBooksAPI = new GoogleBooksAPI();
            Book book = googleBooksAPI.searchBookByISBN(isbn);

            if (book != null) {
                try {
                    // Load the bookInfoPane.fxml using FXMLLoader
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/AddBookInfor.fxml"));
                    Parent bookInfoPane = loader.load();

                    // Get the BookInfoController and set the book details
                    AddBookInforController controller = loader.getController();
                    controller.setStackPane(stackPane);
                    controller.setBook(book);
                    controller.setBookDetails(book);

                    // Add the loaded pane to the mainStackPane
                    stackPane.getChildren().add(bookInfoPane);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception (e.g., display an error message)
                }
            } else {
                // Handle case when book is not found
                System.out.println("Book not found");
            }
        }
    }


    public void cancelButtonPress(ActionEvent actionEvent) {
        stackPane.getChildren().clear();
    }
}
