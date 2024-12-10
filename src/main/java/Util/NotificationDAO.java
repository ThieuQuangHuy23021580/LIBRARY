package Util;

import model.Book;
import model.Notification;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public static boolean checkDuplicateNotification(int userId, String message) {
        String query = "select 1 from notification where userId = ? and message = ?";
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, message);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void generateNotifications() throws SQLException {
        Connection connection = DatabaseConnect.getConnection();

        String query = "SELECT userId, bookISBN, datediff(returnDate, now()) as daysRemaining from loan HAVING daysRemaining <= 3";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int userId = resultSet.getInt("userId");
            String bookISBN = resultSet.getString("bookISBN");
            int dayRemaining = resultSet.getInt("daysRemaining");
            Book book = BookDAO.getBookByISBN(bookISBN);
            String message = "";
            if (dayRemaining >= 0) {
                message = "Sách: '" + book.getTitle() + "'(Sắp hết hạn - còn " + dayRemaining + " ngày)";
            } else
                message = "Sách: '" + book.getTitle() + "'(Đã quá hạn trả " + -dayRemaining + " ngày\n" + "Số tiền phải trả:" + -dayRemaining * 300000 + " VNĐ";
            if (checkDuplicateNotification(userId, message)) {
                String insertQuery = "INSERT INTO notification(userId, message, isRead) VALUES (?,?,?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, userId);
                insertStatement.setString(2, message);
                insertStatement.setBoolean(3, false);
                insertStatement.executeUpdate();
            }
        }
    }

    public static List<Notification> getNotificationsForUser(User user) {
        try {
            Connection connection = DatabaseConnect.getConnection();

            String query = "SELECT message FROM notification WHERE userId= ? and isRead = false";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());

            ResultSet resultSet = statement.executeQuery();
            List<Notification> notifications = new ArrayList<>();
            while (resultSet.next()) {
                Notification notification = new Notification(user, resultSet.getString("message"),false);
                notifications.add(notification);
            }
            return notifications;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void markNotificationAsRead(Notification notification) {
        String sql = "update notification set isRead = true where userId = ? and message = ?";
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, notification.getUser().getId());
            ps.setString(2, notification.getMessage());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

