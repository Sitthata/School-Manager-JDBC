package Student;

import Connection.SchoolConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private final SchoolConnection schoolConnection;

    public StudentManager(SchoolConnection schoolConnection) {
        this.schoolConnection = schoolConnection;
    }

    public List<Student> getAllStudent() {
        String sql = "SELECT * FROM school.students";
        List<Student> studentsList = new ArrayList<>();

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                studentsList.add(new Student(id, firstName, lastName, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsList;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM school.students WHERE student_id = ?";

        try(Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                return new Student(id, firstName, lastName, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addStudent(String firstName, String lastName, String email) {
        String sql = "INSERT INTO school.students(first_name, last_name, email) VALUES(?,?,?)";

        try (Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            schoolConnection.disconnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteStudent(int id) {
        String sql = "DELETE FROM school.students WHERE student_id = ?";

        try(Connection connection = schoolConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            schoolConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
