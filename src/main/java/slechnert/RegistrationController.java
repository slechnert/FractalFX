//package slechnert;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.image.ImageView;
//
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class RegistrationController {
//    DAO dao;
//    List<User> registeredUsers;
//
//    public RegistrationController() {
//        dao = new DAO();
//        registeredUsers = dao.getAllUsers();
//    }
//
//    @FXML
//    private Label prompt;
//    @FXML
//    private TextField regUserName;
//    @FXML
//    private PasswordField regPass1;
//    @FXML
//    private PasswordField regPass2;
//    @FXML
//    private TextField regEmail;
//    @FXML
//    private ImageView regNameCheck;
//    @FXML
//    private ImageView regNameCross;
//    @FXML
//    private ImageView regPassCheck1;
//    @FXML
//    private ImageView regPassCross1;
//    @FXML
//    private ImageView regPassCheck2;
//    @FXML
//    private ImageView regPassCross2;
//    @FXML
//    private ImageView regEmailCheck;
//    @FXML
//    private ImageView regEmailCross;
//
//    @FXML
//    private void checkName() {
//        boolean nameFormat;
//        Boolean nameAvailable = null;
//        if (regUserName.getText().length() > 4 && regUserName.getText().length() < 24) {
//            nameFormat = true;
//        } else {
//            nameFormat = false;
//        }
//        for (User u : registeredUsers) {
//            if (u.getName().equalsIgnoreCase(regUserName.getText())) {
//                nameAvailable = false;
//                break;
//            } else {
//                nameAvailable = true;
//            }
//        }
//        if (nameFormat && nameAvailable) {
//            regNameCross.setVisible(true);
//            regNameCheck.setVisible(false);
//        } else if (!nameFormat || (!nameAvailable && nameAvailable != null)) {
//            regNameCross.setVisible(false);
//            regNameCheck.setVisible(true);
//        }
//        if (!nameAvailable) {
//            prompt.setText("Username taken!");
//            prompt.setVisible(true);
//        } else if (nameAvailable && !nameFormat) {
//            prompt.setText("Invalid Username Length!");
//            prompt.setVisible(true);
//        } else if (nameAvailable && nameFormat) {
//            prompt.setVisible(false);
//        }
//    }
//
//    @FXML
//    private void checkPass1() {
//        boolean passwordLength;
//        if (regPass1.getText().length() >= 4 && regPass1.getText().length() <= 32) {
//            passwordLength = true;
//        } else {
//            passwordLength = false;
//        }
//        if (passwordLength) {
//            regPassCheck1.setVisible(true);
//            regPassCross1.setVisible(false);
//            if (prompt.getText().equals("Invalid PW Length!")) {
//                prompt.setVisible(false);
//            }
//        } else {
//            regPassCross1.setVisible(true);
//            regPassCheck1.setVisible(false);
//            prompt.setText("Invalid PW Length!");
//            prompt.setVisible(true);
//        }
//    }
//
//    @FXML
//    private void checkPass2() {
//        if(regPassCheck1.isVisible() && regPass1.getText().equals(regPass2.getText())){
//            regPassCheck2.setVisible(true);
//            regPassCross2.setVisible(false);
//            prompt.setVisible(false);
//        } else if(regPassCross1.isVisible()){
//            prompt.setText("Check original PW!");
//            prompt.setVisible(true);
//            regPassCross2.setVisible(true);
//            regPassCheck2.setVisible(false);
//        } else if(!regPass1.getText().equals(regPass2.getText())){
//            prompt.setText("Passwords don't match!");
//            prompt.setVisible(true);
//            regPassCross2.setVisible(true);
//            regPassCheck2.setVisible(false);
//        }
//    }
//
//    @FXML
//    private void checkEmail() {
//        boolean emailFormat;
//        Boolean emailAvailable = null;
//        if (checkEmailString(regEmail.getText())) {
//            emailFormat = true;
//        } else {
//            emailFormat = false;
//        }
//        for (User u : registeredUsers) {
//            if (regEmail.getText().equalsIgnoreCase(u.getEmail())) {
//                emailAvailable = false;
//                break;
//            } else {
//                emailAvailable = true;
//            }
//        }
//        if (!emailFormat) {
//            prompt.setText("Invalid Email Format");
//            prompt.setVisible(true);
//            regEmailCross.setVisible(true);
//            regEmailCheck.setVisible(false);
//        } else if (emailFormat && !emailAvailable && emailAvailable != null) {
//            prompt.setText("Email already registered!");
//            prompt.setVisible(true);
//            regEmailCross.setVisible(true);
//            regEmailCheck.setVisible(false);
//        } else if (emailFormat && emailAvailable) {
//            prompt.setVisible(false);
//            regEmailCheck.setVisible(true);
//            regEmailCross.setVisible(false);
//        }
//    }
//
//    @FXML
//    private Button regCreateUserButton;
//
//    @FXML
//    private void createUser() {
//        if(regNameCheck.isVisible() && regPassCheck2.isVisible() && regEmailCheck.isVisible()){
//            dao.createUser(new User(regUserName.getText(), regPass2.getText(), regEmail.getText()));
//        } else {
//            prompt.setText("Please check your details!");
//            prompt.setVisible(true);
//        }
//        //TODO CHECK FOR VALIDITY AND CREATE USER IN DB + PASS TO VIS
//    }
//
//    @FXML
//    private void regBackButton() {
//        //TODO go to login
//    }
//
//    public static boolean checkEmailString(String emailToCheck) {
//        Pattern pattern = Pattern.compile("^[a-zA-z0-9._]{5,}@[[a-zA-z0-9.]&&[^_]]+\\.[a-z]{2,6}$");
//        Matcher matcher = pattern.matcher(emailToCheck);
//        if (emailToCheck == "") {
//            throw new IllegalArgumentException("Good luck trying to find valid emails in an empty string");
//        } else {
//            return matcher.lookingAt();
//        }
//    }
//
//}
