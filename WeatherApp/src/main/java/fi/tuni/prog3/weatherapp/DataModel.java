package fi.tuni.prog3.weatherapp;

/**
 * Class to communicate between 2 controller
 * @author Huy Duong Cat
 */
public class DataModel {
    private static String text;
    /**
     * 
     * @return stored text
     */
    public static String getText() {
        return text;
    }
    /**
     * Store a text
     * @param text Text that need to be stored.
     */
    public static void setText(String text) {
        DataModel.text = text;
    }
    
    /**
     * Clear the stored text.
     */
    public static void clearText(){
        DataModel.text = null;
    }
}
