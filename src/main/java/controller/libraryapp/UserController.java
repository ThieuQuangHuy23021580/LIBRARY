package controller.libraryapp;

import Model.Account;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    private Account account;
    private ObservableList<User> users;
    private List<User> usersList = new ArrayList<>();
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User,String> userNameColumn;
    @FXML
    private TableColumn<User,Integer> ageColumn;
    public void initialize() {
        users = FXCollections.observableArrayList(usersList);
        tableView.setItems(users);
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        try {
        getUser();}
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addUser(User user) {
        usersList.add(user);
    }
    public void getUser() throws SQLException {
        DatabaseConnect db = new DatabaseConnect();
        String sql = "SELECT userName,age FROM userInfo";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User(rs.getString("userName"), rs.getInt("age"));
                addUser(user);
            }
            users.setAll(usersList);
        }
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
