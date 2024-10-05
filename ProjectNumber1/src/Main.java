public class Main {
    //Code your solution to problem number one here
    static int problemOne(String s) {
        int vowelCount = 0;
        //Creates the integer vowelCount with a starting value of 0
        for (int i = 0; i < s.length(); i++) {
            char letter = s.charAt(i);
            //Creates an integer 'i' that looks through each character in the string 's' using a for loop
            if (letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u') {
                vowelCount++;
                //If a letter is == to a,e,i,o,u (vowels) it adds one value to the vowelCount integer
            }
        }
        System.out.println("Vowel count is: " + vowelCount);
        return vowelCount;
        //Prints the vowelCount of string 's' and returns the integer vowelCount

    }

    //Code you problem number two here
        static int problemTwo (String s){
            int bobCount = 0;
            //Creates an integer bobCount with a starting value of 0
            for (int i = 0; i <= s.length() - 3; i++) {

                String substring = s.substring(i, i + 3);
                //Extracts a substring of 3 letters starting at position i

                if (substring.equals("bob")) {
                    bobCount++;
                    //If the substring is equal to "bob" then it adds 1 value to the bobCount integer
                }
            }

            System.out.println("Number of bob's: " + bobCount);
            return bobCount;
            //Prints out the new bob count and returns the integer
        }


        //Code your solution to problem number 3 here
        static String problemThree(String s) {
            String longest = "";
            String placeHolder = "";
            //Creates longest and placeHolder strings

            for(int i = 0; i < s.length(); i++) {
                placeHolder += s.charAt(i);
                //Adds the character located at the i position of the s string to the 'placeHolder' string

                if (i == s.length() - 1 || s.charAt(i + 1) < s.charAt(i)) {
                    //Checks if the last character or the next character is smaller than the current character, if the character is smaller, then the alphabetical order is broken
                    //Each character has an ASCII value, which is why this works

                    if (placeHolder.length() > longest.length()) {
                        longest = placeHolder;
                        //Checks if the 'placeHolder' length is bigger than the 'longest' length, if so then the 'placeHolder' string becomes the 'longest' string
                    }
                    placeHolder = "";
                    //Resets placeHolder substring - IMPORTANT
                }
            }
            System.out.println("Longest alphabetical string: " + longest);
            return longest;
        }

    public static void main(String[] args) {
        /*
        Set s to a string and run your method using s as the parameter
        Run your method in a println statement to determine what the output was
        Once you think you have it working try running the tests.
        These tests will put your methods through several, different Strings to test
        all possible cases.  If you have 100% success then there is no bugs in your methods.
         */
        problemOne("wiaytixcaigoiaernsi");
        problemTwo("aoboboboboobobbboboo");
        problemThree("dmegjmydadjwsdhckjjrydwj");

    }
}
