import Connection.SchoolConnection;
import Course.CourseManager;
import Registeration.RegistrationManager;
import Student.StudentManager;
import UserInterface.UserInterface;

public class Main {
    public static void main(String[] args) {
        school();
    }

    public static void school() {
        // Create a test for me
        SchoolConnection schoolConnection = new SchoolConnection();
        StudentManager studentManager = new StudentManager(schoolConnection);
        CourseManager courseManager = new CourseManager(schoolConnection);
        RegistrationManager registrationManager = new RegistrationManager(schoolConnection);

        UserInterface menu = new UserInterface(studentManager, courseManager, registrationManager);
        menu.run();

    }

}