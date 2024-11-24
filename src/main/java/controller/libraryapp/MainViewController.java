package controller.libraryapp;

import Model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainViewController {
    private Account account;

    @FXML
    private FlowPane recommendFlowPane;

    @FXML
    private AnchorPane bookPane;

    @FXML
    private Button categoriesButton_active;

    @FXML
    private Button categoriesButton_inactive;

    @FXML
    private Label discover;

    @FXML
    private FlowPane flowHome;

    @FXML
    private Rectangle gradient_down_rectangle;

    @FXML
    private Rectangle gradient_left_rectangle;

    @FXML
    private Rectangle gradient_right_rectangle;

    @FXML
    private Rectangle gradient_up_rectangle;

    @FXML
    private Button homeButton_active;

    @FXML
    private Button homeButton_inactive;

    @FXML
    private AnchorPane home_anchorpane;

    @FXML
    private Label librahub;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button the_most_popular_books;

    @FXML
    private MenuButton userMenuButton;

    @FXML
    private Label userName;

    @FXML
    public void initialize() {
        showBook();
        userName.setCursor(Cursor.HAND);
        userMenuButton.setCursor(Cursor.HAND);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // Tạo đối tượng là sách.
    void showBook() {
        try {
            ArrayList<StackPane> bookObjects = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/controller/fxml_designs/BookObject.fxml")));
                StackPane bookObject = loader.load();
                bookObjects.add(bookObject);
            }
            for (StackPane bookObject : bookObjects) {
                recommendFlowPane.getChildren().add(bookObject);
            }

        } catch (IOException e) {
            System.out.println("Book Object: " + e.getMessage());
        }
    }

    public void logOut() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/controller/fxml_designs/LoginView.fxml")));
        Parent root = loader.load();
        Stage stage = (Stage) userMenuButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setUserName(String name) {
        userName.setText(name);
    }

    public void userInfo() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/controller/fxml_designs/userProfile.fxml")));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("yourInfo");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        UserController controller = loader.getController();
        controller.setAccount(account);
    }

    public void searchBook() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/controller/fxml_designs/BookObject.fxml")));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("yourInfo");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


}
