package com.nyokinyoki.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.nyokinyoki.Timetable.Course;
import com.nyokinyoki.Timetable.Timeslot;

public final class CourseDAO extends AbstractDAO<Course> {

    private static CourseDAO instance = null;

    private CourseDAO() {
        String sql = "CREATE TABLE IF NOT EXISTS courses (" + "id INTEGER PRIMARY KEY," + "courseName TEXT NOT NULL"
                + ");";
        executeUpdate(sql);
    }

    public static synchronized CourseDAO getInstance() {
        if (instance == null) {
            instance = new CourseDAO();
        }
        return instance;
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM courses ORDER BY id ASC;";

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

    @Override
    public void removeAll() {
        String sql = "DELETE FROM courses;";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove all courses", e);
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

    public List<Course> getByPeriod(int dayOfWeek, int beginPeriod) {
        List<Timeslot> timeslots = TimeslotDAO.getInstance().getByPeriod(dayOfWeek, beginPeriod);

        return timeslots.stream().map(timeslot -> getById(timeslot.getCourseId())).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}