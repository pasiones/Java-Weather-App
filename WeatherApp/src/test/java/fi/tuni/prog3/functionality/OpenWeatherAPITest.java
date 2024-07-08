package fi.tuni.prog3.functionality;

import com.google.gson.JsonObject;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Admin
 */
public class OpenWeatherAPITest {
    private OpenWeatherAPI weatherAPI;
    private Double[] latLon;

    @Test
    public void testLookUpLocation() throws IOException {
        this.weatherAPI = new OpenWeatherAPI();
        this.latLon = weatherAPI.lookUpLocation("New York");
        assertNotNull(latLon);
        assertEquals(2, latLon.length);
        assertNotNull(latLon[0]);
        assertNotNull(latLon[1]);
        assertTrue(latLon[0] instanceof Double);
        assertTrue(latLon[1] instanceof Double);
    }

    @Test
    public void testGetCurrentWeather() throws IOException {
        this.weatherAPI = new OpenWeatherAPI();
        this.latLon = weatherAPI.lookUpLocation("New York");
        JsonObject weatherData = weatherAPI.getCurrentWeather(latLon[0], latLon[1], "metric");
        assertNotNull(weatherData);
        assertTrue(weatherData.has("weather"));
        assertTrue(weatherData.has("main"));
    }

    @Test
    public void testGetDailyForecastWeather() throws IOException{
        this.weatherAPI = new OpenWeatherAPI();
        this.latLon = weatherAPI.lookUpLocation("New York");
        JsonObject forecastData = weatherAPI.getDailyForecastWeather(latLon[0], latLon[1], "metric");
        assertNotNull(forecastData);
        assertTrue(forecastData.has("list"));
    }

    @Test
    public void testGetHourlyForecastWeather() throws IOException {
        this.weatherAPI = new OpenWeatherAPI();
        this.latLon = weatherAPI.lookUpLocation("New York");
        JsonObject hourlyForecastData = weatherAPI.getHourlyForecastWeather(latLon[0], latLon[1], "metric");
        assertNotNull(hourlyForecastData);
        assertTrue(hourlyForecastData.has("list"));
    }
}
