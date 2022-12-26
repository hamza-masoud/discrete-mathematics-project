package Controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import Main.proj.UserData;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

public class EncryptDecryptController implements Initializable {

//    Nodes to get data from it
    public Label dragDropOrChooseFile;

    Long key;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        getKey();
        Dragboard db = event.getDragboard();
        event.setDropCompleted(db.hasFiles());
        event.consume();

//        todo get the file and to encrypt or decrypt
        File file = db.getFiles().get(0);
        System.out.println(file.getPath());
    }

    public void chooseFileAction(MouseEvent e) throws FileNotFoundException {
        FileChooser file = new FileChooser();
        file.setTitle("Choose File to encrypt or decrypt");
        File g = file.showOpenDialog(null);
        Scanner scan = new Scanner(g);
        StringBuilder strB = new StringBuilder();
        while (scan.hasNextLine()) {
            strB.append(scan.nextLine());
        }
        //        todo get the file and to encrypt or decrypt
    }

}
