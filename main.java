// package project;

import java.util.InputMismatchException;
import java.util.Scanner;

abstract class Student {
    private String name;
    private int id;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public abstract void display();

    // Overloaded constructor
    public Student() {
        this("", 0);
    }
}

class RegularStudent extends Student {
    private int semester;

    public RegularStudent(String name, int id, int semester) {
        super(name, id);
        this.semester = semester;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public void display() {
        System.out.println("Regular Student:");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Semester: " + semester);
    }
}

class HonorsStudent extends Student {
    private String honorsProgram;

    public HonorsStudent(String name, int id, String honorsProgram) {
        super(name, id);
        this.honorsProgram = honorsProgram;
    }

    public String getHonorsProgram() {
        return honorsProgram;
    }

    public void setHonorsProgram(String honorsProgram) {
        this.honorsProgram = honorsProgram;
    }

    @Override
    public void display() {
        System.out.println("Honors Student:");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Honors Program: " + honorsProgram);
    }
}

class StudentRegistrationSystem {
    private static final int MAX_STUDENTS = 100;
    private Student[] students;
    private int numStudents;

    public StudentRegistrationSystem() {
        students = new Student[MAX_STUDENTS];
        numStudents = 0;
    }

    public void edit() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Edit Menu ---");
            System.out.println("1. Update Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                updateStudent();
            } else if (choice == 2) {
                deleteStudent();
            } else if (choice == 3) {
                System.out.println("Returning to the Main Menu...");
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < numStudents; i++) {
            if (students[i].getId() == id) {
                System.out.println("Student found. Enter updated information:");
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                students[i].setName(name);

                if (students[i] instanceof RegularStudent) {
                    RegularStudent regularStudent = (RegularStudent) students[i];
                    System.out.print("Enter semester: ");
                    int semester = scanner.nextInt();
                    scanner.nextLine();
                    regularStudent.setSemester(semester);
                } else if (students[i] instanceof HonorsStudent) {
                    HonorsStudent honorsStudent = (HonorsStudent) students[i];
                    System.out.print("Enter honors program: ");
                    String honorsProgram = scanner.nextLine();
                    honorsStudent.setHonorsProgram(honorsProgram);
                }

                System.out.println("Student information updated.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }

    private void deleteStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < numStudents; i++) {
            if (students[i].getId() == id) {
                System.out.println("Student found. Deleting student...");
                for (int j = i; j < numStudents - 1; j++) {
                    students[j] = students[j + 1];
                }
                numStudents--;
                System.out.println("Student deleted.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }

    public void displayAllStudents() {
        if (numStudents == 0) {
            System.out.println("No students to display.");
            return;
        }

        System.out.println("\n--- All Students ---");
        for (int i = 0; i < numStudents; i++) {
            students[i].display();
            System.out.println();
        }
    }

    public void addStudent(Student student) {
        if (numStudents == MAX_STUDENTS) {
            System.out.println("Maximum number of students reached. Cannot add more students.");
            return;
        }

        students[numStudents] = student;
        numStudents++;
        System.out.println("Student added successfully.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentRegistrationSystem system = new StudentRegistrationSystem();

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Display All Students");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            if (choice == 1) {
                addStudent(system, scanner);
            } else if (choice == 2) {
                system.edit();
            } else if (choice == 3) {
                system.displayAllStudents();
            } else if (choice == 4) {
                System.out.println("Exiting the program...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent(StudentRegistrationSystem system, Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("--- Student Type ---");
        System.out.println("1. Regular Student");
        System.out.println("2. Honors Student");
        System.out.print("Enter student type: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        if (type == 1) {
            System.out.print("Enter semester: ");
            int semester = scanner.nextInt();
            scanner.nextLine();
            RegularStudent student = new RegularStudent(name, id, semester);
            system.addStudent(student);
        } else if (type == 2) {
            System.out.print("Enter honors program: ");
            String honorsProgram = scanner.nextLine();
            HonorsStudent student = new HonorsStudent(name, id, honorsProgram);
            system.addStudent(student);
        } else {
            System.out.println("Invalid student type. Student not added.");
        }
    }
}