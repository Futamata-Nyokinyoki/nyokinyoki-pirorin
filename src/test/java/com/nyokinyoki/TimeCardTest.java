package com.nyokinyoki;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class TimeCardTest {

    @Test
    public void testGetAllTimestamps() {
        TimeCard timeCard = new TimeCard();
        LocalDateTime timestamp1 = LocalDateTime.parse("2023-05-08T09:00:00");
        LocalDateTime timestamp2 = LocalDateTime.parse("2023-05-12T13:00:00");
        timeCard.stamp(timestamp1);
        timeCard.stamp(timestamp2);
        List<LocalDateTime> expected = new ArrayList<>();
        expected.add(timestamp1);
        expected.add(timestamp2);
        List<LocalDateTime> actual = timeCard.getAllTimestamps();
        assertEquals(expected, actual);
    }

    /*
     * @Test public void testGetTimestampsByDate() { TimeCard timeCard = new
     * TimeCard(); LocalDateTime timestamp1 = LocalDateTime.of(2023, 5, 8, 9, 0, 0);
     * LocalDateTime timestamp2 = LocalDateTime.of(2023, 5, 9, 12, 30, 0);
     * timeCard.stamp(timestamp1); timeCard.stamp(timestamp2); List<LocalDateTime>
     * expected = new ArrayList<>(); expected.add(timestamp1); List<LocalDateTime>
     * actual = timeCard.getTimestampsByDate(LocalDate.of(2023, 5, 8));
     * assertEquals(expected, actual); }
     * 
     * @Test public void testGetTimestampsByCourse() { TimeCard timeCard = new
     * TimeCard(); Course course = new Course(2601, "マルチエージェントシステム"); LocalDateTime
     * timestamp1 = LocalDateTime.of(2023, 5, 8, 9, 0, 0); LocalDateTime timestamp2
     * = LocalDateTime.of(2023, 5, 9, 12, 30, 0); timeCard.stamp(timestamp1);
     * timeCard.stamp(timestamp2); TimestampDAO.getInstance().setCourse(timestamp1,
     * course); List<LocalDateTime> expected = new ArrayList<>();
     * expected.add(timestamp1); List<LocalDateTime> actual =
     * timeCard.getTimestampsByCourse(course); assertEquals(expected, actual); }
     * 
     * @Test public void testGetTimestampsByDateAndTimeslot() { TimeCard timeCard =
     * new TimeCard(); LocalDateTime timestamp1 = LocalDateTime.of(2023, 5, 8, 9, 0,
     * 0); LocalDateTime timestamp2 = LocalDateTime.of(2023, 5, 8, 12, 30, 0);
     * LocalDateTime timestamp3 = LocalDateTime.of(2023, 5, 8, 15, 0, 0);
     * timeCard.stamp(timestamp1); timeCard.stamp(timestamp2);
     * timeCard.stamp(timestamp3); Timeslot timeslot = new Timeslot(LocalTime.of(9,
     * 0), LocalTime.of(12, 0)); List<LocalDateTime> expected = new ArrayList<>();
     * expected.add(timestamp1); expected.add(timestamp2); List<LocalDateTime>
     * actual = timeCard.getTimestampsByDateAndTimeslot(LocalDate.of(2023, 5, 8),
     * timeslot); assertEquals(expected, actual); }
     */
}
