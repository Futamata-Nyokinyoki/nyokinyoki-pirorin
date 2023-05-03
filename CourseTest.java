import org.junit.*;
import static org.junit.Assert.*;

public class CourseTest {

    @Test
    public void testCourseGetters() {
        int courseId = 1;
        String courseName = "Mathematics";
        int dayOfWeek = 1;
        int classPeriod = 3;

        Course course = new Course(courseId, courseName, dayOfWeek, classPeriod);

        assertEquals(courseId, course.getCourseId());
        assertEquals(courseName, course.getCourseName());
        assertEquals(dayOfWeek, course.getDayOfWeek());
        assertEquals(classPeriod, course.getClassPeriod());
    }

    @Test
    public void testCourseToString() {
        int courseId = 1;
        String courseName = "Mathematics";
        int dayOfWeek = 1;
        int classPeriod = 3;

        Course course = new Course(courseId, courseName, dayOfWeek, classPeriod);

        String expectedString = "Course{id=1, name=Mathematics, dayOfWeek=1, classPriod=3}";
        assertEquals(expectedString, course.toString());
    }
}