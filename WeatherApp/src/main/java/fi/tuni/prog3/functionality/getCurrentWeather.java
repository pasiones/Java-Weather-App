package fi.tuni.prog3.functionality;
import com.google.gson.JsonObject;

/**
 * getCurrentWeather is a class responsible for fetching and managing current 
 * weather information from the OpenWeatherAPI, storing it in a JSon file, and 
 * providing methods to retrieve specific weather data.
 * 
 * <p>
 * This class utilizes the OpenWeatherAPI for weather data retrieval and the FileHandlerIO
 * class for file operations.
 * </p>
 * 
 * <p>
 * Example usage:
 * <pre>{@code
 * // Create a getCurrentWeather instance with a specified filename
 * getCurrentWeather weather = new getCurrentWeather("weather_data.json");
 * 
 * // Fetch new weather information for a location and store it in the file
 * weather.getNewWeatherInfo("London", "metric");
 * 
 * // Retrieve weather data from the file
 * boolean success = weather.getState();
 * if (success) {
 *     System.out.println("Current temperature: " + weather.getTemp());
 *     System.out.println("Description: " + weather.getDescription());
 *     // Retrieve other weather attributes as needed
 * } else {
 *     System.out.println("Failed to retrieve weather data.");
 * }
 * }</pre>
 * </p>
 * 
 * @author Dat Minh Lam
 */
public class getCurrentWeather {
    private String fileName = "";
    private JsonObject CurrentWeatherData;
    private String description = "";
    private String icon = "";
    private String temp = "";
    private String feels_like = "";
    private String humidity = "";
    private String wind_speed = "";
    private String visibility = "";
    private String city = "";
    
    /**
     * Constructs a new getCurrentWeather instance with the specified filename.
     * @param filename the name of the file to store weather data.
     */
    public getCurrentWeather(String filename){     
        this.fileName = filename;
    }
    
    /**
     * Fetches new weather information for a location and stores it in the file.
     * @param location the location for which to fetch weather information.
     * @param unit the unit of measurement for the weather data (e.g., metric, 
     * imperial).
     * @throws Exception if an error occurs while retrieving weather data.
     */
    public void getNewWeatherInfo(String location, String unit) throws Exception {
        OpenWeatherAPI api = new OpenWeatherAPI();
        FileHandlerIO fileHandler = new FileHandlerIO();
        Double[] LatAndLon = api.lookUpLocation(location);
        this.CurrentWeatherData = api.getCurrentWeather(LatAndLon[0], LatAndLon[1], unit);
        this.icon = CurrentWeatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        this.description = CurrentWeatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        JsonObject Main = CurrentWeatherData.getAsJsonObject("main");
        this.temp = Main.get("temp").getAsString();
        this.feels_like = Main.get("feels_like").getAsString();
        this.humidity = Main.get("humidity").getAsString();
        this.wind_speed = CurrentWeatherData.getAsJsonObject("wind").get("speed").getAsString();
        this.visibility = CurrentWeatherData.get("visibility").getAsString();
        this.city = CurrentWeatherData.get("name").getAsString();
        fileHandler.writeToFile(fileName, CurrentWeatherData);
    }
    
    /**
     * Retrieves weather data from the file and updates the class attributes.
     * @return true if weather data was successfully retrieved, otherwise false.
     * @throws Exception if an error occurs while reading from the file.
     */
    public boolean getState() throws Exception {
        FileHandlerIO fileHandler = new FileHandlerIO();
        this.CurrentWeatherData = fileHandler.readFromFile(fileName);
        if (this.CurrentWeatherData == null) {
            return false;
        }
        this.icon = CurrentWeatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        this.description = CurrentWeatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        JsonObject Main = CurrentWeatherData.getAsJsonObject("main");
        this.temp = Main.get("temp").getAsString();
        this.feels_like = Main.get("feels_like").getAsString();
        this.humidity = Main.get("humidity").getAsString();
        this.wind_speed = CurrentWeatherData.getAsJsonObject("wind").get("speed").getAsString();
        this.visibility = CurrentWeatherData.get("visibility").getAsString();
        this.city = CurrentWeatherData.get("name").getAsString();
        return true;
    }
    
    /**
     * Retrieves the description of the current weather.
     * @return the description of the current weather.
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Retrieves the icon representing the current weather condition.
     * @return the icon representing the current weather condition.
     */
    public String getIcon() {
        return this.icon;
    }
    
    /**
     * Retrieves the current temperature.
     * @return the current temperature.
     */
    public String getTemp() {
        return this.temp;
    }    
    
    /**
     * Retrieves the "feels like" temperature.
     * @return the "feels like" temperature.
     */
    public String getFeelsLike() {
        return this.feels_like;
    }  
    
    /**
     * Retrieves the humidity percentage.
     * @return the humidity percentage.
     */
    public String getHumidity() {
        return this.humidity;
    }    
    
    /**
     * Retrieves the wind speed.
     * @return the wind speed.
     */
    public String getWindSpeed() {
        return this.wind_speed;
    }  
    
    /**
     * Retrieves the visibility distance.
     * @return the visibility distance.
     */
    public String getVisibility() {
        return this.visibility;
    }
    
    /**
     * Retrieves the city name. 
     * @return the city name.
     */
    public String getCity() {
        return this.city;
    }
}
