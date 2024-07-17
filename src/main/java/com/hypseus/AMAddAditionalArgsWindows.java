package com.hypseus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

import javax.sound.sampled.Clip;

public class AMAddAditionalArgsWindows implements Initializable {

    public String gameName;
    private Stage previousStage;
    private Stage currentStage;

    @FXML
    TextField AMText = new TextField();
    @FXML
    Button AMSave = new Button();
    @FXML
    Button AMCancel = new Button();

    public void setStages(Stage previousStage, Stage currentStage) {
        this.previousStage = previousStage;
        this.currentStage = currentStage;
        currentStage.setOnCloseRequest(this::handleWindowClose);
    }

    private void handleWindowClose(WindowEvent event) {
        if (previousStage != null) {
            previousStage.show();
        }
    }

    @FXML
    public void AMAddArgsSaveClicked(MouseEvent mouseEvent) {
        String argsFile = gameName;
        // File file = new File(argsFile);
        try {
            BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(argsFile));
            bw.write(AMText.getText());
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentStage.close();
    }

    @FXML
    public void AMAddArgsCancelClicked(MouseEvent mouseEvent) {
        if (previousStage != null) {
            previousStage.show();
        }
        currentStage.close();
    }

    private void readArgs() {
        String argsFile = gameName;
        System.out.println("--gameName--> " + gameName);
        System.out.println("----> " + argsFile);
        File file = new File(argsFile);
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new java.io.FileReader(argsFile));
                String line = br.readLine();
                if (line != null) {
                    AMText.setText(line);
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void readClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        gameName = clipboard.getString();
        System.out.println("game --> " + gameName);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        readClipboard();
        readArgs();

    }
}