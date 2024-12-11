package Util;

import javafx.scene.control.Alert;

public class AlertManager {

    public static void showAlert(String content, String title) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static void showConfirmAlert(String content, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
