package controller.libraryapp;

import Util.AlertManager;
import Util.*;
import Util.BookDAO;
import Util.NotificationDAO;
import Util.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Book;
import model.Notification;
import model.User;


import java.io.IOException;
import java.util.List;

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
    @FXML
    private ImageView userAvatar;
    @FXML
    protected Button noNotifiButton;
    @FXML
    protected Button havingNotifiButton;
    @FXML
    private MenuItem addBookButton;

    private List<Notification> notifications;

    private User user;

    public void setUser(User user) {
        this.user = user;
        try {
            UserDAO.loadImageFromDatabase(user, userAvatar);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (user.getRole().equals(User.MANAGER)) {
            listLoanButton.setVisible(false);
            manageUserButton.setVisible(true);
            addBookButton.setVisible(true);

        } else {
            listLoanButton.setVisible(true);
            manageUserButton.setVisible(false);
            addBookButton.setVisible(false);
        }
        notifications = NotificationDAO.getNotificationsForUser(user);
        if (!notifications.isEmpty()) {
            haveNotification();
        } else {
            noNotification();
        }
        userName.setText(user.getUserName());
        showBook();
    }

    public void haveNotification() {
        noNotifiButton.setVisible(false);
        havingNotifiButton.setVisible(true);
    }
    public void noNotification() {
        noNotifiButton.setVisible(true);
        havingNotifiButton.setVisible(false);
    }


    @FXML
    public void initialize() {

        categoriesAnchorPane.setManaged(false);
        categoriesAnchorPane.setVisible(false);
        Circle circle = new Circle(17.5);
        circle.setCenterX(17.5);
        circle.setCenterY(17.5);
        userAvatar.setClip(circle);

    }

    @FXML
    void getDiscoveryCategories() {
        discoverCatetegories.setVisible(true);
        fictionCateList.setVisible(false);
        educationCateList.setVisible(false);
        otherCateList.setVisible(false);
        comicCateList.setVisible(false);
        healthAndFitnessCateList.setVisible(false);
        businessAndEconomicCateList.setVisible(false);
    }

    @FXML
    void getFictionCateList() {
        fictionCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    @FXML
    void getEducationCateList() {
        educationCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    @FXML
    void getComicCateList() {
        comicCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    @FXML
    void getHealthAndFitnessCateList() {
        healthAndFitnessCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    @FXML
    void getBusinessAndEconomicCateList() {
        businessAndEconomicCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    @FXML
    void getOtherCateList() {
        otherCateList.setVisible(true);
        discoverCatetegories.setVisible(false);
    }

    public void setListBook(List<Book> books) throws IOException {
        for (Book book : books) {
            SceneManager.loadBookObject(book, user, mainStackPane,
                    bookObject -> {
                        recommendFlowPane.getChildren().add(bookObject);
                    },
                    error -> {
                        System.out.println("Lỗi khi tải BookObject: " + error.getMessage());
                    }
            );

        }
    }

    void showBook() {
        try {
            List<Book> books = BookDAO.getRandomBook();
            setListBook(books);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private StackPane mainStackPane;

    @FXML
    private void handleSearchButtonAction() throws IOException {
        String title = searchTextField.getText().trim();
        recommendFlowPane.getChildren().clear();
        recommendLabel.setText("Result for " + title + ":");

        List<Book> books = BookDAO.getBooksByTitle(title);
        setListBook(books);
    }

    public void showListLoan() throws IOException {
        SceneManager.showUserLoan(user);
    }

    public void logOut() throws IOException {

        Boolean isCofirm = AlertManager.showConfirmAlert("r u sure u want to logout", "no");
        if(isCofirm){
            SceneManager.showLoginView();
        }

    }

    public void userInfo() throws IOException {
        SceneManager.showUserDashboard(user);
    }

    public void manageUser() {
        SceneManager.showManageUser(user);
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


    public void handleNoNotifiButonPressed() {
        AlertManager.showAlert("You have no notifi", "omg");
    }

    public void handleHavingNotifiButonPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/NotificationView.fxml"));
            StackPane notiView = loader.load();
            NotificationController controller = loader.getController();
            controller.setList(notifications);
            controller.setMainViewController(this);
            controller.setView(notiView, mainStackPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void whiteHomeButtonPressed() {
        homeButton_active.setVisible(false);
        homeButton_active.setManaged(false);
        homeButton_inactive.setVisible(true);
        homeButton_inactive.setManaged(true);
        categoriesButton_active.setVisible(true);
        categoriesButton_active.setManaged(true);
        categoriesButton_inactive.setVisible(false);
        categoriesButton_inactive.setManaged(false);
        homeAnchorPane.setManaged(true);
        homeAnchorPane.setVisible(true);
        categoriesAnchorPane.setManaged(false);
        categoriesAnchorPane.setVisible(false);
        recommendLabel.setText("Recommend");
        cleanUp();
        showBook();
    }
    @FXML
    public void whiteCategoriesButtonPressed() {
        homeButton_active.setVisible(true);
        homeButton_active.setManaged(false);
        homeButton_inactive.setVisible(false);
        homeButton_inactive.setManaged(false);
        categoriesButton_active.setVisible(false);
        categoriesButton_active.setManaged(false);
        categoriesButton_inactive.setVisible(true);
        categoriesButton_inactive.setManaged(true);
        categoriesAnchorPane.setManaged(true);
        categoriesAnchorPane.setVisible(true);
        homeAnchorPane.setManaged(false);
        homeAnchorPane.setVisible(false);
        recommendLabel.setText("Discovery all categories now.");
        cleanUp();
    }

}
