module fi.tuni.progthree.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens fi.tuni.prog3.weatherapp to javafx.fxml;
    exports fi.tuni.prog3.weatherapp;
    requires com.google.gson;
}
