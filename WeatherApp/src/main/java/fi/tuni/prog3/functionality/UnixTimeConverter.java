package fi.tuni.prog3.functionality;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
/**
 * UnixTimeConverter is a utility class for converting Unix timestamps to 
 * human-readable date and time formats, considering a specified offset.
 * 
 * <p>
 * This class provides methods to extract the day of the week, date, and hour from a Unix
 * timestamp, adjusting for a given offset.
 * </p>
 * 
 * <p>
 * The offset represents the difference in seconds between the local time and UTC (Coordinated
 * Universal Time) at the given location.
 * </p>
 * 
 * <p>
 * Example usage:
 * <pre>{@code
 * long unixTime = 1619708027;
 * int offset = 3600; // 1 hour offset from UTC
 * UnixTimeConverter converter = new UnixTimeConverter(unixTime, offset);
 * String dayOfWeek = converter.getDayOfWeek();
 * String date = converter.getDate();
 * String hour = converter.getHour();
 * }</pre>
 * </p>
 * 
 * <p>
 * Note: This class assumes the offset is provided in seconds.
 * </p>
 * 
 * @author Dat Minh Lam
 */
public class UnixTimeConverter {
    private String dayOfWeek = "";
    private String formattedDate = "";
    private String formattedHour = "";

    /**
     * Constructs a new UnixTimeConverter instance, converting the given Unix timestamp
     * to a local date and time, adjusted by the specified offset.
     * @param unixTime the Unix timestamp to convert.
     * @param offset the offset in seconds between the local time and UTC.
     */
    public UnixTimeConverter(long unixTime, int offset) {
        Instant instant = Instant.ofEpochSecond(unixTime);
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDate date = instant.atOffset(zoneOffset).toLocalDate();
        LocalTime time = instant.atOffset(zoneOffset).toLocalTime();
        String rawDayOfWeek = date.getDayOfWeek().toString();
        this.dayOfWeek = rawDayOfWeek.substring(0, 1).toUpperCase() + rawDayOfWeek.substring(1,3).toLowerCase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");
        this.formattedDate = date.format(formatter);
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm"); // 24-hour format
        this.formattedHour = time.format(hourFormatter);
    }
    
    /**
     * Retrieves the day of the week as a string abbreviation (e.g., "Mon").
     * @return the day of the week.
     */
    public String getDayOfWeek() {
        return this.dayOfWeek;
    }

    /**
     * Retrieves the formatted date in "dd-MM" format. 
     * @return the formatted date.
     */
    public String getDate() {
        return this.formattedDate;
    }

    /**
     * Retrieves the formatted hour in 24-hour format (HH:mm). 
     * @return the formatted hour.
     */
    public String getHour() {
        return this.formattedHour;
    }
}
