import java.sql.*;
import java.util.*;

public class TimeTableDAO {
    private final String url = "jdbc:sqlite:NyokinyokiPirorin.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load SQLite JDBC driver", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public TimeTableDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS time_tables (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "courseId INTEGER NOT NULL," + "FOREIGN KEY(courseId) REFERENCES courses(id)" + ");";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create time_tables table", e);
        }
    }

    public List<Course> getCourses() {
        String sql = "SELECT * FROM time_tables;";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Course> courses = new ArrayList<>();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                Course course = new CourseDAO().getCourse(courseId);
                courses.add(course);
            }

            return courses;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get courses from timetable", e);
        }
    }

    public void addCourse(int courseId) {
        String sql = "INSERT INTO time_tables (courseId) VALUES (?);";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.executeUpdate();
            return;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add course to timetable", e);
        }
    }

    public void removeCourse(int courseId) {
        String sql = "DELETE FROM time_tables WHERE courseId = ?;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.executeUpdate();
            return;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove course from timetable", e);
        }
    }
}
