package model;

public class User {
    public static final String USER = "User";
    public static final String MANAGER = "Manager";
    private int id;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private String role;

    public User(int id, String userName, String email, String phone, String password, String role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public User(int id, String email,String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        phone = null;
        role = USER;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}