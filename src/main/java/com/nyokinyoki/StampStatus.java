package com.nyokinyoki;

import java.time.*;
import java.time.format.*;

public class StampStatus {
    private final LocalDateTime timestamp;
    private final int status;
    private final Timeslot timeslot;

    public static final int OUT_INVALID = 0;
    public static final int IN_INVALID = 1;
    public static final int START = 2;
    public static final int END = 3;

    public StampStatus(LocalDateTime timestamp, int status, Timeslot timeslot) {
        this.timestamp = timestamp;
        this.status = status;
        this.timeslot = timeslot;
    }

    public int getStatus() {
        return status;
    }

    public Timeslot getTimeslot() {
        return timeslot;
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
        if (timeslot != null) {
            sb.append(", courseId=" + timeslot.getCourse().getId());
            sb.append(", courseName='" + timeslot.getCourse().getCourseName() + "'");
            sb.append(", timeslot=" + timeslot);
        }
        sb.append("}");
        return sb.toString();
    }
}
