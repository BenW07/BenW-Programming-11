package com.example.friendbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Controller class for the Friends Book GUI with data persistence usage
public class FriendsBookController {
    private FriendsManager friendsManager = new FriendsManager();
    private String currentFileName = null; // Keeps track of currently loaded file

    //FXML fields taken from the .fxml class
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextArea interestsArea;
    @FXML
    private ListView<Friend> friendsListView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label interestsLabel;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button saveAsButton;
    @FXML
    private Button newFileButton;
    @FXML
    private Label currentFileLabel;

    private ObservableList<Friend> friendsObservableList = FXCollections.observableArrayList();

    //Initializes controller
    //modifies: this
    //effects: sets up ListView, buttons, and initial state
    @FXML
    public void initialize() {
        //Sets up the ListView
        friendsListView.setItems(friendsObservableList);
        friendsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFriendDetails(newValue));

        //Disables delete button when no friend is selected
        deleteButton.disableProperty().bind(
                friendsListView.getSelectionModel().selectedItemProperty().isNull());

        //Initialize file status label
        updateCurrentFileLabel();
    }

    //Handles adding a new friend with all required field validation
    //modifies: this, friendsManager
    //effects: creates and adds a new friend if all validations pass
    @FXML
    private void handleAddFriend() {
        try {
            //gets and trims all input values
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String interests = interestsArea.getText().trim();

            //Validates that all required fields are filled
            if (name.isEmpty()) {
                showAlert("Missing Information", "Name is required");
                return;
            }

            if (email.isEmpty()) {
                showAlert("Missing Information", "Email is required");
                return;
            }

            if (phone.isEmpty()) {
                showAlert("Missing Information", "Phone number is required");
                return;
            }

            if (interests.isEmpty()) {
                showAlert("Missing Information", "Interests are required");
                return;
            }

            //Validates that age is provided and is a positive number
            if (ageText.isEmpty()) {
                showAlert("Missing Information", "Age is required");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                showAlert("Invalid Age", "Age must be a number");
                return;
            }

            if (age <= 0) {
                showAlert("Invalid Age", "Age must be positive");
                return;
            }

            //Validates email format
            if (!email.contains("@") || !email.contains(".")) {
                showAlert("Invalid Email", "Please enter a valid email address");
                return;
            }

            //Creates and adds a new friend
            Friend newFriend = new Friend(name, age, email, phone, interests);
            friendsManager.addFriend(newFriend);
            friendsObservableList.setAll(friendsManager.getAllFriends());
            clearFields();

        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    //Handles deleting the selected friend
    //modifies: this, friendsManager
    //effects: removes selected friend from list
    @FXML
    private void handleDeleteFriend() {
        Friend selectedFriend = friendsListView.getSelectionModel().getSelectedItem();
        if (selectedFriend != null) {
            friendsManager.removeFriend(selectedFriend);
            friendsObservableList.setAll(friendsManager.getAllFriends());
            clearFriendDetails();
        }
    }

    //Handles saving the current friends list to a file
    //modifies: file system
    //effects: saves friends to current file or prompts for new file if none selected
    @FXML
    private void handleSaveFile() {
        if (currentFileName == null) {
            handleSaveAsFile(); // If no current file, prompt for new file
        } else {
            saveFriendsToFile(new File(currentFileName));
        }
    }

    //Handles saving the friends list to a new file
    //modifies: file system, this
    //effects: prompts user for file location and saves friends data
    @FXML
    private void handleSaveAsFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Friends File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        //Gets the stage from any UI component
        Stage stage = (Stage) saveAsButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            //Makes sure that the file has .txt extension
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            saveFriendsToFile(file);
        }
    }

    //Handles loading a friends file
    //modifies: this, friendsManager
    //effects: prompts user to select a file and loads friends data
    @FXML
    private void handleLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Friends File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        Stage stage = (Stage) loadButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            loadFriendsFromFile(file);
        }
    }

    //Handles creating a new friends file (clears current data)
    //modifies: this, friendsManager
    //effects: clears all current friend data and resets file tracking
    @FXML
    private void handleNewFile() {
        // Ask user if they want to save current data before clearing
        if (!friendsManager.getAllFriends().isEmpty()) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("New File");
            confirmAlert.setHeaderText("Create New Friends List");
            confirmAlert.setContentText("Do you want to save the current friends list before creating a new one?");

            ButtonType saveButton = new ButtonType("Save");
            ButtonType dontSaveButton = new ButtonType("Don't Save");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmAlert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == saveButton) {
                    handleSaveFile(); // Save before clearing
                } else if (result.get() == cancelButton) {
                    return; // User cancelled, don't create new file
                }
                // If "Don't Save" was selected, continue with clearing
            }
        }

        // Clear all data and reset file tracking
        friendsManager = new FriendsManager();
        friendsObservableList.clear();
        currentFileName = null;
        clearFriendDetails();
        updateCurrentFileLabel();
    }

    //Saves the current friends list to the specified file using String manipulation
    //requires: file is not null
    //modifies: file system
    //effects: writes all friend data to the file in a custom format
    private void saveFriendsToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            List<Friend> friends = friendsManager.getAllFriends();

            // Write each friend's data on a separate line using a delimiter
            for (Friend friend : friends) {
                // Create a string with friend data separated by a delimiter (|)
                // Format: name|age|email|phone|interests
                String friendData = friend.getName() + "|" +
                        friend.getAge() + "|" +
                        friend.getEmail() + "|" +
                        friend.getPhoneNumber() + "|" +
                        friend.getInterests();

                // Replace any existing pipe characters in the data to avoid conflicts
                friendData = friendData.replace("|", "&#124;");

                writer.write(friendData);
                writer.newLine();
            }

            currentFileName = file.getAbsolutePath();
            updateCurrentFileLabel();
            showInfoAlert("Save Successful", "Friends list saved to " + file.getName());

        } catch (IOException e) {
            showAlert("Save Error", "Unable to save file: " + e.getMessage());
        }
    }

    //Loads friends data from the specified file using String manipulation
    //requires: file exists and is readable
    //modifies: this, friendsManager
    //effects: reads friend data from file and populates the friends list
    private void loadFriendsFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Friend> loadedFriends = new ArrayList<>();
            String line;
            int lineNumber = 0;

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    // Parse the friend data using string manipulation
                    Friend friend = parseFriendFromString(line);
                    if (friend != null) {
                        loadedFriends.add(friend);
                    }
                } catch (Exception e) {
                    showAlert("Load Error",
                            "Error parsing line " + lineNumber + ": " + e.getMessage() +
                                    "\nThis line will be skipped.");
                }
            }

            // Clear current data and load new friends
            friendsManager = new FriendsManager();
            for (Friend friend : loadedFriends) {
                friendsManager.addFriend(friend);
            }

            friendsObservableList.setAll(friendsManager.getAllFriends());
            currentFileName = file.getAbsolutePath();
            updateCurrentFileLabel();
            clearFriendDetails();

            showInfoAlert("Load Successful",
                    "Loaded " + loadedFriends.size() + " friends from " + file.getName());

        } catch (IOException e) {
            showAlert("Load Error", "Unable to load file: " + e.getMessage());
        }
    }

    //Parses a friend object from a string using string manipulation
    //requires: data string in format "name|age|email|phone|interests"
    //modifies: nothing
    //effects: returns a Friend object parsed from the string, or null if parsing fails
    private Friend parseFriendFromString(String data) {
        // Restore any pipe characters that were escaped during saving
        data = data.replace("&#124;", "|");

        // Split the string by the delimiter (boundary between data pieces)
        String[] parts = data.split("\\|");

        // Makes sure that we have exactly 5 parts (name, age, email, phone, interests)
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid data format. Expected 5 fields, got " + parts.length);
        }

        try {
            String name = parts[0].trim();
            int age = Integer.parseInt(parts[1].trim());
            String email = parts[2].trim();
            String phone = parts[3].trim();
            String interests = parts[4].trim();

            // Validates the parsed data
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || interests.isEmpty()) {
                throw new IllegalArgumentException("One or more required fields are empty");
            }

            if (age <= 0) {
                throw new IllegalArgumentException("Age must be positive");
            }

            if (!email.contains("@") || !email.contains(".")) {
                throw new IllegalArgumentException("Invalid email format");
            }

            return new Friend(name, age, email, phone, interests);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid age format: " + parts[1]);
        }
    }

    //Updates the current file label to show which file is currently loaded
    //modifies: currentFileLabel
    //effects: displays the current file name or "No file loaded"
    private void updateCurrentFileLabel() {
        if (currentFileName == null) {
            currentFileLabel.setText("No file loaded");
        } else {
            // Extracts just the filename from the full path
            File file = new File(currentFileName);
            currentFileLabel.setText("Current file: " + file.getName());
        }
    }

    //Displays friend details in the detail labels
    //requires: friend can be null
    //modifies: detail labels
    //effects: shows friend's information in labels, or clears if friend is null
    private void showFriendDetails(Friend friend) {
        if (friend != null) {
            nameLabel.setText(friend.getName());
            ageLabel.setText(String.valueOf(friend.getAge()));
            emailLabel.setText(friend.getEmail());
            phoneLabel.setText(friend.getPhoneNumber());
            interestsLabel.setText(friend.getInterests());
        }
    }

    //Clears all friend detail labels
    //modifies: this
    //effects: resets all detail labels to empty
    private void clearFriendDetails() {
        nameLabel.setText("");
        ageLabel.setText("");
        emailLabel.setText("");
        phoneLabel.setText("");
        interestsLabel.setText("");
    }

    //Clears all input fields
    //modifies: this
    //effects: clears all input fields to empty
    private void clearFields() {
        nameField.clear();
        ageField.clear();
        emailField.clear();
        phoneField.clear();
        interestsArea.clear();
    }

    //Shows an error alert popup
    //requires: title and message not null
    //effects: displays an error message to the user
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Shows an information alert popup
    //requires: title and message not null
    //effects: displays an information message to the user
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}