package Util;

public class Alert {

    public static void showAlert(String content, String title) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
