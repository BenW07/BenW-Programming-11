package com.example.inventorymanager;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Main class acting as an entry point for the Inventory Manager application
public class Main extends Application {

    //Requires: InventoryManager.fxml exists in resources
    //Modifies: primaryStage
    //Effects: Loads UI from FXML, configures and displays main window
    @Override
    public void start(Stage primaryStage) {
        try {
            //Loads the FXML file created with Scene Builder
            Parent root = FXMLLoader.load(getClass().getResource("InventoryManager.fxml"));

            //Creates the scene with the loaded FXML
            Scene scene = new Scene(root, 800, 600);

            //Sets up the primary stage
            primaryStage.setTitle("Simple Inventory Manager");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            //Handles any errors that occur during startup
            System.err.println("Error loading application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Launches the actual JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}