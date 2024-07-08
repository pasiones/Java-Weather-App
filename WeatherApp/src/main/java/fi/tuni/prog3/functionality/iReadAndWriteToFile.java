package fi.tuni.prog3.functionality;
import com.google.gson.JsonObject;

/**
 * Interface with methods to read from a file and write to a file.
 */
public interface iReadAndWriteToFile {

    /**
     * Reads JSON from the given file.
     * @param fileName name of the file to read from.
     * @return JsonObject which is the data from the given file.
     * @throws Exception if the method e.g, cannot find the file.
     */
    public JsonObject readFromFile(String fileName) throws Exception;

    /**
     * Write the current state as JSON into the given file.
     * @param fileName name of the file to write to.
     * @param data weather information that needs to be saved.
     * @return true if the write was successful, otherwise false.
     * @throws Exception if the method e.g., cannot write to a file.
     */
    public boolean writeToFile(String fileName, JsonObject data) throws Exception;
}
