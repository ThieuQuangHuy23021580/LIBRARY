package controller.libraryapp;

import Util.NotificationDAO;
import Util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            SceneManager.setPrimaryStage(primaryStage);
            SceneManager.showLoginView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
