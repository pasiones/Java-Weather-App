package fi.tuni.prog3.weatherapp;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX application for displaying weather information.
 * @author Huy Duong Cat
 */
public class App extends Application {

    private static Scene scene;
    
    
    /**
     * Starts the JavaFX application by loading the primary FXML file.
     * @param stage The primary stage for this application.
     * @throws IOException If an error occurs during loading of FXML file or setting the scene.
     */
    
    @Override
    public void start(Stage stage) throws IOException {
        
        
        scene = new Scene(loadFXML("primary"), 600, 800);
        
        File cssFile = new File("hourVboxStyle.css");
        String cssPath = cssFile.toURI().toString();
        scene.getStylesheets().add(cssPath);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the root FXML file to be displayed.
     * @param fxml The name of the FXML file to be loaded.
     * @throws IOException If an error occurs during loading of FXML file.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads the FXML file.
     * @param fxml The name of the FXML file to be loaded.
     * @return The root node of the loaded FXML file.
     * @throws IOException If an error occurs during loading of FXML file.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    /**
     * Main method to launch the JavaFX application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }

}