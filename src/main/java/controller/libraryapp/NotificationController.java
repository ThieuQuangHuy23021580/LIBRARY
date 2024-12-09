package controller.libraryapp;
import Util.NotificationDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Notification;
import model.User;

import java.util.List;

public class NotificationController {

    @FXML
    private ListView<Notification> notificationListView;

    private User user;
    private List<Notification> notificationList;

    public void setUser(User user) {
        this.user = user;
        // Lấy danh sách thông báo từ cơ sở dữ liệu
        notificationList = NotificationDAO.getNotificationsForUser(user);
        notificationListView.getItems().addAll(notificationList);
        initializeListView();
    }

    // Hàm khởi tạo để tùy chỉnh giao diện ListView
    private void initializeListView() {
        // Tùy chỉnh cách hiển thị từng item trong ListView
        notificationListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Notification notification, boolean empty) {
                super.updateItem(notification, empty);
                if (empty || notification == null) {
                    setText(null);
                    setStyle(null);
                } else {
                    setText(notification.getMessage());
                    // Nếu đã đọc, hiển thị màu nhạt hơn
                    if (notification.isRead()) {
                        setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
                    } else {
                        setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });
    }

    // Hàm xử lý khi người dùng nhấn nút "Đánh dấu đã đọc"
    @FXML
    private void markAsRead() {
        Notification selectedNotification = notificationListView.getSelectionModel().getSelectedItem();
        if (selectedNotification == null) {
            // Hiển thị thông báo nếu không có thông báo nào được chọn
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn một thông báo để đánh dấu đã đọc!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Đánh dấu thông báo là đã đọc
        selectedNotification.setRead(true);

        // Cập nhật trạng thái trong cơ sở dữ liệu
        NotificationDAO.markNotificationAsRead(selectedNotification);

        // Cập nhật lại danh sách hiển thị
        notificationListView.refresh();
    }
}
