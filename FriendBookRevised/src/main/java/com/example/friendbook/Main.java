package com.example.friendbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //requires: FXML file
    //modifies: stage
    //effects: loads the FXML GUI, sets up the scene and displays the window
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/friendbook/friendsbook.fxml"));

        //Creates a new scene for the GUI
        Scene scene = new Scene(root);

        //sets up the main window
        stage.setTitle("Friends Book");
        stage.setScene(scene);
        stage.show();
    }

    //Starts the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}