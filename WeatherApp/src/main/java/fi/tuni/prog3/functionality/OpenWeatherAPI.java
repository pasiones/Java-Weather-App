package fi.tuni.prog3.functionality;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * OpenWeatherAPI is a class that provides methods for accessing weather data
 * using the OpenWeatherMap API. It allows users to look up location coordinates
 * based on a location name, retrieve current weather data, daily forecast data, 
 * and hourly forecast data for a given location.
 * 
 * <p>
 * This class implements the {@link iAPI} interface.
 * </p>
 * 
 * @author Dat Minh Lam
 * @see iAPI
 */
public class OpenWeatherAPI implements iAPI {
    Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("OPENWEATHER_API_KEY");
    
    /**
     * Constructs a new OpenWeatherAPI instance.
     */
    public OpenWeatherAPI() {
    }
    
    /**
     * Looks up the latitude and longitude coordinates of a given location.
     * @param loc the name of the location to look up.
     * @return a Double array containing latitude and longitude coordinates.
     * @throws IOException if an I/O error occurs while communicating with the 
     * API.
     */
    @Override
    public Double[] lookUpLocation(String loc) throws IOException {
        Double[] LatAndLon = new Double[2];
        String encodedCity = URLEncoder.encode(loc, "UTF-8");
        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",
                encodedCity, API_KEY);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Gson gson = new Gson();
            JsonObject locationData = gson.fromJson(response.toString(), JsonObject.class);
            Double lat = locationData.getAsJsonObject("coord").get("lat").getAsDouble();
            Double lon = locationData.getAsJsonObject("coord").get("lon").getAsDouble();
            LatAndLon[0] = lat;
            LatAndLon[1] = lon;    
        } finally {
            conn.disconnect();
        }
        return LatAndLon;
    }
    
    /**
     * Retrieves current weather data for a specified location. 
     * @param lat the latitude coordinate of the location.
     * @param lon the longitude coordinate of the location.
     * @param unit the unit of measurement for the weather data (e.g., metric, imperial).
     * @return a JsonObject containing the current weather data.
     * @throws IOException if an I/O error occurs while communicating with the API.
     */
    @Override
    public JsonObject getCurrentWeather(double lat, double lon, String unit) throws IOException {
        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%.2f&lon=%.2f&appid=%s&units=%s",
                lat, lon, API_KEY, unit);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Gson gson = new Gson();
            JsonObject WeatherData = gson.fromJson(response.toString(), JsonObject.class);
            return WeatherData;
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Retrieves daily forecast weather data for a specified location.
     * @param lat the latitude coordinate of the location.
     * @param lon the longitude coordinate of the location.
     * @param unit the unit of measurement for the weather data (e.g., metric, imperial).
     * @return a JsonObject containing the daily forecast weather data.
     * @throws IOException if an I/O error occurs while communicating with the API.
     */
    @Override
    public JsonObject getDailyForecastWeather(double lat, double lon, String unit) throws IOException {
        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/forecast/daily?lat=%.2f&lon=%.2f&cnt=7&appid=%s&units=%s",
                lat, lon, API_KEY, unit);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Gson gson = new Gson();
            return gson.fromJson(response.toString(), JsonObject.class);
        } finally {
            conn.disconnect();
        }
    }
    
    /**
     * Retrieves hourly forecast weather data for a specified location.
     * @param lat the latitude coordinate of the location.
     * @param lon the longitude coordinate of the location.
     * @param unit the unit of measurement for the weather data (e.g., metric, imperial).
     * @return a JsonObject containing the hourly forecast weather data.
     * @throws IOException if an I/O error occurs while communicating with the API.
     */
    @Override
    public JsonObject getHourlyForecastWeather(double lat, double lon, String unit) throws IOException {
        String apiUrl = String.format("https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=%.2f&lon=%.2f&cnt=12&appid=%s&units=%s",
                lat, lon, API_KEY, unit);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Gson gson = new Gson();
            return gson.fromJson(response.toString(), JsonObject.class);
        } finally {
            conn.disconnect();
        }
    }
}
