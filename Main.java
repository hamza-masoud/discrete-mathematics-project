import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxmlLoader = new FXMLLoader((new File("Fxml/MainScreen.fxml")).toURI().toURL()).load();
        Scene scene = new Scene(fxmlLoader, 600, 400);

        primaryStage.setTitle("Discrete Mathematics Project (Hamza Masoud - 120191633)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}