package slechnert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button forgotPw;

    @FXML
    private void setForgetMsg() {
        forgotPw.setText("Just make a new one, duh");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
