package controller.libraryapp;

import Util.Alert;
import Util.BookDAO;
import Util.NotificationDAO;
import Util.SceneManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.Book;
import model.Notification;
import model.User;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainViewController {

    @FXML
    private FlowPane recommendFlowPane;
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
    private AnchorPane bookPane;

    @FXML
    private AnchorPane viewPane;

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
    @FXML
    private Label recommendLabel;

    private User user;

    public void setUser(User user) {
        this.user = user;
        if (user.getRole().equals(User.MANAGER)) {
            listLoanButton.setVisible(false);
            manageUserButton.setVisible(true);
        } else {
            listLoanButton.setVisible(true);
            manageUserButton.setVisible(false);
        }
        userName.setText(user.getUserName());

    }


    @FXML
    public void initialize() {
        fictionButton.setOnMouseEntered(event -> getFictionCateList());
        fictionButton.setOnMouseExited(event -> getDiscoveryCategories());
        educationButton.setOnMouseEntered(event -> getEducationCateList());
        educationButton.setOnMouseExited(event -> getDiscoveryCategories());
        comicButton.setOnMouseEntered(event -> getComicCateList());
        comicButton.setOnMouseExited(event -> getDiscoveryCategories());
        healthAndFitnessButton.setOnMouseEntered(event -> getHealthAndFitnessCateList());
        healthAndFitnessButton.setOnMouseExited(event -> getDiscoveryCategories());
        businessAndEconomicButton.setOnMouseEntered(event -> getBusinessAndEconomicCateList());
        businessAndEconomicButton.setOnMouseExited(event -> getDiscoveryCategories());
        otherCategoriesButton.setOnMouseEntered(event -> getOtherCateList());
        otherCategoriesButton.setOnMouseExited(event -> getDiscoveryCategories());
    }

    void getDiscoveryCategories() {
        discoverCatetegories.setVisible(true);
        fictionCateList.setVisible(false);
        educationCateList.setVisible(false);
        otherCateList.setVisible(false);
        comicCateList.setVisible(false);
        healthAndFitnessCateList.setVisible(false);
        businessAndEconomicCateList.setVisible(false);
    }

    void getFictionCateList() {
        fictionCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    void getEducationCateList() {
        educationCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    void getComicCateList() {
        comicCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    void getHealthAndFitnessCateList() {
        healthAndFitnessCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    void getBusinessAndEconomicCateList() {
        businessAndEconomicCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    void getOtherCateList() {
        otherCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    public void setListBook(List<Book> books) throws IOException {
        for (Book book : books) {
            StackPane bookObject = SceneManager.loadBookObject(book, user, mainStackPane);
            recommendFlowPane.getChildren().add(bookObject);
        }
    }

    void showBook() {
        new Thread(() -> {
            try {
                List<Book> books = BookDAO.getRandomBook();
                // Gọi phương thức UI trong luồng chính để cập nhật giao diện
                Platform.runLater(() -> {
                    try {
                        setListBook(books);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    @FXML
    private StackPane mainStackPane; // Reference to the main StackPane in your FXML

    @FXML
    private void handleSearchButtonAction() throws IOException {
        String title = searchTextField.getText().trim();
        recommendFlowPane.getChildren().clear();
        recommendLabel.setText("Result for " + title + ":");

        List<Book> books = BookDAO.getBooksByTitle(title);
        setListBook(books);
    }

    public void configButtonPress(ActionEvent actionEvent) {
    }

    public void deleteButtonPress(ActionEvent actionEvent) {
    }

    public void borrowButtonPress(ActionEvent actionEvent) {
    }

    public void returnButtonPress(ActionEvent actionEvent) {
    }

    public void showListLoan() throws IOException {
        SceneManager.showUserLoan(user);
    }

    public void logOut() throws IOException {
        SceneManager.showLoginView();
        Alert.showAlert("r u sure u want to logout", "no");
    }

    public void userInfo() throws IOException {
        SceneManager.showUserDashboard(user);
    }

    public void manageUser() {
        SceneManager.showManageUser();
    }

    public void showNotifications() {
        SceneManager.showNotification(user);
    }

    public void cleanUp() {
        recommendFlowPane.getChildren().clear();
    }

    public void addBookButtonPressed(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/AddBookView.fxml"));
            AnchorPane addBookAnchorPane = loader.load();
            AddBookController controller = loader.getController();
            controller.setStackPane(mainStackPane);

            mainStackPane.getChildren().add(addBookAnchorPane);

            // Optionally, make the new AnchorPane visible or set a transition effect
            addBookAnchorPane.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading AddBook FXML: " + e.getMessage());
        }
    }

    public void handleCategoryButtonAction(String category, String labelText) {
        List<Book> books = BookDAO.getBooksByCategory(category);
        recommendFlowPane.getChildren().clear();
        recommendLabel.setText(labelText);
        try {
            setListBook(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleFictionButtonAction(ActionEvent actionEvent) {
        handleCategoryButtonAction("Fiction", "Fiction");
    }

    public void handleHealthAndFitnessButtonAction(ActionEvent actionEvent) {
        handleCategoryButtonAction("Health & Fitness", "Health and Fitness");
    }

    public void handleBusinessButtonAction(ActionEvent actionEvent) {
        handleCategoryButtonAction("Business & Economics", "Business and Economics");
    }

    public void handleOtherButtonAction(ActionEvent actionEvent) {
        handleCategoryButtonAction("Other", "Other");
    }

    public void handleEducationButtonAction(ActionEvent actionEvent) {
        handleCategoryButtonAction("Education", "Education");
    }

    public void handleComicButtonAction(ActionEvent actionEvent) {
        handleCategoryButtonAction("Comics & Graphic Novels", "Comics and Graphic Novels");
    }


    public void handleNoNotifiButonPressed(ActionEvent actionEvent) {
        Alert.showAlert("You have no notifi","omg");
    }

    public void handleHavingNotifiButonPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/NotificationView.fxml"));
            StackPane notiView = loader.load();
            NotificationController controller = loader.getController();
            controller.setUser(user);
            controller.setView(notiView,mainStackPane);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void handleCloseButtonAction() {

    }
}
