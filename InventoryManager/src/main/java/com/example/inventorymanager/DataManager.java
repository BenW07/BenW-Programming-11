package com.example.inventorymanager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Class meant for managing data persistence
public class DataManager {

    //Requires: filePath string
    //Modifies: nothing
    //Effects: Returns true if the filePath string works (contains no invalid character/null/empty), or false if otherwise
    public static boolean isValidFilePath(String filePath) {
        //Checks if the filePath is invalid
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        //Checks for these invalid characters in the file paths
        String[] invalidChars = {"<", ">", ":", "\"", "|", "?", "*"};
        for (String invalidChar : invalidChars) {
            if (filePath.contains(invalidChar)) {
                return false;
            }
        }

        return true;
    }

    //Requires: A valid original/backupFile
    //Modifies: Creates a backupFile
    //Effects: Copies the info from originalFile to backupFile, then returns true if successful
    public static boolean createBackup(String originalFile, String backupFile) {
        //Checks if the file paths are valid
        if (!isValidFilePath(originalFile) || !isValidFilePath(backupFile)) {
            return false;
        }

        //Creates a file object for the original file
        File original = new File(originalFile);
        if (!original.exists()) {
            return false;
        }

        //Try-with-resources to close streams automatically
        try (FileInputStream fis = new FileInputStream(original);
             FileOutputStream fos = new FileOutputStream(backupFile)) {

            //Reads/writes data in chunks of 1024 bytes
            byte[] buffer = new byte[1024];
            int length;
            //Reads from original file and writes to backup until the file ends
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            return true;

        } catch (IOException e) {
            //Prints an error message if an exception is caught
            System.err.println("Error creating backup: " + e.getMessage());
            return false;
        }
    }

    //Requires: Valid filePath
    //Modifies: Nothing
    //Effects: Returns true if file exists and can be read, returns false otherwise
    public static boolean isFileReadable(String filePath) {
        if (!isValidFilePath(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return file.exists() && file.canRead();
    }

    //Requires: Valid filePath
    //Modifies: Nothing
    //Effects: Returns true if file exists and can be written to, false otherwise
    public static boolean isFileWritable(String filePath) {
        if (!isValidFilePath(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return file.exists() && file.canWrite();
    }

    //Requires: Valid, readable filePath
    //Modifies: Nothing
    //Effects: Returns list of all lines from file, empty list if error occurs
    public static List<String> readAllLines(String filePath) {
        //Creates an empty list to store lines
        List<String> lines = new ArrayList<>();

        //Checks if the file is readable
        if (!isFileReadable(filePath)) {
            return lines;
        }

        //Try-with-resources to close the reader automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            //Read each line until the end of the file (null)
            while ((line = reader.readLine()) != null) {
                //Adds each line to the list
                lines.add(line);
            }
        } catch (IOException e) {
            //Prints an error message if an exception is caught
            System.err.println("Error reading file: " + e.getMessage());
        }

        return lines;
    }

    //Requires: Valid filePath with non-null line lists
    //Modifies: Creates/Overwrites file at filePath
    //Effects: Writes all lines to file then creates parent directories if needed
    public static boolean writeAllLines(String filePath, List<String> lines) {
        //Validates inputs
        if (!isValidFilePath(filePath) || lines == null) {
            return false;
        }

        //Creates parent directories if they don't exist
        new File(filePath).getParentFile().mkdirs();

        //Try-with-resources to automatically close the writer
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.println(line);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            return false;
        }
    }

    //Requires: Valid filePath
    //Modifies: nothing
    //Effects: Returns size of the file in bytes. Returns -1 if file doesn't exist or is invalid
    public static long getFileSize(String filePath) {
        if (!isValidFilePath(filePath)) {
            return -1;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return -1;
        }

        return file.length();
    }

    //Requires: Valid filePath
    //Modifies: Deletes file at the filePath if it exists
    //Effects: Removes file from file system returns true if success, false otherwise
    public static boolean deleteFile(String filePath) {
        if (!isValidFilePath(filePath)) {
            return false;
        }

        //If file doesn't exist then deletion is considered successful
        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }

        return file.delete();
    }

    //Requires: csvLine string and expectedFields count
    //Modifies: Nothing
    //Effects: Returns true if csvLine has the correct # of comma-separated values
    public static boolean isValidCsvLine(String csvLine, int expectedFields) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return false;
        }

        //Splits the lines by commas
        String[] fields = csvLine.split(",");
        //Checks if the number of fields expected matches the count
        return fields.length == expectedFields;
    }

    //Requires: Input string
    //Modifies: Nothing
    //Effects: Returns a CSV version of the input wrapped in quotes if needed
    public static String escapeCsvField(String input) {
        if (input == null) {
            return "";
        }

        //If the field contains a comma, quote, or other special characters, wrap it in quotes
        if (input.contains(",") || input.contains("\n") || input.contains("\"")) {
            //Escape quotes by doubling them
            return "\"" + input.replace("\"", "\"\"") + "\"";
        }

        return input;
    }
}