package slechnert;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationController implements Initializable {
    DAO dao;
    List<User> registeredUsers;

    public RegistrationController() {
    }

    @FXML
    public Button close;

    @FXML
    public void stop() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Label header;
    @FXML
    private Label prompt;
    @FXML
    private TextField regUserName;
    @FXML
    private PasswordField regPass1;
    @FXML
    private PasswordField regPass2;
    @FXML
    private TextField regEmail;
    @FXML
    private ImageView regNameCheck;
    @FXML
    private ImageView regNameCross;
    @FXML
    private ImageView regPassCheck1;
    @FXML
    private ImageView regPassCross1;
    @FXML
    private ImageView regPassCheck2;
    @FXML
    private ImageView regPassCross2;
    @FXML
    private ImageView regEmailCheck;
    @FXML
    private ImageView regEmailCross;

    private void initializeFontColor() {
        regUserName.setStyle("-fx-text-inner-color: darkgray; -fx-background-color:  #1c1c1c");
        regPass1.setStyle("-fx-text-inner-color: darkgray; -fx-background-color:  #1c1c1c");
        regPass2.setStyle("-fx-text-inner-color: darkgray; -fx-background-color:  #1c1c1c");
        regEmail.setStyle("-fx-text-inner-color: darkgray; -fx-background-color:  #1c1c1c");
    }

    @FXML
    private void switchLabels() {
        if (header.isVisible() && !prompt.isVisible()) {
            header.setVisible(false);
            prompt.setVisible(true);
        } else {
            header.setVisible(true);
            prompt.setVisible(false);
        }
    }


    @FXML
    private boolean checkName() {
        boolean nameFormat = false;
        boolean nameAvailable = false;
        if (regUserName.getText().length() >= 4 && regUserName.getText().length() < 24) {
            nameFormat = true;
        }
        for (User u : registeredUsers) {
            if (u.getName().equalsIgnoreCase(regUserName.getText())) {
                nameAvailable = false;
                break;
            } else {
                nameAvailable = true;
            }
        }
        if (!nameFormat || !nameAvailable) {
            regNameCross.setVisible(true);
            regNameCheck.setVisible(false);

            if (!nameFormat) {
                prompt.setText("Invalid Length!");
                if (!prompt.isVisible()) {
                    switchLabels();
                }
            } else {
                prompt.setText("Username taken!");
                if (!prompt.isVisible()) {
                    switchLabels();
                }
            }
            return false;
        }
        regNameCross.setVisible(false);
        regNameCheck.setVisible(true);
        if (prompt.isVisible()) {
            switchLabels();
        }
        return true;
    }

    @FXML
    private boolean checkPass1() {
        boolean passwordLength = false;
        if (regPass1.getText().length() >= 4 && regPass1.getText().length() <= 32) {
            passwordLength = true;
        }
        if (passwordLength) {
            regPassCheck1.setVisible(true);
            regPassCross1.setVisible(false);
            if (prompt.getText().equals("Invalid Length!") && prompt.isVisible()) {
                switchLabels();
            }
            return true;
        } else {
            regPassCross1.setVisible(true);
            regPassCheck1.setVisible(false);
            prompt.setText("Invalid Length!");
            if (!prompt.isVisible()) {
                switchLabels();
            }
            return false;
        }
    }

    @FXML
    private boolean checkPass2() {
        if (checkPass1() && regPass1.getText().equals(regPass2.getText())) {
            regPassCheck2.setVisible(true);
            regPassCross2.setVisible(false);
            if (prompt.isVisible()) {
                switchLabels();
            }
            return true;
        } else if (!checkPass1()) {
            regPassCross2.setVisible(true);
            regPassCheck2.setVisible(false);
            prompt.setText("Check first PW!");
            if (!prompt.isVisible()) {
                switchLabels();
            }
        } else if (!regPass1.getText().equals(regPass2.getText())) {
            regPassCross2.setVisible(true);
            regPassCheck2.setVisible(false);
            prompt.setText("No PW Match!");
            if (!prompt.isVisible()) {
                switchLabels();
            }
        }
        return false;
    }

    @FXML
    private boolean checkEmail() {
        boolean emailFormat;
        boolean emailAvailable = false;
        if (checkEmailString(regEmail.getText())) {
            emailFormat = true;
        } else {
            emailFormat = false;
        }
        for (User u : registeredUsers) {
            if (regEmail.getText().equalsIgnoreCase(u.getEmail())) {
                break;
            } else {
                emailAvailable = true;
            }
        }
        if (!emailFormat) {
            prompt.setText("Invalid Format");
            regEmailCross.setVisible(true);
            regEmailCheck.setVisible(false);
            if (!prompt.isVisible()) {
                switchLabels();
            }
            return false;
        } else if (!emailAvailable) {
            prompt.setText("Email taken");
            regEmailCross.setVisible(true);
            regEmailCheck.setVisible(false);
            if (!prompt.isVisible()) {
                switchLabels();
            }
            return false;
        } else {
            regEmailCheck.setVisible(true);
            regEmailCross.setVisible(false);
            if (prompt.isVisible()) {
                switchLabels();
            }
            return true;
        }
    }

    public static boolean checkEmailString(String emailToCheck) {
        Pattern pattern = Pattern.compile("^[a-zA-z0-9._]{4,}@[[a-zA-z0-9.]&&[^_]]+\\.[a-z]{2,6}$");
        Matcher matcher = pattern.matcher(emailToCheck);
        if (emailToCheck == "") {
            throw new IllegalArgumentException("Good luck trying to find valid emails in an empty string");
        }
        return matcher.lookingAt();
    }

    @FXML
    private Button regCreateUserButton;

    @FXML
    private void createUser() {
        if (checkName() && checkPass2() && checkEmail()) {
            dao.createUser(new User(regUserName.getText(), regPass2.getText(), regEmail.getText()));
            prompt.setText("Success!");
            prompt.setTextFill(Paint.valueOf("#54CC70"));
            if (!prompt.isVisible()) {
                switchLabels();
            }
            regCreateUserButton.setDisable(true);
            regBackButton.styleProperty();
            String bstyle = String.format("-fx-text-inner-color: %s; -fx-font-style: %s; -fx-background-color: %s;", "bold", "#54CC70");
            regBackButton.setStyle(bstyle);
        } else {
            prompt.setText("Please check your details!");
            prompt.setVisible(true);
        }
    }

    @FXML
    private Button regBackButton;

    @FXML
    private void toLogin() throws IOException {
        Parent logParent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene logScene = new Scene(logParent);
        Stage window = (Stage) (regBackButton.getScene().getWindow());
        window.hide();
        window.setScene(logScene);
        window.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = new DAO();
        registeredUsers = dao.getAllUsers();
        initializeFontColor();

    }
}
