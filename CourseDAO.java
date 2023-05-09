import java.util.*;
import java.sql.*;
import java.util.stream.Collectors;

public class CourseDAO extends AbstractDAO<Course> {
    private final TimeSlotDAO timeSlotDAO;

    public CourseDAO() {
        timeSlotDAO = new TimeSlotDAO();
        String sql = "CREATE TABLE IF NOT EXISTS courses (" + "id INTEGER PRIMARY KEY," + "courseName TEXT NOT NULL"
                + ");";
        executeUpdate(sql);
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM courses;";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String courseName = resultSet.getString("courseName");

                courses.add(new Course(id, courseName));
            }

            return courses;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get courses", e);
        }
    }

    @Override
    public void add(Course course) {
        String sql = "INSERT INTO courses (id, courseName) VALUES (?, ?);";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, course.getId());
            statement.setString(2, course.getCourseName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add course", e);
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM courses WHERE id = ?;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove course", e);
        }
    }

    public Course getById(int id) {
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

    public List<Course> getByTimeSlot(int dayOfWeek, int beginPeriod) {
        List<TimeSlot> timeSlots = timeSlotDAO.getByPeriod(dayOfWeek, beginPeriod);

        return timeSlots.stream().map(timeSlot -> getById(timeSlot.getCourseId())).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}