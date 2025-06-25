package com.example.friendbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

//Controller class for the Friends Book GUI
public class FriendsBookController {
    private FriendsManager friendsManager = new FriendsManager();

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

    private ObservableList<Friend> friendsObservableList = FXCollections.observableArrayList();

    //Initializes controller
    //modifies: this
    //effects: sets up ListView and buttons
    @FXML
    public void initialize() {
        //Sets up the ListView
        friendsListView.setItems(friendsObservableList);
        friendsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFriendDetails(newValue));

        //Disables delete button when no friend is selected
        deleteButton.disableProperty().bind(
                friendsListView.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void handleAddFriend() {
        try {
            //gets and trims all input values
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String interests = interestsArea.getText().trim();

            //Validates required fields
            if (name.isEmpty() || email.isEmpty()) {
                showAlert("Missing Information", "Name and email are required");
                return;
            }

            //Validates that age is a positive number
            if (ageText.isEmpty()) {
                showAlert("Invalid Age", "Please enter an age");
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

    //modifies: this
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

    //effects: shows friend's information in labels
    private void showFriendDetails(Friend friend) {
        if (friend != null) {
            nameLabel.setText(friend.getName());
            ageLabel.setText(String.valueOf(friend.getAge()));
            emailLabel.setText(friend.getEmail());
            phoneLabel.setText(friend.getPhoneNumber());
            interestsLabel.setText(friend.getInterests());
        }
    }

    //modifies: this
    //effects: resets all detail labels to empty
    private void clearFriendDetails() {
        nameLabel.setText("");
        ageLabel.setText("");
        emailLabel.setText("");
        phoneLabel.setText("");
        interestsLabel.setText("");
    }

    //modifies: this
    //effects: clears all input fields to empty
    private void clearFields() {
        nameField.clear();
        ageField.clear();
        emailField.clear();
        phoneField.clear();
        interestsArea.clear();
    }

    //Shows an alert pop up
    //effects: displays an error message to the user
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}