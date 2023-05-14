package com.nyokinyoki;

import java.time.*;
import java.time.format.*;

public class StampStatus {
    private final LocalDateTime timestamp;
    private final int status;
    private final TimeSlot timeSlot;

    public static final int OUT_INVALID = 0;
    public static final int IN_INVALID = 1;
    public static final int START = 2;
    public static final int END = 3;

    public StampStatus(LocalDateTime timestamp, int status, TimeSlot timeSlot) {
        this.timestamp = timestamp;
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append("StampStatus {");
        sb.append("timestamp=" + timestamp.format(formatter));
        sb.append(", status='" + statusDescription + "'");
        if (timeSlot != null) {
            sb.append(", courseId=" + timeSlot.getCourse().getId());
            sb.append(", courseName='" + timeSlot.getCourse().getCourseName() + "'");
            sb.append(", timeSlot=" + timeSlot);
        }
        sb.append("}");
        return sb.toString();
    }
}
