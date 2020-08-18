package slechnert;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    //    private User currentUser;
    FXMLLoader login = new FXMLLoader();
    FXMLLoader register = new FXMLLoader();
    FXMLLoader visualizer = new FXMLLoader();
    Scene visScene;

    public MainController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visualizer.setLocation(getClass().getResource("visualizer.fxml")); //loading the first scene
        Parent vis = null; //assigning the first scene from the loader to a variable
        try {
            vis = visualizer.load();
        } catch (IOException e) {
            System.out.println("Failed loading login");
            e.printStackTrace();
        }
        vis.getStylesheets().add(getClass().getResource("Font.css").toExternalForm());
        visScene = new Scene(vis);
    }

//login startup
//        FXMLLoader loader = new FXMLLoader(); //instantiating my loader
//        loader.setLocation(getClass().getResource("login.fxml")); //loading the first scene
//        Parent root = loader.load(); //assigning the first scene from the loader to a variable
//        primaryStage.setScene(new Scene(root)); //assigning the variable to the stage
//        root.getStylesheets().add(getClass().getResource("Font.css").toExternalForm());
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        primaryStage.show();


    public Scene getVisScene() {
        visualizer.setLocation(getClass().getResource("visualizer.fxml")); //loading the first scene
        Parent vis = null; //assigning the first scene from the loader to a variable
        try {
            vis = visualizer.load();
        } catch (IOException e) {
            System.out.println("Failed loading login");
            e.printStackTrace();
        }
        vis.getStylesheets().add(getClass().getResource("Font.css").toExternalForm());
        visScene = new Scene(vis);
        return visScene;
    }


    public FXMLLoader getLogin() {
        return login;
    }

    public void setLogin(FXMLLoader login) {
        this.login = login;
    }

    public FXMLLoader getRegister() {
        return register;
    }

    public void setRegister(FXMLLoader register) {
        this.register = register;
    }

    public FXMLLoader getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(FXMLLoader visualizer) {
        this.visualizer = visualizer;
    }
}
