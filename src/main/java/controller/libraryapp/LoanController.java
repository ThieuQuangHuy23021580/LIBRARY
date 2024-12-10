package controller.libraryapp;

import Util.LoanDAO;
import Util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import model.Loan;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LoanController {

    private User user;
    @FXML
    FlowPane flowHome;
    @FXML
    StackPane myLibraryStackPane;
    @FXML
    AnchorPane bookObjectContain;
    @FXML
    Label beginningTimeLabel;
    @FXML
    Label deadlineTimeLabel;

    List<Pair<AnchorPane, String>> anchorPaneBooks = new ArrayList<>();


    List<Loan> loans = new ArrayList<>();


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
                Label borrowDate = new Label(loan.getLoanDate().toString());
                borrowDate.setLayoutX(480.0);
                borrowDate.setLayoutY(140.0);
                borrowDate.setStyle("-fx-font-size: 26;");
                borrowDate.setTextFill(javafx.scene.paint.Color.WHITE);
                Label returnDate = new Label(loan.getReturnDate().toString());
                returnDate.setLayoutX(880.0);
                returnDate.setLayoutY(140.0);
                returnDate.setStyle("-fx-font-size: 26;");
                returnDate.setTextFill(javafx.scene.paint.Color.WHITE);
                myLibraryBookInfo.getChildren().add(borrowDate);
                myLibraryBookInfo.getChildren().add(returnDate);
                BookObjectController controller = loader.getController();
                controller.setBook(loan.getBook());
                controller.setUser(user);
                controller.setLoanController(this);
                controller.setMainStackPane(myLibraryStackPane);

                AnchorPane bookObjectContainer = (AnchorPane) myLibraryBookInfo.lookup("#bookObjectContain");
                bookObjectContainer.getChildren().add(st);
                flowHome.getChildren().add(myLibraryBookInfo);
                anchorPaneBooks.add(new Pair<>(myLibraryBookInfo, loan.getBook().getIsbn()));
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

    public void handleCloseButtonAction(ActionEvent actionEvent) {
        SceneManager.showMainView(user);
        flowHome.getChildren().clear();
    }
}
