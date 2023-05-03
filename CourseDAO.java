import java.util.*;
import java.sql.*;

public class CourseDAO {
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

    public CourseDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS courses (" + "courseId INTEGER PRIMARY KEY,"
                + "courseName TEXT NOT NULL," + "dayOfWeek INTEGER NOT NULL," + "classPeriod INTEGER NOT NULL" + ");";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create courses table", e);
        }
    }

    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (courseId, courseName, dayOfWeek, classPeriod) VALUES (?, ?, ?, ?);";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, course.getCourseId());
            statement.setString(2, course.getCourseName());
            statement.setInt(3, course.getDayOfWeek());
            statement.setInt(4, course.getClassPeriod());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add course", e);
        }
    }

    public Course getCourse(int courseId) {
        String sql = "SELECT * FROM courses WHERE courseId = ?;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String courseName = resultSet.getString("courseName");
                    int dayOfWeek = resultSet.getInt("dayOfWeek");
                    int classPeriod = resultSet.getInt("classPeriod");

                    return new Course(courseId, courseName, dayOfWeek, classPeriod);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get course", e);
        }
    }

    public List<Course> getCourses(int dayOfWeek, int classPeriod) {
        String sql = "SELECT * FROM courses WHERE dayOfWeek = ? AND classPeriod = ?;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, dayOfWeek);
            statement.setInt(2, classPeriod);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Course> courses = new ArrayList<>();
                while (resultSet.next()) {
                    int resultCourseId = resultSet.getInt("courseId");
                    String courseName = resultSet.getString("courseName");

                    Course course = new Course(resultCourseId, courseName, dayOfWeek, classPeriod);
                    courses.add(course);
                }
                return courses;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get courses", e);
        }
    }

    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET courseName = ?, dayOfWeek = ?, classPeriod = ? WHERE courseId = ?;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, course.getCourseName());
            statement.setInt(2, course.getDayOfWeek());
            statement.setInt(3, course.getClassPeriod());
            statement.setInt(4, course.getCourseId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update course", e);
        }
    }

    public boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE courseId = ?;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, courseId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete course", e);
        }
    }

}
