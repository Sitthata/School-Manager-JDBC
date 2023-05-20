package UserInterface;

import Course.*;
import Registeration.*;
import Student.*;
import Connection.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {

    private final StudentManager studentManager;
    private final CourseManager courseManager;
    private final RegistrationManager registrationManager;
    private final Scanner sc;

    public UserInterface(StudentManager studentManager, CourseManager courseManager, RegistrationManager registrationManager) {
        this.studentManager = studentManager;
        this.courseManager = courseManager;
        this.registrationManager = registrationManager;
        this.sc = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while(running) {
            System.out.println("Welcome to School Manager App");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Add Course");
            System.out.println("4. Delete Course");
            System.out.println("5. Register Student for a course");
            System.out.println("6. Drop Student from a course");
            System.out.println("7. View all courses a student is registered for");
            System.out.println("8. View all students in a course");
            System.out.println("9. View all available courses");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter an integer.");
                sc.next(); // consume the invalid input
                continue;
            }

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> deleteStudent();
                case 3 -> addCourse();
                case 4 -> deleteCourse();
                case 5 -> registerStudentForCourse();
                case 6 -> dropStudentFromCourse();
                case 7 -> viewAllCourseFromStudent();
                case 8 -> viewAllStudentFromCourses();
                case 9 -> viewAllCourses();
                case 10 -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
        sc.close();
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String studentName = sc.next();
        System.out.print("Enter student email: ");
        String studentEmail = sc.next();
        System.out.print("Enter student phone: ");
        String studentPhone = sc.next();
        boolean result = studentManager.addStudent(studentName, studentEmail, studentPhone);
        if (result) {
            System.out.println("Student added successfully");
        } else {
            System.out.println("Failed to add student");
        }
        System.out.println();
    }

    private void deleteStudent() {
        System.out.print("Enter student id: ");
        int studentId = sc.nextInt();
        studentManager.deleteStudent(studentId);
        System.out.println();
    }

    private void addCourse() {
        System.out.println("Course ID should be in this format 'CSEXXX' where X is a digit");
        System.out.print("Enter course id: ");
        String courseId = sc.nextLine();
        System.out.print("Enter course name: ");
        String courseName = sc.next();
        System.out.print("Enter course description: ");
        String courseDescription = sc.next();
        courseManager.addCourse(courseId, courseName, courseDescription);
        System.out.println();
    }

    private void deleteCourse() {
        System.out.print("Enter course id: ");
        String courseId = sc.next();
        courseManager.deleteCourse(courseId);
        System.out.println();
    }

    private void registerStudentForCourse() {
        System.out.print("Enter student id: ");
        int studentId = sc.nextInt();
        System.out.println("Enter course id: ");
        String courseId = sc.next();
        registrationManager.registerStudentForCourse(studentId, courseId);
        System.out.println();
    }

    private void dropStudentFromCourse() {
        System.out.print("Enter student id: ");
        int studentId = sc.nextInt();
        System.out.println("Enter course id: ");
        String courseId = sc.next();
        registrationManager.dropStudentFromCourse(studentId, courseId);
        System.out.println();
    }

    private void viewAllCourseFromStudent() {
        System.out.print("Enter student id: ");
        int studentId = sc.nextInt();
        registrationManager.getCourseForStudent(studentId).forEach(course -> {
            System.out.format("%s | %s | %s\n", course.getId(), course.getName(), course.getDescription());
        });
        System.out.println();
    }

    private void viewAllStudentFromCourses() {
        System.out.print("Enter course id: ");
        String courseId = sc.nextLine();
        registrationManager.getStudentForCourse(courseId).forEach(student ->
            System.out.format("%d | %s | %s | %s\n", student.getId(), student.getFirstName(), student.getLastName(), student.getEmail()
        ));
        System.out.println();
    }

    private void viewAllCourses() {
        courseManager.getAllCourses().forEach(course -> {
            System.out.format("%s | %s | %s\n", course.getId(), course.getName(), course.getDescription());
        });
        System.out.println();
    }

}
