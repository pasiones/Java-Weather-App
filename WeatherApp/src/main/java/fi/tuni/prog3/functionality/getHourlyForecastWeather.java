package fi.tuni.prog3.functionality;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * getHourlyForecastWeather is a class responsible for fetching and managing 
 * hourly forecast weather information from the OpenWeatherAPI, storing it in 
 * a JSon file, and providing methods to retrieve specific weather data for 
 * the next twelve hours.
 * 
 * <p>
 * This class utilizes the OpenWeatherAPI for weather data retrieval 
 * and the FileHandlerIO class for file operations.
 * </p>
 * 
 * <p>
 * Example usage:
 * <pre>{@code
 * // Create a getHourlyForecastWeather instance with a specified filename
 * getHourlyForecastWeather weather = new getHourlyForecastWeather("hourly_forecast_data.json");
 * 
 * // Fetch new hourly forecast weather information for a location and store 
 * // it in the file
 * weather.getNewWeatherInfo("London", "metric");
 * 
 * // Retrieve weather data from the file
 * boolean success = weather.getState();
 * if (success) {
 *     String[] hours = weather.getHours();
 *     String[] temps = weather.getTemps();
 *     // Retrieve other weather attributes as needed
 * } else {
 *     System.out.println("Failed to retrieve hourly forecast weather data.");
 * }
 * }</pre>
 * </p>
 *  
 * <p>
 * This class retrieves weather information for the next twelve hours and 
 * provides methods to access weather attributes such as icons, temperatures, 
 * wind speeds, and hours of the day.
 * </p>
 * 
 * <p>
 * The timezone offset is used to convert Unix timestamps to local times.
 * </p>
 * 
 * @author Dat Minh Lam
 */
public class getHourlyForecastWeather {
    private String fileName = "";
    private JsonObject HourlyForecastWeather;
    private int offset = 0;
    private final String[] descriptions = new String[12];
    private final String[] icons = new String[12];
    private final String[] temps = new String[12];
    private final String[] wind_speed = new String[12];
    private final String[] hours = new String[12];
    
    /**
     * Constructs a new getHourlyForecastWeather instance with the specified 
     * filename.
     * @param filename the name of the file to store forecast data.
     */
    public getHourlyForecastWeather(String filename) {
        this.fileName = filename;
    }
    
    /**
     * Fetches new hourly forecast weather information for a location and stores 
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
        this.HourlyForecastWeather = api.getHourlyForecastWeather(LatAndLon[0], LatAndLon[1], unit);
        JsonArray TwelveHoursForecast = HourlyForecastWeather.getAsJsonArray("list");
        this.offset = HourlyForecastWeather.getAsJsonObject("city").get("timezone").getAsInt();
        for (int i = 0; i < 12; i++) {
            JsonObject entry = TwelveHoursForecast.get(i).getAsJsonObject();
            UnixTimeConverter time = new UnixTimeConverter(entry.get("dt").getAsLong(), offset);
            this.hours[i] = time.getHour();
            this.temps[i] = entry.getAsJsonObject("main").get("temp").getAsString();
            this.wind_speed[i] = entry.getAsJsonObject("wind").get("speed").getAsString();
            this.icons[i] = entry.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
            this.descriptions[i] = entry.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        }
        fileHandler.writeToFile(fileName, HourlyForecastWeather);
    }
    
    /**
     * Retrieves hourly forecast weather data from the file and updates the class attributes.
     * @return true if weather data was successfully retrieved, otherwise false.
     * @throws Exception if an error occurs while reading from the file.
     */
    public boolean getState() throws Exception {
        FileHandlerIO fileHandler = new FileHandlerIO();
        this.HourlyForecastWeather = fileHandler.readFromFile(fileName);
        if (this.HourlyForecastWeather == null) {
            return false;
        }
        JsonArray TwelveHoursForecast = HourlyForecastWeather.getAsJsonArray("list");
        this.offset = HourlyForecastWeather.getAsJsonObject("city").get("timezone").getAsInt();
        for (int i = 0; i < 12; i++) {
            JsonObject entry = TwelveHoursForecast.get(i).getAsJsonObject();
            UnixTimeConverter time = new UnixTimeConverter(entry.get("dt").getAsLong(), offset);
            this.hours[i] = time.getHour();
            this.temps[i] = entry.getAsJsonObject("main").get("temp").getAsString();
            this.wind_speed[i] = entry.getAsJsonObject("wind").get("speed").getAsString();
            this.icons[i] = entry.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
            this.descriptions[i] = entry.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        }
        return true;
    }

    /**
     * Retrieves an array of weather descriptions for the next twelve hours.
     * @return an array of weather descriptions.
     */
    public String[] getDescription() {
        return this.descriptions;
    }

    /**
     * Retrieves an array of weather icons for the next twelve hours.
     * @return an array of weather icons.
     */
    public String[] getIcons() {
        return this.icons;
    }

    /**
     * Retrieves an array of temperatures for the next twelve hours.
     * @return an array of temperatures.
     */
    public String[] getTemps() {
        return this.temps;
    }

    /**
     * Retrieves an array of wind speeds for the next twelve hours.
     * @return an array of wind speeds.
     */
    public String[] getWindSpeed() {
        return this.wind_speed;
    }

    /**
     * Retrieves an array of hours for the next twelve hours.
     * @return an array of hours.
     */
    public String[] getHours() {
        return this.hours;
    }
}
