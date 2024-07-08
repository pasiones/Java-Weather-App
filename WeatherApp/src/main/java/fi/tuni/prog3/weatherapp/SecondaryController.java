package fi.tuni.prog3.weatherapp;
import fi.tuni.prog3.functionality.*;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import java.io.IOException;
import javafx.fxml.FXML;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * This controller display the favorites and history.
 * @author Huy Duong Cat
 */
public class SecondaryController {
    
    private final FileHandlerIO fileHandler = new FileHandlerIO();

    // History
    @FXML
    private ScrollPane historyScrlPane;
    private final String histFileName = "history.txt";
    private ArrayList<String> histArray;
    private Button ClearHistBtn;
    // Favourites
    @FXML
    private ScrollPane favScrlPane;
    private final String favFileName = "favourites.txt";
    private ArrayList<String> favArray;
    private Button ClearFavBtn;
 

    /**
     * Initialize the screen
     * @throws Exception file not found
     */
    public void initialize() throws Exception {
        // Load data 
        favArray = fileHandler.readFile(favFileName);
        histArray = fileHandler.readFile(histFileName);
        populateGrid(historyScrlPane, histArray, "history");
        populateGrid(favScrlPane, favArray, "favourites");
    }
    
    /**
     * Switch back to primary screen.
     * @throws IOException if primary not found 
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    /**
     * populate a scroll pane with data
     * @param scrlPane scrolPane to be populated
     * @param data data used for populating
     * @param description type of data i.e. history, favorites
     */
    private void populateGrid(  ScrollPane scrlPane, 
                                ArrayList<String> data, 
                                String description) {
        GridPane gridPane = getGrid();
        int colCount = 0, rowCount = 0;
        // populate hist
        for (int counter = 0; counter < data.size(); counter++){
            Button button = createButton(data, counter, description);
            gridPane.add(button, colCount, rowCount); // Add button to grid
            
            // Update column and row count
            colCount++;
            if (colCount == 4) { // If reached 4 columns, move to next row
                colCount = 0;
                rowCount++;
            }
        }
        
        // Set column constraints to make columns fill the available width
        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 4.0); // Set each column to take 25% of the width
            gridPane.getColumnConstraints().add(column);
        }
        scrlPane.setContent(gridPane);
        scrlPane.setFitToWidth(true);
    }
    
    /**
     * Create a formatted gridPane
     * @return formatted gridPane
     */
    private GridPane getGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setStyle(  "-fx-background-color:  #274e66; "
                            + "-fx-background-radius: 10");
        //gridPane.setPadding(new Insets(10));
        gridPane.setVgap(1);
        gridPane.setHgap(1);
        gridPane.setPrefHeight(200);
        return gridPane;
    }
    
    /**
     * Create a button represent a member in favorites or history.
     * @param data the data use to create button.
     * @param i the order of the data and button.
     * @param description type of data i.e. favorites or history
     * @return Formatted button.
     */
    private Button createButton(ArrayList<String> data,int i, String description) {
        Button button = new Button(data.get(i));
        button.setOnAction(new ButtonEventHandler());
        button.setPrefWidth(100);
        button.setPrefHeight(50);
        button.setFont(Font.font(15));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle("-fx-border-color: black; "
                        + "-fx-alignment: center-left; "
                        + "-fx-background-color:   #274e66; "
                        + "-fx-text-fill: white;"
                        + "-fx-font-weight: bold;");
        return button;
    }

    /**
     * Event handler for each buttons. When pressed the button data is pass to 
     * DataModel and application switch to primary screen.
     * 
     */
    private class ButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for set A buttons
            Button sourceButton = (Button) event.getSource();
            DataModel.setText(sourceButton.getText());
            try {
                // Call the switchToPrimary method to switch to the primary stage
                switchToPrimary();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mange "clear History" button.
     * When press clear all history and overwrite history file.
     * @throws IOException if cannot write to file
     */
    @FXML 
    private void clearHistBtnHandler() throws IOException {
        histArray.clear();
        fileHandler.writeArrayToFile(histArray, histFileName);
        populateGrid(historyScrlPane, histArray, histFileName);
    }
    
    /**
     * Mange "clear Favorites" button.
     * When press clear all history and overwrite history file.
     * @throws IOException if cannot write to file
     */
    @FXML 
    private void clearFavBtnHandler() throws IOException {
        favArray.clear();
        fileHandler.writeArrayToFile(favArray, favFileName);
        populateGrid(favScrlPane, favArray, favFileName);
    }
}