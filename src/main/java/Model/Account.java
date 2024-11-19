package Model;

public class Account {
    private String email;
    private String password;
    private User user;

    public Account(String email, String password, User user) {
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
