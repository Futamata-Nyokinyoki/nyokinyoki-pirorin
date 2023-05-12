package src.main.java.com.nyokinyoki.nyokinyoki_pirorin;

import java.util.*;
import java.util.stream.Collectors;

public class TimeTable {
    private final List<Course> courses;
    private final TimeTableDAO timeTableDAO;
    private final CourseDAO courseDAO;

    public TimeTable(TimeTableDAO timeTableDAO, CourseDAO courseDAO) {
        this.timeTableDAO = timeTableDAO;
        this.courseDAO = courseDAO;
        this.courses = timeTableDAO.getAll();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        if (!this.isAvailable(course)) {
            throw new IllegalArgumentException("Course is not available");
        }

        timeTableDAO.add(course);
        courses.add(course);
    }

    public void addCourse(int id) {
        addCourse(courseDAO.getById(id));
    }

    public void removeCourse(Course course) {
        timeTableDAO.remove(course.getId());
        courses.removeIf(c -> c.equals(course));
    }

    public void removeCourse(int id) {
        removeCourse(courseDAO.getById(id));
    }

    public List<Course> getAvailableCourses() {
        return courseDAO.getAll().stream().filter(this::isAvailable).collect(Collectors.toList());
    }

    public List<Course> getAvailableCoursesByTimeSlot(int dayOfWeek, int beginPeriod) {
        return courseDAO.getByTimeSlot(dayOfWeek, beginPeriod).stream().filter(this::isAvailable)
                .collect(Collectors.toList());
    }

    public boolean isAvailable(Course course) {
        return courses.stream()
                .noneMatch(existingCourse -> existingCourse.equals(course) || existingCourse.overlapsWith(course));
    }

    @Override
    public String toString() {
        return courses.stream().map(Course::toString).collect(Collectors.joining("\n"));
    }
}
