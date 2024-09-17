import java.util.Scanner;
//imports the scanner that allows for user input to be processed
class main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //Creates a scanner that reads input from the user (same scanner imported above)

        System.out.print("Do you want to encrypt or decrypt text? Enter 'encrypt' or 'decrypt': ");
        System.out.println();
        //Prints initial question of whether the user wants to decrypt or encrypt text

        String choiceInitial = scan.nextLine().toLowerCase();
        //Creates a string called choiceInitial that determines whether the user wants to encrypt or decrypt text


        if (choiceInitial.equals("encrypt")) {
            //If the choice is 'encrypt' the program will execute the following

            System.out.print("Please enter the text you want encrypted");
            System.out.println();
            //Prints message that tells the user to enter text

            String originalText = scan.nextLine();
            //Turns the user input into the string originalText

            String encryptedText = encryptText(originalText);
            //Makes the encryptedText string equal the originalText string encrypted using the encryptText method

            System.out.println("Original Text: " + originalText);
            System.out.println("Encrypted Text: " + encryptedText);
            //Prints the original and encrypted text for the user

            scan.close();
            //Closes the scanner

        } else if (choiceInitial.equals("decrypt")) {

            System.out.print("Please enter the text you want decrypted: ");
            String encryptedText = scan.nextLine();
            //Asks users for encrypted text, then scans user inputted encrypted text and turns it into the string encryptedText

            String decryptedText = decryptText(encryptedText);
            //Makes the decryptedText string equal the decrypted version of the encryptedText string

            System.out.println("Encrypted Text: " + encryptedText);
            System.out.println("Decrypted: " + decryptedText);
            //Prints result of decrypted text and original encrypted text

        } else {
            System.out.println("Invalid choice. Please try again.");
            System.out.println();
        }

    }


    public static String encryptText(String text) {
        //Creates a method named encryptText that 'encrypts' the string of text that the user inputs

        String[] words = text.split(" ");
        //Splits the text entered at every space, into an array of words

        StringBuilder encryptedText = new StringBuilder();
        //This allows for the creation of new strings without having to individually create new objects


        for (String word : words) {
            //Looks through every word in the array (words are separated by spaces)
            if (word.length() > 1) {
                //Checks if the word has more than 1 character

                String encryptedWord = word.charAt(word.length() - 1) + word.substring(0, word.length() - 1) + "inay" + " oh";
                //Gets the last character of the word, then the rest of the word, excluding the last character then adds "inay" and " oh" to the end of each word
                encryptedText.append(encryptedWord).append(" ");
                //Appends the encrypted word to the encryptedText string (append means it adds the text or characters to the end of the stringbuilder object, in this case the encrypted word string)
            } else {

                encryptedText.append(word).append("ayhh ").append(" ");
                //If the word only has 1 letter than just add ayhh at the beginning then append it
            }
        }


        return encryptedText.toString();
        //Converts the StringBuilder into a string
    }

    public static String decryptText(String text) {
        //Creates a method named decryptText that 'decrypts' the string of text that the user inputs

        String[] words = text.split(" ");
        //Splits the text entered at every space, into an array of words

        StringBuilder decryptedText = new StringBuilder();
        //This allows for the creation of new strings without having to individually create new objects,

        for (String word : words) {
            //Looks through every word in the array (words separated by spaces)
            if (word.endsWith("inay")) {
                //Checks if the word ends with "inay"

                String realWord = word.substring(1, word.length() - 4) + word.substring(0,1);
                //Removes the "inay" from the words and adds the first letter to the end, making the rest of the word the realWord string

                decryptedText.append(realWord).append(" ");
                //Appends the decryptedWord string to the decryptedText string

            } else if (word.endsWith("ayhh")) {
                //If the word ends with ayhh

                String decryptedWord = word.substring(0, word.length() - 4);
                //Removes "ayhh"

                decryptedText.append(decryptedWord).append(" ");
                //Appends the decryptedWord string to teh decryptedText string
            }
        }

        return decryptedText.toString();
        //Returns the decrypted text as a string
    }
}