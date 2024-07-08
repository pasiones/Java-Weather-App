package fi.tuni.prog3.functionality;

import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Dat Minh Lam
 */
public class UnitsHandlerTest {
    
    private UnitsHandler unitsHandler;
    private final String filename = "testTempState.txt";
    private void setUp() {
        unitsHandler = new UnitsHandler("metric", "°C", "m/s", filename);
    }

    @Test
    public void testPressButton() throws Exception {
        setUp();
        unitsHandler.pressButton();
        assertEquals("imperial", unitsHandler.getUnit());
        assertEquals("°F", unitsHandler.getUnitVisual());
        assertEquals("mph", unitsHandler.getUnitWind());
        deleteTestFile(filename);
    }

    @Test
    public void testCheckTempState() throws Exception {
        setUp();
        unitsHandler.pressButton();
        unitsHandler.pressButton();
        unitsHandler.checkTempState();
        assertEquals("metric", unitsHandler.getUnit());
        assertEquals("°C", unitsHandler.getUnitVisual());
        assertEquals("m/s", unitsHandler.getUnitWind());
        deleteTestFile(filename);
    }
    
    private void deleteTestFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            if (!file.delete()) {
                System.err.println("Failed to delete the test file: " + filename);
            }
        }
    }
}
