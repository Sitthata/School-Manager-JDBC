package Registeration;

import Connection.SchoolConnection;
import Course.Course;
import Student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationManager {
    private final SchoolConnection schoolConnection;

    public RegistrationManager(SchoolConnection schoolConnection) {
        this.schoolConnection = schoolConnection;
    }

    public void registerStudentForCourse(int studentId, String courseId) {
        String sql = "INSERT INTO school.registration (student_id, course_id) VALUES(?,?)";

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropStudentFromCourse(int studentId, String courseId) {
        String sql = "DELETE FROM school.registration WHERE student_id = ? AND course_id = ?";

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCourseForStudent(int studentId) {
        // Return all courses that a student is registered for
        String sql = "SELECT * FROM school.registration JOIN school.courses s on s.course_id = registration.course_id WHERE student_id = ?";
        List<Course> courseList = new ArrayList<>();

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseList.add(new Course(
                        resultSet.getString("course_id"),
                        resultSet.getString("course_name"),
                        resultSet.getString("course_description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public List<Student> getStudentForCourse(String courseId) {
        String sql = "SELECT * FROM school.registration JOIN school.students s on s.student_id = registration.student_id WHERE course_id = ?";
        List<Student> studentList = new ArrayList<>();

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentList.add(new Student(
                        resultSet.getInt("student_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }
}
