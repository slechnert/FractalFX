package slechnert;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {


//login startup
        FXMLLoader loader = new FXMLLoader(); //instantiating my loader
        loader.setLocation(getClass().getResource("login.fxml")); //loading the first scene
//        loader.setLocation(getClass().getResource("visualizer.fxml")); //loading the first scene
        Parent root = loader.load(); //assigning the first scene from the loader to a variable
        primaryStage.setScene(new Scene(root)); //assigning the variable to the stage
        root.getStylesheets().add(getClass().getResource("Font.css").toExternalForm());
//        primaryStage.initStyle(StageStyle.UNDECORATED);
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
