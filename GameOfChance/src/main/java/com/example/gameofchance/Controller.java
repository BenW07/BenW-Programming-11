package com.example.gameofchance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//Imports meant for handling various aspects of javaFX SceneBuilder

import java.util.Random;
//Import allowing for randomly generated numbers

public class Controller {
    @FXML private ImageView firstDiceImage;
    @FXML private ImageView secondDiceImage;
    //Displays first and second dice images
    @FXML private Label diceValueOne;
    @FXML private Label diceValueTwo;
    //Shows first and second dice value as text
    @FXML private Label resultLabel;
    //Shows the game result (higher or lower)
    @FXML private Label balanceLabel;
    //Shows total balance
    @FXML private TextField betAmount;
    //Text field where the player puts their bet amount
    @FXML private Button higherButton;
    @FXML private Button lowerButton;
    @FXML private Button startButton;
    //Buttons to guess lower, higher, or start the game

    private int balance = 100;
    //Creates an initial balance of 100
    private int currentBet;
    //Stores the current bet amount
    private int firstRoll;
    //Value of first dice roll
    private int secondRoll;
    //Value of second dice roll
    private Random random = new Random();
    //Creates random number

    private final Image[] diceImages = new Image[6];
    //Array that stores all dice images (1-6)

    @FXML //This method runs when the FXML loads
    public void initialize() {
        for (int i = 0; i < 6; i++) {
            diceImages[i] = new Image(getClass().getResourceAsStream("/dice_" + (i + 1) + ".png"));
        }
        //Loads all dice images from the resources

        updateBalance(); //Shows initial balance
        resetGame(); //Creates initial game state
    }

    @FXML //This method runs when the start button is clicked
    private void onStartButtonClicked(ActionEvent event) {
        try {
            currentBet = Integer.parseInt(betAmount.getText());
            // Get the bet amount from text field and turn it into a number

            if (currentBet <= 0 || currentBet > balance) {
                resultLabel.setText("Invalid bet amount!");
                return;
                // Validates current bet
            }

            firstRoll = random.nextInt(6) + 1;
            // Rolls first dice (1-6)
            firstDiceImage.setImage(diceImages[firstRoll - 1]);
            // Shows matching dice image
            diceValueOne.setText("Value: " + firstRoll);
            // Displays the dice value

            secondDiceImage.setImage(null);
            diceValueTwo.setText("");
            // Clears second dice for the guess

            resultLabel.setText("Will the next roll be higher or lower?"); // Updates the GUI for the guessing part
            startButton.setDisable(true); // Disables start button
            betAmount.setDisable(true); // Disables bet input
            higherButton.setDisable(false); // Enables higher button
            lowerButton.setDisable(false); // Enables lower button
        } catch (NumberFormatException e) {
            // Catches if a player puts in an invalid number, then displays the below text
            resultLabel.setText("Please enter a valid number!");
        }
    }

    @FXML // Called when higher button is clicked
    private void onHigherButtonClick(ActionEvent event) {
        playGame(true); // True means player guessed higher
    }

    @FXML // Called when lower button is clicked
    private void onLowerButtonClick(ActionEvent event) {
        playGame(false); // False means player guessed lower
    }

    // Winner vs Loser below
    private void playGame(boolean isHigher) {
        secondRoll = random.nextInt(6) + 1;
        // Roll second dice
        secondDiceImage.setImage(diceImages[secondRoll - 1]);
        diceValueTwo.setText("Value: " + secondRoll);
        // Shows second dice image and value

        // Check game result
        if (secondRoll == firstRoll) {
            // Tie = loss
            balance -= currentBet;
            // on a loss, you take away the current bet amount from the balance
            resultLabel.setText("Tie! You lose your bet.");
        } else if ((isHigher && secondRoll > firstRoll) || (!isHigher && secondRoll < firstRoll)) {
            // Win
            balance += currentBet;
            // on a win, you add the current bet amount to the balance
            resultLabel.setText("You win! +" + currentBet);
        } else {
            // Lose
            balance -= currentBet;
            // on a loss, you take away the current bet amount from the balance
            resultLabel.setText("You lose! -" + currentBet);
        }

        updateBalance(); // Updates the GUI and balance amount
        resetGame(); // Resets GUI and dice amounts for next round
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + balance);

        // Check if player can continue/ ran out of money
        if (balance <= 0) {
            resultLabel.setText("Game over! You're out of money.");
            startButton.setDisable(true);
        }
    }

    //Resets GUI info for new round below
    private void resetGame() {
        higherButton.setDisable(true); // Disables higher button
        lowerButton.setDisable(true); // Disables lower button
        startButton.setDisable(false); // Enables start button
        betAmount.setDisable(false); // Enables bet amount
        betAmount.clear(); // Clears previous bet amount
    }
}