package src.test.java.com.nyokinyoki.nyokinyoki_pirorin;

import org.junit.Test;

import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.TimeSlot;

import static org.junit.Assert.*;

public class TimeSlotTest {

    @Test
    public void testGetDuration() {
        TimeSlot timeSlot = new TimeSlot(1, 1, 1, 2, 4);
        int expectedDuration = 3;
        assertEquals(expectedDuration, timeSlot.getDuration());
    }

    @Test
    public void testOverlapsWith() {
        TimeSlot timeSlot1 = new TimeSlot(1, 1, 1, 2, 4);
        TimeSlot timeSlot2 = new TimeSlot(2, 2, 1, 3, 5);
        TimeSlot timeSlot3 = new TimeSlot(3, 3, 1, 5, 6);
        TimeSlot timeSlot4 = new TimeSlot(4, 4, 2, 2, 4);

        assertTrue(timeSlot1.overlapsWith(timeSlot2));
        assertTrue(timeSlot2.overlapsWith(timeSlot1));
        assertFalse(timeSlot1.overlapsWith(timeSlot3));
        assertFalse(timeSlot1.overlapsWith(timeSlot4));
        assertFalse(timeSlot3.overlapsWith(timeSlot4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidConstructor() {
        new TimeSlot(1, 1, 1, 4, 2);
    }
}
