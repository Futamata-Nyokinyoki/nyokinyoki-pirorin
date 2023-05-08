import java.sql.*;
import java.util.*;

public class TimeSlotDAO {

    private final String url = "jdbc:sqlite:NyokinyokiPirorin.db";

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

    public TimeSlotDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS time_slots (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "courseId INTEGER NOT NULL," + "dayOfWeek INTEGER NOT NULL," + "beginPeriod INTEGER NOT NULL,"
                + "endPeriod INTEGER NOT NULL," + "FOREIGN KEY(courseId) REFERENCES courses(id)" + ");";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create time_slots table", e);
        }
    }

    public List<TimeSlot> getTimeSlotsByCourseId(int courseId) {
        String sql = "SELECT * FROM time_slots WHERE courseId = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<TimeSlot> timeSlots = new ArrayList<>();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int dayOfWeek = resultSet.getInt("dayOfWeek");
                    int beginPeriod = resultSet.getInt("beginPeriod");
                    int endPeriod = resultSet.getInt("endPeriod");

                    TimeSlot timeSlot = new TimeSlot(id, courseId, dayOfWeek, beginPeriod, endPeriod);
                    timeSlots.add(timeSlot);
                }

                return timeSlots;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get time slots by course id", e);
        }
    }

    public List<TimeSlot> getTimeSlotsByDayAndBeginPeriod(int dayOfWeek, int beginPeriod) {
        String sql = "SELECT * FROM time_slots WHERE dayOfWeek = ? AND beginPeriod = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dayOfWeek);
            statement.setInt(2, beginPeriod);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<TimeSlot> timeSlots = new ArrayList<>();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int courseId = resultSet.getInt("courseId");
                    int endPeriod = resultSet.getInt("endPeriod");

                    TimeSlot timeSlot = new TimeSlot(id, courseId, dayOfWeek, beginPeriod, endPeriod);
                    timeSlots.add(timeSlot);
                }

                return timeSlots;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get time slots by dayOfWeek and beginPeriod", e);
        }
    }

}
