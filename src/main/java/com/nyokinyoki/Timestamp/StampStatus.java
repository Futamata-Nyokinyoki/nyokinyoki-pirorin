package com.nyokinyoki.Timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.nyokinyoki.Timetable.Timeslot;

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
            statusDescription = "通常打刻";
            break;
        case IN_INVALID:
            statusDescription = "授業打刻";
            break;
        case START:
            statusDescription = "授業開始打刻";
            break;
        case END:
            statusDescription = "授業終了打刻";
            break;
        default:
            statusDescription = "UNKNOWN";
            break;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append(timestamp.format(formatter));
        sb.append(" ").append(statusDescription);
        if (timeslot != null) {
            sb.append(" ").append(timeslot.getCourse().getId());
            sb.append(" ").append(timeslot.getCourse().getCourseName());
            sb.append(" ").append(timeslot);
        }
        return sb.toString();
    }
}
