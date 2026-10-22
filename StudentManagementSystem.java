import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class Student {
    private int id;
    private String name;
    private int age;
    private String course;
    private double marks;

    public Student(int id, String name, int age, String course, double marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

    public double getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Name: " + name
                + " | Age: " + age
                + " | Course: " + course
                + " | Marks: " + marks;
    }
}

public class StudentManagementSystem {

    private static final ArrayList<Student> students = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {

        loadStudents();

        while (true) {
            System.out.println("\n================================");
            System.out.println("   STUDENT MANAGEMENT SYSTEM");
            System.out.println("================================");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    saveStudents();
                    System.out.println("Student records saved.");
                    System.out.println("Thank you for using the application!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Enter 1 to 6.");
            }
        }
    }

    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid integer: ");
            sc.nextLine();
        }

        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    private static double readDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("Enter a valid number: ");
            sc.nextLine();
        }

        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }

    private static void addStudent() {
        System.out.println("\n--- Add Student ---");

        System.out.print("Enter Student ID: ");
        int id = readInt();

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty() || name.contains("|")) {
            System.out.println("Invalid name!");
            return;
        }

        System.out.print("Enter Age: ");
        int age = readInt();

        if (age <= 0) {
            System.out.println("Age must be greater than zero.");
            return;
        }

        System.out.print("Enter Course: ");
        String course = sc.nextLine().trim();

        if (course.isEmpty() || course.contains("|")) {
            System.out.println("Invalid course!");
            return;
        }

        System.out.print("Enter Marks (0-100): ");
        double marks = readDouble();

        if (marks < 0 || marks > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return;
        }

        Student student = new Student(id, name, age, course, marks);

        students.add(student);
        saveStudents();

        System.out.println("Student added successfully!");
    }

    private static void viewStudents() {
        System.out.println("\n--- Student Records ---");

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }

        System.out.println("Total Students: " + students.size());
    }

    private static void searchStudent() {
        System.out.println("\n--- Search Student ---");

        System.out.print("Enter Student ID: ");
        int id = readInt();

        Student student = findStudent(id);

        if (student != null) {
            System.out.println("Student found!");
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");

        System.out.print("Enter Student ID: ");
        int id = readInt();

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter New Name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty() || name.contains("|")) {
            System.out.println("Invalid name!");
            return;
        }

        System.out.print("Enter New Age: ");
        int age = readInt();

        if (age <= 0) {
            System.out.println("Age must be greater than zero.");
            return;
        }

        System.out.print("Enter New Course: ");
        String course = sc.nextLine().trim();

        if (course.isEmpty() || course.contains("|")) {
            System.out.println("Invalid course!");
            return;
        }

        System.out.print("Enter New Marks (0-100): ");
        double marks = readDouble();

        if (marks < 0 || marks > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return;
        }

        student.setName(name);
        student.setAge(age);
        student.setCourse(course);
        student.setMarks(marks);

        saveStudents();

        System.out.println("Student updated successfully!");
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");

        System.out.print("Enter Student ID: ");
        int id = readInt();

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        students.remove(student);
        saveStudents();

        System.out.println("Student deleted successfully!");
    }

    private static Student findStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    private static void saveStudents() {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Student student : students) {
                writer.write(
                        student.getId() + "|"
                        + student.getName() + "|"
                        + student.getAge() + "|"
                        + student.getCourse() + "|"
                        + student.getMarks()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving records: " + e.getMessage());
        }
    }

    private static void loadStudents() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split("\\|", -1);

                    if (data.length != 5) {
                        continue;
                    }

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    int age = Integer.parseInt(data[2]);
                    String course = data[3];
                    double marks = Double.parseDouble(data[4]);

                    if (id <= 0 || name.isEmpty() || age <= 0
                            || course.isEmpty() || marks < 0 || marks > 100
                            || findStudent(id) != null) {
                        continue;
                    }

                    students.add(
                            new Student(id, name, age, course, marks)
                    );

                } catch (NumberFormatException e) {
                    System.out.println("Skipping an invalid student record.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading records: " + e.getMessage());
        }
    }
}

