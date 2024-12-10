package controller.libraryapp;
import Util.NotificationDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Notification;
import model.User;

import java.io.IOException;
import java.util.List;

public class NotificationController {


    private User user;
    private List<Notification> notifications;
    private StackPane mainStackPane;
    private StackPane stackPane;
    @FXML
    VBox notificationList;


    public void setUser(User user) {
        this.user = user;
    }

    public void setView(StackPane stackPane, StackPane st) {
        this.stackPane = stackPane;
        this.mainStackPane = st;
        getNotificationList();
    }

    public void getNotificationList() {
        try {

            notifications = NotificationDAO.getNotificationsForUser(user);
            for(Notification notification : notifications) {
                Label label = new Label(notification.getMessage());
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/controller/fxml_designs/NotificationObject.fxml"));
                AnchorPane child = loader1.load();

                label.setLayoutX(250.0);
                label.setLayoutY(50.0);
                label.setStyle("-fx-font-size: 25;");

                child.getChildren().add(label);
                notificationList.getChildren().add(child);
            }
            mainStackPane.getChildren().add(stackPane);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void handleCloseButtonAction() {
        mainStackPane.getChildren().remove(stackPane);
    }
}
