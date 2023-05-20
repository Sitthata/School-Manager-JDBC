package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SchoolConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/school";
    private static final String DB_NAME = "root";
    private static final String DB_PASSWORD = "deeprock25";
    private Connection connection;

    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_NAME, DB_PASSWORD);
        return connection;
    }

    public void disconnect() throws SQLException {
        if(connection != null) connection.close();
    }


}
