public class Teacher {
    /* This class is meant to model the teacher string, by giving them a first name, last name, and subject
       This class also has getters, setters, and constructors that output formatted strings to represent a teacher
     */

    private String firstName;
    private String lastName;
    private String subject;
    //Strings of the teacher's first and last name, and the subject they teach

public Teacher(String firstName, String lastName, String subject) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.subject = subject;
    //Assigns the first and last name, as well as the subject
    }

public String toString() {
    return "Name: " + firstName + " " + lastName + " Subject: " + subject;
    //Returns the first and last name, and subject in a formatted output
    }

public String getFirstName() {
    return firstName;
    //Returns the teacher's first name
    }

public void setFirstName(String firstName) {
    this.firstName = firstName;
    //Set the teachers first name
    }

public String getLastName() {
    return lastName;
    //Returns teacher's last name
    }

public void setLastName(String lastName) {
    this.lastName = lastName;
    //Set the teachers last name
    }

public String getSubject() {
    return subject;
    }

public void setSubject(String subject) {
    this.subject = subject;
    //Set the subject the teacher teaches
    }
}
