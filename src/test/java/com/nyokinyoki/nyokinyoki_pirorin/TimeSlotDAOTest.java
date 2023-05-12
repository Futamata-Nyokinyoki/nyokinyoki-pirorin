package src.test.java.com.nyokinyoki.nyokinyoki_pirorin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.TimeSlot;
import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.TimeSlotDAO;

import java.util.List;
import static org.junit.Assert.*;

public class TimeSlotDAOTest {
    private TimeSlotDAO timeSlotDAO;

    @Before
    public void setUp() {
        timeSlotDAO = new TimeSlotDAO();
    }

    @After
    public void tearDown() {
        // Clean up the database
        timeSlotDAO.removeAll();
    }

    @Test
    public void testAddAndGetAll() {
        TimeSlot timeSlot = new TimeSlot(0, 1, 1, 2, 4);
        timeSlotDAO.add(timeSlot);

        List<TimeSlot> timeSlots = timeSlotDAO.getAll();
        assertEquals(1, timeSlots.size());

        TimeSlot retrievedTimeSlot = timeSlots.get(0);
        assertEquals(timeSlot.getCourseId(), retrievedTimeSlot.getCourseId());
        assertEquals(timeSlot.getDayOfWeek(), retrievedTimeSlot.getDayOfWeek());
        assertEquals(timeSlot.getBeginPeriod(), retrievedTimeSlot.getBeginPeriod());
        assertEquals(timeSlot.getEndPeriod(), retrievedTimeSlot.getEndPeriod());
    }

    @Test
    public void testRemove() {
        TimeSlot timeSlot1 = new TimeSlot(0, 1, 1, 2, 4);
        TimeSlot timeSlot2 = new TimeSlot(0, 2, 1, 3, 5);
        timeSlotDAO.add(timeSlot1);
        timeSlotDAO.add(timeSlot2);

        int timeSlot1Id = timeSlotDAO.getAll().get(0).getId();
        timeSlotDAO.remove(timeSlot1Id);

        List<TimeSlot> timeSlots = timeSlotDAO.getAll();
        assertEquals(1, timeSlots.size());

        TimeSlot retrievedTimeSlot2 = timeSlots.get(0);
        assertEquals(timeSlot2.getCourseId(), retrievedTimeSlot2.getCourseId());
        assertEquals(timeSlot2.getDayOfWeek(), retrievedTimeSlot2.getDayOfWeek());
        assertEquals(timeSlot2.getBeginPeriod(), retrievedTimeSlot2.getBeginPeriod());
        assertEquals(timeSlot2.getEndPeriod(), retrievedTimeSlot2.getEndPeriod());
    }

    @Test
    public void testGetByCourseId() {
        TimeSlot timeSlot1 = new TimeSlot(0, 1, 1, 2, 4);
        TimeSlot timeSlot2 = new TimeSlot(0, 1, 1, 5, 7);
        TimeSlot timeSlot3 = new TimeSlot(0, 2, 1, 3, 5);
        timeSlotDAO.add(timeSlot1);
        timeSlotDAO.add(timeSlot2);
        timeSlotDAO.add(timeSlot3);

        List<TimeSlot> timeSlots = timeSlotDAO.getByCourseId(1);
        assertEquals(2, timeSlots.size());

        for (TimeSlot retrievedTimeSlot : timeSlots) {
            assertEquals(1, retrievedTimeSlot.getCourseId());
        }
    }

    @Test
    public void testGetByPeriod() {
        TimeSlot timeSlot1 = new TimeSlot(0, 1, 1, 2, 4);
        TimeSlot timeSlot2 = new TimeSlot(0, 2, 1, 3, 5);
        TimeSlot timeSlot3 = new TimeSlot(0, 3, 1, 2, 3);
        timeSlotDAO.add(timeSlot1);
        timeSlotDAO.add(timeSlot2);
        timeSlotDAO.add(timeSlot3);

        List<TimeSlot> timeSlots = timeSlotDAO.getByPeriod(1, 2);
        assertEquals(2, timeSlots.size());

        for (TimeSlot retrievedTimeSlot : timeSlots) {
            assertEquals(1, retrievedTimeSlot.getDayOfWeek());
            assertEquals(2, retrievedTimeSlot.getBeginPeriod());
        }
    }
}
