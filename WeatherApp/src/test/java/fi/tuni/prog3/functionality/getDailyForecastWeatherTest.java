package fi.tuni.prog3.functionality;

import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Admin
 */
public class getDailyForecastWeatherTest {
    private getDailyForecastWeather weather;
    private final String filename = "daily_forecast_history.json";

    /**
     * Test of getNewWeatherInfo method, of class getDailyForecastWeather.
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
     * Test of getState method, of class getDailyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetState() throws Exception {
        setUp();
        assertTrue(weather.getState());
        cleanUp();
    }

    /**
     * Test of getIcons method, of class getDailyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetIcons() throws Exception {
        setUp();
        assertNotNull(weather.getIcons());
        assertTrue(weather.getIcons() instanceof String[]);
        assertEquals(7, weather.getIcons().length);
        cleanUp();
    }

    /**
     * Test of getMin_temp method, of class getDailyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetMin_temp() throws Exception {
        setUp();
        assertNotNull(weather.getMin_temp());
        assertTrue(weather.getMin_temp() instanceof String[]);
        assertEquals(7, weather.getMin_temp().length);
        cleanUp();
    }

    /**
     * Test of getMax_temp method, of class getDailyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetMax_temp() throws Exception {
        setUp();
        assertNotNull(weather.getMax_temp());
        assertTrue(weather.getMax_temp() instanceof String[]);
        assertEquals(7, weather.getMax_temp().length);
        cleanUp();
    }

    /**
     * Test of getDays_in_week method, of class getDailyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDays_in_week() throws Exception {
        setUp();
        assertNotNull(weather.getDays_in_week());
        assertTrue(weather.getDays_in_week() instanceof String[]);
        assertEquals(7, weather.getDays_in_week().length);
        cleanUp();
    }

    /**
     * Test of getDates method, of class getDailyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDates() throws Exception {
        setUp();
        assertNotNull(weather.getDates());
        assertTrue(weather.getDates() instanceof String[]);
        assertEquals(7, weather.getDates().length);
        cleanUp();
    }

    /**
     * Test of getDescription method, of class getDailyForecastWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDescription() throws Exception {
        setUp();
        assertNotNull(weather.getDescription());
        assertTrue(weather.getDescription() instanceof String[]);
        assertEquals(7, weather.getDescription().length);
        cleanUp();
    }
    
    private void setUp() throws Exception {
        weather = new getDailyForecastWeather(filename);
        weather.getNewWeatherInfo("New York", "metric");
    }
    
    private void cleanUp() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
}
