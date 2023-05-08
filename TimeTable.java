import java.util.*;

public class TimeTable {
    private final List<Course> courses;

    public TimeTable(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public boolean isConflict(Course course) {
        for (Course other : courses) {
            if (course.isConflict(other)) {
                return true;
            }
        }

        return false;
    }
}
