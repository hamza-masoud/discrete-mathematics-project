package Controllers;

import Main.proj.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class GenerateKey implements Initializable {

    public TextField KeyInput;

    public Button NextBtn;

    Long key;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        KeyInput.textProperty().addListener((observable, oldValue, newValue) -> {
            // make next btn clickable after check the input not empty
            NextBtn.setDisable(Objects.equals(newValue, ""));
            // remove all non-digits from the new string
            KeyInput.setText(newValue.replaceAll("\\D", ""));
        });
    }
    public long generateKey() {
        return Math.abs(new Random().nextLong());
    }

    public void GenerateKeyAction(ActionEvent event) {
        this.KeyInput.setText(String.valueOf(this.generateKey()));
    }

    public void stepTwo(ActionEvent event) throws Exception {
        key = Long.parseLong(KeyInput.textProperty().get());
        AnchorPane result_screen = (AnchorPane) ((Button)event.getSource()).getParent();

        FXMLLoader EncryptDecryptWidget = new FXMLLoader((new File("Fxml/EncryptDecrypt/EncryptDecryptWidget.fxml")).toURI().toURL());
        EncryptDecryptController controller = EncryptDecryptWidget.getController();
        controller.setScene(result_screen.getScene());
        result_screen.getChildren().removeAll(result_screen.getChildren().toArray(new Node[]{}));
        result_screen.getChildren().addAll(((AnchorPane)EncryptDecryptWidget.load()).getChildrenUnmodifiable());
        UserData new_data = new UserData();
        new_data.key = this.key;
        result_screen.getScene().getWindow().setUserData(new_data);
    }

}
