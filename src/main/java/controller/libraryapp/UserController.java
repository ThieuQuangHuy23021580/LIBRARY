package controller.libraryapp;

import Util.Alert;
import Util.SceneManager;
import Util.UserDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.User;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileInputStream;
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
    @FXML
    private ImageView userImageView;
    @FXML
    private Button uploadButton;

    private User user;

    public void initialize() {
        Circle circle = new Circle(125);
        circle.setCenterX(125);
        circle.setCenterY(125);
        userImageView.setClip(circle);
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
        UserDAO.loadImageFromDatabase(user,userImageView);
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
        String name = userNameTextField.getText();
        String phone = userPhoneTextField.getText();
        String password = userPasswordTextField.getText();

        if (name.isEmpty() && phone.isEmpty() && password.isEmpty()) {
            Alert.showAlert("no", "fill");
        }
        if (!phone.isEmpty() && checkPhone(phone)) {
            user.setPhone(phone);
            userPhone.setText(phone);
        } else Alert.showAlert("no", "must contain number only");
        if(!password.isEmpty() && LoginViewController.checkStrongPassword(password)) {
            user.setPassword(password);
            userPassword.setText(password);
        }
        if(!name.isEmpty()) {
            user.setUserName(name);
        }
        UserDAO.handleUpdateInfo(user);

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

    public void chooseImageFromDesktop() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());

            if (selectedFile != null) {
                UserDAO.saveImagePathToDatabase(selectedFile,user);

                try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
                    Image image = new Image(fileInputStream);
                    userImageView.setImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
