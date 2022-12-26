package Main.proj;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxmlLoader = new FXMLLoader((new File("Fxml/MainScreen.fxml")).toURI().toURL()).load();
        Scene scene = new Scene(fxmlLoader);

        primaryStage.setTitle("Discrete Mathematics Project (Hamza Masoud - 120191633)");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(655);
        primaryStage.setMinWidth(1050);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}