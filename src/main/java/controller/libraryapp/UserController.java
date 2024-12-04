package controller.libraryapp;

import Util.SceneManager;
import Util.UserDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.util.Duration;
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
    private Button seeUserPasswordButton;
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
        userPassword.setText(convert(user.getPassword()));
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
        String userName = userNameTextField.getText();

        String phone = userPhoneTextField.getText();
        String password = userPasswordTextField.getText();
        if (userName == null || phone == null || password == null) {
            UserDAO.showAlert("no", "fill all");
        }
        if (checkPhone(phone)) {
            user.setPhone(phone);
        } else UserDAO.showAlert("no", "must contain number only");
        user.setUserName(userName);
        user.setPassword(userPasswordTextField.getText());
        UserDAO.handleUpdateInfo(user);

        userNameTextField.setText(userName);
        userPhone.setText(phone);
        userPassword.setText(password);

        cancelUpdate();
    }

    public void cancelUpdate() {
        changeInfoButton.setVisible(true);
        updateInfoButton.setVisible(false);
        cancelInfoButton.setVisible(false);

        showUserInfo();
    }

    public void showPassword() {
        userPassword.setText(user.getPassword());

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5), e -> userPassword.setText(convert(user.getPassword())))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private String convert(String password) {
        String rs = "";
        for (int i = 0; i < password.length(); i++) {
            rs += "*";
        }
        return rs;
    }

    private boolean checkPhone(String phone) {
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    public void backtoMain() throws IOException {
        SceneManager.showMainView(user);
    }
}
