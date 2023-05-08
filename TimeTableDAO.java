import java.sql.*;
import java.util.*;

public class TimeTableDAO {
    private final String url = "jdbc:sqlite:NyokinyokiPirorin.db";
    private final CourseDAO courseDAO;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load SQLite JDBC driver", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public TimeTableDAO() {
        courseDAO = new CourseDAO();

        String sql = "CREATE TABLE IF NOT EXISTS time_tables (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "courseId INTEGER NOT NULL," + "FOREIGN KEY(courseId) REFERENCES courses(id)" + ");";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create time_tables table", e);
        }
    }

    // TODO: N + 1 問題が起きるらしいので，時間があれば改善する
    public TimeTable getTimeTable() {
        String sql = "SELECT * FROM time_tables;";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            List<Course> cources = new ArrayList<>();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                Course course = courseDAO.getCourse(courseId);
                if (course != null) {
                    cources.add(course);
                }
            }

            return new TimeTable(cources);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get timetable", e);
        }
    }

    public void addCourse(int courseId) {
        Course course = courseDAO.getCourse(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course does not exist");
        }
        if (getTimeTable().isConflict(course)) {
            throw new IllegalArgumentException("Course time slot conflicts with registered course");
        }

        String sql = "INSERT INTO time_tables (courseId) VALUES (?);";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.executeUpdate();
            return;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add course to timetable", e);
        }
    }
}
