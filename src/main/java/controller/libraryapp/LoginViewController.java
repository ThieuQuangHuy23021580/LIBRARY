package controller.libraryapp;


import Model.Account;
import Model.User;
import javafx.animation.TranslateTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;


public class LoginViewController {

    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField ConfirmPasswordField;

    @FXML
    private Label lb_text1;

    @FXML
    private CheckBox show;

    @FXML
    private Button toSignUpButton;

    @FXML
    private Label lb_text2;

    @FXML
    private Label lb_text3;

    @FXML
    private Button signInButton;

    @FXML
    private Label lb_text4;

    @FXML
    private Button signUpButton;

    @FXML
    private AnchorPane layer1;

    @FXML
    private PasswordField passwordField;

    @FXML
    private AnchorPane layer2;

    @FXML
    private Button toSignInButton;

    @FXML
    private Label lb_text5;

    @FXML
    private ImageView bg;
    @FXML
    private TextField showPassword;

    Account account = new Account();
    DatabaseConnect db = new DatabaseConnect();
    Connection conn = null;

    Rectangle clip;


    @FXML
    void initialize() {
        showPassword.setVisible(false);
        lb_text2.setVisible(false);
        lb_text5.setVisible(false);
        lb_text4.setVisible(false);
        toSignInButton.setVisible(false);
        signUpButton.setVisible(false);
        ConfirmPasswordField.setVisible(false);
        clip = new Rectangle(bg.getFitWidth(), 400);
        clip.setY(800);
        bg.setClip(clip);

    }

    @FXML
    void signUpButtonPressed(ActionEvent event) throws SQLException {
        if (emailAddressField.getText().isEmpty() || passwordField.getText().isEmpty() || ConfirmPasswordField.getText().isEmpty()) {
            showAlert("Please fill all", "no");
            return;
        }
        PreparedStatement st = null;
        ResultSet key = null;

        try {
            conn = db.getConnection();
            if (passwordField.getText().equals(ConfirmPasswordField.getText())) {
                String sql1 = "INSERT INTO userInfo(userName, age) VALUES (?, ?)";
                st = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, "Update");
                st.setInt(2, 0);
                st.executeUpdate();

                key = st.getGeneratedKeys();
                int userId = 0;
                if (key.next()) {
                    userId = key.getInt(1);
                }

                st.close();
                if (key != null) key.close();

                String sql = "INSERT INTO user(email, password, userId) VALUES (?, ?, ?)";
                st = conn.prepareStatement(sql);
                st.setString(1, emailAddressField.getText());
                st.setString(2, passwordField.getText());
                st.setInt(3, userId);
                st.executeUpdate();

                st.close();

                // Điều hướng tới màn hình đăng nhập
                toSignInButtonPressed(event);
            } else {
                showAlert("Password mismatch", "no");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error: " + e.getMessage(), "error");
        } finally {
            // Đảm bảo tài nguyên được đóng
            try {
                if (key != null) key.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void signInButtonPressed(ActionEvent event) throws IOException, SQLException {
        if (emailAddressField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert("Please fill all fields", "Error");
            return;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = db.getConnection();

            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, emailAddressField.getText());
            pstmt.setString(2, passwordField.getText());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("userId");

                String userInfoSql = "SELECT * FROM userInfo WHERE userId = ?";
                pstmt.close();
                pstmt = conn.prepareStatement(userInfoSql);
                pstmt.setInt(1, userId);
                ResultSet userInfo = pstmt.executeQuery();
                loginViewToMenu(event);
                if (userInfo.next()) {
                    User user = new User(userInfo.getString("userName"),userInfo.getInt("age"));
                    account.setUser(user);
                }
                userInfo.close();
            } else {
                showAlert("Invalid email or password", "Error");
            }

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }


    public void loginViewToMenu(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/fxml_designs/NewMainView.fxml"));
        Parent root = loader.load();

        MainViewController controller = loader.getController();
        controller.setUserName(emailAddressField.getText());
        controller.setAccount(account);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showAlert(String content, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public void showCharacter() {
        if (show.isSelected()) {
            showPassword.setVisible(true);
            showPassword.setText(passwordField.getText());
            passwordField.setVisible(false);
        } else {
            passwordField.setVisible(true);
            passwordField.setText(showPassword.getText());
            showPassword.setVisible(false);
        }
    }

    /**
     * Chuyển khung hình từ Đăng nhập sang Đăng kí.
     *
     * @param actionEvent sự kiện nhấn nút Sign Up.
     */
    public void toSignUpButtonPressed(ActionEvent actionEvent) {
        show.setVisible(false);
        //Dịch chuyển khung hình hiên thị background trái sang phải.
        TranslateTransition moveClip = new TranslateTransition(Duration.seconds(0.8), clip);
        moveClip.setToY(-800);
        moveClip.play();
        System.out.println("moveclip " + moveClip.getNode().getTranslateY());

        //Dịch chuyển layer1 sang phải.
        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(0.8));
        slide1.setNode(layer1);
        slide1.setToX(793);
        slide1.play();

        //Dịch chuyển layer2 sang trái.
        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(0.5));
        slide2.setNode(layer2);
        slide2.setToX(-420);
        slide2.play();

        // Kiểm tra vị trí dịch chuyển của layer2, thay đổi hiển thị các đối tượng.
        slide2.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double currentTranslateX = layer2.getTranslateX();

            if (currentTranslateX <= -240) {
                lb_text2.setVisible(true);
                lb_text5.setVisible(true);
                lb_text4.setVisible(true);
                toSignInButton.setVisible(true);
                signUpButton.setVisible(true);
                ConfirmPasswordField.setVisible(true);
                lb_text1.setVisible(false);
                lb_text3.setVisible(false);
                toSignUpButton.setVisible(false);
                signInButton.setVisible(false);
            }
        });

        slide1.setOnFinished(event -> {
        });
        slide2.setOnFinished(event -> {
        });

    }

    /**
     * Chuyển khung hình từ Đăng kí sang Đăng nhập.
     *
     * @param actionEvent sự kiện nhấn nút Sign In.
     */
    public void toSignInButtonPressed(ActionEvent actionEvent) {
        emailAddressField.setText(null);
        passwordField.setText(null);
        show.setVisible(true);
        //Dịch chuyển khung hình hiên thị background phải sang trái.
        TranslateTransition moveClip = new TranslateTransition(Duration.seconds(0.8), clip);
        moveClip.setToY(0);
        moveClip.play();
        System.out.println("moveclip " + moveClip.getNode().getTranslateY());

        // Dịch chuyển layer1 sang trái.
        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(0.8));
        slide1.setNode(layer1);
        slide1.setToX(0);
        slide1.play();

        //Dịch chuyển layer2 sang phải.
        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(0.5));
        slide2.setNode(layer2);
        slide2.setToX(0);
        slide2.play();

        // Kiểm tra vị trí dịch chuyển của layer2, thay đổi hiển thị các đối tượng.
        slide2.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double currentTranslateX = layer2.getTranslateX();

            if (currentTranslateX >= -200) {
                layer2.setTranslateX(0);
                lb_text2.setVisible(false);
                lb_text5.setVisible(false);
                lb_text4.setVisible(false);
                toSignInButton.setVisible(false);
                signUpButton.setVisible(false);
                ConfirmPasswordField.setVisible(false);
                lb_text1.setVisible(true);
                lb_text3.setVisible(true);
                toSignUpButton.setVisible(true);
                signInButton.setVisible(true);
            }
        });

        slide1.setOnFinished(event -> {
        });
        slide2.setOnFinished(event -> {
        });

    }

}




