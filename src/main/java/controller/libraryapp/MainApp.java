package controller.libraryapp;

import Util.SwitchScene;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            SwitchScene.setPrimaryStage(primaryStage);
            SwitchScene.showLoginView();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
