package com.nyokinyoki.Attend;

import org.junit.Test;

//import com.nyokinyoki.Attend.AttendStatus;
import com.nyokinyoki.Timetable.Timeslot;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

public class AttendStatusTest {

    @Test
    public void testGetDate() {
        LocalDate date = LocalDate.of(2023, 5, 1);
        Timeslot timeslot = new Timeslot(1, 2606, 1, 1, 1);
        AttendStatus attendStatus = new AttendStatus(date, timeslot, AttendStatus.PRESENT);

        assertEquals(date, attendStatus.getDate());
    }

    @Test
    public void testGetTimeslot() {
        LocalDate date = LocalDate.of(2023, 5, 1);
        Timeslot timeslot = new Timeslot(1, 2606, 1, 1, 1);
        AttendStatus attendStatus = new AttendStatus(date, timeslot, AttendStatus.PRESENT);

        assertEquals(timeslot, attendStatus.getTimeslot());
    }

    @Test
    public void testGetStatus() {
        LocalDate date = LocalDate.of(2023, 5, 1);
        Timeslot timeslot = new Timeslot(1, 2606, 1, 1, 1);
        AttendStatus attendStatus = new AttendStatus(date, timeslot, AttendStatus.PRESENT);

        assertEquals(AttendStatus.PRESENT, attendStatus.getStatus());
    }

}