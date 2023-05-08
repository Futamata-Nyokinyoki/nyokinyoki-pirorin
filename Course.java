import java.util.*;

public class Course {
    private final int id;
    private final String courseName;

    public Course(int id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean isConflict(Course other) {
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
        List<TimeSlot> timeSlots = timeSlotDAO.getTimeSlotsByCourseId(id);
        List<TimeSlot> otherTimeSlots = timeSlotDAO.getTimeSlotsByCourseId(other.id);

        for (TimeSlot timeSlot : timeSlots) {
            for (TimeSlot otherTimeSlot : otherTimeSlots) {
                if (timeSlot.isConflict(otherTimeSlot)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", courseName='" + courseName + '\'' + '}';
    }
}
