package controller.libraryapp;

import Util.LoanDAO;
import Util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import model.Book;
import model.Loan;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LoanController {

    private User user;
    @FXML
    private FlowPane flowHome;
    @FXML
    private AnchorPane bookObjectContain;
    @FXML
    private AnchorPane myLibraryBookInfo;
    @FXML
     StackPane myLibraryStackPane;


    List<Loan> loans = new ArrayList<>();

    public void initialize() {
    }

    public void setUser(User user) {
        this.user = user;
        getLoanList();
    }

    private void getLoanList() {
        try {
            loans = LoanDAO.getLoanByUserId(user.getId());
            for (Loan loan : loans) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/MyLibraryBookInfo.fxml"));
                AnchorPane root = loader.load();
                FXMLLoader book = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/controller/fxml_designs/BookObject.fxml")));
                AnchorPane bookObjectContain = book.load();
                BookObjectController controller = book.getController();
                controller.setBook(loan.getBook());
                controller.setUser(user);
                controller.setMainStackPane(myLibraryStackPane);
                root.getChildren().add(bookObjectContain);
                flowHome.getChildren().add(root);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
