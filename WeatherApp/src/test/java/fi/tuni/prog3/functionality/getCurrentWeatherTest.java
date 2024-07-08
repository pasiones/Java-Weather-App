package fi.tuni.prog3.functionality;

import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Admin
 */
public class getCurrentWeatherTest {
    private getCurrentWeather weather;
    private final String filename = "current_weather_history.json";
    /**
     * Test of getNewWeatherInfo method, of class getCurrentWeather.
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
     * Test of getState method, of class getCurrentWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetState() throws Exception {
        setUp();
        assertTrue(weather.getState());
        cleanUp();
    }

    /**
     * Test of getDescription method, of class getCurrentWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDescription() throws Exception {
        setUp();
        assertNotNull(weather.getDescription());
        assertTrue(weather.getDescription() instanceof String);
        cleanUp();
    }

    /**
     * Test of getIcon method, of class getCurrentWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetIcon() throws Exception {
        setUp();
        assertNotNull(weather.getIcon());
        assertTrue(weather.getIcon() instanceof String);
        cleanUp();
    }

    /**
     * Test of getTemp method, of class getCurrentWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetTemp() throws Exception {
        setUp();
        assertNotNull(weather.getTemp());
        assertTrue(weather.getTemp() instanceof String);
        cleanUp();
    }

    /**
     * Test of getFeelsLike method, of class getCurrentWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetFeelsLike() throws Exception {
        setUp();
        assertNotNull(weather.getFeelsLike());
        assertTrue(weather.getFeelsLike() instanceof String);
        cleanUp();
    }

    /**
     * Test of getHumidity method, of class getCurrentWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetHumidity() throws Exception {
        setUp();
        assertNotNull(weather.getHumidity());
        assertTrue(weather.getHumidity() instanceof String);
        cleanUp();
    }

    /**
     * Test of getWindSpeed method, of class getCurrentWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetWindSpeed() throws Exception {
        setUp();
        assertNotNull(weather.getWindSpeed());
        assertTrue(weather.getWindSpeed() instanceof String);
        cleanUp();
    }

    /**
     * Test of getVisibility method, of class getCurrentWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetVisibility() throws Exception {
        setUp();
        assertNotNull(weather.getVisibility());
        assertTrue(weather.getVisibility() instanceof String);
        cleanUp();
    }

    /**
     * Test of getCity method, of class getCurrentWeather.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetCity() throws Exception {
        setUp();
        assertNotNull(weather.getCity());
        assertTrue(weather.getCity() instanceof String);
        cleanUp();
    }
    
    private void setUp() throws Exception {
        weather = new getCurrentWeather(filename);
        weather.getNewWeatherInfo("New York", "metric");
    }
    
    private void cleanUp() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
}
