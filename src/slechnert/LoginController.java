package slechnert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private List<User> users;
    private DAO dao;

    public LoginController() {
        this.dao = new DAO();
    }

    private void getUserList() {
        users = dao.getAllUsers();
    }

    @FXML
    private Button forgotPw;

    @FXML
    private void setForgetMsg() {
        forgotPw.setText("Simply create a new one :)");
    }

    @FXML
    private Label nuhnuhPrompt;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private User checkLogin() throws InterruptedException {
        for (User u : users) {
            if (u.getName().equals(usernameTF.getText()) && u.getPassword().equals(passwordField.getText())) {
                return u;
            }
        }
        Thread t = Thread.currentThread();
        loginButton.setDisable(true);
        nuhnuhPrompt.setVisible(true);
        loginButton.setText("Please wait... 3");
        t.sleep(1000);
        loginButton.setText("Please wait... 2");
        t.sleep(1000);
        loginButton.setText("Please wait... 1");
        t.sleep(1000);
        loginButton.setText("Let's Go!");
        loginButton.setDisable(false);
        nuhnuhPrompt.setVisible(false);
        return null;
    }

    @FXML
    private void login() throws InterruptedException {
        User currentUser = checkLogin();
        if (currentUser != null) {
            //TODO swap scene and pass user
        }
    }

    @FXML
    private Button newUserButton;

    @FXML
    private void goToRegistration() {
        //TODO swap scene to registration.fxml
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserList();
    }

}
