package src.test.java.com.nyokinyoki.nyokinyoki_pirorin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.Course;
import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.TimeSlot;
import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.TimeSlotDAO;

import java.util.List;

import static org.junit.Assert.*;

public class CourseTest {
    private TimeSlotDAO timeSlotDAO;
    private Course course1;
    private Course course2;
    private Course course3;

    @Before
    public void setUp() {
        timeSlotDAO = new TimeSlotDAO();

        // TimeSlot(id, courseId, dayOfWeek, beginPeriod, endPeriod)
        timeSlotDAO.add(new TimeSlot(0, 1, 1, 2, 4));
        timeSlotDAO.add(new TimeSlot(0, 1, 2, 3, 5));
        timeSlotDAO.add(new TimeSlot(0, 2, 1, 4, 6));
        timeSlotDAO.add(new TimeSlot(0, 2, 2, 5, 7));
        timeSlotDAO.add(new TimeSlot(0, 3, 1, 2, 3));

        course1 = new Course(1, "Course 1", timeSlotDAO);
        course2 = new Course(2, "Course 2", timeSlotDAO);
        course3 = new Course(3, "Course 3", timeSlotDAO);
    }

    @After
    public void tearDown() {
        // Clean up the database
        timeSlotDAO.removeAll();
    }

    @Test
    public void testGetId() {
        assertEquals(1, course1.getId());
        assertEquals(2, course2.getId());
        assertEquals(3, course3.getId());
    }

    @Test
    public void testGetCourseName() {
        assertEquals("Course 1", course1.getCourseName());
        assertEquals("Course 2", course2.getCourseName());
        assertEquals("Course 3", course3.getCourseName());
    }

    @Test
    public void testGetTimeSlots() {
        List<TimeSlot> timeSlots1 = course1.getTimeSlots();
        List<TimeSlot> timeSlots2 = course2.getTimeSlots();
        List<TimeSlot> timeSlots3 = course3.getTimeSlots();

        assertEquals(2, timeSlots1.size());
        assertEquals(2, timeSlots2.size());
        assertEquals(1, timeSlots3.size());
    }

    @Test
    public void testOverlapsWith() {
        assertTrue(course1.overlapsWith(course3));
        assertTrue(course1.overlapsWith(course2));
        assertFalse(course2.overlapsWith(course3));
    }

    @Test
    public void testEquals() {
        Course anotherCourse1 = new Course(1, "Another Course 1", timeSlotDAO);
        assertTrue(course1.equals(anotherCourse1));
        assertFalse(course1.equals(course2));
    }
}
