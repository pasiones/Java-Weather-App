package fi.tuni.prog3.weatherapp;
import fi.tuni.prog3.functionality.*;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;

/**
 * Primary screen for display weather information. 
 * The data called from the API is pass to here and stored in global values.
 * <p>
 * The current temperature data is stored in strings.
 * The daily and hourly data is stored in arrays of strings.
 * </p>
 * @author Huy Duong Cat
 */
public class PrimaryController {
    // Handle for fav and hist:
    private final FileHandlerIO fileHandler = new FileHandlerIO();
    private final String historyFileName = "history.txt";
    private ArrayList<String> historyArray;
    private final String favouritesFileName = "favourites.txt";
    private ArrayList<String> favouritesArray;
    
    private final String currentState = "current_weather_state.json";
    private final String dailyState = "daily_forecast_state.json";
    private final String hourlyState = "hourly_forecast_state.json";
    
    // Value for temperature unit
    private String unit = "metric";
    private String unitVisual = "°C";
    private String unitWind = "m/s";
    private final String tempState = "tempState.txt";
    private final UnitsHandler unitHandler = new UnitsHandler(unit, unitVisual, unitWind, tempState);
    
    // Global value 
    private final getCurrentWeather currentWeather = new getCurrentWeather(currentState);
    private final getDailyForecastWeather dailyWeather = new getDailyForecastWeather(dailyState);
    private final getHourlyForecastWeather hourlyWeather = new getHourlyForecastWeather(hourlyState);
    
    // Current
    private String  curCity = "Hanoi",
                    curTemp = "",
                    curFeelLikeTemp = "",
                    curHumid = "",
                    curWind = "",
                    curVisibility= "",
                    curIcon = "";
    
    // Weekly
    private String[] weeklyDays = new String[7],
                     weeklyDates = new String [7],
                     weeklyIcons = new String[7],
                     weeklyMaxTemps = new String[7],
                     weeklyMinTemps = new String[7];
    
    // Hourly 
    private String[] HrDescriptions = new String[12],
                     HrIcons = new String[12],
                     HrTemps = new String[12],
                     HrWind_speed = new String[12],
                     HrHours = new String[12];
    
    // UI elements
    @FXML
    private Button primaryButton, searchBtn, unitButton;
    @FXML
    private TextField searchTxt;
    @FXML   
    private Label   cityLabel, 
                    currentTempLb, 
                    feelLikeLb, 
                    visibilityLB, 
                    humidLb, 
                    windLb;
    @FXML
    private ImageView currentWeatherIcon;
    @FXML
    private VBox    day0VBox, 
                    day1VBox, 
                    day2VBox, 
                    day3VBox, 
                    day4VBox, 
                    day5VBox, 
                    day6VBox, 
                    mainVBox,
                    curWeatherVbox;
    @FXML
    private HBox weeklyHBox, hourlyHBox;
    @FXML
    private ScrollPane hourlyScrollPane;
    @FXML 
    private ToggleButton favToggleButton;
    
    /**
     * The 'initialize' method initialize the start screen by populate the UI elements.
     * The method make sure the unit state of the application is consistent.
     * The method keep track of the application previous state and check if there have been an input from the secondary screen.
     * If there is no previous state, a predetermined city is loaded for display.
     * 
     * @throws Exception if there is file not found.
     */
    public void initialize() throws Exception {
        // check tempUnit state
        UpdateTempDisplay();
        // Read hist and fav:
        readFav();
        readHist();
        
        if (DataModel.getText() != null) { // Check if the user input history or favourites
            curCity = DataModel.getText();
            DataModel.clearText();
            getData();
            setData();
        } else { // Check if there was an old state of program
            if (currentWeather.getState()
                    && dailyWeather.getState() 
                    && hourlyWeather.getState()) {    
                setData();
            } else {
                getData();
                setData();
            }            
        }
        checkFavStatus();       
        
        // Set minimum width for the Hourly HBox
        hourlyHBox.minWidthProperty().bind(hourlyScrollPane.widthProperty().add(50));
    }
    
    /**
     * The 'switchToSecondary' method used to switch to secondary screen which display history and favorites. 
     * @throws IOException if there is the secondary screen not found
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }   
    
    /**
     * 'geData' method used to get all the weather info of a city and the desired unit ie. imperial and metric
     * @throws Exception when city name is invalid
     */
    private void getData() throws Exception {
        currentWeather.getNewWeatherInfo(curCity, unit);
        dailyWeather.getNewWeatherInfo(curCity, unit);
        hourlyWeather.getNewWeatherInfo(curCity, unit);
    }
    
    /**
     * 'setData' method populates the UI components in the main screen.
     * @throws Exception 
     */
    private void setData() throws Exception {
        getCurrentData();
        setNewCurrentVal();
        getDailyVal();
        setDailyVal();
        getHourlyData();
        setHourVal();
    }
    
    /**
     * 'searchBtnHandler' handle the search button. The method check if the input 
     * is valid. If the input is valid, the method get the related data and populate
     * the UI elements with the data. If the input is invalid, the method clear the 
     * primary screen and notify the user of the invalid input.
     * 
     * @throws Exception when input is invalid
     */
    @FXML
    private void searchBtnHandler() throws Exception {
        favToggleButton.setDisable(false);
        unitButton.setDisable(false);
        curCity = searchTxt.getText();
        UpdateTempDisplay();
        try {
            getData();
            setData();
            checkFavStatus();
            addHistory();
        } catch (Exception e){
            cityLabel.setText("City name invalid");
            removeFromView();
        }
    }

    /**
     * Link the "enter key press" with the "searchBtnHandler" method
     * @param event enter key press
     * @throws Exception input invalid
     */
    @FXML
    private void handleKeyPress(KeyEvent event) throws Exception {
        switch (event.getCode()) {
            case ENTER:
                searchBtnHandler();
                break;
        }
    }
    
    ////-------------Favourites Handler----------------////
    
    /**
     * Handle favorite button. 
     * The button add or remove the current city from the favorites list.
     * @throws IOException 
     */
    @FXML 
    private void favButtonHandler() throws IOException {
        checkFavStatus();
        if (checkFavStatus() == false) {
            fileHandler.addToFile(curCity, favouritesFileName);
            readFav();
            checkFavStatus();
        } else {
            favouritesArray.remove(curCity);
            fileHandler.writeArrayToFile(favouritesArray, favouritesFileName);            
            readFav();
            checkFavStatus();
        }
    }
    
    /**
     * Read the favorite file and add its content to the global favorites array
     * @throws IOException 
     */
    private void readFav() throws IOException {
        favouritesArray = fileHandler.readFile(favouritesFileName);
    }
    
    /**
     * Check if the current city in the favorites list and change the state of 
     * the favorites button.
     * @return 
     */
    private Boolean checkFavStatus() {
        for(String element : favouritesArray) {
            if(curCity == null 
                    ? element == null : curCity.equals(element)) {
                favToggleButton.setText("Remove from Favorites");
                favToggleButton.setSelected(true);
                return true;
            } 
        }
        favToggleButton.setText("Add to Favorites");
        favToggleButton.setSelected(false);
        return false;
    }

  
    //// ------------------- History handler --------------------------////
    
    /**
     * Read the history file and add it to the global history array.
     * @throws IOException 
     */
    private void readHist() throws IOException {
        historyArray = fileHandler.readFile(historyFileName);
    }
    
    /**
     * Add the current history the history file. And rewrite the history array.
     * @throws Exception 
     */
    private void addHistory() throws Exception {
        if (inHistory()) {
            
        } else {
            fileHandler.addToFile(curCity, historyFileName);
        }
        readHist();
    }
    
    /**
     * Check if the current city already in the history array.
     * @return true if the current city is found in the history array, false otherwise.
     */
    private Boolean inHistory() {
        for (String element : historyArray) {
            if (curCity == null 
                    ? element == null : curCity.equals(element)){
                return true;
            } 
        }
        return false;
    }
    
    ////------------------- Temp Unit Button handler------------------////
    
    /**
     * Handle the "temperature unit" button.
     * Change to new temperature state and update the 'tempState" file.
     * @throws Exception 
     */
    @FXML
    private void unitButtonHandler() throws Exception {
        unitHandler.pressButton();
        this.unit = unitHandler.getUnit();
        this.unitVisual = unitHandler.getUnitVisual();
        this.unitWind = unitHandler.getUnitWind();
        unitButton.setText(unitVisual);
        getData();
        setData();
    }
    
    /**
     * Update global values accordingly.
     * @throws Exception if file not found.
     */
    private void UpdateTempDisplay() throws Exception {
        unitHandler.checkTempState();
        this.unit = unitHandler.getUnit();
        this.unitVisual = unitHandler.getUnitVisual();
        this.unitWind = unitHandler.getUnitWind();
        unitButton.setText(unitVisual);
    }
    
    
    ////-------------Get and Set Data Handler----------------------------///
    
    /**
     * Remove all the UI component from view.
     */
    private void removeFromView(){
        favToggleButton.setDisable(true);
        unitButton.setDisable(true);
        String removedText = "--";
        currentTempLb.setText(removedText + " " + unitVisual);
        feelLikeLb.setText(removedText + " " + unitVisual);
        humidLb.setText(" " + removedText + " %");
        windLb.setText(" " + removedText + " " + unitWind);
        visibilityLB.setText(removedText + " m");
        
        File iconFile = new File("icons/" + removedText + ".png");
        currentWeatherIcon.setImage(new Image(iconFile.toURI().toString()));
            /// Daily
            for (int i = 0; i < 7; i++) {
                VBox currentDayVBox;
                switch (i) {
                    case 0:
                        currentDayVBox = day0VBox;
                        break;
                    case 1:
                        currentDayVBox = day1VBox;
                        break;
                    case 2:
                        currentDayVBox = day2VBox;
                        break;
                    case 3:
                        currentDayVBox = day3VBox;
                        break;
                    case 4:
                        currentDayVBox = day4VBox;
                        break;
                    case 5:
                        currentDayVBox = day5VBox;
                        break;
                    case 6:
                        currentDayVBox = day6VBox;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + i);
                }
                currentDayVBox.getChildren().clear();
            }
            /// Hourly:
            hourlyHBox.getChildren().clear();
            for (int i = 0; i < 12; i ++) {
                VBox hourVBox = getHourVbox();
                hourlyHBox.getChildren().addAll(hourVBox);
                HBox.setHgrow(hourVBox, Priority.ALWAYS);
            }     
    }
    
    /**
     * Get all the relevant current time data.
     * 
     */
    private void getCurrentData(){
        curCity = currentWeather.getCity();
        curTemp = currentWeather.getTemp();
        curFeelLikeTemp = currentWeather.getFeelsLike();
        curHumid = currentWeather.getHumidity();
        curWind = currentWeather.getWindSpeed();
        curVisibility = currentWeather.getVisibility();
        curIcon = currentWeather.getIcon();
    }
    
    /**
     * Populate the UI elements with the current time data
     */
    private void setNewCurrentVal() {
        cityLabel.setText(curCity);
        currentTempLb.setText(curTemp + " " + unitVisual);
        feelLikeLb.setText(curFeelLikeTemp + " " + unitVisual);
        humidLb.setText(" " + curHumid + " %");
        windLb.setText(" " + curWind + " " + unitWind);
        visibilityLB.setText(curVisibility + " m");
        
        File iconFile = new File("icons/" + curIcon + ".png");
        currentWeatherIcon.setImage(new Image(iconFile.toURI().toString()));
    }
    
    /**
     * Get all the relevant weekly data.
     */
    private void getDailyVal() { 
        weeklyDays = dailyWeather.getDays_in_week();
        weeklyDates = dailyWeather.getDates();
        weeklyIcons = dailyWeather.getIcons();
        weeklyMaxTemps = dailyWeather.getMax_temp();
        weeklyMinTemps = dailyWeather.getMin_temp();
    }
    
    /**
     * Populate the UI elements with the weekly data.
     */
    private void setDailyVal() {
        for (int i = 0; i < 7; i++) {
            VBox currentDayVBox;
            switch (i) {
                case 0:
                    currentDayVBox = day0VBox;
                    break;
                case 1:
                    currentDayVBox = day1VBox;
                    break;
                case 2:
                    currentDayVBox = day2VBox;
                    break;
                case 3:
                    currentDayVBox = day3VBox;
                    break;
                case 4:
                    currentDayVBox = day4VBox;
                    break;
                case 5:
                    currentDayVBox = day5VBox;
                    break;
                case 6:
                    currentDayVBox = day6VBox;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i);
            }
            currentDayVBox.getChildren().clear();
            // dayLabel
            Label dayLb = new Label(weeklyDays[i] + " \n" + weeklyDates[i]);
            dayLb.setStyle("-fx-font-size: 15px; -fx-text-fill: white");
            // icon
            File iconFile = new File("icons/" + weeklyIcons[i] + ".png");
            Image iconImg = new Image(iconFile.toURI().toString());
            ImageView icon = new ImageView();
            icon.setImage(iconImg);
            icon.setFitHeight(40);
            icon.setFitWidth(40);
            // min max temp
            Label tempLB = new Label(   "max: " + weeklyMaxTemps[i] 
                                        + " " + unitVisual +"\n" 
                                        + "min: "+ weeklyMinTemps[i] 
                                        + " " + unitVisual);
            tempLB.setStyle("-fx-text-fill: white");
            // add children
            currentDayVBox.getChildren().addAll(
                    dayLb,
                    icon,
                    tempLB
            );
        }
    }
    
    
    /**
     * Get all the relevant hourly data.
     */
    private void getHourlyData() {
        HrDescriptions = hourlyWeather.getDescription();
        HrIcons = hourlyWeather.getIcons();
        HrTemps = hourlyWeather.getTemps();
        HrWind_speed = hourlyWeather.getWindSpeed();
        HrHours = hourlyWeather.getHours();
    }
    
    /**
     * Create loop to populate 12 hourly VBox.
     */
    private void setHourVal() {
        hourlyHBox.getChildren().clear();
        for (int i = 0; i < 12; i ++){
            VBox hourVBox = getHourVbox();
            hourlyHBox.getChildren().addAll(hourVBox);
            HBox.setHgrow(hourVBox, Priority.ALWAYS);
            populateHourVbox(hourVBox, i);
        } 
    }
    
    /**
     * Format for a hourly VBox.
     * @return formatted VBox
     */
    private VBox getHourVbox() {
        VBox hourVBox = new VBox();
        hourVBox.setMaxSize(500, 8000);
        hourVBox.setMinWidth(125);
        hourVBox.setStyle(  "-fx-background-color: #274e66; "
                            + "-fx-background-radius: 10;");
        hourVBox.getStyleClass().addAll("white-text", "rounded-vbox");
        hourVBox.setAlignment(Pos.TOP_CENTER);
        return hourVBox;
    }
    
    /**
     * This method used for populating an hourly VBox
     * @param hrVBox the VBox to populate
     * @param i the order of the data and the VBox populating
     */
    private void populateHourVbox(VBox hrVBox, int i) {
        // Hour label
        Label hrLb = new Label(HrHours[i]);
        // Icon
        File iconFile = new File("icons/" + HrIcons[i] + ".png");
        Image iconImg = new Image(iconFile.toURI().toString());
        ImageView icon = new ImageView();
        icon.setImage(iconImg);
        icon.setFitHeight(50);
        icon.setFitWidth(50);
        // Description
        Label descriptLb = new Label(HrDescriptions[i]);
        // Temp 
        HBox tempHbox = setTempHbox(i);
        // windsp
        HBox windspHBox = setWindHbox(i);
        // add children 
        hrVBox.getChildren().addAll(hrLb, 
                                    icon, 
                                    descriptLb,
                                    tempHbox,
                                    windspHBox);
    }
    
    /**
     * Populate the temperature HBox use for Hourly VBox
     * @param i the order of the VBox and Data
     * @return HBox displaying temperature and icon
     */
    private HBox setTempHbox(int i){
        ImageView tempIcon = setIcon("redTemp", 20, 20);
        Label tempLb = new Label (HrTemps[i] + " " + unitVisual);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(tempIcon, tempLb);
        hbox.setAlignment(Pos.TOP_CENTER);
        return hbox;
    }
    
    /**
     * Populate the wind HBox use for Hourly VBox
     * @param i the order of the VBox and Data
     * @return HBox displaying wind speed and icon
     */
    private HBox setWindHbox(int i) {
        ImageView windIcon = setIcon("windy", 20, 20);
        Label windSpLB = new Label (" " + HrWind_speed[i] + " " + unitWind);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(windIcon, windSpLB);
        hbox.setAlignment(Pos.TOP_CENTER);
        return hbox;
    }
    
    /**
     * Helper method use to get icon at desired dimension
     * @param iconName the name of Icon to extract
     * @param width desired width
     * @param height desired height
     * @return ImageView displaying icon
     */
    private ImageView setIcon(String iconName, int width, int height) {
        File iconFile = new File("icons/" + iconName + ".png");
        Image iconImg = new Image(iconFile.toURI().toString());
        ImageView icon = new ImageView();
        icon.setImage(iconImg);
        icon.setFitHeight(height);
        icon.setFitWidth(width);
        return icon;
    }
}
