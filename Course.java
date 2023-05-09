import java.util.*;

public class Course {
    private final int id;
    private final String courseName;
    private final TimeSlotDAO timeSlotDAO;

    public Course(int id, String courseName) {
        this.id = id;
        this.courseName = courseName;
        this.timeSlotDAO = new TimeSlotDAO();
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlotDAO.getByCourseId(id);
    }

    public boolean isConflict(Course other) {
        List<TimeSlot> timeSlots = this.getTimeSlots();
        List<TimeSlot> otherTimeSlots = other.getTimeSlots();

        return timeSlots.stream().anyMatch(ts1 -> otherTimeSlots.stream().anyMatch(ts2 -> ts1.isConflict(ts2)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course) {
            Course other = (Course) obj;
            return id == other.id;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", courseName='" + courseName + '\'' + '}';
    }
}
