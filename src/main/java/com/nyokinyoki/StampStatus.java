package com.nyokinyoki;

public class StampStatus {
    private final int status;
    private final TimeSlot timeSlot;

    public static final int OUT_INVALID = 0;
    public static final int IN_INVALID = 1;
    public static final int START = 2;
    public static final int END = 3;

    public StampStatus(int status, TimeSlot timeSlot) {
        this.status = status;
        this.timeSlot = timeSlot;
    }

    public int getStatus() {
        return status;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String toString() {
        String statusDescription;
        switch (status) {
        case OUT_INVALID:
            statusDescription = "OUT_INVALID";
            break;
        case IN_INVALID:
            statusDescription = "IN_INVALID";
            break;
        case START:
            statusDescription = "START";
            break;
        case END:
            statusDescription = "END";
            break;
        default:
            statusDescription = "UNKNOWN";
            break;
        }

        if (timeSlot == null) {
            return "StampStatus {" + "status=" + statusDescription + '}';
        }

        return "StampStatus {" + "courseId=" + timeSlot.getCourse().getId() + ", courseName='"
                + timeSlot.getCourse().getCourseName() + "', timeSlot=" + timeSlot + ", status=" + statusDescription
                + '}';
    }
}
