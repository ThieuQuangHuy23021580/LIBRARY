package controller.libraryapp;

import javafx.scene.control.TextField;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    private ObservableList<User> users;
    private List<User> usersList = new ArrayList<>();
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User,String> userNameColumn;
    @FXML
    private TableColumn<User,Integer> ageColumn;

    @FXML
    TextField username;
    @FXML
    TextField phone;
    private User user;
    public void setUser(User user) {
        this.user = user;
        username.setText(user.getUserName());
        phone.setText(user.getPhone());
    }
    public void initialize() {
        users = FXCollections.observableArrayList(usersList);
        tableView.setItems(users);
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
    }




    }

