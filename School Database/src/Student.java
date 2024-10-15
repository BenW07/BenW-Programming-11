public class Student {
    /*  This class is meant to represent a student with fields such as their first name, last name, grade, and unique student number
        This class also gives methods to get and set student information
     */
        private String firstName;
        private String lastName;
        private String grade;
        //Creates strings for student's first and last name as well as grade
        private static int studentNumberCounter = 1;
        //Static counter to generate unique student number for each student
        private int studentNumber;
        //Unique student number integer

    public Student(String firstName, String lastName, String grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
        this.studentNumber = studentNumberCounter++;
        //Assigns the first and last name, the grade, and the unique student number to the student
    }

    public String getStudentInfo() {
        return "Name: " + firstName + " " + lastName + " Grade: " + grade + " Student Number: " + studentNumber;
        //Returns the student's full name, grade, and student number in a formatted fashion
    }

    public String getFirstName() {
        return  firstName;
        //Returns student's first name
    }

    public void setFirstName() {
        this.firstName = firstName;
        //Sets student's first name
    }

    public String getLastName() {
        return lastName;
        //Returns student's last name
    }

    public void setLastName() {
        this.lastName = lastName;
        //Sets student's last name
    }

    public String getGrade() {
        return grade;
        //Returns student grade
    }

    public void setGrade() {
        this.grade = grade;
        //Sets student grade
    }

    public int getStudentNumber() {
        return studentNumber;
        //Returns the unique student number
    }
}

