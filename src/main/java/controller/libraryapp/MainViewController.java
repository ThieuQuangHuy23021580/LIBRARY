package controller.libraryapp;

import Util.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.Book;
import model.User;


import java.io.IOException;
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
        mainStackPane.getChildren().add(root);
    }


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

    @FXML
    private StackPane mainStackPane; // Reference to the main StackPane in your FXML

    @FXML
    private void handleSearchButtonAction() {
        String title = searchTextField.getText().trim();
        recommendFlowPane.getChildren().clear();

        // Fetch books by title
        List<Book> books = DatabaseUtil.getBooksByTitle(title);

        if (books == null || books.isEmpty()) {
            System.out.println("No books found with title: " + title);
        } else {
            try {
                for (Book book : books) {
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/controller/fxml_designs/BookObject.fxml")));
                    StackPane bookObject = loader.load();

                    // Pass book details to the controller
                    BookObjectController controller = loader.getController();
                    controller.setMainStackPane(mainStackPane);

                    controller.setBookDetails(book,user);


                    recommendFlowPane.getChildren().add(bookObject);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading book objects for search results.");
            }
        }
    }

    public void configButtonPress(ActionEvent actionEvent) {
    }

    public void deleteButtonPress(ActionEvent actionEvent) {
    }
    public void borrowButtonPress(ActionEvent actionEvent) {
    }

    public void returnButtonPress(ActionEvent actionEvent) {
    }
    public void logOut() throws IOException {
        SwitchScene.showLoginView();
    }

    public void userInfo() throws IOException {
        SwitchScene.showUserDashboard(user);
    }

    public void addBookButtonPressed(ActionEvent actionEvent) {
        try {
            // Load the AddBook FXML layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/AddBookView.fxml"));
            AnchorPane addBookAnchorPane = loader.load();
            AddBookController controller = loader.getController();
            controller.setStackPane(mainStackPane);



            // Add the AddBook AnchorPane to the main StackPane
            mainStackPane.getChildren().add(addBookAnchorPane);

            // Optionally, make the new AnchorPane visible or set a transition effect
            addBookAnchorPane.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading AddBook FXML: " + e.getMessage());
        }
    }
}
