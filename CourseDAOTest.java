import org.junit.*;
import static org.junit.Assert.*;

import java.util.List;

public class CourseDAOTest {
    private static CourseDAO courseDAO;

    @BeforeClass
    public static void setUpClass() {
        courseDAO = new CourseDAO();
    }

    @Before
    public void setUp() {
        // Clean up the database before each test
        for (Course course : courseDAO.getCourses(1, 1)) {
            courseDAO.deleteCourse(course.getCourseId());
        }
    }

    @Test
    public void testAddAndGetCourse() {
        Course course = new Course(1, "Mathematics", 1, 1);
        assertTrue(courseDAO.addCourse(course));

        Course retrievedCourse = courseDAO.getCourse(1);
        assertNotNull(retrievedCourse);
        assertEquals(course.getCourseId(), retrievedCourse.getCourseId());
        assertEquals(course.getCourseName(), retrievedCourse.getCourseName());
        assertEquals(course.getDayOfWeek(), retrievedCourse.getDayOfWeek());
        assertEquals(course.getClassPeriod(), retrievedCourse.getClassPeriod());
    }

    @Test
    public void testGetCourses() {
        Course course1 = new Course(2, "Mathematics", 1, 1);
        Course course2 = new Course(3, "English", 1, 1);

        assertTrue(courseDAO.addCourse(course1));
        assertTrue(courseDAO.addCourse(course2));

        List<Course> courses = courseDAO.getCourses(1, 1);
        assertNotNull(courses);
        assertEquals(2, courses.size());
    }

    @Test
    public void testUpdateCourse() {
        Course course = new Course(4, "Mathematics", 1, 1);
        assertTrue(courseDAO.addCourse(course));

        Course updatedCourse = new Course(1, "Advanced Mathematics", 2, 2);
        assertTrue(courseDAO.updateCourse(updatedCourse));

        Course retrievedCourse = courseDAO.getCourse(1);
        assertNotNull(retrievedCourse);
        assertEquals(updatedCourse.getCourseId(), retrievedCourse.getCourseId());
        assertEquals(updatedCourse.getCourseName(), retrievedCourse.getCourseName());
        assertEquals(updatedCourse.getDayOfWeek(), retrievedCourse.getDayOfWeek());
        assertEquals(updatedCourse.getClassPeriod(), retrievedCourse.getClassPeriod());
    }

    @Test
    public void testDeleteCourse() {
        Course course = new Course(5, "Mathematics", 1, 1);
        assertTrue(courseDAO.addCourse(course));

        assertTrue(courseDAO.deleteCourse(1));
        assertNull(courseDAO.getCourse(1));
    }

    @AfterClass
    public static void tearDownClass() {
        // Clean up the database after all tests
        for (Course course : courseDAO.getCourses(1, 1)) {
            courseDAO.deleteCourse(course.getCourseId());
        }
    }
}
