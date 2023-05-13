package com.nyokinyoki;

import java.time.*;
import java.util.*;

import com.nyokinyoki.TimeTable.*;
import com.nyokinyoki.TimeTable.Course.*;
import com.nyokinyoki.TimeTable.Course.TimeSlot.*;

public class AttendanceManager {
    private final TimeTable timeTable;
    private final TimeCard timeCard;

    public AttendanceManager(TimeTable timeTable, TimeCard timeCard) {
        this.timeTable = timeTable;
        this.timeCard = timeCard;
    }

    public StampStatus getStampStatus(LocalDateTime timestamp) {
        TimeSlot timeSlot = timeTable.getOngoingTimeSlot(timestamp);
        int status = timeSlot.getStampStatus(timestamp);
        return new StampStatus(status, timeSlot);
    }

    public List<StampStatus> getStampStatusesByDate(LocalDateTime date) {
        List<StampStatus> stampStatuses = new ArrayList<>();
        List<LocalDateTime> timestamps = timeCard.getTimestampsByDate(date);
        for (LocalDateTime timestamp : timestamps) {
            TimeSlot timeSlot = timeTable.getOngoingTimeSlot(timestamp);
            int status = timeSlot.getStampStatus(timestamp);
            stampStatuses.add(new StampStatus(status, timeSlot));
        }
        return stampStatuses;
    }

    public AttendStatus getAttendStatus(List<LocalDateTime> timestamps) {
        TimeSlot timeSlot = timeTable.getOngoingTimeSlot(timestamps.get(0));
        int status = timeSlot.getAttendStatus(timestamps);
        return new AttendStatus(timeSlot, status);
    }

    public AttendStatus getAttendStatusByTimeSlot(TimeSlot timeSlot) {
        List<LocalDateTime> timestamps = timeCard.getTimestampsByTimeSlot(timeSlot);
        int status = timeSlot.getAttendStatus(timestamps);
        return new AttendStatus(timeSlot, status);
    }

    public List<AttendStatus> getAttendStatusesByDate(LocalDateTime date) {
        List<AttendStatus> attendStatuses = new ArrayList<>();
        for (TimeSlot timeSlot : timeTable.getTimeSlotsByDayOfWeek(date.getDayOfWeek())) {
            attendStatuses.add(getAttendStatusByTimeSlot(timeSlot));
        }
        return attendStatuses;
    }

    public List<AttendStatus> getAttendStatusesByCourse(Course course) {
        List<AttendStatus> attendStatuses = new ArrayList<>();
        for (TimeSlot timeSlot : course.getTimeSlots()) {
            attendStatuses.add(getAttendStatusByTimeSlot(timeSlot));
        }
        return attendStatuses;
    }
}
