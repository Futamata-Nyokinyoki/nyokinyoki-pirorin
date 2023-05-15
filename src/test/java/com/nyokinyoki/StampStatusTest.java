package com.nyokinyoki;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

public class StampStatusTest {

    @Test
    public void testToString() {
        LocalDateTime timestamp = LocalDateTime.now();
        int status = StampStatus.START;
        Timeslot timeslot = new Timeslot(1, 2606, 1, 1, 1);

        StampStatus stampStatus = new StampStatus(timestamp, status, timeslot);

        String expected = "StampStatus {" + "timestamp=" + timestamp.toString() + ", status='START'" + ", courseId=C001"
                + ", courseName='English'" + ", timeslot=" + timeslot.toString() + "}";

        assertEquals(expected, stampStatus.toString());
        assertNotNull(stampStatus.getTimeslot());
    }
}
