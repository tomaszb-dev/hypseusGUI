package com.hypseus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.net.URL;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    // AnchorPane
    @FXML
    private AnchorPane AMAnchorPane = new AnchorPane();
    @FXML
    private AnchorPane daphneAnchorPane = new AnchorPane();
    @FXML
    private AnchorPane hypseusConfigAnchor = new AnchorPane();

    // Button
    @FXML
    private Button AMGameSelection = new Button();
    @FXML
    private Button AMPathSelection = new Button();
    @FXML
    private Button AMSearchGames = new Button();
    @FXML
    private Button AMpngBezel = new Button();
    @FXML
    private Button binaryPath = new Button();
    @FXML
    private Button daphneGameSelect = new Button();
    @FXML
    private Button daphnePathSelection = new Button();
    @FXML
    private Button folderConf = new Button("Folder Config");
    @FXML
    private Button launchBinary = new Button();
    @FXML
    private Button openArgsButton;
    @FXML
    private Button saveALGconf = new Button();
    @FXML
    private Button saveTempAttrib = new Button();
    @FXML
    private Button searchGames = new Button("Search Games");

    // CheckBox
    @FXML
    private CheckBox AMEightBit = new CheckBox();
    @FXML
    private CheckBox AMManyMouse = new CheckBox();
    @FXML
    private CheckBox AMOrginalOverlay = new CheckBox();
    @FXML
    private CheckBox AMScoreBezel = new CheckBox();
    @FXML
    private CheckBox AMScorePanel = new CheckBox();
    @FXML
    private CheckBox AMVerticalStretch = new CheckBox();
    @FXML
    private CheckBox AMbezelCheckBox = new CheckBox();
    @FXML
    private CheckBox AMblendSprites = new CheckBox();
    @FXML
    private CheckBox AMforceAspect = new CheckBox();
    @FXML
    private CheckBox AMgamepad = new CheckBox();
    @FXML
    private CheckBox AMignoreAspectRatio = new CheckBox();
    @FXML
    private CheckBox AMlinearscale = new CheckBox();
    @FXML
    private CheckBox AMnocrosshair = new CheckBox();
    @FXML
    private CheckBox AMscanlines = new CheckBox();
    @FXML
    private CheckBox AMscaleAlpha = new CheckBox();
    @FXML
    private CheckBox AMscaleFactor = new CheckBox();
    @FXML
    private CheckBox AMScanlineShunt = new CheckBox();
    @FXML
    private CheckBox AMtiphatBox = new CheckBox();
    @FXML
    private CheckBox AMverticalscreen = new CheckBox();
    @FXML
    private CheckBox DPScorePanel = new CheckBox();
    @FXML
    private CheckBox fullScreen = new CheckBox();
    @FXML
    private CheckBox jsrange = new CheckBox();
    @FXML
    CheckBox sinden = new CheckBox();
    @FXML
    CheckBox AMusbscore = new CheckBox();
    // ComboBox
    @FXML
    private ComboBox sindenFill2 = new ComboBox();

    // GridPane
    @FXML
    private GridPane AMGridPane = new GridPane();
    @FXML
    private GridPane daphenConfigGridPane = new GridPane();

    // Label
    @FXML
    private Label AMScaleFSliderView = new Label();
    @FXML
    private Label AMScanASliderView = new Label();
    @FXML
    private Label AMScanlinesShuntView = new Label();
    @FXML
    private Label AMVerticalStretchView = new Label();

    // ListView
    @FXML
    private ListView configAmList = new ListView();

    // PathCfg
    PathCfg pathCfg;

    // RadioButton
    @FXML
    private RadioButton AMopengl = new RadioButton();
    @FXML
    private RadioButton AMtexturestream = new RadioButton();
    @FXML
    private RadioButton AMtexturetarget = new RadioButton();
    @FXML
    private RadioButton AMvulkan = new RadioButton();

    // Slider
    @FXML
    private Slider AMScaleFactorSlider = new Slider();
    @FXML
    private Slider AMScanAlphaSilder = new Slider();
    @FXML
    private Slider AMVerticalStretchSlider = new Slider();
    @FXML
    private Slider AMscanShuntSlider = new Slider();

    // Spinner
    @FXML
    private Spinner AMjsrangeFill = new Spinner();
    @FXML
    private Spinner sindenFill1 = new Spinner();

    // SpinnerValueFactory
    @FXML
    private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);

    // Tab
    @FXML
    private Tab AMConfigTab = new Tab();
    @FXML
    private Tab configTab = new Tab();
    @FXML
    private Tab daphneConfigTab = new Tab();
    @FXML
    private Tab hypseusConfigTab = new Tab();

    // TextArea
    @FXML
    private TextArea displayInfo = new TextArea();
    @FXML
    private TextArea systemInfo = new TextArea();

    // TextField
    @FXML
    private TextField AMGamesFound = new TextField();
    @FXML
    private TextField AMGamesSelectiontxt = new TextField();
    @FXML
    private TextField attributeLine = new TextField();
    @FXML
    private TextField daphneGamesSelectiontxt = new TextField();
    @FXML
    private TextField daphneSelectiontxt = new TextField();
    @FXML
    private TextField pathTobinary = new TextField();
    @FXML
    TextField AMScorePanelX = new TextField();
    @FXML
    TextField AMScorePanelY = new TextField();
    @FXML
    TextField AMusbscoreFill = new TextField();

    /**
     * Method to check the Daphne folders for games
     * @return A list of found games
     */
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

    /*
     * Method to check the AM folders for games
     * @return A list of found games
     */
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
                URL css = getClass().getResource("alert1.css");
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

    /**
     * Method to check the American Laser Games folders for games
     *
     * @param fileCheck The folder to check for games
     * @return A list of found games
     */
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
    /**
     * Method to update the MYDIR path in the game.singe file
     *
     * @param folderFile The game.singe file to update
     * @param pathName   The new path to set
     */
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

    /* @FXML
     protected void setsearchGames() {
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
        alert.getDialogPane().getStylesheets().add("alert1.css");
        alert.setTitle("Message");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
        ;
    }

    /**
     * Method to save the list of found games to a file
     *
     * @param games The list of found games
     */
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

    /**
     * Method to show sysinfo
     */
    @FXML
    private void showSystemInfo(String gameInfo) {
        try {
            systemInfo.setText(gameInfo);
        } catch (Exception ex) {
            systemInfo.setText("No system info available");
        }
    }

    /**
     * Method to show display info
     */
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

    /**
     * Method to show read error alert window
     */
    private void showWriteErrorWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add("alert1.css");
        alert.setTitle("File write error");
        alert.setHeaderText("File write error");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
        ;
    }

    /**
     * Method to serialize data for the config file
     */
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

    /**
     * Method to deserialize data from the config file
     */
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

    /**
     * Method to set the path to the binary file hypseus
     */
    public void setBinaryPath(ActionEvent actionEvent) {
        String binaryPath = "";
        String nameOS = "os.name";
        String os = System.getProperty(nameOS);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        File selectedFile = fileChooser.showOpenDialog(dialog);
        if (selectedFile != null) {
            binaryPath = selectedFile.toString();
            System.out.println(" ----> " + binaryPath);
            dialog.close();
            pathTobinary.setText(selectedFile.toString());
            try {
                FileWriter pathfile = new FileWriter("path.cfg");
                if (os.contains("Windows")) {
                    pathfile.write("\"" + binaryPath + "\"");
                } else {
                    pathfile.write("\'" + binaryPath + "\'");
                }

                pathfile.close();
                updatePathsInConfigFiles("cfg/single", "path.cfg");

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add("alert1.css");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        } else {
            System.out.println("No selection");
        }
    }

    /**
     * Method to update path to binary file hypseus if changed and write it to config file
     */
    private static void updatePathsInConfigFiles(String folderPath, String pathConfigFile) throws IOException {
        // Read the new path from the "path.cfg" file
        String newPath;
        try (BufferedReader reader = new BufferedReader(new FileReader(pathConfigFile))) {
            newPath = reader.readLine().trim();
        }

        // List all .cfg files in the specified folder
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".cfg"));

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                // Read the content of the file, replace the old executable path with the new path, and save the changes
                List<String> lines;
                try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
                    lines = stream.map(line -> replaceExecutablePath(line, newPath))
                            .collect(Collectors.toList());
                }

                // Write the modified content back to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }
    }

    /**
     * Helper method for updatePathsInConfigFiles method to replace the executable path in a line
     */
    private static String replaceExecutablePath(String line, String newPath) {
        int index = line.indexOf("singe");
        if (index > 0) {
            String oldPath = line.substring(0, index).trim();
            if (!oldPath.isEmpty()) {
                line = line.replace(oldPath, newPath);
            }
        }
        return line;
    }

    /**
     * Method to read config file
     */
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

    /**
     * Method to launch binary file hypseus with attributes on MS Windows OS
     */
    private void runOnWindows(String path, String attributes) {
        String[] pathSplit = path.split("\\\\");
        path = pathSplit[0];
        for (int i = 1; i < pathSplit.length - 1; i++) {
            path = path + "\\" + pathSplit[i];
        }
        String binary = pathSplit[pathSplit.length - 1];
        String launchLine = path + "\\" + binary + " " + attributes;
        ProcessBuilder builder = new ProcessBuilder(
                //   "cmd.exe", "/c","\"" + path + "\\" + binary, attributes);
                "cmd.exe", "/c", launchLine);
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
                r.close();
            }
        } catch (Exception launcexcp) {
            System.out.println(launcexcp.getMessage() + "System message");
        }
    }

    /**
     * Method to launch binary file hypseus with attributes on Unix like OSes
     */
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
        String launchLine = binaryNX + " " + attributes;
        ProcessBuilder builder = new ProcessBuilder(
                "bash", "-c", launchLine);
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
                r.close();
            }
        } catch (Exception launcexcp) {
            System.out.println(launcexcp.getMessage() + "System message");
        }
    }

    /*// one config for all games
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
          options += "-jsrange " + AMjsrangeFill.getValue().toString();
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
  }*/

    /**
     * Method to savein tempAttrib.cfg the handwritten attributes for AM games
     */
    @FXML
    private void saveTmpAtt(MouseEvent actionEvent) {
        System.out.println("Saving temp attributes");
        String attributes = attributeLine.getText();
        try {
            FileWriter pathfile = new FileWriter("tempAttrib.cfg");
            pathfile.write(attributes);
            pathfile.close();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getStylesheets().add("alert1.css");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method to read the saved handwritten attributes from the tempAttrib.cfg file
     */
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

    /**
     * Method to launch binary file hypseus with handwritten attributes
     */
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

    /**
     * Method to gather graphic card info
     */
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

    /**
     * Method to read from path.cfg file the path to the binary file hypseus
     */
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
    /**
     * Helper method to handle sinden attribute values
     */
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

    /**
     * Method to read config line with attributes for AM games and set the values in the GUI for single config for all AM games
     */
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
                    // Process -js_range option
                    Pattern checkjs = Pattern.compile("-js_range\\s+([1-9]|20)");
                    Matcher jsFound = checkjs.matcher(line);
                    Pattern checkScaleFactor = Pattern.compile("-scalefactor\\s*(\\d{2,3})(.*)");
                    Matcher scaleFacFound = checkScaleFactor.matcher(line);
                    Pattern checkScanlineAlpha = Pattern.compile("-scanline_alpha\\s*(\\d{1,3})(.*)");
                    Matcher scanlineAlphaFound = checkScanlineAlpha.matcher(line);
                    Pattern checkScanlineShunt = Pattern.compile("-scanline_shunt\\s*(\\d{1,2})(.*)");
                    Matcher scanlineShuntFound = checkScanlineShunt.matcher(line);
                    Pattern checkVerticalSkretch = Pattern.compile("-vertical_stretch\\s*(\\d{1,2})(.*)");
                    Matcher verticalSkretchFound = checkVerticalSkretch.matcher(line);;
                    /*Pattern usbScoreBoard = Pattern.compile("-usbscore?\\s*(\\d{1,2})(.*)");
                    Matcher usbScoreBoardFound = usbScoreBoard.matcher(line);
                    System.out.println(usbScoreBoardFound.group(1));*/
                    if(line.contains("-usbscoreboard")) {
                        AMusbscore.setSelected(true);
                        AMusbscoreFill.setDisable(false);
                        int numb = line.indexOf("-usbscoreboard");
                        String scoreboard = line.substring(numb + 15);
                        numb = scoreboard.indexOf(" ");
                        if (numb > 0)
                            scoreboard = scoreboard.substring(0, numb);
                        AMusbscoreFill.setText(scoreboard);

                    } else {
                        AMusbscore.setSelected(false);
                        AMusbscoreFill.setDisable(true);
                    }

                    if (sindenFound.find()) {
                        String numberSinden = sindenFound.group(1);
                        String colorCode = sindenFound.group(2);
                    //    Platform.runLater(() -> setSindenValues(numberSinden, colorCode));
                        Platform.runLater(() -> {
                            sinden.setSelected(true);
                            sindenFill1.setDisable(false);
                            sindenFill2.setDisable(false);
                            setSindenValues(numberSinden, colorCode);
                            AMManyMouse.setDisable(true);
                        });
                    } else {
                        Platform.runLater(() -> {
                            sinden.setSelected(false);
                            sindenFill1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
                            sindenFill2.setValue("x");
                            sindenFill1.setDisable(true);
                            sindenFill2.setDisable(true);
                        });
                    }
                    if (jsFound.find()) {
                        String jsRange = jsFound.group(1);
                        jsrange.setSelected(true);
                        System.out.println("Enabling and setting Spinner for jsRange to: " + jsRange);
                        final SpinnerValueFactory<Integer> jsValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, Integer.parseInt(jsRange));
                        Platform.runLater(() -> {
                            jsrange.setSelected(true);
                            AMjsrangeFill.setDisable(false);
                            AMjsrangeFill.setValueFactory(jsValueFactory);
                        });
                    } else {
                        jsrange.setSelected(false);
                        AMjsrangeFill.setDisable(true);
                        Platform.runLater(() -> {
                            AMjsrangeFill.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
                        });
                    }

                    // Process -fullscreen option
                    final String finalLine = line;
                    Platform.runLater(() -> fullScreen.setSelected(finalLine.contains("-fullscreen")));

                    // Process -force_aspect_ratio option
                    Platform.runLater(() -> AMforceAspect.setSelected(finalLine.contains("-force_aspect_ratio")));


                    // Process -scalefactor option
                    if (scaleFacFound.find()) {
                        //    if (finalLine.contains("-scalefactor")) {
                        //     AMscaleFactorSlider.setValue(Integer.parseInt(scaleFacFound.group(1)));
                        //     int startIndex = line.indexOf("-scalefactor") + "-scalefactor".length();
                        //     String scaleFac = line.substring(startIndex).trim();
                        int scaleFacInt = Integer.parseInt(scaleFacFound.group(1));
                        final int scaleFac = scaleFacInt;
                       /* Timeline timeline = new Timeline();
                        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250), e -> sliderMovement(scaleFac)));
                        timeline.setCycleCount(1);
                        timeline.play();*/
                        Platform.runLater(() -> {
                            AMscaleFactor.setSelected(true);
                            AMScaleFactorSlider.setDisable(false);
                            AMScaleFactorSlider.setValue(scaleFac);
                            AMScaleFSliderView.setText((scaleFac) + "%");
                            AMScaleFSliderView.setDisable(false);

                        });
                    } else {
                        Platform.runLater(() -> {
                            AMscaleFactor.setSelected(false);
                            AMScaleFactorSlider.setDisable(true);

                        });
                    }

                    // Process -scanline_alpha option
                    if (scanlineAlphaFound.find()) {
                        
                       /* int startIndex = line.indexOf("-scanline_alpha") + "-scanline_alpha".length();
                        String scaleAlp = line.substring(startIndex).trim();
                        int scaleAlpInt = Integer.parseInt(scaleAlp);*/
                        int scaleAlpInt = Integer.parseInt(scanlineAlphaFound.group(1));
                        Platform.runLater(() -> {
                            AMscaleAlpha.setSelected(true);
                            AMScanAlphaSilder.setDisable(false);
                            AMScanAlphaSilder.setValue(scaleAlpInt);
                            AMScanASliderView.setDisable(false);
                        });
                    } else {
                        AMscaleAlpha.setSelected(false);
                        Platform.runLater(() -> AMScanAlphaSilder.setDisable(true));
                    }

                     if (scanlineShuntFound.find()) {
                        int scanShuntInt = Integer.parseInt(scanlineShuntFound.group(1));
                        System.out.println("Setting Scanline Shunt to: " + scanShuntInt);
                        final int scanShunt = scanShuntInt;
                        Platform.runLater(() -> {
                            AMScanlineShunt.setSelected(true);
                            AMScanlinesShuntView.setDisable(false);
                            AMscanShuntSlider.setDisable(false);
                            AMscanShuntSlider.setValue(scanShunt);
                            AMScanlinesShuntView.setDisable(false);
                        });
                    } else {
                        AMScanlineShunt.setSelected(false);
                        Platform.runLater(() -> AMscanShuntSlider.setDisable(true));
                    }

                    if (verticalSkretchFound.find()) {
                        int verticalSkretchInt = Integer.parseInt(verticalSkretchFound.group(1));
                        Platform.runLater(() -> {
                            AMVerticalStretch.setSelected(true);
                            AMVerticalStretchSlider.setDisable(false);
                            AMVerticalStretchSlider.setValue(verticalSkretchInt);
                            AMVerticalStretchView.setDisable(false);
                        });
                    } else {
                        AMVerticalStretch.setSelected(false);
                        Platform.runLater(() -> AMVerticalStretchSlider.setDisable(true));
                    }


                    Platform.runLater(() -> {
                        if (finalLine.contains("-gamepad")) {
                            AMgamepad.setSelected(true);
                        } else {
                            AMgamepad.setSelected(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains("-tiphat")) {
                            AMtiphatBox.setSelected(true);
                        } else {
                            AMtiphatBox.setSelected(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains("-linear_scale")) {
                            AMlinearscale.setSelected(true);
                        } else {
                            AMlinearscale.setSelected(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains(("-vertical_screen"))) {
                            AMverticalscreen.setSelected(true);
                        } else {
                            AMverticalscreen.setSelected(false);
                        }
                    });

                    // Process -bezel option
                    if (finalLine.contains("-bezel")) {
                        Platform.runLater(() -> {
                            AMbezelCheckBox.setSelected(true);
                            AMbezelFile = finalLine.substring(finalLine.indexOf("-bezel") + "-bezel".length()).trim();
                            int nr = AMbezelFile.indexOf(" ");
                            if (nr != -1) {
                                AMbezelFile = AMbezelFile.substring(0, nr);
                            }
                            //  Platform.runLater(() -> {
                            AMbezelCheckBox.setSelected(true);
                            System.out.println("Setting Bezel File to: " + AMbezelFile);
                        });
                    } else {
                        Platform.runLater(() -> AMbezelCheckBox.setSelected(false));
                    }

                    // Process force aspect ratio option
                    Platform.runLater(() -> {
                        if (finalLine.contains("-force_aspect_ratio")) {
                            AMforceAspect.setDisable(false);
                            AMforceAspect.setSelected(true);
                            AMignoreAspectRatio.setSelected(false);
                            AMignoreAspectRatio.setDisable(true);

                        } else {

                            AMforceAspect.setSelected(false);
                            AMignoreAspectRatio.setDisable(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains("-ignore_aspect_ratio")) {
                            AMignoreAspectRatio.setDisable(false);
                            AMignoreAspectRatio.setSelected(true);
                            AMforceAspect.setSelected(false);
                            AMforceAspect.setDisable(true);
                        } else {
                            AMignoreAspectRatio.setSelected(false);
                            AMforceAspect.setDisable(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains("-scanline")) {
                            AMscanlines.setSelected(true);
                        } else {
                            AMscanlines.setSelected(false);
                        }
                    });

                    Platform.runLater(() -> AMScorePanel.setSelected(finalLine.contains("-scorepanel")));

                    Platform.runLater(() -> AMScoreBezel.setSelected(finalLine.contains("-scorebezel")));

                    Platform.runLater(() -> AMEightBit.setSelected(finalLine.contains("-8bit_overlay")));

                    Platform.runLater(() -> AMManyMouse.setSelected(finalLine.contains("-manymouse")));

                    Platform.runLater(() -> {
                        if (finalLine.contains("-opengl")) {
                            AMopengl.setSelected(true);
                            AMvulkan.setSelected(false);
                        } else {
                            AMopengl.setSelected(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains("-vulkan")) {
                            AMvulkan.setSelected(true);
                            AMopengl.setSelected(false);
                        } else {
                            AMvulkan.setSelected(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains("-gamepad")) {
                            AMgamepad.setSelected(true);
                        } else {
                            AMgamepad.setSelected(false);
                        }
                    });

                    Platform.runLater((() -> {
                        if (finalLine.contains("-nocrosshair")) {
                            AMnocrosshair.setSelected(true);
                        } else {
                            AMnocrosshair.setSelected(false);
                        }
                    }));

                    Platform.runLater(() -> {
                        if (finalLine.contains("-original_overlay")) {
                            AMOrginalOverlay.setSelected(true);
                        } else {
                            AMOrginalOverlay.setSelected(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains("-blend_sprites")) {
                            AMblendSprites.setSelected(true);
                        } else {
                            AMblendSprites.setSelected(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains("-texturestream")) {
                            AMtexturestream.setSelected(true);
                        } else {
                            AMtexturestream.setSelected(false);
                        }
                    });

                    Platform.runLater(() -> {
                        if (finalLine.contains("-texturetarget")) {
                            AMtexturetarget.setSelected(true);
                        } else {
                            AMtexturetarget.setSelected(false);
                        }
                    });



                    /*Platform.runLater(() -> {

                        String usbScoreString = usbScoreBoardFound.group(1);
                        String usbScoreString = "com12";
                        System.out.println("Setting USB Scoreboard to: " + usbScoreBoardFound.group(1));
                        if (finalLine.contains("-usbscoreboard")) {
                            AMusbscore.setSelected(true);
                            AMusbscoreFill.setDisable(false);
                            AMusbscoreFill.setText(usbScoreString);
                        } else {
                            AMusbscore.setSelected(false);
                            AMusbscoreFill.setDisable(true);
                        }
                    }); */
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sinden.setSelected(false);
            sindenFill1.setDisable(true);
            sindenFill2.setDisable(true);
            AMforceAspect.setSelected(false);
            fullScreen.setSelected(false);
            jsrange.setSelected(false);
            AMjsrangeFill.setDisable(true);
            AMscaleFactor.setSelected(false);
            AMScaleFactorSlider.setDisable(true);
            AMScaleFSliderView.setDisable(true);
            AMscaleAlpha.setSelected(false);
            AMScanAlphaSilder.setDisable(true);
            AMScanASliderView.setDisable(true);
            AMbezelCheckBox.setSelected(false);
        }
    }

    /**
     * Method to read config line with attributes for AM games and set the values in the GUI for single config for each AM game
     */
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
                    Pattern checkScaleFactor = Pattern.compile("-scalefactor\\s+([1-9]|100)");
                    Matcher scaleFacFound = checkScaleFactor.matcher(line);
                    System.out.println(scaleFacFound);
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
                                break;
                        }
                        if (jsFound.find()) {
                            String jsRange = jsFound.group(1);
                            jsrange.setSelected(true);
                            AMjsrangeFill.setDisable(false);
                            AMjsrangeFill.getValueFactory().setValue(Integer.parseInt(jsRange));
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
                        AMjsrangeFill.setDisable(false);
                        AMjsrangeFill.getValueFactory().setValue(Integer.parseInt(jsFound.group(1)));
                    } else {
                        AMjsrangeFill.setDisable(true);
                        AMjsrangeFill.getValueFactory().setValue(1);
                    }

                    if (line.contains("-fullscreen")) {
                        fullScreen.setSelected(true);
                    } else {
                        fullScreen.setSelected(false);
                    }
                    if (line.contains("-force_aspect_ratio")) {
                        AMforceAspect.setSelected(true);
                    } else {
                        AMforceAspect.setSelected(false);
                    }
                    if (line.contains("-scalefactor")) {
                        AMscaleFactor.setSelected(true);
                        AMScaleFactorSlider.setDisable(false);
                        AMScaleFSliderView.setDisable(false);

                        // Extracting the scale factor value from the line
                        int startIndex = line.indexOf("-scalefactor") + "-scalefactor".length();
                        String scaleFac = line.substring(startIndex).trim(); // trimming any leading or trailing whitespace
                        int scaleFacInt = Integer.parseInt(scaleFac);
                        System.out.println(scaleFacInt);
                        AMScaleFactorSlider.setValue(scaleFacInt);
                        AMScaleFSliderView.setText(scaleFacInt + "%");
                    } else {
                        AMscaleFactor.setSelected(false);
                        AMScaleFactorSlider.setDisable(true);
                        AMScaleFSliderView.setDisable(true);
                    }
                    if (line.contains("-scanline_alpha")) {
                        AMscaleAlpha.setSelected(true);
                        AMScanAlphaSilder.setDisable(false);
                        AMScanASliderView.setDisable(false);
                        // Extracting the scale value from the line
                        int startIndex = line.indexOf("-scanline_alpha") + "-scanline_alpha".length();
                        String scaleAlp = line.substring(startIndex).trim(); // trimming any leading or trailing whitespace
                        int scaleAlpInt = Integer.parseInt(scaleAlp);
                        System.out.println(scaleAlpInt);
                        AMScanAlphaSilder.setValue(scaleAlpInt);
                        AMScanASliderView.setText(Integer.toString(scaleAlpInt) + "");
                    } else {
                        AMscaleAlpha.setSelected(false);
                        AMScanAlphaSilder.setDisable(true);
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
                    if (line.contains ("-scanlines")) {
                        AMscanlines.setSelected(true);
                    } else {
                        AMscanlines.setSelected(false);
                    }
                }
            } else {
                sinden.setSelected(false);
                sindenFill1.setDisable(true);
                sindenFill2.setDisable(true);
                AMforceAspect.setSelected(false);
                fullScreen.setSelected(false);
                jsrange.setSelected(false);
                AMjsrangeFill.setDisable(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to write config line with attributes for AM games and set the values in the GUI for single & AIO config
     */
    public void AMGameConfig(MouseEvent mouseEvent) {
        boolean sindenChecked = sinden.isSelected();
        String sindenFieldOne = sindenFill1.getValue().toString();
        String sindenFieldTwo = sindenFill2.getSelectionModel().getSelectedItem().toString();
        boolean fullScreenChecked = fullScreen.isSelected();
        boolean AMgamepadChecked = AMgamepad.isSelected();
        boolean AMlinearScaleChecked = AMlinearscale.isSelected();
        boolean forceAspectChecked = AMforceAspect.isSelected();
        boolean jsRangeChecked = jsrange.isSelected();
        boolean scaleFacChecked = AMscaleFactor.isSelected();
        boolean scaleAlpha = AMscaleAlpha.isSelected();
        boolean AMbezel = AMbezelCheckBox.isSelected();
        boolean AMtiphatSelect = AMtiphatBox.isSelected();
        boolean AMverticalScreenSelect = AMverticalscreen.isSelected();
        boolean AMscanlinesCheck = AMscanlines.isSelected();
        boolean AMlinearScaleCheck = AMlinearscale.isSelected();

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
            options += " -sinden " + sindenFieldOne + " " + color;
        }
        if (fullScreen.isSelected()) {
            options += " -fullscreen";
        }
        if (AMgamepad.isSelected()) {
            options += " -gamepad";
        }
        if (AMlinearscale.isSelected()) {
            options += " -linear_scale";
        }
        if (AMforceAspect.isSelected()) {
            options += " -force_aspect_ratio";
        }
        if (AMignoreAspectRatio.isSelected()) {
            options += " -ignore_aspect_ratio";
        }
        if (jsrange.isSelected()) {
            options += " -js_range " + AMjsrangeFill.getValue().toString();
        }
        if (AMscaleFactor.isSelected()) {
            int scaleValue = (int) AMScaleFactorSlider.getValue();
            options += " -scalefactor " + scaleValue;
        }
        if (AMscaleAlpha.isSelected()) {
            int alphaValue = (int) AMScanAlphaSilder.getValue();
            options += " -scanline_alpha " + alphaValue;
        }
        if (AMbezel && AMbezelFile.length() > 0) {
            options += " -bezel " + AMbezelFile;
        }
        if (AMtiphatBox.isSelected()) {
            options += " -tiphat";
        }
        if (AMverticalscreen.isSelected()) {
            options += " -vertical_screen";
        }
        if (AMscanlines.isSelected()) {
            options += " -scanlines";
        }
        if (AMScoreBezel.isSelected()) {
            options += " -scorebezel";
        }
        if (AMScorePanel.isSelected()) {
            options += " -scorepanel";
        }
        if (AMEightBit.isSelected()) {
            options += " -8bit_overlay";
        }
        if (AMManyMouse.isSelected()) {
            options += " -manymouse";
        }
        if (AMvulkan.isSelected()) {
            options += " -vulkan";
        }
        if (AMopengl.isSelected()) {
            options += " -opengl";
        }
        if(AMnocrosshair.isSelected()) {
            options += " -nocrosshair";
        }
        if (AMOrginalOverlay.isSelected()) {
            options += " -original_overlay";
        }
        if(AMblendSprites.isSelected()) {
            options += " -blend_sprites";
        }
        if(AMtexturestream.isSelected()) {
            options += " -texturestream";
        }
        if((AMtexturetarget.isSelected())) {
            options += " -texturetarget";
        }
        if(AMScanlineShunt.isSelected()) {
            options += " -scanline_shunt " + (int) AMscanShuntSlider.getValue();
        }
        if(AMVerticalStretch.isSelected()) {
            options += " -vertical_stretch " + (int) AMVerticalStretchSlider.getValue();
        }
        if (AMusbscore.isSelected()) {
            options += " -usbscoreboard " + AMusbscoreFill.getText();
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
                File pathToFile = new File("alert1.css");
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

    /**
     * Method to disable game selection list if one config for all is selected
     */
    public void multiGameConfigSelect(ActionEvent actionEvent) {
        int selection = multiSingleGames.getSelectionModel().getSelectedIndex();
        if (selection == 0) {
            configAmList.setDisable(true);
            setConfigAMPanelAIO();
        } else {
            configAmList.setDisable(false);
        }
    }

    /**
     * Method allowing game selection from the list of AM games on the GUI
     */
    public void onAMlistSelect(MouseEvent mouseEvent) {
        String amgameName = configAmList.getSelectionModel().getSelectedItem().toString();
        setConfigAMPanelSingle(amgameName);
    }

    /**
     * Method allowing game selection from the list of Dp games on the GUI
     */
    public void DPPathSelect(ActionEvent actionEvent) {
    }

    /**
     * Method to read the list of AM games from a file and put them in the list on the GUI
     */
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

    public void AMverticalScreenCheck(MouseEvent mouseEvent) {
        if (AMverticalscreen.isSelected())
            AMverticalscreen.setSelected(true);
        else
            AMverticalscreen.setSelected(false);
    }

    public void AMtiphatSelect(MouseEvent actionEvent) {
        if (AMtiphatBox.isSelected())
            AMtiphatBox.setSelected(true);
        else
            AMtiphatBox.setSelected(false);
    }

    public void AMselectedJsRange(MouseEvent mouseEvent) {
        if (jsrange.isSelected())
            AMjsrangeFill.setDisable(false);
        else
            AMjsrangeFill.setDisable(true);
    }

    public void selectedSinden(MouseEvent mouseEvent) {
        if (sinden.isSelected()) {
            sindenFill1.setDisable(false);
            sindenFill2.setDisable(false);
            AMManyMouse.setDisable(true);
        } else {
            sindenFill1.setDisable(true);
            sindenFill2.setDisable(true);
            AMManyMouse.setDisable(false);
        }
    }

    public void AMignoreAspectSelect(MouseEvent mouseEvent) {
        Platform.runLater(() -> {
            if (AMignoreAspectRatio.isSelected() == true) {
                AMforceAspect.setSelected(false);
                AMforceAspect.setDisable(true);
            } else {
                AMforceAspect.setDisable(false);
            }
        });
    }

    public void AMforceAspectSelect(MouseEvent mouseEvent) {
            if (AMforceAspect.isSelected() == true) {
                AMignoreAspectRatio.setSelected(false);
                AMignoreAspectRatio.setDisable(true);
            } else {
                AMignoreAspectRatio.setDisable(false);
            }
    }

    public void AMlinearscaleCheck(MouseEvent mouseEvent) {
            if (AMlinearscale.isSelected()) {
                AMlinearscale.setSelected(true);
            } else {
                AMlinearscale.setSelected(false);
            }
    }

    public void AMscanlinesCheck(MouseEvent mouseEvent) {

        Platform.runLater(() -> {
        if (AMscanlines.isSelected() == true) {
                AMscanlines.setSelected(true);
            } else {
                AMscanlines.setSelected(false);
            }
        });
    }

    public void AMOpenglCheck(MouseEvent mouseEvent) {
        if (AMopengl.isSelected() == true) {
            AMvulkan.setSelected(false);
        }
    }

    public void AMVulkanCheck(MouseEvent mouseEvent) {
        if (AMvulkan.isSelected() == true) {
            AMopengl.setSelected(false);
        }
    }

    public void onAMScalefactorCheck(MouseEvent mouseEvent) {
        if (AMscaleFactor.isSelected()) {
            AMScaleFactorSlider.setDisable(false);
            AMScaleFSliderView.setDisable(false);
        } else {
            AMScaleFactorSlider.setDisable(true);
            AMScaleFSliderView.setDisable(true);
        }
    }

    /*public void AMScaleFset(MouseEvent mouseEvent) {
        // This method can be used if you need to handle specific mouse events separately
        int scale = (int) AMScaleFactorSlider.getValue();
        AMScaleFSliderView.setText(Integer.toString(scale) + "%");
    }*/

    public void AMgamepadCheck(MouseEvent mouseEvent) {
        if (AMgamepad.isSelected()) {
            AMgamepad.setSelected(true);
        } else {
            AMgamepad.setSelected(false);
        }
    }

    public void onAMScanAlphaCheck(MouseEvent mouseEvent) {
        if (AMScanAlphaSilder.isDisable()) {
            AMScanAlphaSilder.setDisable(false);
            AMScanASliderView.setDisable(false);
        } else {
            AMScanAlphaSilder.setDisable(true);
            AMScanASliderView.setDisable(true);
        }
    }

   /* public void AMScanAlphaSet(MouseEvent mouseEvent) {
        int scale = (int) AMScanAlphaSilder.getValue();
        AMScanASliderView.setText(Integer.toString(scale));
    }*/

    public void AMbezelChecked (MouseEvent mouseEvent){
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

    public void AMScorePanelCheck(MouseEvent mouseEvent) {
        if (AMScorePanel.isSelected()) {
            AMScorePanel.setSelected(true);
        } else {
            AMScorePanel.setSelected(false);
        }
    }

    public void AMScoreBezelCheck(MouseEvent mouseEvent) {
        if (AMScoreBezel.isSelected()) {
            AMScoreBezel.setSelected(true);
        } else {
            AMScoreBezel.setSelected(false);
        }
    }

    public void AMEightBitCheck(MouseEvent mouseEvent) {
        if (AMEightBit.isSelected()) {
            AMEightBit.setSelected(true);
        } else {
            AMEightBit.setSelected(false);
        }
    }

    public void AMManyMouseCheck(MouseEvent mouseEvent) {
        if (AMManyMouse.isSelected()) {
            AMManyMouse.setSelected(true);
        } else {
            AMManyMouse.setSelected(false);
        }
    }

    public void AMcrosshairCheck(MouseEvent mouseEvent) {
        if (AMnocrosshair.isSelected()) {
            AMnocrosshair.setSelected(true);
        } else {
            AMnocrosshair.setSelected(false);
        }
    }

    public void AMorginaloverlayCheck(MouseEvent mouseEvent) {
        if (AMOrginalOverlay.isSelected()) {
            AMOrginalOverlay.setSelected(true);
        } else {
            AMOrginalOverlay.setSelected(false);
        }
    }

    public void AMblendSpritesCheck(MouseEvent mouseEvent) {
                if (AMblendSprites.isSelected()) {
                    AMblendSprites.setSelected(true);
                } else {
                    AMblendSprites.setSelected(false);
                }
    }

    public void AMtexturesCheck(MouseEvent mouseEvent) {
        if (AMtexturestream.isSelected() == true) {
            AMtexturetarget.setSelected(false);
        }
    }

    public void AMtexturetargetCheck(MouseEvent mouseEvent) {
        if (AMtexturetarget.isSelected() == true) {
            AMtexturestream.setSelected(false);
        }
    }

    public void onAMScanlineShuntCheck(MouseEvent mouseEvent) {
        if (AMScanlineShunt.isSelected()) {
            AMScanlineShunt.setSelected(true);
            AMScanlinesShuntView.setDisable(false);
            AMscanShuntSlider.setDisable(false);
        } else {
            AMScanlineShunt.setSelected(false);
            AMScanlinesShuntView.setDisable(true);
            AMscanShuntSlider.setDisable(true);
        }
    }

    public void onAMVerticalStretchCheck(MouseEvent mouseEvent) {
        if (AMVerticalStretch.isSelected()) {
            AMVerticalStretch.setSelected(true);
            AMVerticalStretchSlider.setDisable(false);
            AMVerticalStretchView.setDisable(false);
        } else {
            AMVerticalStretch.setSelected(false);
            AMVerticalStretch.setDisable(true);
            AMVerticalStretchView.setDisable(true);
        }
    }

    public void onAMusbscoreCheck(MouseEvent mouseEvent) {
        if (AMusbscore.isSelected()) {
            AMusbscore.setSelected(true);
            AMusbscoreFill.setDisable(false);
        } else {
            AMusbscore.setSelected(false);
        }
    }


    @FXML
    /**
     * Method to save additional handwritten attributes for AM games
     */
    private void AMAddAditionalArgs(MouseEvent mouseEvent) {
        try {
            ClipboardContent content = new ClipboardContent();
            if (multiSingleGames.getSelectionModel().getSelectedIndex() == 0) {
                content.putString("cfg\\aio\\aioGames.ada");
            } else {
                if (configAmList.getSelectionModel().getSelectedItem() == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You have to select a game from the list", ButtonType.OK);
                    alert.getDialogPane().getStylesheets().add("alert1.css");
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
            // Pass the current and previous stages to the controller
            controller.setStages(mainStage, stage);
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

    public void onAMblankSearchCheck(MouseEvent mouseEvent) {
    }

    public void onAmBlankSkipsCheck(MouseEvent mouseEvent) {
    }

    @Override
    /**
     * Method to initialize the GUI
     */
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
        AMjsrangeFill.setDisable(true);
        AMScaleFactorSlider.setDisable(true);
        AMpngBezel.setDisable(true);
        AMbezelFile = "";


        SpinnerValueFactory<Integer> factory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        AMjsrangeFill.setValueFactory(factory1);
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
       /* String systemInfo = ("OS Name: " + System.getProperty(nameOS)
                + "\nOS Version: " + System.getProperty(versionOS)
                + "\nArchitecture: " + System.getProperty(architectureOS)
                + "\nAvailable processors (cores): " + Runtime.getRuntime().availableProcessors()
                + "\nFree memory (bytes): " + Runtime.getRuntime().freeMemory()
                + "\nTotal memory available to JVM (bytes): " + Runtime.getRuntime().totalMemory()
                + "\nJava Version: " + System.getProperty(javaVersion)
                + diskInfo);*/
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
        // System.out.println("GFX to function -> " + gfx);
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
        AMScanAlphaSilder.setDisable(true);
        AMScanASliderView.setDisable(true);
        AMScanAlphaSilder.valueProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
            int scanAlpha = (int) AMScanAlphaSilder.getValue();
            AMScanASliderView.setText(String.valueOf(scanAlpha));
        });
        AMscanShuntSlider.setDisable(true);
        AMScanlinesShuntView.setDisable(true);
        AMscanShuntSlider.valueProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
            int scanShunt = (int) AMscanShuntSlider.getValue();
            AMScanlinesShuntView.setText(String.valueOf(scanShunt));
        });
        AMVerticalStretchSlider.setDisable(true);
        AMVerticalStretchView.setDisable(true);
        AMVerticalStretchSlider.valueProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
            int scanShunt = (int) AMVerticalStretchSlider.getValue();
            AMVerticalStretchView.setText(String.valueOf(scanShunt));
        });
        getsavedTempAttrib();
        readAMGamesFileList();

    }

    public void DPScorePanelCheck(MouseEvent mouseEvent) {
    }

}