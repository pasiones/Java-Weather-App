package fi.tuni.prog3.functionality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Admin
 */
public class UnixTimeConverterTest {
    private UnixTimeConverter converter;
    @BeforeEach
    public void setUp() {
        long unixTime = 1618851600;
        int offset = 0;
        converter = new UnixTimeConverter(unixTime, offset);
    }
    
    @Test
    public void getDayOfWeek() {
        assertEquals("Mon", converter.getDayOfWeek());
    }
    
    @Test
    public void getDate() {
        assertEquals("19-04", converter.getDate());
    }
    
    @Test
    public void getTime() {
        assertEquals("17:00", converter.getHour());
    }
}
