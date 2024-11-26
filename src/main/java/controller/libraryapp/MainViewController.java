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
    private TextField authorConfigTextField;

    @FXML
    private AnchorPane bookObjectInfo;

    @FXML
    private Button businessAndEconomicButton;

    @FXML
    private AnchorPane businessAndEconomicCateList;

    @FXML
    private AnchorPane categoriesAnchorPane;

    @FXML
    private Button categoriesButton_active;

    @FXML
    private Button categoriesButton_inactive;

    @FXML
    private Button comicButton;

    @FXML
    private AnchorPane comicCateList;

    @FXML
    private Button configAuthor;

    @FXML
    private Button configAuthor1;

    @FXML
    private Button configAuthor11;

    @FXML
    private Button configAuthor111;

    @FXML
    private TextField descriptionConfigTextField;

    @FXML
    private Label discover;

    @FXML
    private AnchorPane discoverCatetegories;

    @FXML
    private Button educationButton;

    @FXML
    private AnchorPane educationCateList;

    @FXML
    private Button fictionButton;

    @FXML
    private AnchorPane fictionCateList;

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
    private Button healthAndFitnessButton;

    @FXML
    private AnchorPane healthAndFitnessCateList;

    @FXML
    private AnchorPane homeAnchorPane;

    @FXML
    private Button homeButton_active;

    @FXML
    private Button homeButton_inactive;

    @FXML
    private AnchorPane home_anchorpane;

    @FXML
    private AnchorPane otherCateList;

    @FXML
    private Button otherCategoriesButton;

    @FXML
    private TextField publishConfigTextField;

    @FXML
    private TextField quantityConfigTextField;

    @FXML
    private AnchorPane recommendAnchorPane;

    @FXML
    private FlowPane recommendFlowPane;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button the_most_popular_books;

    @FXML
    private Label tiltleBook;

    @FXML
    private MenuButton userMenuButton;

    @FXML
    private Label userName;

    @FXML
    public void initialize(){
    showBook();
    fictionButton.setOnMouseEntered(event-> getFictionCateList());
    fictionButton.setOnMouseExited(event-> getDiscoveryCategories());
    educationButton.setOnMouseEntered(event-> getEducationCateList());
    educationButton.setOnMouseExited(event-> getDiscoveryCategories());
    comicButton.setOnMouseEntered(event-> getComicCateList());
    comicButton.setOnMouseExited(event-> getDiscoveryCategories());
    healthAndFitnessButton.setOnMouseEntered(event-> getHealthAndFitnessCateList());
    healthAndFitnessButton.setOnMouseExited(event-> getDiscoveryCategories());
    businessAndEconomicButton.setOnMouseEntered(event-> getBusinessAndEconomicCateList());
    businessAndEconomicButton.setOnMouseExited(event-> getDiscoveryCategories());
    otherCategoriesButton.setOnMouseEntered(event-> getOtherCateList());
    otherCategoriesButton.setOnMouseExited(event-> getDiscoveryCategories());

    userName.setCursor(Cursor.HAND);
    userMenuButton.setCursor(Cursor.HAND);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    void getDiscoveryCategories(){
        discoverCatetegories.setVisible(true);
        fictionCateList.setVisible(false);
        educationCateList.setVisible(false);
        otherCateList.setVisible(false);
        comicCateList.setVisible(false);
        healthAndFitnessCateList.setVisible(false);
        businessAndEconomicCateList.setVisible(false);
    }
    void getFictionCateList(){
        fictionCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }
    void getEducationCateList(){
        educationCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }
    void getComicCateList(){
        comicCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }
    void getHealthAndFitnessCateList(){
        healthAndFitnessCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }
    void getBusinessAndEconomicCateList(){
        businessAndEconomicCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }
    void getOtherCateList(){
        otherCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
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

    public void configButtonPress(ActionEvent actionEvent) {
    }

    public void deleteButtonPress(ActionEvent actionEvent) {
    }

    public void borrowButtonPress(ActionEvent actionEvent) {
    }

    public void returnButtonPress(ActionEvent actionEvent) {
    }
}
