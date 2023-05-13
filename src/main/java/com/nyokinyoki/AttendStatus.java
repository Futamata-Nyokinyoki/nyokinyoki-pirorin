package com.nyokinyoki;

import java.time.*;

public class AttendStatus {
    private final LocalDate date;
    private final TimeSlot timeSlot;
    private final int status;

    public static final int START_STAMP = 1;
    public static final int END_STAMP = 2;

    public static final int ABSENT = 0;
    public static final int LEFT_EARLY = START_STAMP;
    public static final int LATE = END_STAMP;
    public static final int PRESENT = START_STAMP + END_STAMP;

    public AttendStatus(LocalDate date, TimeSlot timeSlot, int status) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
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
            statusDescription = "Unknown";
        }

        return "AttendStatus{" + "date=" + date + ", courseId=" + timeSlot.getCourse().getId() + ", course="
                + timeSlot.getCourse().getCourseName() + ", timeSlot=" + timeSlot + ", status=" + statusDescription
                + '}';
    }
}