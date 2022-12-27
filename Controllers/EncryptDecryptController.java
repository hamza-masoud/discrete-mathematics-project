package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import Main.proj.UserData;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

public class EncryptDecryptController implements Initializable {

//    Nodes to get data from it
    @FXML
    public Label dragDropOrChooseFile;

    @FXML
    ToggleGroup EncryptDecrypt;

    @FXML
    Button submit;

    Long key;

    String functionType;

    File fileToWork;
    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getKey();
        EncryptDecrypt.selectedToggleProperty().addListener((ob, o, n) -> {
            RadioButton rb = (RadioButton)EncryptDecrypt.getSelectedToggle();
            functionType = rb.getText();
        });
    }

    protected void getKey() {
        key = ((UserData)scene.getWindow().getUserData()).key;
    }

    public void acceptDragDrop(DragEvent event) {
        if (event.getDragboard().hasFiles())
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        event.consume();
    }

    public void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        event.setDropCompleted(db.hasFiles());
        event.consume();
        File file = db.getFiles().get(0);
        if (file != null) {
            addFileAsChosen(file);
        }
    }

    public void chooseFileAction(MouseEvent e) {
        FileChooser fileChosen = new FileChooser();
        fileChosen.setTitle("Choose File to encrypt or decrypt");
        File file = fileChosen.showOpenDialog(null);
        if (file != null) {
            addFileAsChosen(file);
        }
    }

    private void addFileAsChosen(File file) {
        ImageView node = new ImageView(new Image("@../../Media/file.png"));
        node.setFitHeight(150);
        node.setFitWidth(150);
        dragDropOrChooseFile.setGraphic(node);
        dragDropOrChooseFile.setText(file.getName());
        fileToWork = file;
        submit.setDisable(false);
    }

    public void submit(ActionEvent event) {

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
