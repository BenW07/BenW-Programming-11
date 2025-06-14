package com.example.gameofchance;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// Imports for javafx aspects

import java.io.IOException;
// import for handling file loading errors

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GameOfChance.fxml"));
        // Loads the FXML file that contains GUI info

        Scene scene = new Scene(fxmlLoader.load(), 500, 440);
        //Creates a scene with the window size 500x440

        //Sets up main window below
        stage.setTitle("Game of Chance"); // Window title
        stage.setScene(scene); // Add the scene to the stage
        stage.show(); // Displays the window
    }

    public static void main(String[] args) {launch(args);
        // Standard main method that launches application and starts the JavaFX application
    }
}