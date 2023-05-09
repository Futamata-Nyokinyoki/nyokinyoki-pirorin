import java.util.*;

public class TimeTable {
    private final List<Course> courses;
    private final TimeTableDAO timeTableDAO;
    private final CourseDAO courseDAO;

    public TimeTable() {
        this.timeTableDAO = new TimeTableDAO();
        this.courseDAO = new CourseDAO();
        this.courses = timeTableDAO.getCourses();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course does not exist");
        }

        if (!this.isAvailable(course)) {
            throw new IllegalArgumentException("Course is not available");
        }

        timeTableDAO.addCourse(course.getId());
        courses.add(course);
    }

    public void addCourse(int id) {
        Course course = courseDAO.getCourse(id);
        this.addCourse(course);
    }

    public void removeCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course does not exist");
        }
        timeTableDAO.removeCourse(course.getId());
        courses.removeIf(c -> c.equals(course));
    }

    public void removeCourse(int id) {
        Course course = courseDAO.getCourse(id);
        this.removeCourse(course);
    }

    public List<Course> getAvailableCourses() {
        List<Course> courses = courseDAO.getCourses();
        List<Course> availableCourses = new ArrayList<>();

        for (Course course : courses) {
            if (this.isAvailable(course)) {
                availableCourses.add(course);
            }
        }

        return availableCourses;
    }

    public List<Course> getAvailableCoursesByTimeSlot(int dayOfWeek, int beginPeriod) {
        List<Course> courses = courseDAO.getCoursesByTimeSlot(dayOfWeek, beginPeriod);
        List<Course> availableCourses = new ArrayList<>();

        for (Course course : courses) {
            if (this.isAvailable(course)) {
                availableCourses.add(course);
            }
        }

        return availableCourses;
    }

    public boolean isAvailable(Course course) {
        for (Course existingCourse : courses) {
            if (existingCourse.equals(course)) {
                return false;
            }

            if (existingCourse.isConflict(course)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < courses.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(courses.get(i));
        }
        return sb.toString();
    }
}
