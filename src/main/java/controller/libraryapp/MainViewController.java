package controller.libraryapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainViewController {

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
    public void initialize(){
    showBook();
    }
    // Tạo đối tượng là sách.
    void showBook(){
        try {
            ArrayList<StackPane> bookObjects = new ArrayList<>();
            for(int i=0; i < 5 ; i++){
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/controller/fxml_designs/BookObject.fxml")));
                StackPane bookObject = loader.load();
                bookObjects.add(bookObject);
            }
            for (StackPane bookObject : bookObjects) {
                recommendFlowPane.getChildren().add(bookObject);
            }

        }
        catch (IOException e){
            System.out.println("Book Object: " + e.getMessage());
        }
    }



}
