package slechnert;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;


public class Main extends Application {

public boolean debug = true;
public User user;
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {


//login startup
        FXMLLoader loader = new FXMLLoader(); //instantiating my loader
        Parent root;
        if(!debug) {
            loader.setLocation(getClass().getResource("login.fxml")); // start with login
            root = loader.load(); //assigning the first scene from the loader to a variable
        } else {
            loader.setLocation(getClass().getResource("visualizer.fxml")); // cut to visualizer
            DAO dao = new DAO();
            user = dao.getUser("simon");
            dao.fillUserCustomSets(user);
            root = loader.load(); //assigning the first scene from the loader to a variable
            ControllerVisualizer cv = loader.getController();
            cv.initData(user);
        }
        //loading the first scene
        primaryStage.setScene(new Scene(root)); //assigning the variable to the stage
        root.getStylesheets().add(getClass().getResource("Font.css").toExternalForm());
        primaryStage.getScene().setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    @Override
    public void init() {
        System.out.println("App starting...");
    }

    @Override
    public void stop() {
        System.out.println("App closed.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
