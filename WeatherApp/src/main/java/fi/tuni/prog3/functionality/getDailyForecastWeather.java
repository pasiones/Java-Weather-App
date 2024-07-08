package fi.tuni.prog3.functionality;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * getDailyForecastWeather is a class responsible for fetching and managing 
 * daily forecast weather information from the OpenWeatherAPI, storing it in a 
 * JSon file, and providing methods to retrieve specific weather data for the 
 * next seven days.
 * 
 * <p>
 * This class utilizes the OpenWeatherAPI for weather data retrieval and the 
 * FileHandlerIO
 * class for file operations.
 * </p>
 * 
 * <p>
 * Example usage:
 * <pre>{@code
 * // Create a getDailyForecastWeather instance with a specified filename
 * getDailyForecastWeather weather = new getDailyForecastWeather("forecast_data.json");
 * 
 * // Fetch new daily forecast weather information for a location and store it 
 * // in the file
 * weather.getNewWeatherInfo("London", "metric");
 * 
 * // Retrieve weather data from the file
 * boolean success = weather.getState();
 * if (success) {
 *     String[] dates = weather.getDates();
 *     String[] descriptions = weather.getDescription();
 *     // Retrieve other weather attributes as needed
 * } else {
 *     System.out.println("Failed to retrieve daily forecast weather data.");
 * }
 * }</pre>
 * </p>
 * 
 * <p>
 * This class retrieves weather information for the next seven days and provides 
 * methods to access weather attributes such as icons, minimum and maximum 
 * temperatures, days of the week, and dates.
 * </p>
 * 
 * <p>
 * The timezone offset is used to convert Unix timestamps to local dates and times.
 * </p>
 * 
 * @author Dat Minh Lam
 */
public class getDailyForecastWeather {
    private String fileName = "";
    private JsonObject DailyForecastWeather;
    private int offset = 0;
    private final String[] descriptions = new String[7];
    private final String[] icons = new String[7];
    private final String[] min_temp = new String[7];
    private final String[] max_temp = new String[7];
    private final String[] days_in_week = new String[7];
    private final String[] dates = new String[7];
    
    /**
     * Constructs a new getDailyForecastWeather instance with the specified 
     * filename.
     * @param filename the name of the file to store forecast data.
     */
    public getDailyForecastWeather(String filename) {
        this.fileName = filename;
    }
    
    /**
     * Fetches new daily forecast weather information for a location and stores 
     * it in the file.
     * @param location the location for which to fetch weather information.
     * @param unit the unit of measurement for the weather data (e.g., metric, 
     * imperial).
     * @throws Exception if an error occurs while retrieving weather data.
     */
    public void getNewWeatherInfo(String location, String unit) throws Exception {
        OpenWeatherAPI api = new OpenWeatherAPI();
        FileHandlerIO fileHandler = new FileHandlerIO();
        Double[] LatAndLon = api.lookUpLocation(location);
        this.DailyForecastWeather = api.getDailyForecastWeather(LatAndLon[0], LatAndLon[1], unit);
        JsonArray SevenDaysForecast = DailyForecastWeather.getAsJsonArray("list");
        this.offset = DailyForecastWeather.getAsJsonObject("city").get("timezone").getAsInt();
        for (int i = 0; i < 7; i++) {
            JsonObject entry = SevenDaysForecast.get(i).getAsJsonObject();
            UnixTimeConverter date = new UnixTimeConverter(entry.get("dt").getAsLong(),offset);
            this.days_in_week[i] = date.getDayOfWeek();
            this.dates[i] = date.getDate();
            this.min_temp[i] = Integer.toString(entry.getAsJsonObject("temp").get("min").getAsInt());
            this.max_temp[i] = Integer.toString(entry.getAsJsonObject("temp").get("max").getAsInt());
            this.icons[i] = entry.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
            this.descriptions[i] = entry.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        }
        fileHandler.writeToFile(fileName, DailyForecastWeather);
    }
    
    /**
     * Retrieves daily forecast weather data from the file and updates the class 
     * attributes.
     * @return true if weather data was successfully retrieved, otherwise false.
     * @throws Exception if an error occurs while reading from the file.
     */
    public boolean getState() throws Exception {
        FileHandlerIO fileHandler = new FileHandlerIO();
        this.DailyForecastWeather = fileHandler.readFromFile(fileName);
        if (this.DailyForecastWeather == null) {
            return false;
        }
        JsonArray SevenDaysForecast = DailyForecastWeather.getAsJsonArray("list");
        this.offset = DailyForecastWeather.getAsJsonObject("city").get("timezone").getAsInt();
        for (int i = 0; i < 7; i++) {
            JsonObject entry = SevenDaysForecast.get(i).getAsJsonObject();
            UnixTimeConverter date = new UnixTimeConverter(entry.get("dt").getAsLong(),offset);
            this.days_in_week[i] = date.getDayOfWeek();
            this.dates[i] = date.getDate();
            this.min_temp[i] = Integer.toString(entry.getAsJsonObject("temp").get("min").getAsInt());
            this.max_temp[i] = Integer.toString(entry.getAsJsonObject("temp").get("max").getAsInt());
            this.icons[i] = entry.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
            this.descriptions[i] = entry.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        }
        return true;
    }
    
    /**
     * Retrieves an array of weather icons for the next seven days.
     * @return an array of weather icons.
     */
    public String[] getIcons() {
        return this.icons;
    }

    /**
     * Retrieves an array of minimum temperatures for the next seven days.
     * @return an array of minimum temperatures.
     */
    public String[] getMin_temp() {
        return this.min_temp;
    }

    /**
     * Retrieves an array of maximum temperatures for the next seven days.
     * @return an array of maximum temperatures.
     */
    public String[] getMax_temp() {
        return this.max_temp;
    }

    /**
     * Retrieves an array of days of the week for the next seven days.
     * @return an array of days of the week.
     */
    public String[] getDays_in_week() {
        return this.days_in_week;       
    }

    /**
     * Retrieves an array of dates for the next seven days.
     * @return an array of dates.
     */
    public String[] getDates() {
        return this.dates;
    }  

    /**
     * Retrieves an array of weather descriptions for the next seven days. 
     * @return an array of weather descriptions.
     */
    public String[] getDescription() {
        return this.descriptions;
    }
}
