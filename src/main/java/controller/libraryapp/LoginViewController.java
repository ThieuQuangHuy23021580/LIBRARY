package controller.libraryapp;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LoginViewController {

    @FXML
    private TextField emailAddressField;

    @FXML
    private TextField ConfirmPasswordField;

    @FXML
    private Label lb_text1;

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
    private TextField passwordField;

    @FXML
    private AnchorPane layer2;

    @FXML
    private Button toSignInButton;

    @FXML
    private Label lb_text5;

    @FXML
    private ImageView bg ;

    Rectangle clip;


    @FXML
    void initialize() {

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
    void signUpButtonPressed(ActionEvent event) {
        System.out.println("Sign up button pressed");
    }

    @FXML
    void signInButtonPressed(ActionEvent event) {
        System.out.println("Sign in button pressed");
    }

    @FXML
    void EnterEmailAddress(ActionEvent event) {
    String s = emailAddressField.getText();
        System.out.println("Enter email address: " + s);
    }

    @FXML
    void EnterPassword(ActionEvent event) {
        String s = passwordField.getText();
        System.out.println("Enter password: " + s);
    }

    @FXML
    public void EnterConfirmPassword(ActionEvent actionEvent) {
        String s = ConfirmPasswordField.getText();
        System.out.println("Enter confirm password: " + s);
    }

    /**
     * Chuyển khung hình từ Đăng nhập sang Đăng kí.
     * @param actionEvent sự kiện nhấn nút Sign Up.
     */
    public void toSignUpButtonPressed(ActionEvent actionEvent) {

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

        slide1.setOnFinished(event -> {});
        slide2.setOnFinished(event -> {});

    }

    /**
     * Chuyển khung hình từ Đăng kí sang Đăng nhập.
     * @param actionEvent sự kiện nhấn nút Sign In.
     */
    public void toSignInButtonPressed(ActionEvent actionEvent) {

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

        slide1.setOnFinished(event -> {});
        slide2.setOnFinished( event -> {});

    }

}




