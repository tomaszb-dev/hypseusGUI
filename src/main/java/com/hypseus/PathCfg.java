package com.hypseus;

import com.google.gson.Gson;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PathCfg {
    public String daphnePath;
    public String americanPath;
    public String gSelectionType;

    public String getgSelectionType() {
        return gSelectionType;
    }

    public void setgSelectionType(String gSelectionType) {
        this.gSelectionType = gSelectionType;
    }

    public PathCfg() {
        daphnePath = "";
        americanPath = "";
        gSelectionType="allInOne";
    }

    public String getDaphnePath() {
        return daphnePath;
    }
    public String setDaphnePath(String daphnePath) {
        return this.daphnePath = daphnePath;
    }

    public String getAmericanPath() {
        return americanPath;
    }
    public void setAmericanPath(String alPath) {
        this.americanPath = alPath;
    }

    private void showWriteErrorWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File write error");
        alert.setHeaderText("File write error");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
        ;
    }

    public void readCurrentcfg () {
        Gson dataDeser = new Gson();
        PathCfg pathCfg = new PathCfg();
        try {
            FileReader fileReader = new FileReader("pconfig.cfg");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            pathCfg = dataDeser.fromJson(bufferedReader, PathCfg.class);
            this.americanPath = pathCfg.americanPath;
            this.daphnePath = pathCfg.daphnePath;
            bufferedReader.close();
        } catch (IOException e) {
            showWriteErrorWindow("Error while reading from config file");
        }
    }

}
