import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    //stores lines of text into memory
    private ArrayList<String> textLines;
    //creates constants INPUT/OUTPUT_FILE to input the file and output the search result file
    private static final String INPUT_FILE = "ProgrammingHistory.txt";
    private static final String OUTPUT_FILE = "SearchResults.txt";

    //initializes the ArrayList and loads the text file
    public Main() {
        textLines = new ArrayList<>();
        loadProgrammingHistoryText();
    }

    //method allowing characters to repeat
    private String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder();
        //for multiple instances of one character, adds to the character count
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        //returns a proper string from the string builder
        return sb.toString();
    }

    //method to load the programming text file
    private void loadProgrammingHistoryText() {
        //reads the bytes from the file
        FileInputStream fileIn = null;
        Scanner scanner = null;

        //try block with exception in case an error occurs when reading the file
        try {
            //opens file and creates a scanner to read the file
            fileIn = new FileInputStream(INPUT_FILE);
            scanner = new Scanner(fileIn);

            while (scanner.hasNextLine()) {
                //loops through each line until no more lines are left and adds the lines to the ArrayList
                String line = scanner.nextLine();
                textLines.add(line);
            }
            //prints success message with the amount of lines loaded
            System.out.println("Successfully loaded " + textLines.size() + " lines from " + INPUT_FILE);

            //this exception catches file reading errors
        } catch (IOException e) {
            System.err.println("Error reading file " + INPUT_FILE + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            //closes the resources to prevent memory leakage
            try {
                if (scanner != null) {
                    scanner.close();
                }
                if (fileIn != null) {
                    fileIn.close();
                }
                //if an error occurs an error message is printed
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
    }

    //method allowing the search results to be written to the searchResults.txt file
    private void writeSearchResultsToFile(String searchWord, ArrayList<Integer> foundIndexes, int totalCount) {
        FileOutputStream fileOut = null;
        PrintWriter writer = null;

        //this tries to write to the file
        try {
            fileOut = new FileOutputStream(OUTPUT_FILE, true); // append mode
            writer = new PrintWriter(fileOut);

            //prints the search results and the word searched for into the file
            writer.println("\tSEARCH RESULTS");
            writer.println("Searching for word: \"" + searchWord + "\"");
            writer.println("_________________________________________");

            /* loops through the line where the word searched for was found by getting the actual line
             then converting the search to lowercase so that no words would be excluded because of capitalization */
            for (int index : foundIndexes) {
                String line = textLines.get(index);
                String lowerLine = line.toLowerCase();
                String lowerSearchWord = searchWord.toLowerCase();

                //counts how many times the word appears in a specific line
                int lineCount = 0;
                int pos = 0; //tracks position of the search

                //loop meant to find every time the word appears in the line
                while ((pos = lowerLine.indexOf(lowerSearchWord, pos)) != -1) {
                    lineCount++;
                    pos += lowerSearchWord.length();
                }

                //writes the detailed information about the line to the file
                writer.println("Line " + (index + 1) + " (Index " + index + "): " + lineCount + " occurrence(s)");
                writer.println("Content: " + line);
                writer.println();
            }

            /* writes a summary of the information including, the amount of times a word was found, what
            lines it was found in, and the ArrayList indexes where it appears */
            writer.println("SEARCH SUMMARY:");
            writer.println("Word \"" + searchWord + "\" found " + totalCount + " times");
            writer.println("Found in " + foundIndexes.size() + " different lines");
            writer.print("ArrayList indexes where word appears: ");
            //loop to write the indexes where the word was found
            for (int i = 0; i < foundIndexes.size(); i++) {
                writer.print(foundIndexes.get(i));
                if (i < foundIndexes.size() - 1) {
                    writer.print(", ");
                }
            }
            writer.println();
            writer.println("________________________________________");
            writer.println();

            //tells the user that the results are written in the output file
            System.out.println("Search results written to " + OUTPUT_FILE);

            //catches any file writing errors
        } catch (IOException e) {
            System.err.println("Error writing to file " + OUTPUT_FILE + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            //closes resources to prevent memory leakage
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing output file: " + e.getMessage());
            }
        }
    }

    //method to search for a word and return the information about its occurrences
    public void searchWord(String searchWord) {
        //creates an ArrayList to store line indexes where the word is found
        ArrayList<Integer> foundIndexes = new ArrayList<>();
        int totalCount = 0;

        //prints a header for searching the word
        System.out.println("Searching for word: \"" + searchWord + "\"");
        System.out.println("________________________________________");

        //loop searching each line in text file data
        for (int i = 0; i < textLines.size(); i++) {
            String line = textLines.get(i);

            //converts line and searched word to lowercase
            String lowerLine = line.toLowerCase();
            String lowerSearchWord = searchWord.toLowerCase();

            //counts the occurrence of the searched word in the line
            int lineCount = 0;
            int index = 0;

            //loop to find the occurrences of the word in this line
            while ((index = lowerLine.indexOf(lowerSearchWord, index)) != -1) {
                lineCount++;
                totalCount++;
                index += lowerSearchWord.length();
            }

            //if a word is found in the line it displays the results and stores the line index
            if (lineCount > 0) {
                foundIndexes.add(i);
                System.out.println("Line " + (i + 1) + " (Index " + i + "): " + lineCount + " occurrence(s)");
                System.out.println("Content: " + line);
                System.out.println();
            }
        }

        //displays summary of information to the user
        System.out.println("SEARCH SUMMARY:");
        System.out.println("Word \"" + searchWord + "\" found " + totalCount + " times");
        System.out.println("Found in " + foundIndexes.size() + " different lines");
        System.out.print("ArrayList indexes where word appears: ");
        for (int i = 0; i < foundIndexes.size(); i++) {
            System.out.print(foundIndexes.get(i));
            if (i < foundIndexes.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println();

        //writes all the results into the output file
        writeSearchResultsToFile(searchWord, foundIndexes, totalCount);
    }

    //method allowing the display of all lines in the ProgrammingHistory.txt file
    public void displayAllLines() {
        System.out.println("PROGRAMMING HISTORY TEXT (Total lines: " + textLines.size() + ")");
        System.out.println("___________________________________________________________");

        for (int i = 0; i < textLines.size(); i++) {
            System.out.println("Index " + i + " (Line " + (i + 1) + "): " + textLines.get(i));
        }
        System.out.println();
    }

    //this is the main method allowing the program to run
    public static void main(String[] args) {
        Main processor = new Main();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Programming History Text Processor");
        System.out.println("__________________________________");

        while (true) {
            //displays menu allowing the user to choose different options
            System.out.println("\nChoose an option:");
            System.out.println("1. Search for a word");
            System.out.println("2. Display all lines");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            //scans user input allowing for choices
            String choice = scanner.nextLine().trim();

            //switch statement to handle different choices
            switch (choice) {
                //asks the user to choose the word they want to search for
                case "1":
                    System.out.print("Enter the word to search for: ");
                    String searchWord = scanner.nextLine().trim();
                    if (!searchWord.isEmpty()) {
                        processor.searchWord(searchWord);
                    } else {
                        System.out.println("Please enter a valid word.");
                    }
                    break;

                    //displays all lines
                case "2":
                    processor.displayAllLines();
                    break;

                    //closes the menu
                case "3":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                    //when user enters an invalid choice
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}