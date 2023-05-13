package com.nyokinyoki;

import com.nyokinyoki.TimeTable.Course.TimeSlot.*;

public class AttendStatus {
    private final TimeSlot timeSlot;
    private final int status;


    public static final int START_STAMP = 1;
    public static final int END_STAMP = 2;

    public static final int ABSENT = 0;
    public static final int LEFT_EARLY = START_STAMP;
    public static final int LATE = END_STAMP;
    public static final int PRESENT = START_STAMP + END_STAMP;

    public AttendStatus(TimeSlot timeSlot, int status) {
        this.timeSlot = timeSlot;
        this.status = status;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        String statusDescription;
        switch (status) {
        case ABSENT:
            statusDescription = "Absent";
            break;
        case LEFT_EARLY:
            statusDescription = "Left Early";
            break;
        case LATE:
            statusDescription = "Late";
            break;
        case PRESENT:
            statusDescription = "Present";
            break;
        default:
            statusDescription = "Unknown Status";
        }

        return "AttendStatus {" + "timeSlot=" + timeSlot + ", status=" + statusDescription + '}';
    }

}