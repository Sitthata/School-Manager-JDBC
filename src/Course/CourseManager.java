package Course;

import Connection.SchoolConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {


    private final SchoolConnection schoolConnection;

    public CourseManager(SchoolConnection connection) {
        this.schoolConnection = connection;
    }

    public void addCourse(String courseID, String courseName, String courseDescription) {
        String sql = "INSERT INTO school.courses VALUES(?,?,?)";

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, courseID);
            preparedStatement.setString(2, courseName);
            preparedStatement.setString(3, courseDescription);
            preparedStatement.executeUpdate();
            schoolConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM school.courses";
        List<Course> courseList = new ArrayList<>();

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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

    public Course getCourse(String courseID) {
        String sql = "SELECT * FROM school.courses WHERE course_id = ?";

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) return null;
            return new Course(
                    resultSet.getString("course_id"),
                    resultSet.getString("course_name"),
                    resultSet.getString("course_description")
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCourse(String courseID) {
        String sql = "DELETE FROM school.courses WHERE course_id = ?";

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                System.out.println("Course not found");
                return;
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> viewAvaliableCourse() {
        String sql = "SELECT * FROM school.courses";
        List<Course> courseList = new ArrayList<>();

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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


}
