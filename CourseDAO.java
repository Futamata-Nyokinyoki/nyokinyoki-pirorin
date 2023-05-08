import java.util.*;
import java.sql.*;

public class CourseDAO {
    private final String url = "jdbc:sqlite:NyokinyokiPirorin.db";
    private final TimeSlotDAO timeSlotDAO;

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

    public CourseDAO() {
        timeSlotDAO = new TimeSlotDAO();

        String sql = "CREATE TABLE IF NOT EXISTS courses (" + "id INTEGER PRIMARY KEY," + "courseName TEXT NOT NULL"
                + ");";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create courses table", e);
        }
    }

    public Course getCourse(int id) {
        String sql = "SELECT * FROM courses WHERE id = ?;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String courseName = resultSet.getString("courseName");

                    return new Course(id, courseName);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get course", e);
        }
    }

    public List<Course> getCoursesByTimeSlot(int dayOfWeek, int beginPeriod) {
        List<TimeSlot> timeSlots = timeSlotDAO.getTimeSlotsByDayAndBeginPeriod(dayOfWeek, beginPeriod);
        List<Course> courses = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            Course course = getCourse(timeSlot.getCourseId());
            if (course != null) {
                courses.add(course);
            }
        }

        return courses;
    }

}