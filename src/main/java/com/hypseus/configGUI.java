package com.hypseus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.transformation.TransformationList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.*;
import java.net.URL;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.layout.GridPane;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.OperatingSystem;


public class configGUI implements Initializable {

    public ChoiceBox multiSingleGames;
    OperatingSystem os;
    String gfx;
    String display;
    String AMbezelFile;
    private Button openArgsButton;

    @FXML
    private Tab configTab = new Tab();
    @FXML
    private Tab hypseusConfigTab = new Tab();
    @FXML
    private Tab AMConfigTab = new Tab();
    @FXML
    private AnchorPane hypseusConfigAnchor = new AnchorPane();
    @FXML
    private Tab daphneConfigTab = new Tab();
    @FXML
    private GridPane daphenConfigGridPane = new GridPane();
    @FXML
    private Button AMSearchGames = new Button();
    @FXML
    private Button daphnePathSelection = new Button();
    @FXML
    private Button AMPathSelection = new Button();
    @FXML
    private Button saveALGconf = new Button();
    @FXML
    private Button daphneGameSelect = new Button();
    @FXML
    private Button AMGameSelection = new Button();
    @FXML
    private AnchorPane AMAnchorPane = new AnchorPane();
    @FXML
    private AnchorPane daphneAnchorPane = new AnchorPane();
    @FXML
    private GridPane AMGridPane = new GridPane();
    @FXML
    private TextField AMGamesSelectiontxt = new TextField();
    @FXML
    private TextField daphneSelectiontxt = new TextField();
    @FXML
    private TextField daphneGamesSelectiontxt = new TextField();
    PathCfg pathCfg;
    @FXML
    private ListView configAmList = new ListView();
    @FXML
    Button folderConf = new Button("Folder Config");
    @FXML
    Button searchGames = new Button("Search Games");
    @FXML
    CheckBox jsrange = new CheckBox();
    @FXML
    Spinner jsrangeFill = new Spinner();
    @FXML
    Spinner sindenFill1 = new Spinner();
    @FXML
    ComboBox sindenFill2 = new ComboBox();
    @FXML
    CheckBox sinden = new CheckBox();
    @FXML
    TextArea systemInfo = new TextArea();
    @FXML
    TextArea displayInfo = new TextArea();
    @FXML
    Button binaryPath = new Button();
    @FXML
    TextField pathTobinary = new TextField();
    @FXML
    Button launchBinary = new Button();
    @FXML
    TextField attributeLine = new TextField();
    @FXML
    Button saveTempAttrib = new Button();
    @FXML
    TextField AMGamesFound = new TextField();
    @FXML
    CheckBox fullScreen = new CheckBox();
    @FXML
    CheckBox forceAspect = new CheckBox();
    @FXML
    CheckBox ignoreAspectRatio = new CheckBox();
    @FXML
    RadioButton openglSelectAm = new RadioButton();
    @FXML
    RadioButton vulkanSelectAm = new RadioButton();
    @FXML
    GridPane AMGridePane = new GridPane();
    @FXML
    Slider AMScaleFactorSlider = new Slider();
    @FXML
    CheckBox AMscaleFactor = new CheckBox();
    @FXML
    Label AMScaleFSliderView = new Label();
    @FXML
    Slider AMscaleFactorSlider = new Slider();
    @FXML
    Label AMScanASliderView = new Label();
    @FXML
    Slider AMScanLAlphaSilder = new Slider();
    @FXML
    CheckBox AMscaleAlpha = new CheckBox();
    @FXML
    Button AMpngBezel = new Button();
    @FXML
    CheckBox AMbezelCheckBox = new CheckBox();
    @FXML
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);

    @FXML
    public void DaphnePathSelect(ActionEvent event) {

        final String[] pliki = new String[2];
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        File selectedFile = directoryChooser.showDialog(dialog);
        if (selectedFile != null) {
            pliki[0] = selectedFile.toString();
            pliki[1] = "daphne";
            dialog.close();
            pathCfg = new PathCfg();
            pathCfg.setAmericanPath(selectedFile.toString());
            dataSerialize("pconfig.cfg");
            daphneGamesSelectiontxt.setText(selectedFile.toString());
        } else {
            System.out.println("No selection");
        }
    }

    public void AMPathSelect(ActionEvent event) {
        final String[] pliki = new String[2];
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        File selectedFile = directoryChooser.showDialog(dialog);
        if (selectedFile != null) {
            pliki[0] = selectedFile.toString();
            pliki[1] = "american";
            dialog.close();
            List<String> AMFoundGames = checkAMGameFolders(selectedFile);
            int count = AMFoundGames.size();
            if (count > 0) {
                saveAmList(AMFoundGames);
                pathCfg = new PathCfg();
                pathCfg.setAmericanPath(selectedFile.toString());
                dataSerialize("pconfig.cfg");
                AMGamesSelectiontxt.setText(selectedFile.toString());
                AMGamesFound.setText(count + " game(s) found");
            } else {
                URL css = getClass().getResource("/hypseus/com/alert1.css");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().getStylesheets().add(css.toString());
                alert.setTitle(null);
                alert.setHeaderText("Wrong folder");
                alert.setContentText("Check your folder. No games found...");
                alert.showAndWait();
            }
        } else {
            System.out.println("No selection");
        }
    }


    public List<String> checkAMGameFolders(File fileCheck) {
        int count = 0;
        List<String> amFoundGames = new ArrayList<>();
        for (File file : fileCheck.listFiles())
            if (file.isDirectory() && file.getName().endsWith(".singe")) {
                int found = 0;
                for (File folderFile : file.listFiles()) {
                    if (folderFile.getName().endsWith(".cfg"))
                        found++;
                    if (folderFile.getName().contains("service.singe"))
                        found++;
                    if (folderFile.getName().contains(file.getName())) {
                        updateSingeFileName(folderFile, file.getAbsolutePath());
                    }

                }
                if (found == 2) {
                    count++;
                    amFoundGames.add(file.getName());
                }
            }

        return amFoundGames;
    }

    private void updateSingeFileName(File folderFile, String pathName) {
        File tempFile = new File(folderFile.getAbsolutePath() + ".new");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(folderFile));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.startsWith("MYDIR")) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                } else {
                    String systemInfo = System.getProperty("os.name");
                    if (systemInfo.contains("Windows")) {
                        pathName = pathName.replace("\\", "/");
                        bufferedWriter.write("MYDIR = " + "\"" + pathName + "/" + "\"");
                        //bufferedWriter.write("MYDIR = " + pathName);
                        bufferedWriter.newLine();
                    } else {
                        pathName = pathName.replace("\\", "/");
                        bufferedWriter.write("MYDIR = " + "\"" + pathName + "/" + "\"");
                        bufferedWriter.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Alert for game.singe file update: " + e.getMessage());
            return;
        }

        // Attempt to delete the original file and rename the new file to the original file's name
        if (folderFile.delete()) {
            if (!tempFile.renameTo(folderFile)) {
                System.err.println("Failed to rename the new file to the original file name.");
                tempFile.delete();
            }
        } else {
            System.err.println("Failed to delete the original file.");
            tempFile.delete();
        }
    }

    @FXML
/*    protected void setsearchGames() {
        List<String> games = new ArrayList<>();
        dataDeSerialize("pconfig.cfg");
        String daphnePath = pathCfg.getDaphnePath();
        String americanPath = pathCfg.getAmericanPath();
        File daphneDir = new File(daphnePath);
        File americanDir = new File(americanPath);
        if (americanDir.exists()) {
            if (americanDir.isDirectory()) {
                for (File file : americanDir.listFiles()) {
                    if (file.isDirectory()) {
                        for (File fileGame : file.listFiles()) {
                            if (fileGame.getName().equals(file.getName())) {
                                String gameString = fileGame.getName();
                                int position = gameString.lastIndexOf(".");
                                gameString = gameString.substring(0, position) + ".txt";
                                File gameTxt = new File(fileGame.getParent() + "\\" + gameString);
                                if (gameTxt.exists()) {
                                    games.add(gameString);
                                    configAmList.getItems().add(gameString);
                                }

                            }
                        }
                    }
                }
                int count = games.size();
                String plur = "";
                if (count == 1)
                    plur = "Game";
                else
                    plur = "Games";

                showMessageWindow("There is " + count + " " + "AmericanLaser" + plur);
                saveAmList(games);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You have to set paths to Daphne and American Laser Games folders", ButtonType.OK);
                alert.getDialogPane().getStylesheets().add("com/hypseus/alert.css");
                alert.showAndWait();
            }
        }
    }
*/
    private void showMessageWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add("com/hypseus/alert.css");
        alert.setTitle("Message");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
        ;
    }

    private void saveAmList(List<String> games) {
        try {
            FileWriter fileWriter = new FileWriter("amlist.lst");
            for (String game : games) {
                String name = game.substring(0, game.lastIndexOf("."));
                try {
                    fileWriter.write(name + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showSystemInfo(String gameInfo) {
        try {
            systemInfo.setText(gameInfo);
        } catch (Exception ex) {
            systemInfo.setText("No system info available");
        }
    }

    private void showDisplayInfo(String gfx) {
        String input = gfx;
        String name = "";
        String deviceId = "";
        String vRam = "";
        String driverVersion = "";
        System.out.println(deviceId + ", " + vRam + ", " + driverVersion);

        try {
            displayInfo.setText(input + "\n" + vRam + "\n" + driverVersion);
        } catch (Exception ex) {
            displayInfo.setText("No display info available");
        }
    }

    @FXML
    private void showWriteErrorWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add("com/hypseus/alert.css");
        alert.setTitle("File write error");
        alert.setHeaderText("File write error");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
        ;
    }


    private void dataSerialize(String configFileName) {
        // Gson dataser = new Gson();
        Gson dataser = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(configFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataser.toJson(pathCfg));
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            showWriteErrorWindow("Error while writing to config file");
        }

    }

    private void dataDeSerialize(String configFileName) {
        Gson dataDeser = new Gson();
        try {
            FileReader fileReader = new FileReader(configFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            pathCfg = dataDeser.fromJson(bufferedReader, PathCfg.class);
            bufferedReader.close();
        } catch (IOException e) {
            showWriteErrorWindow("Error while reading from config file");
        }
    }

    public void selectedJsRange(MouseEvent mouseEvent) {
        if (jsrange.isSelected())
            jsrangeFill.setDisable(false);
        else
            jsrangeFill.setDisable(true);
    }

    public void selectedSinden(MouseEvent mouseEvent) {
        if (sinden.isSelected()) {
            sindenFill1.setDisable(false);
            sindenFill2.setDisable(false);
        } else {
            sindenFill1.setDisable(true);
            sindenFill2.setDisable(true);
        }
    }

    // one config for all games
    public void saveALGconf(ActionEvent actionEvent) {
        String sysModeWindows = "hypseus.exe";
        String options = sysModeWindows + " ";
        if (openglSelectAm.isSelected() == true)
            options += " -opengl";
        if (vulkanSelectAm.isSelected() == true)
            options += " -vulkan";
        if (forceAspect.isSelected())
            options += " -force_aspect_ratio";
        if (ignoreAspectRatio.isSelected())
            options += " -ignore_aspect_ratio";
        if (jsrange.isSelected())
            options += "-jsrange " + jsrangeFill.getValue().toString();
        if (sinden.isSelected()) {
            options += " -siden " + sindenFill1.getValue().toString();
            String color = sindenFill2.getSelectionModel().getSelectedItem().toString();
            char c = ' ';
            switch (color) {
                case "white":
                    c = 'w';
                    break;
                case "green":
                    c = 'g';
                    break;
                case "red":
                    c = 'r';
                    break;
                case "blue":
                    c = 'b';
                    break;
                default:
                    c = 'x';
                    break;
            }
            options += " " + c;
        }

        System.out.println(options);
    }

    public void setBinaryPath(ActionEvent actionEvent) {
        String plik = "";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        File selectedFile = fileChooser.showOpenDialog(dialog);
        if (selectedFile != null) {
            plik = selectedFile.toString();
            dialog.close();
            pathTobinary.setText(selectedFile.toString());
            try {
                FileWriter pathfile = new FileWriter("path.cfg");
                pathfile.write(plik);
                pathfile.close();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add("com/hypseus/alert.css");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        } else {
            System.out.println("No selection");
        }
    }

    private void readConfig() {
        try {
            File file = new File("path.cfg");
            if (file.exists()) {
                FileReader readConf = new FileReader("path.cfg");
                BufferedReader buffread = new BufferedReader(readConf);
                String path = buffread.readLine();
                pathTobinary.setText(path);
                buffread.close();
                readConf.close();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    private void runOnWindows(String path, String attributes) {
        String[] pathSplit = path.split("\\\\");
        path = pathSplit[0];
        for (int i = 1; i < pathSplit.length - 1; i++) {
            path = path + "\\" + pathSplit[i];
        }
        String binary = pathSplit[pathSplit.length - 1];
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", path + "\\" + binary + " " + attributes);
        System.out.println(path + "\\" + binary + " " + attributes);
        builder.redirectErrorStream(true);
        try {
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (Exception launcexcp) {
            System.out.println(launcexcp.getMessage() + "System message");
        }
    }

    private void runOnUnix(String path, String attributes) {
        String[] pathSplit = path.split("\\\\");
        path = pathSplit[0];
        for (int i = 1; i < pathSplit.length - 1; i++) {
            path = path + "\\" + pathSplit[i];
        }
        String binary = pathSplit[pathSplit.length - 1];
        String binaryNX = "";
        if (binary.contains("hypseus.bin")) {
            String patternString = "(.*/)";
            String replacement = "$1./";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(binary);
            binaryNX = matcher.replaceAll(replacement);
        }
        System.out.println(binary);
        ProcessBuilder builder = new ProcessBuilder(
                "bash", "-c", binaryNX + " " + attributes);
        builder.redirectErrorStream(true);
        try {
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (Exception launcexcp) {
            System.out.println(launcexcp.getMessage() + "System message");
        }
    }

    @FXML
    private void LaunchWithAttributes(ActionEvent actionEvent) {
        String path = "";
        String attributes = "";
        path = pathTobinary.getText();
        attributes = attributeLine.getText();
        String systemInfo = os.toString();
        if (systemInfo.contains("Windows")) {
            runOnWindows(path, attributes);
        } else {
            runOnUnix(path, attributes);
        }
    }

    @FXML
    private void saveTmpAtt(ActionEvent actionEvent) {
        String attributes = attributeLine.getText();
        try {
            FileWriter pathfile = new FileWriter("tempAttrib.cfg");
            pathfile.write(attributes);
            pathfile.close();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getStylesheets().add("com/hypseus/alert.css");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void getsavedTempAttrib() {
        String attributes = "";
        try {
            FileReader fileAttrib = new FileReader("tempAttrib.cfg");
            BufferedReader attrib = new BufferedReader(fileAttrib);
            attributes = attrib.readLine();
            fileAttrib.close();
            attrib.close();
        } catch (IOException fileEx) {
            System.out.println(fileEx.getMessage());
        }
        attributeLine.setText(attributes);
    }

    private String parseGfxData() {
        String dataToparse = gfx;
        int number = dataToparse.indexOf("name");
        dataToparse = dataToparse.substring(number);
        int number1 = dataToparse.indexOf("versionInfo");
        dataToparse = dataToparse.substring(0, number1 - 1);
        dataToparse = dataToparse.replace("[", "");
        dataToparse = dataToparse.replace("]", "");
        String[] parsedData = dataToparse.split(",");
        HashMap<String, String> gfxMap = new HashMap<>();
        String resultGfx = "";
        for (String e : parsedData) {
            String[] gfxInfoElements = e.split("=");
            gfxMap.put(gfxInfoElements[0], gfxInfoElements[1]);
            resultGfx += gfxInfoElements[0].trim() + ": " + gfxInfoElements[1].trim() + "\n";
        }
        return resultGfx;
    }

    private String readBinaryPath() {
        String pathToBinary = "";
        try {
            FileReader readPath = new FileReader("path.cfg");
            BufferedReader bufferedReader = new BufferedReader(readPath);
            pathToBinary = bufferedReader.readLine();
            bufferedReader.close();
            readPath.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return pathToBinary;
    }

    private void setSindenValues(String numberSinden, String colorCode) {
        String colorSinden = colorCode;
        switch (colorSinden) {
            case "g":
                colorSinden = "green";
                break;
            case "r":
                colorSinden = "red";
                break;
            case "b":
                colorSinden = "blue";
                break;
            case "w":
                colorSinden = "white";
                break;
            case "x":
                colorSinden = "x";
                break;
            default:
                System.out.println("Unknown color code: " + colorSinden);
                break;
        }
        sinden.setSelected(true);
        sindenFill1.setDisable(false);
        sindenFill2.setDisable(false);
        int numberToInt = Integer.parseInt(numberSinden);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, numberToInt);
        sindenFill1.setValueFactory(valueFactory);
        sindenFill2.setValue(colorSinden);
    }

    private void setConfigAMPanelAIO() {

        // Initialize the spinner's value factory
        //  sindenFill1.setValueFactory(valueFactory);
        File conf = new File("cfg/aio/aioGames.cfg");
        if (conf.exists()) {
            try (BufferedReader readConf = new BufferedReader(new FileReader(conf))) {
                String line;
                while ((line = readConf.readLine()) != null) {
                    // Process -sinden option
                    Pattern checkSinden = Pattern.compile("-sinden\\s+([1-9]|10)\\s+(x|w|r|g|b)");
                    Matcher sindenFound = checkSinden.matcher(line);
                    if (sindenFound.find()) {
                        String numberSinden = sindenFound.group(1);
                        String colorCode = sindenFound.group(2);
                        Platform.runLater(() -> setSindenValues(numberSinden, colorCode));
                    } else {
                        sinden.setSelected(false);
                        Platform.runLater(() -> {
                            sindenFill1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
                            sindenFill2.setValue("x");
                            sindenFill1.setDisable(true);
                            sindenFill2.setDisable(true);
                        });
                    }

                    // Process -js_range option
                    Pattern checkjs = Pattern.compile("-js_range\\s+([1-9]|20)");
                    Matcher jsFound = checkjs.matcher(line);
                    if (jsFound.find()) {
                        String jsRange = jsFound.group(1);
                        jsrange.setSelected(true);
                        System.out.println("Enabling and setting Spinner for jsRange to: " + jsRange);
                        Platform.runLater(() -> {
                            jsrangeFill.setDisable(false);
                            SpinnerValueFactory<Integer> jsValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, Integer.parseInt(jsRange));
                            jsrangeFill.setValueFactory(jsValueFactory);
                        });
                    } else {
                        jsrange.setSelected(false);
                        Platform.runLater(() -> {
                            jsrangeFill.setDisable(true);
                            jsrangeFill.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
                        });
                    }

                    // Process -fullscreen option
                    String finalLine = line;
                    Platform.runLater(() -> fullScreen.setSelected(finalLine.contains("-fullscreen")));

                    // Process -force_aspect_ratio option
                    String finalLine1 = line;
                    Platform.runLater(() -> forceAspect.setSelected(finalLine1.contains("-force_aspect_ratio")));

                    // Process -scalefactor option
                    if (line.contains("-scalefactor")) {
                        AMscaleFactor.setSelected(true);
                        int startIndex = line.indexOf("-scalefactor") + "-scalefactor".length();
                        String scaleFac = line.substring(startIndex).trim();
                        int scaleFacInt = Integer.parseInt(scaleFac);
                        Platform.runLater(() -> {
                            AMscaleFactorSlider.setDisable(false);
                            AMscaleFactorSlider.setValue(scaleFacInt);
                        });
                    } else {
                        AMscaleFactor.setSelected(false);
                        Platform.runLater(() -> AMscaleFactorSlider.setDisable(true));
                    }

                    // Process -scanline_alpha option
                    if (line.contains("-scanline_alpha")) {
                        AMscaleAlpha.setSelected(true);
                        int startIndex = line.indexOf("-scanline_alpha") + "-scanline_alpha".length();
                        String scaleAlp = line.substring(startIndex).trim();
                        int scaleAlpInt = Integer.parseInt(scaleAlp);
                        Platform.runLater(() -> {
                            AMScanLAlphaSilder.setDisable(false);
                            AMScanLAlphaSilder.setValue(scaleAlpInt);
                        });
                    } else {
                        AMscaleAlpha.setSelected(false);
                        Platform.runLater(() -> AMScanLAlphaSilder.setDisable(true));
                    }

                    // Process -bezel option
                    if (line.contains("-bezel")) {
                        AMbezelCheckBox.setSelected(true);
                        AMbezelFile = line.substring(line.indexOf("-bezel") + "-bezel".length()).trim();
                        int nr = AMbezelFile.indexOf(" ");
                        if (nr != -1) {
                            AMbezelFile = AMbezelFile.substring(0, nr);
                        }
                        Platform.runLater(() -> {
                            AMbezelCheckBox.setSelected(true);
                            System.out.println("Setting Bezel File to: " + AMbezelFile);
                        });
                    } else {
                        Platform.runLater(() -> AMbezelCheckBox.setSelected(false));
                    }

                    // Process force aspect ratio option
                    Platform.runLater(() -> {
                        if (finalLine.contains("-force_aspect_ratio")) {
                            forceAspect.setDisable(false);
                            forceAspect.setSelected(true);
                            ignoreAspectRatio.setSelected(false);
                            ignoreAspectRatio.setDisable(true);
                        } else {
                            forceAspect.setSelected(false);
                            ignoreAspectRatio.setDisable(false);
                        }
                    });

                Platform.runLater(() -> {
                    if (finalLine.contains("-ignore_aspect_ratio")) {
                        ignoreAspectRatio.setDisable(false);
                        ignoreAspectRatio.setSelected(true);
                        forceAspect.setSelected(false);
                        forceAspect.setDisable(true);
                    } else {
                        ignoreAspectRatio.setSelected(false);
                        forceAspect.setDisable(false);
                    }
                });
            }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sinden.setSelected(false);
            sindenFill1.setDisable(true);
            sindenFill2.setDisable(true);
            forceAspect.setSelected(false);
            fullScreen.setSelected(false);
            jsrange.setSelected(false);
            jsrangeFill.setDisable(true);
            AMscaleFactor.setSelected(false);
            AMscaleFactorSlider.setDisable(true);
            AMScaleFSliderView.setDisable(true);
            AMscaleAlpha.setSelected(false);
            AMScanLAlphaSilder.setDisable(true);
            AMScanASliderView.setDisable(true);
            AMbezelCheckBox.setSelected(false);
        }
    }


    private void setConfigAMPanelSingle(String amgameName) {
        try {
            File readCfg = new File("cfg\\single\\" + amgameName + ".cfg");
            if (readCfg.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(readCfg));
                String line;
                while ((line = br.readLine()) != null) {
                    Pattern checkSinden = Pattern.compile("-sinden\\s+([1-9]|10)\\s+(x|w|r|g|b)");
                    Matcher sindenFound = checkSinden.matcher(line);
                    Pattern checkjs = Pattern.compile("-js_range\\s+([1-9]|20)");
                    Matcher jsFound = checkjs.matcher(line);
                    if (sindenFound.find()) {
                        String numberSinden = sindenFound.group(1);
                        String colorSinden = sindenFound.group(2);
                        switch (colorSinden) {
                            case "g":
                                colorSinden = "green";
                                break;
                            case "r":
                                colorSinden = "red";
                                break;
                            case "b":
                                colorSinden = "blue";
                                break;
                            case "w":
                                colorSinden = "white";
                                break;
                            case "x":
                                colorSinden = "x";
                                colorSinden = "x";
                                break;
                        }
                        if (jsFound.find()) {
                            String jsRange = jsFound.group(1);
                            jsrange.setSelected(true);
                            jsrangeFill.setDisable(false);
                            jsrangeFill.getValueFactory().setValue(Integer.parseInt(jsRange));
                        }
                        sinden.setSelected(true);
                        int numberToInt = Integer.parseInt(numberSinden);
                        System.out.println(numberToInt);
                        sindenFill1.getValueFactory().setValue(numberToInt);
                        sindenFill2.setValue(colorSinden);
                        sindenFill1.setDisable(false);
                        sindenFill2.setDisable(false);
                        System.out.println(numberSinden + " " + " " + colorSinden);
                    }
                    if (jsFound.find()) {

                    }
                    if (line.contains("-fullscreen")) {
                        fullScreen.setSelected(true);
                    } else {
                        fullScreen.setSelected(false);
                    }
                    if (line.contains("-force_aspect_ratio")) {
                        forceAspect.setSelected(true);
                    } else {
                        forceAspect.setSelected(false);
                    }
                    if (line.contains("-scalefactor")) {
                        AMscaleFactor.setSelected(true);
                        AMscaleFactorSlider.setDisable(false);
                        AMScaleFSliderView.setDisable(false);

                        // Extracting the scale factor value from the line
                        int startIndex = line.indexOf("-scalefactor") + "-scalefactor".length();
                        String scaleFac = line.substring(startIndex).trim(); // trimming any leading or trailing whitespace
                        int scaleFacInt = Integer.parseInt(scaleFac);
                        System.out.println(scaleFacInt);
                        AMscaleFactorSlider.setValue(scaleFacInt);
                        AMScaleFSliderView.setText(scaleFacInt + "%");
                    } else {
                        AMscaleFactor.setSelected(false);
                        AMscaleFactorSlider.setDisable(true);
                        AMScaleFSliderView.setDisable(true);
                    }
                    if (line.contains("-scanline_alpha")) {
                        AMscaleAlpha.setSelected(true);
                        AMScanLAlphaSilder.setDisable(false);
                        AMScanASliderView.setDisable(false);
                        // Extracting the scale value from the line
                        int startIndex = line.indexOf("-scanline_alpha") + "-scanline_alpha".length();
                        String scaleAlp = line.substring(startIndex).trim(); // trimming any leading or trailing whitespace
                        int scaleAlpInt = Integer.parseInt(scaleAlp);
                        System.out.println(scaleAlpInt);
                        AMScanLAlphaSilder.setValue(scaleAlpInt);
                        AMScanASliderView.setText(Integer.toString(scaleAlpInt) + "");
                    } else {
                        AMscaleAlpha.setSelected(false);
                        AMScanLAlphaSilder.setDisable(true);
                        AMScanASliderView.setDisable(true);
                    }
                    if (line.contains("-bezel")) {
                        AMbezelCheckBox.setSelected(true);
                        AMbezelFile = line.substring(line.indexOf("-bezel") + "-bezel".length()).trim();
                        int nr = AMbezelFile.indexOf(" ");
                        if (nr != -1) {
                            AMbezelFile = AMbezelFile.substring(0, nr);
                        }
                        System.out.println(AMbezelFile);
                    } else {
                        AMbezelCheckBox.setSelected(false);
                    }
                }
            } else {
                sinden.setSelected(false);
                sindenFill1.setDisable(true);
                sindenFill2.setDisable(true);
                forceAspect.setSelected(false);
                fullScreen.setSelected(false);
                jsrange.setSelected(false);
                jsrangeFill.setDisable(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // one config for each game on the list
    public void AMGameConfig(MouseEvent mouseEvent) {
        boolean sindenChecked = sinden.isSelected();
        String sindenFieldOne = sindenFill1.getValue().toString();
        String sindenFieldTwo = sindenFill2.getSelectionModel().getSelectedItem().toString();
        boolean fullScreenChecked = fullScreen.isSelected();
        boolean forceAspectChecked = forceAspect.isSelected();
        boolean jsRangeChecked = jsrange.isSelected();
        boolean scaleFacChecked = AMscaleFactor.isSelected();
        boolean scaleAlpha = AMscaleAlpha.isSelected();
        boolean AMbezel = AMbezelCheckBox.isSelected();

        String pathToBinary = readBinaryPath();
        String text = pathToBinary;
        dataDeSerialize("pconfig.cfg");
        String americanPath = pathCfg.getAmericanPath();
        text += " singe vldp -framefile ";
        String color = "";
        String options = "";
        switch (sindenFieldTwo) {
            case "green":
                color = "g";
                break;
            case "red":
                color = "r";
                break;
            case "white":
                color = "w";
                break;
            case "blue":
                color = "b";
                break;
            default:
                color = "x";
                break;
        }
        System.out.println(forceAspectChecked + "<--");
        if (sindenChecked) {
            options += "-sinden " + sindenFieldOne + " " + color;
        }
        if (fullScreenChecked) {
            options += " -fullscreen";
        }
        if (forceAspectChecked) {
            options += " -force_aspect_ratio";
        }
        if (jsRangeChecked) {
            options += " -js_range " + jsrangeFill.getValue().toString();
        }
        if (scaleFacChecked) {
            int scaleValue = (int) AMScaleFactorSlider.getValue();
            options += " -scalefactor " + scaleValue;
        }
        if (scaleAlpha) {
            int alphaValue = (int) AMScanLAlphaSilder.getValue();
            options += " -scanline_alpha " + alphaValue;
        }
        if (AMbezel && AMbezelFile.length() > 0){
            options += " -bezel " + AMbezelFile;
        }

        var selection = multiSingleGames.getSelectionModel().getSelectedIndex();
        System.out.println("Selection ---->" + selection);
        if (multiSingleGames.getSelectionModel().getSelectedIndex() == 0) {
            try {
                // all in one game selection configuration
                FileWriter allGameConfWriter = new FileWriter("cfg\\aio\\aioGames.cfg");
                pathCfg.gSelectionType = "allInOne";
                dataSerialize("pconfig.cfg");
                allGameConfWriter.write(options);
                allGameConfWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            pathCfg.gSelectionType = "singleOnEach";
            dataSerialize("pconfig.cfg");
            var test = configAmList.getSelectionModel().getSelectedItem();
            System.out.println("------> " + test);
            if (configAmList.getSelectionModel().getSelectedItem() != null) {
                String amgameName = configAmList.getSelectionModel().getSelectedItem().toString();
                text += " " + americanPath + "\\" + amgameName + ".singe\\" + amgameName + ".txt" + " -script " + americanPath + "\\" + amgameName + ".singe" + "\\" + amgameName + ".singe ";
                text += options;
                try {
                    FileWriter singleGameConfWriter = new FileWriter("cfg\\single\\" + amgameName + ".cfg");
                    singleGameConfWriter.write(text);
                    singleGameConfWriter.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                File pathToFile = new File("com/hypseus/alert1.css");
            //    URL css = getClass().getResource(String.valueOf(pathToFile.toURI()));
                Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
                noSelection.getDialogPane().getStylesheets().add(String.valueOf(pathToFile));
                noSelection.setHeaderText("No game selected");
                noSelection.setContentText("Please select a game to configure");
                noSelection.showAndWait();

            }
        }
        System.out.println(text);
    }

    public void multiGameConfigSelect(ActionEvent actionEvent) {
        int selection = multiSingleGames.getSelectionModel().getSelectedIndex();
        if (selection == 0) {
            String lineConf = "";
            configAmList.setDisable(true);
            setConfigAMPanelAIO();
        } else {
            configAmList.setDisable(false);
        }
    }

    public void onIgnoreAspectClick(MouseEvent mouseEvent) {
        if (ignoreAspectRatio.isSelected() == true) {
            forceAspect.setSelected(false);
            forceAspect.setDisable(true);
        } else {
            forceAspect.setDisable(false);
        }
    }

    public void onForceAspectClick(MouseEvent mouseEvent) {
        if (forceAspect.isSelected() == true) {
            ignoreAspectRatio.setSelected(false);
            ignoreAspectRatio.setDisable(true);
        } else {
            ignoreAspectRatio.setDisable(false);
        }
    }

    public void onOpenglClickAm(MouseEvent mouseEvent) {
        if (openglSelectAm.isSelected() == true) {
            vulkanSelectAm.setSelected(false);
            vulkanSelectAm.setDisable(true);
        } else {
            vulkanSelectAm.setDisable(false);
        }
    }

    public void onVulkanClickAm(MouseEvent mouseEvent) {
        if (vulkanSelectAm.isSelected() == true) {
            openglSelectAm.setSelected(false);
            openglSelectAm.setDisable(true);
        } else {
            openglSelectAm.setDisable(false);
        }
    }

    private void readAMGamesFileList() {
        int count = 0;
        File config = new File("amlist.lst");
        if (config.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(config));
                String line;
                while ((line = br.readLine()) != null) {
                    configAmList.getItems().add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onAMlistSelect(MouseEvent mouseEvent) {
        String amgameName = configAmList.getSelectionModel().getSelectedItem().toString();
        setConfigAMPanelSingle(amgameName);
    }

    public void DPPathSelect(ActionEvent actionEvent) {
    }

    public void onAMScalefactorSet(MouseEvent mouseEvent) {
        if (AMscaleFactor.isSelected()) {
            AMScaleFactorSlider.setDisable(false);
            AMScaleFSliderView.setDisable(false);
        } else {
            AMScaleFactorSlider.setDisable(true);
            AMScaleFSliderView.setDisable(true);
        }
    }

    public void AMScaleFset(MouseEvent mouseEvent) {
        // This method can be used if you need to handle specific mouse events separately
        int scale = (int) AMScaleFactorSlider.getValue();
        AMScaleFSliderView.setText(Integer.toString(scale));
    }

    public void onAMScanAlphaSet(MouseEvent mouseEvent) {
        if (AMScanLAlphaSilder.isDisable()) {
            AMScanLAlphaSilder.setDisable(false);
            AMScanASliderView.setDisable(false);
        } else {
            AMScanLAlphaSilder.setDisable(true);
            AMScanASliderView.setDisable(true);
        }
    }
    public void AMScanAlphaSet(MouseEvent mouseEvent) {
    int scale = (int) AMScanLAlphaSilder.getValue();
    AMScanASliderView.setText(Integer.toString(scale));
    }

    public void bezelChecked(MouseEvent mouseEvent) {
        if (AMbezelCheckBox.isSelected())
            AMpngBezel.setDisable(false);
        else
            AMpngBezel.setDisable(true);
    }

    public void AMpngSelectBezel(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        File selectedFile = fileChooser.showOpenDialog(dialog);
        if (selectedFile != null) {
            String file = selectedFile.toString();
            dialog.close();
            int nr = file.lastIndexOf("\\");
            file = file.substring(nr + 1);
            System.out.println("bezel file --> " + file);
            AMbezelFile = file;



        } else {
            System.out.println("No selection");
        }
    }
    @FXML
    private void AMAddAditionalArgs(MouseEvent mouseEvent) {
        try {
            ClipboardContent content = new ClipboardContent();
            if (multiSingleGames.getSelectionModel().getSelectedIndex() == 0) {
                content.putString("cfg\\aio\\aioGames.ada");
            } else {
                if (configAmList.getSelectionModel().getSelectedItem() == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You have to select a game from the list", ButtonType.OK);
                    alert.getDialogPane().getStylesheets().add("com/hypseus/alert.css");
                    alert.showAndWait();
                    return;
                }
                String selectedGame = configAmList.getSelectionModel().getSelectedItem().toString();
                content.putString("cfg\\single\\" + selectedGame + ".ada");
            }
            Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(content);
            System.out.println("----content---> " + content.getString());
            Node node = (Node) mouseEvent.getSource();
            Stage mainStage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AMAddAditionalArgsWindows.fxml"));
            Parent root = loader.load();

            // Get the controller of the new window
            AMAddAditionalArgsWindows controller = loader.getController();
            // Create the new scene and stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            controller.setStages(mainStage, stage);  // Pass the current and previous stages to the controller

            stage.setScene(scene);
            stage.setTitle("Additional arguments for American Laser Games");
            stage.getIcons().add(new Image(HypseusSingeGuiApp.class.getResourceAsStream("img/hypseus-logo_thumb.png")));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) mouseEvent.getSource()).getScene().getWindow());

            stage.showAndWait();
        //    stage.show();
        //    mainStage.hide();
        } catch (IOException e) {
            e.printStackTrace();
            // addToLog(time + " " + "error while opening configuration window: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // System.out.println("Initializing...");
       //    pathCfg = new PathCfg();
        sindenFill1.setDisable(true);
        sindenFill2.setDisable(true);
        dataDeSerialize("pconfig.cfg");
        if (pathCfg.gSelectionType.contains("allInOne")) {
            setConfigAMPanelAIO();
        }
        if (pathCfg.americanPath.trim().length() > 0)
            AMGamesSelectiontxt.setText(pathCfg.americanPath);
        else AMGamesSelectiontxt.setText("Path to American Laser Games rom folder");

        /*Image amConfigTabImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/hypseus/img/amPanBack.jpg.bak")));
        BackgroundImage amBackTab = new BackgroundImage(amConfigTabImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        AMGridePane.setBackground(new Background(amBackTab));
*/
        if (pathCfg.daphnePath.trim().length() > 0)
            daphneGamesSelectiontxt.setText(pathCfg.daphnePath);
        else
            daphneGamesSelectiontxt.setText("Path to Daphne rom folder");
        if (pathCfg.gSelectionType.contains("singleOnEach")){
            multiSingleGames.getSelectionModel().select(1);
        } else {
            multiSingleGames.getSelectionModel().select(0);
        }


        //daphnePathSelection.getStyleClass().add("Button50L");
        daphnePathSelection.setOnMouseEntered(event -> {
            daphnePathSelection.setStyle("-fx-font-weight: bold;" +
                    "-fx-float: left;" +
                    "-fx-padding:10px;" +
                    "-fx-width: 50%;" +
                    "-fx-height: 100%;" +
                    "-fx-background-color: rgb(179,235,252);" +
                    "-fx-font-size: 18px;");
        });
        daphnePathSelection.setOnMouseExited(event -> {
            daphnePathSelection.setStyle("-fx-font-weight: normal;" +
                    "-fx-float: left;" +
                    "-fx-padding:10px;" +
                    "-fx-width: 50%;" +
                    "-fx-height: 100%;" +
                    "-fx-background-color: rgb(179,235,252);" +
                    "-fx-font-size: 18px;");
        });
        AMPathSelection.getStyleClass().add("Button50L");
        AMSearchGames.getStyleClass().add("Button50L");
        saveALGconf.getStyleClass().add("Button50L");
        multiSingleGames.getStyleClass().add("Button50L");
        AMPathSelection.getStyleClass().add("Button50L");
        binaryPath.getStyleClass().add("Button50L");
        jsrangeFill.setDisable(true);
        AMScaleFactorSlider.setDisable(true);
        AMpngBezel.setDisable(true);
        AMbezelFile = "";

        SpinnerValueFactory<Integer> factory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        jsrangeFill.setValueFactory(factory1);

        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        sindenFill1.setValueFactory(valueFactory);
        sindenFill2.getItems().addAll("white", "red", "green", "blue", "x");
        sindenFill2.getSelectionModel().select(4);


        String getSystemInfo = "";
        String getDisplayInfo = "";
        String nameOS = "os.name";
        String versionOS = "os.version";
        String architectureOS = "os.arch";
        String javaVersion = "java.version";
        File[] roots = File.listRoots();
        String diskInfo = "";
        /*for (File root : roots) {
            diskInfo = ("\nFile system: " + root.getAbsolutePath()
                    + "\nTotal space (bytes): " + root.getTotalSpace()
                    + "\nFree space (bytes): " + root.getFreeSpace()
                    + "\nUsable space (bytes): " + root.getUsableSpace());
        }*/
        String systemInfo = ("OS Name: " + System.getProperty(nameOS)
                + "\nOS Version: " + System.getProperty(versionOS)
                + "\nArchitecture: " + System.getProperty(architectureOS)
                + "\nAvailable processors (cores): " + Runtime.getRuntime().availableProcessors()
                + "\nFree memory (bytes): " + Runtime.getRuntime().freeMemory()
                + "\nTotal memory available to JVM (bytes): " + Runtime.getRuntime().totalMemory()
                + "\nJava Version: " + System.getProperty(javaVersion)
                + diskInfo);
        readConfig();
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        ComputerSystem sys = hal.getComputerSystem();
        GlobalMemory mem = hal.getMemory();
        VirtualMemory vmem = hal.getMemory().getVirtualMemory();
        os = si.getOperatingSystem();
        gfx = hal.getGraphicsCards().toString();
        display = hal.getDisplays().toString();
        parseGfxData();
        getSystemInfo = cpu + "\n" + mem + "\n" + sys + "\n" + vmem + "\n" + os;
        showSystemInfo(getSystemInfo);
        System.out.println("GFX to function -> " + gfx);
        showDisplayInfo(parseGfxData() + "\n" + display);
        //    System.out.println(gfx + display);
        AMScaleFactorSlider.setDisable(true);
        AMScaleFSliderView.setDisable(true);
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        sindenFill1.setValueFactory(valueFactory);
        AMScaleFactorSlider.valueProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
            int scale = (int) AMScaleFactorSlider.getValue();
            AMScaleFSliderView.setText(scale + "%");
        });
        AMScanLAlphaSilder.setDisable(true);
        AMScanASliderView.setDisable(true);
        AMScanLAlphaSilder.valueProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
            int scanAlpha = (int) AMScanLAlphaSilder.getValue();
            AMScanASliderView.setText(scanAlpha+ "");
        });
        getsavedTempAttrib();
        readAMGamesFileList();

    }
}