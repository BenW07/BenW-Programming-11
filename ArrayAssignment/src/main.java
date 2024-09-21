import java.util.Scanner;

    public class main {


        public static int[] addValue(int[] array, int value) {
            //Creates a method to allow a value to be added to an array
            int[] newArray = new int[array.length + 1];
            //Creates a new array with one more value than the last array
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
                //Copies all the elements from the original array to the new array in the same positions, pretty much just editing the original array
            }

            newArray[array.length] = value;
            //Adds the new value to the last position in the new array (Because it's 1 size larger, the last position is array.length)
            return newArray;
            //Returns the new array with the added value

        }


        public static int[] popValue(int[] array) {
            //Creates a method to delete (pop) a value from the end of the array
            if (array.length == 0) {
                System.out.println("Array is empty. Cannot pop values.");
                //Checks if the array is empty (== 0) and if so then prints out a response for the user
                return array;
                //Returns the same array if it is empty/has no change

            }

            int[] newArray = new int[array.length - 1];
            // Copy all elements except the last one, creating a new array that is one length smaller
            for (int i = 0; i < array.length - 1; i++) {
                newArray[i] = array[i];
                //Creates a new array by while copying all previous elements except for the last one which was popped
            }
            return newArray;
            //Returns the new array that no longer has the last element
        }


        public static int[] insertValue(int[] array, int value, int index) {
           //Creates method that allows you to insert a value into a specific index in the array
            if (index < 0 || index > array.length) {
                System.out.println("Invalid index.");
                //If the user enters an index number that is invalid (Below 0 or above the length of the array) it will print a message
                return array;
                //Returns the array unchanged
            }

            int[] newArray = new int[array.length + 1];
            //Creates a new array that is one element larger
            for (int i = 0; i < index; i++) {
                newArray[i] = array[i];
                //Copies the elements of the original array until the inserted value index
            }

            newArray[index] = value;
            //Insert the new value at the specified index

            for (int i = index; i < array.length; i++) {
                newArray[i + 1] = array[i];
                //Copies the remaining elements from the original array (after the index) into the new array, the original elements are shifted one array forward to accommodate the value added in the middle
            }
            return newArray;
            //Returns the new array, which now has the inserted value
        }


        public static void main(String[] args) {
            //Main method to run the program
            Scanner scanner = new Scanner(System.in);
            //Creates a scanner that can read user input


            int[] array = new int[0];
            //Creates an empty array
            boolean running = true;
            //Creates a variable that allows us to control the upcoming loop


            while (running) {
                //Runs the program until the user quits
                System.out.println("Current array: ");
                displayArray(array);
                System.out.println();
                //Shows the current array to the user


                System.out.println("Choose an option:");
                System.out.println("1. Add a value to the end of the array");
                System.out.println("2. Delete (pop) a value from the end of the array");
                System.out.println("3. Insert a value into a specific index");
                System.out.println("4. Quit");
                System.out.println();
                //Shows the user the menu options they have

                int choice = scanner.nextInt();
                //Reads the users choice

                switch (choice) {
                    //Switches what to do based on the user inputted choice
                    case 1:
                        System.out.print("Enter the value to add: ");
                        int addValue = scanner.nextInt();
                        array = addValue(array, addValue);
                        break;
                        //Adds a value to the end of the array by reading the user input
                    case 2:
                        array = popValue(array);
                        break;
                        //Deletes (pops) a value from the ned of the array
                    case 3:
                        System.out.print("Enter the value to insert: ");
                        int insertValue = scanner.nextInt();
                        System.out.print("Enter the index to insert at: ");
                        int index = scanner.nextInt();
                        array = insertValue(array, insertValue, index);
                        break;
                        //Inserts a value at a specific index by getting the value from the user via scanner then getting the index where the value should go via scanner
                    case 4:
                        running = false;
                        break;
                        //Quits the program by setting the loop control variable to false
                    default:
                        System.out.println("Invalid option. Try again.");
                        //Handles invalid options by printing a message to the user
                }
            }

            System.out.println("Program ended.");
            scanner.close();
            //Prints a message telling the user that the program has ended and closes the scanner to prevent memory leaks
        }


        public static void displayArray(int[] array) {
            if (array.length == 0) {
                System.out.println("Array is empty.");
                //Checks if the array is empty and tells the user if it is
            } else {
                for (int i : array) {
                    System.out.print(i + " ");
                    //Prints each value/element in the array with spaces between them
                }
                System.out.println();
                //Moves to the next line after printing the array
            }
        }
    }


