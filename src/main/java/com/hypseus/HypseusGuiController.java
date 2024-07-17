package com.hypseus;

import com.almasb.fxgl.animation.AnimationBuilder;
import javafx.fxml.FXML;
import java.nio.file.Paths;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HypseusGuiController implements Initializable {
    private static double screenWidth;
    private static double screenHeight;
    private static final int maxRetries = 4;
    private static final int retryDelayMs = 50;
    private ImageView stillPosterView;
    private ImageView stillLogoView;
    private PathCfg pathCfg;
    private String time =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms").format(Calendar.getInstance().getTime());
    String AMGameName;
    String AMadditionalArgs;

    @FXML
    private final HBox mainHBox = new HBox();
    @FXML
    private StackPane leftStackP, rightStackP, midStackP;
    @FXML
    private Button config, start;
    @FXML
    private ListView americanList;
    @FXML
    private MediaView demoView;
    @FXML
    private MediaPlayer demoPlayer = null;
    @FXML
    private BorderPane VideoPlay,logoPane;
    @FXML
    private Pane posterPane,comLogoPane;
    @FXML
    private GridPane GridButtons;
    @FXML
    private TextArea gameDescription;
    @FXML
    public static final ObservableList amData = FXCollections.observableArrayList();
    @FXML
    private TabPane amTab, dpTab;
    @FXML
    private ListView daphneList = new ListView();
    @FXML
    private ImageView comLogo;


    private void saveAmList(List<String> games) {
        try {
            FileWriter fileWriter = new FileWriter("amlist.lst");
            for (String game : games) {
                String name = game.substring(0, game.lastIndexOf("."));
                try {
                    fileWriter.write(name + "\n");
                } catch (IOException e) {
                    addToLog(time + " error 1 while writing to American Laser Disc games list file + " + e.getMessage());
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            addToLog(time + " error 2 while writing to American Laser Disc games list file + " + e.getMessage());
        }
    }

    public List<String> checkAMGameFolders(File fileCheck) {
        int count = 0;

        List<String> amFoundGames = new ArrayList<>();
        try {
            for (File file : fileCheck.listFiles())
                if (file.isDirectory() && file.getName().endsWith(".singe")) {
                    int found = 0;
                    for (File folderFile : file.listFiles()) {
                        if (folderFile.getName().endsWith(".cfg"))
                            found++;
                        if (folderFile.getName().contains("service.singe"))
                            found++;
                    }
                    if (found == 2) {
                        count++;
                        amFoundGames.add(file.getName());
                    }
                }


        } catch (Exception exception) {
            noPathSet();
            addToLog(time + " no path set to Amercican Laser Disc games folder");
        }
        return amFoundGames;
    }


    private void dataDeSerialize(String configFileName) {
        Gson dataDeser = new Gson();
        try {
            FileReader fileReader = new FileReader(configFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            pathCfg = dataDeser.fromJson(bufferedReader, PathCfg.class);
            bufferedReader.close();
        } catch (IOException e) {
            //    showWriteErrorWindow("Error while reading from config file");
            addToLog(time + " " + "error while reading from config file (deserialize): " + e.getMessage());
        }
    }

    @FXML
    private void onSelectTabAM() {
        try {
            comLogo.setImage(null);
            URL url = getClass().getResource("img/ALG_logo.png");
            Image stillLogo = new Image(url.toString());
            comLogo = new ImageView(stillLogo);
            comLogo.setPreserveRatio(false);
            comLogo.fitWidthProperty().bind(comLogoPane.widthProperty());
            comLogo.fitHeightProperty().bind(comLogoPane.heightProperty());
            comLogo.setSmooth(true);
            comLogoPane.getChildren().add(comLogo);
//            if (americanList != null)
//                americanList.getSelectionModel().selectFirst();
            try {
                URL path = getClass().getResource("img/nosignal.svi");
                String filePath = Paths.get(path.toURI()).toString();
                playDemo(filePath, false);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "@");
                addToLog(time + " " + "error on getting main AM ressources: " +  ex.getMessage());
            }
            showPoster("img/noposter.png");
            try {
                gameDescription.setText("No game selected.");
                addToLog(time + " " + "no games selected");
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "#");
                addToLog(time + " " + "error on getting game description: " + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("No AM logo");
            addToLog(time + " " + "no American Laser Disc logo file found: " + ex.getMessage());
        }
        //    gameSelected();
    }

    @FXML
    private void onSelectTabDp(Event event) {
        try {
            comLogo.setImage(null);
            URL url = getClass().getResource("img/Daphne_logo.png");
            Image stillLogo = new Image(url.toString());
            comLogo = new ImageView(stillLogo);
            comLogo.setPreserveRatio(false);
            comLogo.fitWidthProperty().bind(comLogoPane.widthProperty());
            comLogo.fitHeightProperty().bind(comLogoPane.heightProperty());
            comLogo.setSmooth(true);
            comLogoPane.getChildren().add(comLogo);
            try {
                URL path = getClass().getResource("img/nosignal.svi");
                String filePath = Paths.get(path.toURI()).toString();
                playDemo(filePath, false);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "##");
                addToLog(time + " " + "error on getting main DP ressources: " + ex.getMessage());
            }
            showPoster("img/noposter.png");
            try {
                gameDescription.setText("No game selected.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "@@");
                addToLog(time + " " + "error on getting DP game description: " + ex.getMessage());
            }
            americanList.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            System.out.println("No Daphne logo");
            addToLog(time + "No daphne logo file found: " + ex.getMessage());
        }
    }

    @FXML
    private void openConfig(Event event) {
        Parent root;
        try {
            Node node = (Node) event.getSource();
            Stage mainStage = (Stage) node.getScene().getWindow();
            mainStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("configGUI.fxml"));
            root = loader.load();
            loader.setController(this);
            Stage stage = new Stage();
            stage.setTitle("Configuration window");
            stage.getIcons().add(new Image(HypseusSingeGuiApp.class.getResourceAsStream("img/hypseus-logo_thumb.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        //    scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("com/hypseus/style.css")).toExternalForm());
            stage.minWidthProperty().setValue(screenWidth * 0.50);
            stage.minHeightProperty().setValue(screenHeight * 0.70);
            //    stage.maxWidthProperty().setValue(screenWidth * 0.50);
            //    stage.maxHeightProperty().setValue(screenHeight * 0.70);
            stage.showAndWait();
            mainStage.show();
            daphneListShow();
            dataDeSerialize("pconfig.cfg");
        } catch (IOException e) {
            System.out.println(e.getMessage());
           addToLog(time + " " + "error while opening configuration window: " + e.getMessage());
        }
    }

    @FXML
    private void gameSelected() {
        String selected = americanList.getSelectionModel().getSelectedItem().toString();
        addToLog(time + " " + "game selected: " + selected);
        System.out.println(selected);
    }

    private void americanListShow() {
        amData.clear();
        int count = 0;
        File config = new File("amlist.lst");
        if (config.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(config));
                String line;
                while ((line = br.readLine()) != null) {
                    amData.add(count++, line);
                }

            } catch (IOException e) {
                e.printStackTrace();
                addToLog(time + "error while reading American Laser Games list file: " + e.getMessage());
            }
        }
        americanList.setItems(amData);
        addToLog(time + " " + "American Laser Disc games list loaded" + " " + amData.size() + " games found");

    }

    private void daphneListShow() {
        // to do daphne games
        amData.clear();
        int count = 0;
        File config = new File("amlist.lst");
        if (config.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(config));
                String line;
                while ((line = br.readLine()) != null) {
                    amData.add(count++, line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        americanList.setItems(amData);

    }

    //TO DO - Daphne Games
/*    private void daphneListUpdate() {
        dpData.clear();
        int count = 0;
        File config = new File("daphnelist.lst");
        if (config.exists()) {
            try {
                BufferedReader br = new BufferedReader(new java.io.FileReader(config));
                String line;
                while ((line = br.readLine()) != null) {
                    amData.add(count++, line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        americanList.setItems(dpData);
    }

    private void daphneListShow() {
        dpData.clear();
        int count = 0;
        File config = new File("amlist.lst");
        if (config.exists()) {
            try {
                BufferedReader br = new BufferedReader(new java.io.FileReader(config));
                String line;
                while ((line = br.readLine()) != null) {
                    dpData.add(count++, line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dpList.setItems(dpData);
    }
 */

/*    private void playDemo(String videoPath, boolean isVideo) {
        if (videoPath != null) {
            try {
                // Create a temporary file for buffering
                File tempFile = File.createTempFile("buffered-video", ".tmp");
                tempFile.deleteOnExit(); // Cleanup automatically

                // Copy the original media file to the temporary file
                try (InputStream input = new FileInputStream(new File(videoPath));
                     OutputStream output = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }

                // Load the media from the temporary file
                Media media = new Media(tempFile.toURI().toString());

                // Dispose of any existing MediaPlayer instance
                if (demoPlayer != null) {
                    demoPlayer.dispose();
                }

                // Create a new MediaPlayer instance
                demoPlayer = new MediaPlayer(media);

                // Error handling for media errors
                demoPlayer.setOnError(() -> {
                    System.out.println("Error occurred: " + demoPlayer.getError().getMessage());
                });

                // Ready event handler
                demoPlayer.setOnReady(() -> {
                    // Check if the player is in the READY state
                    if (demoPlayer.getStatus() == MediaPlayer.Status.READY) {
                        // Configure and play the video
                        demoView.setMediaPlayer(demoPlayer);
                        demoView.setSmooth(true);
                        demoPlayer.seek(Duration.ZERO);
                        demoPlayer.setAutoPlay(true);
                        demoView.setPreserveRatio(false);
                        demoView.fitWidthProperty().bind(VideoPlay.widthProperty());
                        demoView.fitHeightProperty().bind(VideoPlay.heightProperty());
                        demoPlayer.setMute(true);
                        demoPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                        // Log player status
                        String status = demoPlayer.getStatus().toString();
                        System.out.println("SVP video player status: " + status);

                        demoPlayer.play();
                    } else {
                        System.out.println("MediaPlayer is not ready for playback. Current status: " + demoPlayer.getStatus());
                    }
                });

            } catch (Exception ex1) {
                System.out.println("Error occurred: " + ex1.getMessage());
            }
        }
    }
  */


    private void playDemo(String videoPath, boolean isVideo) {
        if (videoPath != null) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    AtomicInteger success = new AtomicInteger(0);
                    AtomicInteger retryCount = new AtomicInteger(0);

                    while (retryCount.get() < maxRetries && success.get() == 0) {
                        try {
                            // Create a temporary file for buffering
                            File tempFile = File.createTempFile("bufferedvideo", ".tmp");
                            tempFile.deleteOnExit(); // Cleanup automatically

                            // Copy the original media file to the temporary file
                            try (InputStream input = new FileInputStream(new File(videoPath));
                                 OutputStream output = new FileOutputStream(tempFile)) {
                                byte[] buffer = new byte[8192];
                                int bytesRead;
                                while ((bytesRead = input.read(buffer)) != -1) {
                                    output.write(buffer, 0, bytesRead);
                                }
                            }

                            // Load the media from the temporary file
                            Media media = new Media(tempFile.toURI().toString());

                            // Dispose of any existing MediaPlayer instance
                            if (demoPlayer != null) {
                                demoPlayer.dispose();
                            }

                            // Create a new MediaPlayer instance
                            demoPlayer = new MediaPlayer(media);

                            // Error handling for media errors
                            demoPlayer.setOnError(() -> {
                                handleError(demoPlayer.getError(), videoPath, isVideo, retryCount, success);
                            });

                            // Ready event handler
                            demoPlayer.setOnReady(() -> {
                                // Check if the player is in the READY state
                                if (demoPlayer.getStatus() == MediaPlayer.Status.READY) {
                                    Platform.runLater(() -> {
                                        // Configure and play the video
                                        demoView.setMediaPlayer(demoPlayer);
                                        demoView.setSmooth(true);
                                        demoPlayer.seek(Duration.ZERO);
                                        demoPlayer.setAutoPlay(true);
                                        demoView.setPreserveRatio(false);
                                        demoView.fitWidthProperty().bind(VideoPlay.widthProperty());
                                        demoView.fitHeightProperty().bind(VideoPlay.heightProperty());
                                        demoPlayer.setMute(true);
                                        demoPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                                        // Log player status
                                        String status = demoPlayer.getStatus().toString();
                                        System.out.println("SVP video player status: " + status);

                                        demoPlayer.play();
                                    });
                                    success.set(1); // Mark as successful
                                } else {
                                    System.out.println("MediaPlayer is not ready for playback. Current status: " + demoPlayer.getStatus());
                                    handleRetry(videoPath, isVideo, retryCount, success);
                                }
                            });

                            // Wait for the player to be ready
                            Thread.sleep(100);
                            if (success.get() == 1) {
                                break;
                            }

                        } catch (Exception ex1) {
                            System.out.println("Error occurred: " + ex1.getMessage());
                            handleRetry(videoPath, isVideo, retryCount, success);
                        }
                    }
                    return null;
                }
            };

            new Thread(task).start();
        }
    }

    private void handleError(MediaException error, String videoPath, boolean isVideo, AtomicInteger retryCount, AtomicInteger success) {
        if (error != null) {
            System.out.println("Error occurred: " + error.getMessage());
            addToLog(time + " Error occurred: " + error.getMessage());
        } else {
            System.out.println("An unknown error occurred in the MediaPlayer.");
            addToLog(time + " An unknown error occurred in the MediaPlayer.");
        }
        handleRetry(videoPath, isVideo, retryCount, success);
    }

    private void handleRetry(String videoPath, boolean isVideo, AtomicInteger retryCount, AtomicInteger success) {
        if (retryCount.incrementAndGet() < maxRetries) {
            System.out.println("Retrying to load the media file... Attempt " + retryCount.get());
            addToLog(time + " Retrying to load the media file... Attempt " + retryCount.get());
            try {
                Thread.sleep(retryDelayMs);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println("Max retries reached. Unable to load the media file. Loading fallback video.");
            addToLog(time + " Max retries reached. Unable to load the media file. Loading fallback video.");
            Platform.runLater(this::loadFallbackVideo);
            success.set(1); // Prevent further retries
        }
    }

    private void loadFallbackVideo() {
        Platform.runLater(() -> {
            try {
                String fallbackVideoPath = "img/nosignal.svi"; // Adjust the path to the actual fallback video location
                // Similar logic to load and play the fallback video
                File tempFile = File.createTempFile("bufferedvideo", ".tmp");
                tempFile.deleteOnExit(); // Cleanup automatically

                // Copy the fallback media file to the temporary file
                try (InputStream input = new FileInputStream(new File(fallbackVideoPath));
                     OutputStream output = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }

                // Load the media from the temporary file
                Media media = new Media(tempFile.toURI().toString());

                // Dispose of any existing MediaPlayer instance
                if (demoPlayer != null) {
                    demoPlayer.dispose();
                }

                // Create a new MediaPlayer instance
                demoPlayer = new MediaPlayer(media);

                // Error handling for media errors
                demoPlayer.setOnError(() -> {
                    MediaException error = demoPlayer.getError();
                    if (error != null) {
                        System.out.println("Error occurred: " + error.getMessage());
                        addToLog(time + " Error occurred: " + error.getMessage());
                    } else {
                        System.out.println("An unknown error occurred in the MediaPlayer.");
                        addToLog(time + " An unknown error occurred in the MediaPlayer.");
                    }
                });

                // Ready event handler
                demoPlayer.setOnReady(() -> {
                    // Check if the player is in the READY state
                    if (demoPlayer.getStatus() == MediaPlayer.Status.READY) {
                        // Configure and play the video
                        demoView.setMediaPlayer(demoPlayer);
                        demoView.setSmooth(true);
                        demoPlayer.seek(Duration.ZERO);
                        demoPlayer.setAutoPlay(true);
                        demoView.setPreserveRatio(false);
                        demoView.fitWidthProperty().bind(VideoPlay.widthProperty());
                        demoView.fitHeightProperty().bind(VideoPlay.heightProperty());
                        demoPlayer.setMute(true);
                        demoPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                        // Log player status
                        String status = demoPlayer.getStatus().toString();
                        System.out.println("Fallback video player status: " + status);
                        addToLog(time + " Fallback video player status: " + status);

                        demoPlayer.play();
                    } else {
                        System.out.println("MediaPlayer is not ready for playback. Current status: " + demoPlayer.getStatus());
                        addToLog(time + " MediaPlayer is not ready for playback. Current status: " + demoPlayer.getStatus());
                    }
                });

            } catch (Exception ex) {
                System.out.println("Failed to load fallback video: " + ex.getMessage());
                addToLog(time + " Failed to load fallback video: " + ex.getMessage());
            }
        });
    }
    private void showLogo() {
        try {
            URL url = getClass().getResource("img/hypseus-minilogo.png");
            Image stillLogo = new Image(url != null ? url.toString() : null);
            stillLogoView = new ImageView(stillLogo);
            stillLogoView.setPreserveRatio(false);
            stillLogoView.fitWidthProperty().bind(logoPane.widthProperty());
            stillLogoView.fitHeightProperty().bind(logoPane.heightProperty());
            stillLogoView.setSmooth(true);
            logoPane.getChildren().add(stillLogoView);
        } catch (Exception ex) {
            System.out.println("No logo");
            addToLog(time + " no logo file avalaible: " + ex.getMessage());
        }
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
        }
    }

    private void noPathSet() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add("com/hypseus/alert.css");
        alert.setTitle("Further configuration needed");
        alert.setHeaderText("Games folders paths not found");
        alert.setContentText("Please Choose proper folders for games\n in the configuration applet");
        alert.showAndWait();
        addToLog(time + " " + alert.getContentText());
    }

    private void showPoster(String poster) {
            File pathToFile = new File(poster);
            if (pathToFile.exists()) {
            stillPosterView = new ImageView(pathToFile.toURI().toString());
            stillPosterView.setPreserveRatio(false);
            stillPosterView.fitWidthProperty().bind(posterPane.widthProperty());
            stillPosterView.fitHeightProperty().bind(posterPane.heightProperty());
            stillPosterView.setSmooth(true);
            posterPane.getChildren().add(stillPosterView);
        } else  {
            String noPoster = "img/noposter.png";
            URL url = getClass().getResource(noPoster);
            Image stillPoster = new Image(url.toString());
            stillPosterView = new ImageView(stillPoster);
            stillPosterView.setPreserveRatio(false);
            stillPosterView.fitWidthProperty().bind(posterPane.widthProperty());
            stillPosterView.fitHeightProperty().bind(posterPane.heightProperty());
            stillPosterView.setSmooth(true);
            posterPane.getChildren().add(stillPosterView);
        }
    }

    private void showGameInfo(String gameInfo) {
        try {
            FileReader input = new FileReader(gameInfo);
            //        BufferedReader gameFile = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(gameInfo)));
            BufferedReader gameFile = new BufferedReader(input);
            String line;
            String text = "";
            while ((line = gameFile.readLine()) != null) {
                text = text + line + "\n";
            }
            gameDescription.setText(text);
        } catch (Exception ex) {
            gameDescription.setText("No game info available for this selection");
            addToLog(time + " " + ex.getMessage());
        }
    }

    public void onStartGame(ActionEvent actionEvent) {
        String selected = "";
        String gamePath = "";
        String lineConf = "";
        String linePath = "";
        try {
            selected = americanList.getSelectionModel().getSelectedItem().toString();
            AMGameName = selected;
            addToLog(time + " " + "game selected: " + selected);
            readArgs();
            String os = System.getProperty("os.name");
            if (pathCfg.gSelectionType.contains("allInOne")) {
                File conf = new File("cfg/aio/aioGames.cfg");
                try {
                    BufferedReader readConf = new BufferedReader(new FileReader(conf));
                    lineConf = readConf.readLine();
                    readConf.close();
                } catch (IOException ex) {
                    addToLog(time + " " + ex);
                }
                File path = new File("path.cfg");
                try {
                    BufferedReader readPath = new BufferedReader(new FileReader(path));
                    linePath = readPath.readLine();
                    readPath.close();
                } catch (IOException ex) {
                    addToLog(time + " " + ex);
                }
                lineConf = lineConf +  " "  + AMadditionalArgs;
                gamePath = linePath + " singe vldp -framefile " + pathCfg.americanPath + "\\" + selected + ".singe" + "\\" + selected + ".txt " +
                        "-script " + pathCfg.americanPath + "\\" + selected + ".singe" + "\\" + selected + ".singe" + lineConf;
                System.out.println("--gamePath--> " +  gamePath);
            } else {
                selected = "cfg\\single\\" + selected + ".cfg";
                File gameFile = new File(selected);
                if (gameFile.exists()) {
                    try {
                        FileReader fileReader = new FileReader(selected);
                        BufferedReader file = new BufferedReader(fileReader);
                        gamePath = file.readLine();
                        file.close();
                        fileReader.close();
                    } catch (Exception exception) {
                        addToLog(time + " " + exception.getMessage());
                    }
                    gamePath = gamePath + " " + AMadditionalArgs;
                    System.out.println("--gamePath--> " +  gamePath);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No game config");
                    alert.setContentText("Please configure your game");
                    alert.showAndWait();
                    addToLog(time + " " + alert.getContentText() + ": " + selected);
                }
            }
            if (os.contains("Windows")) {
                runOnWindows(gamePath);
            } else {
                runOnUnix(gamePath);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No game selected");
            alert.setContentText("Please select a game to play");
            alert.showAndWait();
            addToLog(time + " " + alert.getTitle() + " " + alert.getContentText());
        }
    }


    private void runOnWindows(String path) {
        addToLog(time + " " + "launching game: " + path );
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", path);
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
                addToLog("External hypseus binary log: " + line);
            }
        } catch (Exception launcexcp) {
            addToLog(time + " -" + launcexcp.getMessage());
            System.out.println("System message: " + launcexcp.getMessage());
        }
    }
    private void runOnUnix(String path) {
        addToLog(time + " " + "launching game: " + path );
        ProcessBuilder builder = new ProcessBuilder(
                "bash", "-c",  path);
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
                addToLog("External hypseus binary log: " + line);
            }
        } catch (Exception launcexcp) {
            addToLog(time + " " + launcexcp.getMessage());
            System.out.println("System message: " + launcexcp.getMessage());
        }
    }

    private void readArgs() {
        if (pathCfg.gSelectionType.contains("allInOne")) {
            AMGameName = "cfg\\aio\\" + "aioGames" + ".ada";
        } else {
            AMGameName = "cfg\\single\\" + AMGameName + ".ada";
        }
        String argsFile = AMGameName;
        System.out.println("--gameName1--> " + AMGameName);
        System.out.println("----> " + argsFile);
        File file = new File(argsFile);
        AMadditionalArgs = "";
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new java.io.FileReader(argsFile));
                String line = br.readLine();
                if (line != null) {
                    AMadditionalArgs = line;

                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String nameOS = "os.name";
        final String versionOS = "os.version";
        final String architectureOS = "os.arch";
        final String javaVersion = "java.version";

        screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        int sWidth = (int) screenWidth;
        int sHeight = (int) screenHeight;
        String screenRes = sWidth + " x " + sHeight;
        String sysInfo = "OS Name: " + System.getProperty(nameOS)
                + "\nOS Version: " + System.getProperty(versionOS)
                + "\nArchitecture: " + System.getProperty(architectureOS)
                + "\nJava Version: " + System.getProperty(javaVersion);
        addToLog(time + " " + "using usable screen resolution of " + screenRes);
        addToLog(time +  " " + "System information: " + "\n" + sysInfo);

        System.out.println(screenRes);
        System.out.println(sysInfo);

        midStackP.prefHeightProperty().bind(mainHBox.heightProperty());
        leftStackP.getStyleClass().add("HypseusGuiColumns");
        midStackP.getStyleClass().add("HypseusGuiColumns");
        rightStackP.getStyleClass().add("HypseusGuiColumns");
        //    start.getStyleClass().add("Button50L");
        //    start.setStyle("-fx-font-weight: normal;"); // Set initial font weight
        //    start.setOnMouseEntered(event -> {
        //        start.setStyle("-fx-font-weight: bold;");
        //    });
        //    start.setOnMouseExited(event -> {
        //        start.setStyle("-fx-font-weight: normal;");
        //    });
        //    config.getStyleClass().add("Button50R");
        //    config.setStyle("-fx-font-weight: normal;"); // Set initial font weight
        //    config.setOnMouseEntered(event -> {
        //        config.setStyle("-fx-font-weight: bold;");
        //    });
        //    config.setOnMouseExited(event -> {
        //        config.setStyle("-fx-font-weight: normal;");
        //    });

        try {
            URL path = getClass().getResource("img/nosignal.svi");
            String filePath = Paths.get(path.toURI()).toString();
            playDemo(filePath, false);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        showPoster("img/noposter.png");
        try {
            gameDescription.setText("No game selected.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            URL daphneUrl = getClass().getResource("img/Daphne_logo.png");
            Image stillLogo = new Image(daphneUrl.toString());
            comLogo = new ImageView(stillLogo);
            comLogo.setPreserveRatio(false);
            comLogo.fitWidthProperty().bind(comLogoPane.widthProperty());
            comLogo.fitHeightProperty().bind(comLogoPane.heightProperty());
            comLogo.setSmooth(true);
            comLogoPane.getChildren().add(comLogo);
        } catch (Exception ex) {
            System.out.println("No Daphne logo");
        }

        showLogo();
        GridButtons.getStyleClass().add("GridButtons");
        americanList.getSelectionModel().selectedItemProperty().addListener((observableValue, old, t1) -> {
            String selected ="";
            if (americanList.getSelectionModel().getSelectedItem() != null) {
                selected = (String) americanList.getSelectionModel().getSelectedItem();
                String shortselected;
                boolean isVideo;
                PathCfg cfg = new PathCfg();
                cfg.readCurrentcfg();

                try {
                    int number = selected.indexOf("-");
                    if (number != -1) {
                        shortselected = selected.substring(0, number);
                    } else {
                        shortselected = selected;
                    }
                    String videoPath="";
                    String osInfo = System.getProperty(nameOS);
                    if (osInfo.contains("Windows")) {
                        videoPath = cfg.getAmericanPath() + "\\" + selected + ".singe" + "\\" + shortselected + ".svp";
                    } else {
                        videoPath = cfg.getAmericanPath() + "/" + selected + ".singe" + "/" + shortselected + ".svp";
                    }
                    File file = new File(videoPath);
                    isVideo = true;
                    if (!file.exists()) {
                        isVideo = false;
                        URL path = getClass().getResource("img/nosignal.svi");
                        String filePath = Paths.get(path.toURI()).toString();
                        videoPath = filePath;
                    }
                    String pathWiki = new File("wiki").getAbsolutePath();
                    String pathImg = new File("gimg").getAbsolutePath();
                    System.out.println(pathWiki + " " + pathImg);

                    showPoster(pathImg + "\\" +  shortselected + ".jpg");
                    System.out.println(shortselected);
                    showGameInfo(pathWiki + "\\" + shortselected + ".info");
                    System.out.println("-->" + videoPath + "<--");
                    addToLog(time + " playing svp: " + videoPath);
                    playDemo(videoPath, isVideo);

                } catch (Exception ex1) {
                    System.out.println(time + " Error occurred while getting file size: " + ex1.getMessage());
                }
            }
        });

        File config = new File("pconfig.cfg");
        if (!config.exists()) {
            try {
                config.createNewFile();
                FileWriter writeConfig = new FileWriter(config);
                writeConfig.write("{\n" +
                        "  \"daphnePath\": \"\",\n" +
                        "  \"americanPath\": \"\"\n" +
                        "}");
                writeConfig.close();
                noPathSet();
            } catch (IOException e) {
                noPathSet();
            }
        } else {
            dataDeSerialize("pconfig.cfg");
            List <String> amFoundGames = checkAMGameFolders(new File(pathCfg.getAmericanPath()));
            saveAmList(amFoundGames);
        }
        americanListShow();
    }
}

