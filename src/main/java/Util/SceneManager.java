package Util;

import controller.libraryapp.*;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Book;
import model.Notification;
import model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SceneManager {

    private static Stage primaryStage;
    private static final Map<String, Scene> sceneCache = new HashMap<>();
    private static final Map<String, FXMLLoader> loaderCache = new HashMap<>();

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    private static void loadSceneIfNotCached(String sceneName, String fxmlPath) throws IOException {
        if (!sceneCache.containsKey(sceneName)) {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            sceneCache.put(sceneName, scene);
            loaderCache.put(sceneName, loader);
        }
    }


    public static void showScene(String sceneName, String fxmlPath) {
        try {
            loadSceneIfNotCached(sceneName, fxmlPath);
            cleanController(sceneName); // Dọn dẹp dữ liệu trước khi chuyển
            primaryStage.setScene(sceneCache.get(sceneName));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getController(String sceneName) {
        FXMLLoader loader = loaderCache.get(sceneName);
        if (loader == null) {
            throw new IllegalArgumentException("FXML chưa được tải: " + sceneName);
        }
        return loader.getController();
    }

    public static StackPane loadBookObject(Book book, User user, StackPane sp) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SceneManager.class.getResource("/controller/fxml_designs/BookObject.fxml")));
        StackPane bookObject = loader.load();

        BookObjectController controller = loader.getController();
        controller.setBook(book);
        controller.setUser(user);
        controller.setMainStackPane(sp);
        return bookObject;
    }


    // Clean up data of controllers
    private static void cleanController(String sceneName) {
        Object controller = loaderCache.get(sceneName).getController();
        if (controller instanceof MainViewController) {
            ((MainViewController) controller).cleanUp();
        }
    }

    public static void showLoginView() {
        showScene("LoginView", "/controller/fxml_designs/LoginView.fxml");
    }

    public static void showMainView(User user) {
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                loadSceneIfNotCached("MainView", "/controller/fxml_designs/NewMainView.fxml");
                MainViewController controller = getController("MainView");
                controller.setUser(user);
                return null;
            }
        };

        loadTask.setOnSucceeded(event -> {
            showScene("MainView", "/controller/fxml_designs/NewMainView.fxml");
        });

        loadTask.setOnFailed(event -> {
            Throwable ex = loadTask.getException();
            ex.printStackTrace();
            // Hiển thị thông báo lỗi cho người dùng (nếu cần)
        });

        new Thread(loadTask).start();
    }

    public static void showUserDashboard(User user) {
        showScene("UserInfo", "/controller/fxml_designs/UserInfo.fxml");
        UserController controller = getController("UserInfo");
        controller.setUser(user);
    }

    public static void showUserLoan(User user) {
        showScene("Loan", "/controller/fxml_designs/MyLibraryView.fxml");
        LoanController controller = getController("Loan");
        controller.setUser(user);
    }

    public static void showManageUser() {
        showScene("ManageUser","/controller/fxml_designs/ManageUser.fxml");
        ManageUserController controller = getController("ManageUser");
    }

    public static void showNotification(User user) {
        showScene("Notification","/controller/fxml_designs/Notification.fxml");
        NotificationController controller = getController("Notification");
        controller.setUser(user);
    }
}
