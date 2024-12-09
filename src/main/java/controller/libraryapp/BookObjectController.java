package controller.libraryapp;

import Util.LoanDAO;
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
import model.Loan;
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
    private Button moreInfobutton;

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
        setBookDetails(book,user);
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setLoanController(LoanController loanController) {
        this.controller = loanController;
    }

    public void setBookDetails(Book book, User user) {
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
        moreInfobutton.setOnAction(event -> displayBookInfo(book, user));
    }

    private void displayBookInfo(Book book, User user) {
        String fxmlPath = user.getRole().equals("Manager")
                ? "/controller/fxml_designs/AdminBookObjectInfo.fxml"
                : "/controller/fxml_designs/BookObjectInfor.fxml";
        DatabaseUtil databaseUtil = new DatabaseUtil();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (user.getRole().equals("Manager")) {
                loader.setControllerFactory(param -> {
                    try {
                        return new AdminBookObjectInfoController(book, book.getIsbn(), databaseUtil.getConnection());
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
                infoController.setUp(book,user);
                infoController.setLoanController(controller);
            }

            // Add the detailed view to the main StackPane
            mainStackPane.getChildren().add(bookInfoPane);

            // Configure the close button to remove the detailed view
            Button closeButton = (Button) bookInfoPane.lookup("#closeButton");
            closeButton.setOnAction(e -> mainStackPane.getChildren().remove(bookInfoPane));
        } catch (IOException e) {
            System.err.println("Error loading detailed book info view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
