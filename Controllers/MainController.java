package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.File;

public class MainController {
    public Pane result_screen;
    public void launchCalendar(ActionEvent event) throws Exception {
        Parent CalendarWidget = new FXMLLoader((new File("Fxml/CalendarWidget.fxml")).toURI().toURL()).load();
        result_screen.getChildren().removeAll(result_screen.getChildren().toArray(new Node[]{}));
        result_screen.getChildren().addAll(CalendarWidget.getChildrenUnmodifiable());
    }

    public void launchEncryptDecrypt(ActionEvent event) throws Exception {
        Parent EncryptDecryptWidget = new FXMLLoader((new File("Fxml/EncryptDecrypt/KeyGenerateWidget.fxml")).toURI().toURL()).load();
        result_screen.getChildren().removeAll(result_screen.getChildren().toArray(new Node[]{}));
        result_screen.getChildren().addAll(EncryptDecryptWidget.getChildrenUnmodifiable());
    }

    public void launchCompressDecompress(ActionEvent event) throws Exception {
        Parent CompressDecompressWidget = new FXMLLoader((new File("Fxml/CompressDecompressWidget.fxml")).toURI().toURL()).load();
        result_screen.getChildren().removeAll(result_screen.getChildren().toArray(new Node[]{}));
        result_screen.getChildren().addAll(CompressDecompressWidget.getChildrenUnmodifiable());
    }
}
