package fi.tuni.prog3.functionality;
import com.google.gson.JsonObject;
import java.io.IOException;
/**
 * Interface for extracting data from the OpenWeatherMap API.
 */
public interface iAPI {

    /**
     * Returns coordinates for a location.
     * @param loc Name of the location for which coordinates should be fetched.
     * @return String.
     * @throws java.io.IOException
     */
    public Double[] lookUpLocation(String loc) throws IOException;

    /**
     * Returns the current weather for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @param unit Units of measurement.
     * @return JsonObject.
     * @throws java.io.IOException
     */
    public JsonObject getCurrentWeather(double lat, double lon, String unit) throws IOException;
    
    /**
     * Returns the daily forecast weather for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @param unit unit Units of measurement.
     * @return JsonObject.
     * @throws java.io.IOException 
     */
    public JsonObject getDailyForecastWeather(double lat, double lon, String unit) throws IOException;
    
    /**
     * Returns the hourly forecast weather for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @param unit unit Units of measurement.
     * @return JsonObject.
     * @throws IOException
     */
    public JsonObject getHourlyForecastWeather(double lat, double lon, String unit) throws IOException;
}
