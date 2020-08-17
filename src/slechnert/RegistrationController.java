package slechnert;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.List;

public class RegistrationController {
    DAO dao;
    List<User> registeredUsers;

    public RegistrationController() {
        dao = new DAO();
        registeredUsers = dao.getAllUsers();
    }

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

    @FXML
    private void checkName() {
        boolean nameFormat;
        Boolean nameAvailable = null;
        if (regUserName.getText().length() > 4 && regUserName.getText().length() < 24) {
            nameFormat = true;
        } else {
            nameFormat = false;
        }
        for (User u : registeredUsers) {
            if (u.getName().equalsIgnoreCase(regUserName.getText())) {
                nameAvailable = false;
            } else {
                nameAvailable = true;
            }
        }
        if (nameFormat && nameAvailable) {
            regNameCross.setVisible(true);
            regNameCheck.setVisible(false);
        } else if (!nameFormat || (!nameAvailable && nameAvailable != null)) {
            regNameCross.setVisible(false);
            regNameCheck.setVisible(true);
        }
    }

    @FXML
    private void checkPass1() {

    }

    @FXML
    private void checkPass2() {
    }

    @FXML
    private void checkEmail() {
    }

    @FXML
    private Button regCreateUserButton;

    @FXML
    private void createUser() {
        //TODO CHECK FOR VALIDITY AND CREATE USER IN DB + PASS TO VIS
    }

    ;


    @FXML
    private void regBackButton() {
        //TODO go to login
    }
}
