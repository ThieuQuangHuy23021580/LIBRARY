package controller.libraryapp;

import Util.SwitchScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.User;


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
    private AnchorPane viewPane;

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
    private MenuItem listLoanButton;
    @FXML
    private MenuItem manageUserButton;
    @FXML
    private Label userName;
    @FXML
    private StackPane parent;

    private User user;

    public void setUser(User user) {
        this.user = user;
        if(user.getRole().equals(User.MANAGER)) {
           listLoanButton.setVisible(false);
           manageUserButton.setVisible(true);
        }
        userName.setText(user.getUserName());
    }

    public void setView(Parent root) {
        parent.getChildren().add(root);
    }


    @FXML
    public void initialize() {
        showBook();
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
        SwitchScene.showLoginView();
    }

    public void userInfo() throws IOException {
        SwitchScene.showUserDashboard(user);
    }
}
