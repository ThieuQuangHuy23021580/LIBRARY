package controller.libraryapp;

import Util.SwitchScene;
import Util.UserDAO;
import javafx.scene.control.*;
import model.User;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;


public class UserController {

    @FXML
    private TextField userNameTextField;
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
    @FXML
    private Label userName;
    @FXML
    private Label userPhone;
    @FXML
    private Label userPassword;
    @FXML
    private Label userEmail;
    @FXML
    private Label userId;

    private User user;

    public void initialize() {
        updateInfoButton.setVisible(false);
        cancelInfoButton.setVisible(false);
    }
    public void setUser(User user) {
        this.user = user;
        showUserInfo();
    }

    public void showUserInfo() {
       userPasswordTextField.setVisible(false);
       userNameTextField.setVisible(false);
       userPhoneTextField.setVisible(false);
       userId.setText(String.valueOf(user.getId()));
       userName.setText(user.getUserName());
       userPhone.setText(user.getPhone());
       userPassword.setText(user.getPassword());
       userEmail.setText(user.getEmail());
    }

    public void changeInfo() {
        changeInfoButton.setVisible(false);
        updateInfoButton.setVisible(true);
        cancelInfoButton.setVisible(true);

        userPasswordTextField.setVisible(true);
        userNameTextField.setVisible(true);
        userPhoneTextField.setVisible(true);
    }

    public void updateInfo() throws SQLException {
        user.setUserName(userNameTextField.getText());
        user.setPassword(userPasswordTextField.getText());
        user.setPhone(userPhoneTextField.getText());
        UserDAO.handleUpdateInfo(user);

        userName.setText(user.getUserName());
        userPhone.setText(user.getPhone());
        userPassword.setText(user.getPassword());

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
