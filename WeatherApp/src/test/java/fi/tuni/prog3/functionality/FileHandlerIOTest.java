package fi.tuni.prog3.functionality;

import com.google.gson.JsonObject;
import java.io.File;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileHandlerIOTest {

    private String TEST_FILENAME = "";
    
    @Test
    public void testReadFromFile() {
        createTestFile();
        FileHandlerIO fileHandler = new FileHandlerIO();
        try {
            JsonObject jsonObject = fileHandler.readFromFile(TEST_FILENAME);
            assertNotNull(jsonObject);
            assertEquals("value", jsonObject.get("key").getAsString());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        deleteTestFile(TEST_FILENAME);
    }

    @Test
    public void testWriteToFile() {
        createTestFile();
        FileHandlerIO fileHandler = new FileHandlerIO();
        JsonObject testJsonObject = new JsonObject();
        testJsonObject.addProperty("key", "value");
        try {
            assertTrue(fileHandler.writeToFile(TEST_FILENAME, testJsonObject));
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        deleteTestFile(TEST_FILENAME);
    }
    
    @Test
    public void testAddToFileAndReadFile() throws IOException {
        FileHandlerIO fileHandler = new FileHandlerIO();
        String filename = "testfile.txt";
        fileHandler.addToFile("John", filename);
        fileHandler.addToFile("Bob", filename);
        fileHandler.addToFile("Alice", filename);
        ArrayList<String> members = fileHandler.readFile(filename);

        assertNotNull(members);
        assertEquals(3, members.size());
        assertEquals("John", members.get(0));
        assertEquals("Bob", members.get(1));
        assertEquals("Alice", members.get(2));

        deleteTestFile(filename);
    }

    @Test
    public void testWriteCitiesToFile() throws IOException {
        ArrayList<String> cities = new ArrayList<>();
        cities.add("New York");
        cities.add("Los Angeles");
        cities.add("Chicago");

        FileHandlerIO fileHandler = new FileHandlerIO();

        String filename = "testcities.txt";
        fileHandler.writeArrayToFile(cities, filename);

        ArrayList<String> readCities = fileHandler.readFile(filename);
        assertNotNull(readCities);
        assertEquals(3, readCities.size());
        assertEquals("New York", readCities.get(0));
        assertEquals("Los Angeles", readCities.get(1));
        assertEquals("Chicago", readCities.get(2));

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
    
    private void createTestFile() {
        JsonObject testJsonObject = new JsonObject();
        testJsonObject.addProperty("key", "value");
        TEST_FILENAME = "test_file.json";
        try {
            Files.write(Paths.get(TEST_FILENAME), testJsonObject.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
