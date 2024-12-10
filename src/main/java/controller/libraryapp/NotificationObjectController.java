package controller.libraryapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class NotificationObjectController {
      @FXML
      Label messageLabel;

      AnchorPane pane;
      public void setMessage(String message) {
          messageLabel.setText(message);
      }
      public void setAnchorPane(AnchorPane pane) {
          this.pane = pane;
      }

      public AnchorPane getNotificationObject() {
          return pane;
      }

}
