package fi.tuni.prog3.functionality;

import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Admin
 */
public class getHourlyForecastWeatherTest {
    private getHourlyForecastWeather weather;
    private final String filename = "hourly_forecast_history.json";
    
    /**
     * Test of getNewWeatherInfo method, of class getHourlyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetNewWeatherInfo() throws Exception {
        setUp();
        File file = new File(filename);
        assertTrue(file.exists());
        file.delete();
    }

    /**
     * Test of getState method, of class getHourlyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetState() throws Exception {
        setUp();
        assertTrue(weather.getState());
        cleanUp();
    }

    /**
     * Test of getDescription method, of class getHourlyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDescription() throws Exception {
        setUp();
        assertNotNull(weather.getDescription());
        assertTrue(weather.getDescription() instanceof String[]);
        assertEquals(12, weather.getDescription().length);
        cleanUp();
    }

    /**
     * Test of getIcons method, of class getHourlyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetIcons() throws Exception {
        setUp();
        assertNotNull(weather.getIcons());
        assertTrue(weather.getIcons() instanceof String[]);
        assertEquals(12, weather.getIcons().length);
        cleanUp();
    }

    /**
     * Test of getTemps method, of class getHourlyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetTemps() throws Exception {
        setUp();
        assertNotNull(weather.getTemps());
        assertTrue(weather.getTemps() instanceof String[]);
        assertEquals(12, weather.getTemps().length);
        cleanUp();
    }

    /**
     * Test of getWindSpeed method, of class getHourlyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetWindSpeed() throws Exception {
        setUp();
        assertNotNull(weather.getWindSpeed());
        assertTrue(weather.getWindSpeed() instanceof String[]);
        assertEquals(12, weather.getWindSpeed().length);
        cleanUp();
    }

    /**
     * Test of getHours method, of class getHourlyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetHours() throws Exception {
        setUp();
        assertNotNull(weather.getHours());
        assertTrue(weather.getHours() instanceof String[]);
        assertEquals(12, weather.getHours().length);
        cleanUp();
    }
    
    private void setUp() throws Exception {
        this.weather = new getHourlyForecastWeather(filename);
        weather.getNewWeatherInfo("New York", "metric");
    }
    
    private void cleanUp() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
}
