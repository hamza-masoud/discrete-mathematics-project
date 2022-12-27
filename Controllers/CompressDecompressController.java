package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CompressDecompressController implements Initializable {

//    Nodes to get data from it
    @FXML
    public Label dragDropOrChooseFile;

    @FXML
    ToggleGroup CompressDecompress;

    @FXML
    Button submit;

    Long key;

    String functionType = "Compress";
    String readFileExtension = "txt";
    String writeFileExtension = "zip";

    File fileToWork;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CompressDecompress.selectedToggleProperty().addListener((ob, o, n) -> {
            RadioButton rb = (RadioButton)CompressDecompress.getSelectedToggle();
            functionType = rb.getText();
            if (functionType.equals("Compress")) {
                readFileExtension = "txt";
                writeFileExtension = "zip";
            } else if (functionType.equals("Decompress")) {
                readFileExtension = "zip";
                writeFileExtension = "txt";
            }
        });
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

    public void submit(ActionEvent e) throws IOException {
        String result = "";
        if (functionType.equals("Compress"))
            result = compress();
        else if (functionType.equals("Decompress"))
            result = decompress();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*." + writeFileExtension));
        File file = fileChooser.showSaveDialog(null);
        FileWriter write = new FileWriter(file);
        write.write(result);
        write.close();
    }

    private String compress() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(fileToWork);
        StringBuilder string = new StringBuilder();
        //read file
        while (fileScanner.hasNextLine())
            string.append(fileScanner.nextLine()).append('\n');
        fileScanner.close();

        return compress(string);
    }

    private String decompress() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(fileToWork);
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (fileScanner.hasNext())
            arrayList.add(Integer.valueOf(fileScanner.next()));
        fileScanner.close();

        return decompress(arrayList);
    }


    public static String compress(StringBuilder string) {
        Map<String, Integer> pairChars = new HashMap<>();
        for (int i = 0; i < 256; i++)
            pairChars.put(String.valueOf((char) i), i);

        String lastChar = "";
        StringBuilder result = new StringBuilder();
        for (char nChar : string.toString().toCharArray()) {
            String sumChar = lastChar + nChar;
            if (pairChars.containsKey(sumChar))
                lastChar = sumChar;
            else {
                result.append(pairChars.get(lastChar)).append(' ');
                pairChars.put(sumChar, pairChars.size());
                lastChar = String.valueOf(nChar);
            }
        }
        if (!lastChar.isEmpty())
            result.append(pairChars.get(lastChar)).append(' ');

        return result.toString();
    }

    public static String decompress(List<Integer> encodedMessage) {
        Map<Integer, String> pairChar = new HashMap<>();
        for (int i = 0; i < 256; i++)
            pairChar.put(i, String.valueOf((char) i));

        String characters = String.valueOf(encodedMessage.remove(0));
        StringBuilder result = new StringBuilder(characters);
        for (int value : encodedMessage) {
            String sumChar = "";
            if (pairChar.containsKey(value))
                sumChar = pairChar.get(value);
            else
                sumChar = characters + characters.charAt(0);
            result.append(sumChar);
            pairChar.put(pairChar.size(), characters + sumChar.charAt(0));
            characters = sumChar;
        }
        return result.toString();
    }

}
