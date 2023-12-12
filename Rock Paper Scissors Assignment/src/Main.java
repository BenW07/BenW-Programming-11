import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String rock = "r";
        String paper = "p";
        String scissors = "s";
        //defines valid player inputs

        String compChoice = "compChoice";
        //defines computer choice string

        int randomNumber = (int) (Math.random() * 9 + 1);
        //creates random number from 1 to 9 in order to determine the computer's choice

        if (randomNumber >= 1 && randomNumber <= 3) {
            compChoice = rock;
            //makes computer choose rock if number = generated 1 - 3
        } else if (randomNumber >= 4 && randomNumber <= 6) {
            compChoice = paper;
            //makes computer choose paper if number generated = 4 - 6
        } else if (randomNumber >= 7 && randomNumber <= 9) {
            compChoice = scissors;
            //makes computer choose scissors if number generated = 7 - 9
         }

        System.out.println("How about we play a game of Rock, Paper, Scissors!");
        System.out.println("Type 'r' for rock, 'p' for paper, and 's' for scissors");
        //prompts the player to play Rock, Paper, Scissors and explains valid system inputs

        Scanner scan = new Scanner(System.in);
        //creates a scanner object allowing player to input console text

        String playerChoice = scan.nextLine();
        //reads player input from the console

        if (playerChoice.equals(scissors) && compChoice.equals(rock)) {
            System.out.println("Player chose: Scissors          Computer chose: Rock");
            System.out.println("You Lost!");
        } else if (playerChoice.equals(scissors) && compChoice.equals(paper)) {
            System.out.println("Player chose: Scissors          Computer chose: Paper");
            System.out.println("You Win!");
        } else if (playerChoice.equals(scissors) && compChoice.equals(scissors)) {
            System.out.println("Player chose: Scissors          Computer chose: Scissors");
            System.out.println("Draw!");
            //defines what happens if player picks scissors or types 's'
        }

        if (playerChoice.equals(rock) && compChoice.equals(paper)) {
            System.out.println("Player chose: Rock         Computer chose: Paper");
            System.out.println("You Lost!");
        } else if (playerChoice.equals(rock) && compChoice.equals(scissors)) {
            System.out.println("Player chose: Rock          Computer chose: Scissors");
            System.out.println("You Win!");
        } else if (playerChoice.equals(rock) && compChoice.equals(rock)) {
            System.out.println("Player chose: Rock          Computer chose: Rock");
            System.out.println("Draw!");
            //defines what happens if player picks rock or types 'r'
        }

        if (playerChoice.equals(paper) && compChoice.equals(scissors)) {
            System.out.println("Player chose: Paper         Computer chose: Scissors");
            System.out.println("You Lost!");
        } else if (playerChoice.equals(paper) && compChoice.equals(rock)) {
            System.out.println("Player chose: Paper          Computer chose: Rock");
            System.out.println("You Win!");
        } else if (playerChoice.equals(paper) && compChoice.equals(paper)) {
            System.out.println("Player chose: Paper          Computer chose: Paper");
            System.out.println("Draw!");
            //defines what happens if player picks paper or types 'p'
        }

        if(!playerChoice.equals(rock) && !playerChoice.equals(paper) && !playerChoice.equals(scissors)) {
            System.out.println("That's not a valid choice :(");
            //gives a response if the player enters an invalid answer
        }

        System.out.println("\nIf you would like to play again, please rerun the code.");
        //gives text that prompts the player to try again

        scan.close();
        //closes the scanner to prevent a memory leak
    }
    //I have written the player choice possibilities in a redundant way because I haven't learned how to loop inputs, yet
    // I have also indented some lines to try to improve the readability
}