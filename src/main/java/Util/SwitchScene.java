package Util;

import controller.libraryapp.LoginViewController;
import controller.libraryapp.MainViewController;
import controller.libraryapp.UserController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.util.Objects;

public class SwitchScene {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void showLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(SwitchScene.class.getResource("/controller/fxml_designs/LoginView.fxml"));
        LoginViewController controller = loader.getController();

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("LibraHub");
        primaryStage.show();
    }

    public static void showMainView(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(SwitchScene.class.getResource("/controller/fxml_designs/NewMainView.fxml"));
        Parent root = loader.load();
        MainViewController controller = loader.getController();
        controller.setUser(user);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void showUserDashboard(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SwitchScene.class.getResource("/controller/fxml_designs/userProfile.fxml")));
        Parent root = loader.load();
        UserController controller = loader.getController();
        controller.setUser(user);
        Stage stage = new Stage();
        stage.setTitle("yourInfo");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
}
