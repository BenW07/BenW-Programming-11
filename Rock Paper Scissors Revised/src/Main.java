import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int playerWins = 0;
        int compWins = 0;
        boolean playAgain = true;

        Scanner scan = new Scanner(System.in);
        // creates a scanner object allowing the player to input console text

        System.out.println("How about we play a game of Rock, Paper, Scissors!");
        System.out.println("Type 'r' for rock, 'p' for paper, 's' for scissors, or 'q' to quit");
        System.out.println("_______________________________________________________");
        // prompts the player to play Rock, Paper, Scissors and explains valid system inputs

        while (playAgain) {
            // loops all interactions while playAgain is true
            String rock = "r";
            String paper = "p";
            String scissors = "s";
            String quit = "q";
            // defines valid player inputs

            String compChoice = "";
            // defines computer choice string

            int randomNumber = (int) (Math.random() * 3 + 1);
            // creates a random number from 1 to 3 to determine the computer's choice

            if (randomNumber == 1) {
                compChoice = rock;
                // makes the computer choose rock if the generated number is 1
            } else if (randomNumber == 2) {
                compChoice = paper;
                // makes the computer choose paper if the generated number is 2
            } else if (randomNumber == 3) {
                compChoice = scissors;
                // makes the computer choose scissors if the generated number is 3
            }

            String playerChoice = scan.nextLine();
            // reads player input from the console

            if (playerChoice.equals(scissors) && compChoice.equals(rock)) {
                compWins += 1;
                // adds a point to compWins on the scoreboard
                System.out.println("Player chose: Scissors          Computer chose: Rock");
                System.out.println("\t You Lost!");
                System.out.println("_______________________________________________________");
            } else if (playerChoice.equals(scissors) && compChoice.equals(paper)) {
                playerWins += 1;
                // adds a point to playerWins on the scoreboard
                System.out.println("Player chose: Scissors          Computer chose: Paper");
                System.out.println("\t You Win!");
                System.out.println("_______________________________________________________");
            } else if (playerChoice.equals(scissors) && compChoice.equals(scissors)) {
                System.out.println("Player chose: Scissors          Computer chose: Scissors");
                System.out.println("\t Draw!");
                System.out.println("_______________________________________________________");
                // defines what happens if the player picks scissors or types 's'
            }

            if (playerChoice.equals(rock) && compChoice.equals(paper)) {
                compWins += 1;
                // adds a point to compWins on the scoreboard
                System.out.println("Player chose: Rock         Computer chose: Paper");
                System.out.println("\t You Lost!");
                System.out.println("_______________________________________________________");
            } else if (playerChoice.equals(rock) && compChoice.equals(scissors)) {
                playerWins += 1;
                // adds a point to playerWins on the scoreboard
                System.out.println("Player chose: Rock          Computer chose: Scissors");
                System.out.println("\t You Win!");
                System.out.println("_______________________________________________________");
            } else if (playerChoice.equals(rock) && compChoice.equals(rock)) {
                System.out.println("Player chose: Rock          Computer chose: Rock");
                System.out.println("\t Draw!");
                System.out.println("_______________________________________________________");
                // defines what happens if the player picks rock or types 'r'
            }

            if (playerChoice.equals(paper) && compChoice.equals(scissors)) {
                compWins += 1;
                // adds a point to compWins on the scoreboard
                System.out.println("Player chose: Paper         Computer chose: Scissors");
                System.out.println("\t You Lost!");
                System.out.println("_______________________________________________________");
            } else if (playerChoice.equals(paper) && compChoice.equals(rock)) {
                playerWins += 1;
                // adds a point to playerWins on the scoreboard
                System.out.println("Player chose: Paper          Computer chose: Rock");
                System.out.println("\t You Win!");
                System.out.println("_______________________________________________________");
            } else if (playerChoice.equals(paper) && compChoice.equals(paper)) {
                System.out.println("Player chose: Paper          Computer chose: Paper");
                System.out.println("\t Draw!");
                System.out.println("_______________________________________________________");
                // defines what happens if the player picks paper or types 'p'
            }

            if (!playerChoice.equals(rock) && !playerChoice.equals(paper) && !playerChoice.equals(scissors) && !playerChoice.equals(quit)) {
                System.out.println("That's not a valid choice :(");
                System.out.println("_______________________________________________________");
                // gives a response if the player enters an invalid answer
            }

            if (!playerChoice.equals(quit)) {
                System.out.println("Player Wins: " + playerWins + "\t Computer Wins: " + compWins);
                // prevents the scoreboard from showing when the player types q (cleaner interface)
            }

            if (playerChoice.equals(quit)) {
                playAgain = false;
                // disables the loop if the player types q
            }
        }

        System.out.println("");
        System.out.println("Thanks for playing!");
        // gives the player a goodbye message
        scan.close();
        // closes the scanner to prevent a memory leak
    }
}