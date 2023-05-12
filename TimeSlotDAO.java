import java.sql.*;
import java.util.*;

public class TimeSlotDAO extends AbstractDAO<TimeSlot> {

    public TimeSlotDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS time_slots (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "courseId INTEGER NOT NULL," + "dayOfWeek INTEGER NOT NULL," + "beginPeriod INTEGER NOT NULL,"
                + "endPeriod INTEGER NOT NULL," + "FOREIGN KEY(id) REFERENCES courses(courseId)" + ");";
        executeUpdate(sql);
    }

    @Override
    public List<TimeSlot> getAll() {
        String sql = "SELECT * FROM time_slots";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<TimeSlot> timeSlots = new ArrayList<>();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int courseId = resultSet.getInt("courseId");
                    int dayOfWeek = resultSet.getInt("dayOfWeek");
                    int beginPeriod = resultSet.getInt("beginPeriod");
                    int endPeriod = resultSet.getInt("endPeriod");

                    TimeSlot timeSlot = new TimeSlot(id, courseId, dayOfWeek, beginPeriod, endPeriod);
                    timeSlots.add(timeSlot);
                }

                return timeSlots;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all time slots", e);
        }
    }

    @Override
    public void add(TimeSlot timeSlot) {
        String sql = "INSERT INTO time_slots (courseId, dayOfWeek, beginPeriod, endPeriod) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, timeSlot.getCourseId());
            statement.setInt(2, timeSlot.getDayOfWeek());
            statement.setInt(3, timeSlot.getBeginPeriod());
            statement.setInt(4, timeSlot.getEndPeriod());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add time slot", e);
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM time_slots WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove time slot", e);
        }
    }

    public List<TimeSlot> getByCourseId(int courseId) {
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

    public List<TimeSlot> getByPeriod(int dayOfWeek, int beginPeriod) {
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
