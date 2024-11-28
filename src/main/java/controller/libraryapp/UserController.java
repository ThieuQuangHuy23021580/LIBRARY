package controller.libraryapp;

import Util.SwitchScene;
import Util.UserDAO;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    @FXML
    private TextField userIdTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField userEmailTextField;
    @FXML
    private TextField userPhoneTextField;
    @FXML
    private TextField userPasswordTextField;
    @FXML
    private Button updateInfoButton;
    @FXML
    private Button changeInfoButton;
    @FXML
    private Button cancelInfoButton;

    private User user;

    public void initialize() {
        updateInfoButton.setVisible(false);
        cancelInfoButton.setVisible(false);

        updateInfoButton.setCursor(Cursor.HAND);
        cancelInfoButton.setCursor(Cursor.HAND);
        changeInfoButton.setCursor(Cursor.HAND);

        userIdTextField.setEditable(false);
        userNameTextField.setEditable(false);
        userEmailTextField.setEditable(false);
        userPhoneTextField.setEditable(false);
        userPasswordTextField.setEditable(false);
    }
    public void setUser(User user) {
        this.user = user;
        showUserInfo();
    }

    public void showUserInfo() {
        userIdTextField.setText(String.valueOf(user.getId()));
        userNameTextField.setText(user.getUserName());
        userEmailTextField.setText(user.getEmail());
        userPhoneTextField.setText(user.getPhone());
        userPasswordTextField.setText(user.getPassword());
    }

    public void changeInfo() {
        changeInfoButton.setVisible(false);
        updateInfoButton.setVisible(true);
        cancelInfoButton.setVisible(true);
        userNameTextField.setEditable(true);
        userPhoneTextField.setEditable(true);
        userPasswordTextField.setEditable(true);
    }

    public void updateInfo() throws SQLException {
        user.setUserName(userNameTextField.getText());
        user.setPassword(userPasswordTextField.getText());
        user.setPhone(userPhoneTextField.getText());
        UserDAO.handleUpdateInfo(user);
        cancelUpdate();
    }
    public void cancelUpdate() {
        changeInfoButton.setVisible(true);
        updateInfoButton.setVisible(false);
        cancelInfoButton.setVisible(false);

        showUserInfo();
    }

    public void backtoMain() throws IOException {
        SwitchScene.showMainView(user);
    }
    }

