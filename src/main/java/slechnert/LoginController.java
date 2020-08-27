package slechnert;


import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private List<User> registeredUsers;
    private User loggedUser;
    private DAO dao;
    private int failedLoginCounter = 2;

    public LoginController() {
    }

    private void initializeFontColor() {
        usernameTF.setStyle("-fx-text-inner-color: white; ; -fx-background-color:  #1c1c1c");
        passwordField.setStyle("-fx-text-inner-color: white; -fx-background-color:  #1c1c1c");
    }

    private void getUserList() {
        registeredUsers = dao.getAllUsers();
    }

    @FXML
    public Button close;

    @FXML
    public void stop() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button forgotPw;

    @FXML
    private void setForgetMsg() {
        forgotPw.setText("To recover your data, donate to s.lechner.work@gmail.com");
    }

    @FXML
    private Label prompt;
    @FXML
    private Label header;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    private boolean checkLogin() {
        for (User u : registeredUsers) {
            if (u.getName().equalsIgnoreCase(usernameTF.getText()) && u.getPassword().equals(passwordField.getText())) {
                loggedUser = u;
                return true;
            }
        }
        return false;
    }

    public void failedLogin() {
        prompt.setVisible(true);
        header.setVisible(false);
        forgotPw.setText(failedLoginCounter + " seconds penalty...");
        loginButton.setDisable(true);
    }

    public void recoverLogin() {
        loginButton.setDisable(false);
        prompt.setVisible(false);
        header.setVisible(true);
        forgotPw.setText("Forgot your details?");
    }

    public void sleep() {
        failedLogin();
        PauseTransition pause = new PauseTransition(Duration.seconds(failedLoginCounter));
        pause.setOnFinished(event -> {
            failedLoginCounter++;
            recoverLogin();
        });
        pause.play();
    }

    @FXML
    private void login() throws IOException, SQLException {
        if (checkLogin()) {
            dao.fillUserCustomSets(loggedUser);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("visualizer.fxml"));
            Parent visParent = loader.load();
            ControllerVisualizer cv = loader.getController();
            cv.initData(loggedUser);
            Scene visScene = new Scene(visParent);
            Stage windoof = (Stage) (loginButton.getScene().getWindow());
            windoof.hide();
            windoof.setScene(visScene);
            windoof.show();
        } else {
            sleep();
        }
    }

    @FXML
    private Button newUserButton;

    @FXML
    private void goToRegistration() throws IOException {
        Parent regParent = FXMLLoader.load(Main.class.getResource("registration.fxml"));
        Scene regScene = new Scene(regParent);
        Stage window = (Stage) (newUserButton.getScene().getWindow());
        window.hide();
        regScene.setFill(Color.TRANSPARENT);
        window.setScene(regScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeFontColor();
        dao = new DAO();
        getUserList();
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
