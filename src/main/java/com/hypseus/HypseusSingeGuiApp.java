package com.hypseus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class HypseusSingeGuiApp extends Application {

    private final int MIN_WIDTH = 1500;
    private final int MIN_HEIGHT = 700;
    protected static double screenWidth;
    protected static double screenHeight;

    protected static double minWidth;
    protected static double minHeight;
    private static final String CONFIG_PATH = "cfg/os.cfg";
    private static final String OS_NAME = System.getProperty("os.name");
    String time =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms").format(Calendar.getInstance().getTime());

    @Override
    public void start(Stage stage) {

        checkAndHandleOSConfiguration();

        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(HypseusGuiController.class.getResource("hypseusSingeGui.fxml"));
            screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            int sWidth = (int) screenWidth;
            int sHeight = (int) screenHeight;
            System.out.println(sWidth + " x " + sHeight);
            addToLog(time + " usable screen resolution: " + sWidth + " x " + sHeight);

            if ((screenWidth >= 1600 && screenHeight >= 800) && (screenWidth < 1920 || screenHeight < 1080)) {
                minWidth = 1000;
                minHeight = 600;
            } else if (((screenWidth >= 1920 && screenWidth < 2400) || (screenHeight >= 1080 && screenHeight < 1200))) {
                minWidth = 1100;
                minHeight = 800;
            } else if (screenWidth >= 2400 && screenHeight >= 1200) {
                minWidth = 1400;
                minHeight = 1000;
            } else {
                showResolutionAlert(stage);
            }

            final Scene scene = new Scene(fxmlLoader.load(), minWidth, minHeight);
    //        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("com/hypseus/style.css")).toExternalForm());
              scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm());
            stage.setTitle("Hypseus Singe GUI");
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);
            stage.setMaxWidth(screenWidth);
            stage.setMaxHeight(screenHeight);
            stage.setScene(scene);
    //        stage.getIcons().add(new Image(HypseusSingeGuiApp.class.getResourceAsStream("com/hypseus/img/hypseus-logo_thumb.png")));
                stage.getIcons().add(new Image(HypseusSingeGuiApp.class.getResourceAsStream("img/hypseus-logo_thumb.png")));
            stage.show();
        } catch (Exception ex) {
            System.out.println(ex);
            addToLog(time  + " " + "screen resolution problems: " + ex);
        }
    }

    private void showResolutionAlert(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Resolution Alert");
        alert.setHeaderText("Screen resolution is too low.");
        alert.setContentText("Screen resolution must be set to a usable resolution of at least 1600x1200 to allow the GUI application to run.");
        alert.showAndWait();
    }

    private void checkAndHandleOSConfiguration() {
        File osFile = new File(CONFIG_PATH);
        String storedOSName = readOSFromFile(osFile);

        if (storedOSName == null || storedOSName.isEmpty() || hasOSChanged(storedOSName)) {
            updateOSConfigFile(osFile);
            showOSChangeAlert();
        }
    }

    private String readOSFromFile(File osFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(osFile))) {
            return reader.readLine();
        } catch (IOException e) {
            addToLog(time + " " + "error while trying to read os config file: " + e.getMessage());
            return null;
        }
    }

    private boolean hasOSChanged(String storedOSName) {
        addToLog(time + " checking OS changes since last run");
        return (OS_NAME.contains("Windows") && !storedOSName.contains("Windows"))
                || (!OS_NAME.contains("Windows") && storedOSName.contains("Windows"));

    }

    private void updateOSConfigFile(File osFile) {
        try (FileWriter writer = new FileWriter(osFile)) {
            writer.write(OS_NAME);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            addToLog(time + " " + "OS type updated in OS config file");
        }
    }

    private void showOSChangeAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OS Change");
        alert.setContentText("Please reconfigure Hypseus GUI");
        alert.showAndWait();
        addToLog(time + " " + "OS has changed");
    }

    private void addToLog(String errorLog) {
        System.out.println(errorLog);
        File logFile = new File("runtime.log");
        try {
            FileWriter fileWriter = new FileWriter("runtime.log", true);
            if (!logFile.exists()) {
                fileWriter.write(errorLog);
            } else {
                fileWriter.append(errorLog);
                fileWriter.append(System.lineSeparator());
            }
            fileWriter.close();

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            addToLog(time + " " + "error occured while trying to manipulate runtime.log file: " + exception.getMessage());
        }
    }


    public static void main(String[] args) {
        launch();
    }

}