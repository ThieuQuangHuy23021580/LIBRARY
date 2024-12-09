package controller.libraryapp;

import Util.LoanDAO;
import Util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
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
    FlowPane flowHome;
    @FXML
    StackPane myLibraryStackPane;

    List<Pair<AnchorPane, String>> anchorPaneBooks = new ArrayList<>();


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
                AnchorPane myLibraryBookInfo = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/fxml_designs/MyLibraryBookInfo.fxml")));
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SceneManager.class.getResource("/controller/fxml_designs/BookObject.fxml")));
                StackPane st = loader.load();

                BookObjectController controller = loader.getController();
                controller.setBook(loan.getBook());
                controller.setUser(user);
                controller.setLoanController(this);
                controller.setMainStackPane(myLibraryStackPane);

                myLibraryBookInfo.getChildren().add(st);
                flowHome.getChildren().add(myLibraryBookInfo);
                anchorPaneBooks.add(new Pair<>(myLibraryBookInfo,loan.getBook().getIsbn()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteStackPane(String isbn) {
        Pair<AnchorPane, String> targetPair = null;

        for (Pair<AnchorPane, String> pair : anchorPaneBooks) {
            if (pair.getValue().equals(isbn)) {
                targetPair = pair;
                break;
            }
        }

        if (targetPair != null) {
            flowHome.getChildren().remove(targetPair.getKey());
            anchorPaneBooks.remove(targetPair);
        }
    }

}
