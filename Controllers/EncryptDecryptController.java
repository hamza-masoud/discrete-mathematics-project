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
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

    String functionType = "Encrypt";
    String readFileExtension = "txt";
    String writeFileExtension = "encrypted";

    File fileToWork;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EncryptDecrypt.selectedToggleProperty().addListener((ob, o, n) -> {
            RadioButton rb = (RadioButton)EncryptDecrypt.getSelectedToggle();
            functionType = rb.getText();
            if (functionType.equals("Encrypt")) {
                readFileExtension = "txt";
                writeFileExtension = "encrypted";
            } else if (functionType.equals("Decrypt")) {
                readFileExtension = "encrypted";
                writeFileExtension = "txt";
            }
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
        fileChosen.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*."+readFileExtension));

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

    public void submit(ActionEvent event) throws IOException {
        String result = "";
        getKey();
        if (functionType.equals("Encrypt"))
            result = encrypt();
        else if (functionType.equals("Decrypt"))
            result = decrypt();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*." + writeFileExtension));
        File file = fileChooser.showSaveDialog(null);
            FileWriter write = new FileWriter(file);
            write.write(result);
            write.close();
    }

    private String encrypt() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(fileToWork);
        StringBuilder string = new StringBuilder();
        StringBuilder stringResult = new StringBuilder();
        //read file
        while (fileScanner.hasNextLine())
            string.append(fileScanner.nextLine()).append("\n");
        fileScanner.close();
        //encrypt file
        byte[] bytes = string.toString().getBytes();
        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte)((bytes[i] + key) % 256);
            stringResult.append(bytes[i]).append(" ");
        }

        return new String(stringResult.toString().getBytes(), StandardCharsets.UTF_8);
    }

    private String decrypt() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(fileToWork);
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (fileScanner.hasNext())
            arrayList.add(Integer.valueOf(fileScanner.next()));
        fileScanner.close();

        byte[] bytes = new byte[arrayList.size()];
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = (byte)((arrayList.get(i) - key) % 256);

        return new String(bytes, StandardCharsets.UTF_8);
    }

}
