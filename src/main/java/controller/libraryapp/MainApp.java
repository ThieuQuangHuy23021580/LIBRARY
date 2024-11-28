package controller.libraryapp;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainApp extends Application {

    public static ArrayList<String> fxmlLoaders = new ArrayList<>();
    private Stage primaryStage;

    public void addFXML() {
//        fxmlLoaders.add("");
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/NewMainView.fxml"));
            MainViewController controller = loader.getController();
            loader.setController(controller);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("LibraHub");
            primaryStage.setResizable(false);


            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
