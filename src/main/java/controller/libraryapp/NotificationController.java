package controller.libraryapp;

import Util.NotificationDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Notification;
import model.User;

import java.io.IOException;
import java.util.List;

public class NotificationController {

    private List<Notification> notifications;
    private StackPane mainStackPane;
    private StackPane stackPane;
    @FXML
    VBox notificationList;

    private AnchorPane selectedNotificationPane;
    private Notification selectedNotification;
    private MainViewController mainViewController;

    public void setList(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setView(StackPane stackPane, StackPane st) {
        this.stackPane = stackPane;
        this.mainStackPane = st;
        getNotificationList();
    }
    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void getNotificationList() {
        try {

            for (Notification notification : notifications) {
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/controller/fxml_designs/NotificationObject.fxml"));
                AnchorPane notificationPane = loader1.load();
                Label label = new Label(notification.getMessage());
                label.setLayoutX(12.0);
                label.setLayoutY(5.0);
                label.setPrefHeight(46.0);
                label.setPrefWidth(747.0);
                label.setStyle("-fx-font-size: 25;");
                label.setTextFill(javafx.scene.paint.Color.WHITE);
                label.setWrapText(true);
                notificationPane.getChildren().add(label);
                notificationPane.setOnMouseClicked(event -> {
                    selectedNotificationPane = notificationPane;
                    selectedNotification = notification;
                    label.setTextFill(Color.RED);
                });
                notificationList.getChildren().add(notificationPane);
            }
            mainStackPane.getChildren().add(stackPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCloseButtonAction() {
        mainStackPane.getChildren().remove(stackPane);
        if(notifications.isEmpty()) {
            mainViewController.noNotification();
        }
        else mainViewController.haveNotification();
    }


    public void handleDeleteNofi() {
        if(selectedNotification != null) {
            notificationList.getChildren().remove(selectedNotificationPane);
            NotificationDAO.markNotificationAsRead(selectedNotification);
            notifications.remove(selectedNotification);
        }
    }

}
