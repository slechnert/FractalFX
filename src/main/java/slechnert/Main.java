package slechnert;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    Stage daStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController mc = new MainController();
        daStage = primaryStage;
        stageRefresh(mc.getVisScene());
//login startup
//        FXMLLoader loader = new FXMLLoader(); //instantiating my loader
//        loader.setLocation(getClass().getResource("login.fxml")); //loading the first scene
//        Parent root = loader.load(); //assigning the first scene from the loader to a variable
//        primaryStage.setScene(new Scene(root)); //assigning the variable to the stage
//        root.getStylesheets().add(getClass().getResource("Font.css").toExternalForm());
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        primaryStage.show();
    }

    @Override
    public void init() {
        System.out.println("App starting...");
    }

    @Override
    public void stop() {
        System.out.println("App closed.");
    }

    public void stageRefresh(Scene newScene) {
        daStage.setScene(newScene);
        daStage.initStyle(StageStyle.UNDECORATED);
        daStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
