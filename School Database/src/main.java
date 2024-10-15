public class main {
    public static void main(String[] args) {

        School school = new School("School of Education", "123 Education Street", "Sean Evans");
        //Create a new school object, giving it a name, address, and principal

        System.out.println(school.getSchoolInfo());
        //Shows School info

        Teacher teacher1 = new Teacher("Albert", "Novak\t", "History");
        Teacher teacher2 = new Teacher("Wilmer", "Schrader\t", "Science");
        Teacher teacher3 = new Teacher("Rapunzel", "Fiora\t", "Math");
        //Defines different teachers (\t added for cleaner output)

        school.addTeacher(teacher1);
        school.addTeacher(teacher2);
        school.addTeacher(teacher3);
        //Adds teachers to the school database

        Student student1 = new Student("Grant", "Henning\t", "6\t");
        Student student2 = new Student("Jack", "Holzer\t", "3\t");
        Student student3 = new Student("Leonore", "Baylor\t", "5\t");
        Student student4 = new Student("John", "Doe\t", "6\t");
        Student student5 = new Student("Jane", "Doe\t", "6\t");
        Student student6 = new Student("Dory", "Messmer\t", "3\t");
        Student student7 = new Student("Arthur", "Walter\t", "7\t");
        Student student8 = new Student("Jojo", "Blanco\t", "7\t");
        Student student9 = new Student("Garfield", "Cart\t", "7\t");
        Student student10 = new Student("Mary", "Urban\t", "4\t");
        //Defines different students (\t added for cleaner output)

        school.addStudent(student1);
        school.addStudent(student2);
        school.addStudent(student3);
        school.addStudent(student4);
        school.addStudent(student5);
        school.addStudent(student6);
        school.addStudent(student7);
        school.addStudent(student8);
        school.addStudent(student9);
        school.addStudent(student10);
        //Adds students to the school database

        System.out.println("\nTeachers: ");
        school.listAllTeachers();
        //Shows the list of all teachers

        System.out.println("\nStudents: ");
        school.listAllStudents();
        //Shows the list of all students

        school.deleteStudent(student1);
        school.deleteStudent(student2);
        school.deleteTeacher(teacher1);
        //Removes student 1 and 2, as well as teacher 1

        System.out.println("\nUpdated Teachers:");
        school.listAllTeachers();
        //Shows updated teacher list

        System.out.println("\nUpdated Students:");
        school.listAllStudents();
        //Shows updated student list
    }
}
