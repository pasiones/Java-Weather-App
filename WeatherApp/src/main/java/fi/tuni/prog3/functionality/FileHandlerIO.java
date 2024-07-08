package fi.tuni.prog3.functionality;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * FileHandlerIO is a utility class for reading from and writing to files, 
 * particularly focusing on JSON and text file formats. It provides methods 
 * for reading JSON data from a file, writing JSON data to a file, 
 * appending city names to a text file, reading city names from a text file, 
 * and writing city names to a text file
 * 
 * <p>
 * This class implements the {@link iReadAndWriteToFile} interface.
 * </p>
 * 
 * @author Dat Minh Lam
 * @see iReadAndWriteToFile
 */
public class FileHandlerIO implements iReadAndWriteToFile{
    
    /**
     * Reads JSON from the given file.
     * @param fileName name of the file to read from.
     * @return JsonObject which is the data from the given file.
     * @throws Exception if the method e.g, cannot find the file.
     */
    @Override
    public JsonObject readFromFile(String fileName) throws Exception {
        if (!Files.exists(Paths.get(fileName))) {
            return null; 
        }
        StringBuilder content = new StringBuilder();
        BufferedReader br = null;
    try {
        br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            content.append(line);
        }
    } catch (IOException e) {
        throw e; 
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
            }
        }
    }
    Gson gson = new Gson();
    return gson.fromJson(content.toString(), JsonObject.class);
    }

    /**
     * Write the current state as JSON into the given file.
     * @param fileName name of the file to write to.
     * @param data weather information that needs to be saved.
     * @return true if the write was successful, otherwise false.
     * @throws Exception if the method e.g., cannot write to a file.
     */
    @Override
    public boolean writeToFile(String fileName, JsonObject data) throws Exception {
        BufferedWriter writer = null;
    try {
        writer = new BufferedWriter(new FileWriter(fileName));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonData = gson.toJson(data);
        writer.write(jsonData);
    } catch (IOException e) {
        return false;
    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                return false;
            }
        }
    }
    return true;
    }
    
    /**
     * Write a city name to a given file.
     * @param cityName name of the city that needs to be saved.
     * @param filename name of the file to write to.
     * @throws java.io.IOException if file cannot be written to.
     */
    public void addToFile(String cityName, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(cityName + "\n"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Read from a text file with city name in each line.
     * @param filename name of the file to read from.
     * @return ArrayList that contains all the city name from the file.
     * @throws java.io.IOException if file cannot be read.
     */
    public ArrayList<String> readFile(String filename) throws IOException {
        ArrayList<String> members = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                members.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }
    
    /**
     * Write all city name from an array list into each line of a text file.
     * @param cities name of cities that needs to be saved.
     * @param filename name of the file to be written to.
     * @throws java.io.IOException if file cannot be written to.
     */
    public void writeArrayToFile(ArrayList<String> cities, String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String city : cities) {
                bw.write(city);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
