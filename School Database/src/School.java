import java.util.ArrayList;

public class School {
    /*School class is meant to contain the list of students and teachers as well as info about the school
    This class also has methods to add teachers and students, show teachers and students, and delete teachers and students
     */

    private String name;
    private String address;
    private String principal;
    //Creates school strings for 'name' 'address' and 'principal'
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    //Creates ArrayLists to hold students and teachers at the school
    //Private means that the function can only be accessed from its own class

    public School(String name, String address, String principal) {
        //Initializes the school with its name, address, and principal
        this.name = name;
        this.address = address;
        this.principal = principal;
        this.teachers = new ArrayList<>();
        this.students = new ArrayList<>();
        //Creates empty array lists for teachers and students
    }

    public String getSchoolInfo() {
        return "School Name: " + name + "\n" +
                "Address: " + address + "\n" +
                "Principal: " + principal;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        //Method to add teacher
    }

    public void addStudent(Student student) {
        students.add(student);
        //Method to add student
    }

    public void deleteTeacher(Teacher teacher) {
        teachers.remove(teacher);
        //Remove a specific teacher from the teacher list
    }

    public void deleteStudent(Student student) {
        students.remove(student);
        //Remove a specific student from the student list
    }

    public void listAllStudents() {
        for (Student student : students) {
            //Creates a loop that loops through each student in the student list
            System.out.println(student.getStudentInfo());
            //Print out the names of each student in the student list
        }
    }

    public void listAllTeachers() {
        for (Teacher teacher : teachers) {
            //Creates a loop that loops through each teacher in the teacher list
            System.out.println(teacher);
            //Print out the names of each student in the teacher list
        }
    }

    public String getName() {
        return name;
        //Returns the name of the school
    }

    public void setName(String name) {
        this.name = name;
        //Sets the name of the school
    }

    public String getAddress() {
        return address;
        //Returns the address of the school
    }

    public void setAddress() {
        this.address = address;
        //Sets the name of address
    }

    public String getPrincipal() {
        return principal;
        //Returns the principal string
    }

    public void setPrincipal() {
        this.principal = principal;
        //Sets the name of principal
    }
}
