package model;


public class Notification {

    private User user;
    private String message;
    private boolean isRead;
    public Notification(User user, String message, boolean isRead) {
        this.user = user;
        this.message = message;
        this.isRead = isRead;
    }


    public User getUser() {
        return user;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
