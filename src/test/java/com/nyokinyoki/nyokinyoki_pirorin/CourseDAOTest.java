package src.test.java.com.nyokinyoki.nyokinyoki_pirorin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.Course;
import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.CourseDAO;
import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.TimeSlot;
import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.TimeSlotDAO;

import java.util.List;

import static org.junit.Assert.*;

public class CourseDAOTest {
    private CourseDAO courseDAO;
    private TimeSlotDAO timeSlotDAO;

    @Before
    public void setUp() {
        timeSlotDAO = new TimeSlotDAO();
        courseDAO = new CourseDAO();
    }

    @After
    public void tearDown() {
        courseDAO.removeAll();
        timeSlotDAO.removeAll();
    }

    @Test
    public void testAddAndGetAll() {
        Course course = new Course(0, "Test Course", timeSlotDAO);
        courseDAO.add(course);

        List<Course> courses = courseDAO.getAll();
        assertEquals(1, courses.size());
        assertEquals(course, courses.get(0));
    }

    @Test
    public void testRemove() {
        Course course1 = new Course(1, "Test Course 1", timeSlotDAO);
        Course course2 = new Course(2, "Test Course 2", timeSlotDAO);
        courseDAO.add(course1);
        courseDAO.add(course2);

        courseDAO.remove(course1.getId());
        List<Course> courses = courseDAO.getAll();
        assertEquals(1, courses.size());
        assertEquals(course2, courses.get(0));
    }

    @Test
    public void testGetById() {
        Course course1 = new Course(1, "Test Course 1", timeSlotDAO);
        courseDAO.add(course1);

        Course retrievedCourse = courseDAO.getById(course1.getId());
        assertEquals(course1, retrievedCourse);
    }

    @Test
    public void testGetByTimeSlot() {
        TimeSlot timeSlot1 = new TimeSlot(1, 1, 1, 2, 4);
        TimeSlot timeSlot2 = new TimeSlot(2, 2, 1, 2, 4);
        timeSlotDAO.add(timeSlot1);
        timeSlotDAO.add(timeSlot2);

        Course course1 = new Course(1, "Test Course 1", timeSlotDAO);
        Course course2 = new Course(2, "Test Course 2", timeSlotDAO);
        courseDAO.add(course1);
        courseDAO.add(course2);

        List<Course> courses = courseDAO.getByTimeSlot(1, 2);
        assertEquals(2, courses.size());
        assertTrue(courses.contains(course1));
        assertTrue(courses.contains(course2));
    }

    @Test
    public void testRemoveAll() {
        Course course1 = new Course(1, "Test Course 1", timeSlotDAO);
        Course course2 = new Course(2, "Test Course 2", timeSlotDAO);
        courseDAO.add(course1);
        courseDAO.add(course2);

        courseDAO.removeAll();
        List<Course> courses = courseDAO.getAll();
        assertTrue(courses.isEmpty());
    }
}
