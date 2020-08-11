package slechnert;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class Main extends Application {


    //Initial boot
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(); //instantiating my loader
        loader.setLocation(getClass().getResource("visualizer.fxml")); //loading the first scene
        Parent root = loader.load(); //assigning the first scene from the loader to a variable
        primaryStage.setScene(new Scene(root)); //assigning the variable to the stage
        primaryStage.setTitle("Visualizing");
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
