package fi.tuni.prog3.functionality;

import java.util.ArrayList;

/**
 * This class handles switching units of measurement and maintains the state of 
 * the unit system. It provides methods to switch between metric and imperial 
 * units, as well as methods to read and update the unit state from a file.
 * @author Dat Minh Lam
 */
public class UnitsHandler {
    FileHandlerIO fileHandler = new FileHandlerIO();
    private ArrayList<String> unitArray;
    private String unit;
    private String unitVisual;
    private final String tempStateFile;
    private String unitWind;
    
    /**
     * Constructs a new UnitsHandler with the specified initial unit system and 
     * file name.
     * @param unit the initial unit system ("metric")
     * @param unitVisual the visual representation of the temperature unit
     * @param unitWind the visual representation of the wind speed unit
     * @param filename the name of the file used to store the unit state
     */
    public UnitsHandler(String unit, String unitVisual, String unitWind, String filename) {
        this.unit = unit;
        this.unitVisual = unitVisual;
        this.unitWind = unitWind;
        this.tempStateFile = filename;
    }
    
    /**
     * Simulates pressing a button to switch between metric and imperial units.
     * Writes an empty array to the tempStateFile and switches the unit system.
     * @throws Exception if an error occurs while writing to the file
     */
    public void pressButton() throws Exception {
        ArrayList<String> emptyArr = new ArrayList<>();
        fileHandler.writeArrayToFile(emptyArr, tempStateFile);
        if ("metric".equals(unit)) {
            setToImperial();
        } else {
            setToMetric();
        }
    }
    
    /**
     * Checks the state of the unit system from the tempStateFile and updates 
     * the unit accordingly. Writes an empty array to the tempStateFile after reading.
     * @throws Exception if an error occurs while reading or writing to the file
     */
    public void checkTempState() throws Exception {
        this.unitArray = fileHandler.readFile(tempStateFile);
        if (this.unitArray.size() == 1) {
            this.unit = this.unitArray.get(0); 
        }
        ArrayList<String> emptyArr = new ArrayList<>();
        fileHandler.writeArrayToFile(emptyArr, tempStateFile);
        if ("metric".equals(unit)) {
            setToMetric();
        } else {
            setToImperial();
        }  
    }
    
    /**
     * Sets the unit system to metric (Celsius and meters per second).
     * Writes "metric" to the tempStateFile.
     * @throws Exception if an error occurs while writing to the file
     */
    private void setToMetric() throws Exception {
        unit = "metric";
        unitVisual = "°C";
        unitWind = "m/s";
        fileHandler.addToFile(unit, tempStateFile);
    }
    
    /**
     * Sets the unit system to imperial (Fahrenheit and miles per hour).
     * Writes "imperial" to the tempStateFile.
     * @throws Exception if an error occurs while writing to the file
     */
    private void setToImperial() throws Exception {
        unit = "imperial";
        unitVisual = "°F";
        unitWind = "mph";
        fileHandler.addToFile(unit, tempStateFile);
    }
    
    /**
     * Gets the current unit system.
     * @return the current unit system ("metric" or "imperial")
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * Gets the visual representation of the temperature unit.
     * @return the visual representation of the temperature unit ("°C" or "°F")
     */
    public String getUnitVisual() {
        return this.unitVisual;
    }

    /**
     * Gets the visual representation of the wind speed unit.
     * @return the visual representation of the wind speed unit ("m/s" or "mph")
     */
    public String getUnitWind() {
        return this.unitWind;
    }
}
