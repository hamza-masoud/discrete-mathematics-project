package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.Scanner;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EncryptDecrypt.selectedToggleProperty().addListener((ob, o, n) -> {
            RadioButton rb = (RadioButton)EncryptDecrypt.getSelectedToggle();
            functionType = rb.getText();
        });
    }

    protected void getKey() {
        key = ((UserData)dragDropOrChooseFile.getScene().getWindow().getUserData()).key;
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

    public void submit(ActionEvent event) throws FileNotFoundException {
        String result = null;
        getKey();
        switch (functionType) {
            case "Encrypt" -> result=encrypt();
            case "Decrypt" -> result=decrypt();
        }
        System.out.println(result);
    }

    private String encrypt() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(fileToWork);
        StringBuilder string = new StringBuilder();
        while (fileScanner.hasNextLine())
            string.append(fileScanner.nextLine());

        byte[] bytes = string.toString().getBytes();
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = (byte)((bytes[i] + key) % 256);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    private String decrypt() {
        return null;
    }

}
