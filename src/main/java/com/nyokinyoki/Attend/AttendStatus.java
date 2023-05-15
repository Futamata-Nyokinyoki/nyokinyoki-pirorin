package com.nyokinyoki.Attend;

import java.time.LocalDate;

import com.nyokinyoki.Timetable.Timeslot;

public class AttendStatus {
    private final LocalDate date;
    private final Timeslot timeslot;
    private final int status;

    public static final int START_STAMP = 1;
    public static final int END_STAMP = 2;

    public static final int ABSENT = 0;
    public static final int LEFT_EARLY = START_STAMP;
    public static final int LATE = END_STAMP;
    public static final int PRESENT = START_STAMP + END_STAMP;

    public AttendStatus(LocalDate date, Timeslot timeslot, int status) {
        this.date = date;
        this.timeslot = timeslot;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        String statusDescription;
        switch (status) {
        case ABSENT:
            statusDescription = "欠席";
            break;
        case LEFT_EARLY:
            statusDescription = "早退";
            break;
        case LATE:
            statusDescription = "遅刻";
            break;
        case PRESENT:
            statusDescription = "出席";
            break;
        default:
            statusDescription = "Unknown";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(date);
        sb.append(" ").append(timeslot.getCourse().getId());
        sb.append(" ").append(timeslot.getCourse().getCourseName());
        sb.append(" ").append(timeslot);
        sb.append(" ").append(statusDescription);
        return sb.toString();
    }
}