package Util;

import controller.libraryapp.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.function.Consumer;

public class SceneManager {

    private static Stage primaryStage;
    private static final Map<String, Scene> sceneCache = new HashMap<>();
    private static final Map<String, FXMLLoader> loaderCache = new HashMap<>();
    private static final Map<String, Image> imageCache = new HashMap<>();

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
            cleanController(sceneName);
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

    public static void loadBookObject(Book book, User user, StackPane sp, Consumer<StackPane> onSuccess, Consumer<Throwable> onFailure) {
        Task<StackPane> loadTask = new Task<>() {
            @Override
            protected StackPane call() throws Exception {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SceneManager.class.getResource("/controller/fxml_designs/BookObject.fxml")));
                StackPane bookObject = loader.load();

                BookObjectController controller = loader.getController();
                controller.setBook(book);
                controller.setUser(user);
                controller.setMainStackPane(sp);

                return bookObject;
            }
        };

        // Xử lý khi tải thành công
        loadTask.setOnSucceeded(event -> {
            // Cập nhật UI trong JavaFX Application Thread
            Platform.runLater(() -> onSuccess.accept(loadTask.getValue()));
        });

        // Xử lý khi xảy ra lỗi
        loadTask.setOnFailed(event -> {
            Throwable ex = loadTask.getException();
            ex.printStackTrace();
            // Gửi lỗi đến callback
            Platform.runLater(() -> onFailure.accept(ex));
        });

        // Chạy task trong luồng nền
        new Thread(loadTask).start();
    }


    private static void cleanController(String sceneName) {
        Object controller = loaderCache.get(sceneName).getController();
        if (controller instanceof MainViewController) {
            ((MainViewController) controller).cleanUp();
        }
    }

    public static void showLoginView() {
        showScene("LoginView", "/controller/fxml_designs/LoginView.fxml");
        LoginViewController controller = getController("LoginView");
        controller.setStage(primaryStage);
    }

    public static void showMainView(User user) {
        showScene("MainView", "/controller/fxml_designs/NewMainView.fxml");
        MainViewController controller = getController("MainView");
        controller.setUser(user);
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

    public static void showManageUser(User user) {
        showScene("ManageUser", "/controller/fxml_designs/ManageUser.fxml");
        ManageUserController controller = getController("ManageUser");
        controller.setUser(user);
    }

    public static Image getImage(String url) {
        if (imageCache.containsKey(url)) {
            return imageCache.get(url);
        } else {
            Image image = new Image(url, true);
            imageCache.put(url, image);
            return image;
        }
    }

}
