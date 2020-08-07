package slechnert;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("visualizer.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
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
