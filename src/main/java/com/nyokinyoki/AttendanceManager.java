package com.nyokinyoki;

import com.nyokinyoki.TimeTable.TimeTable;
import com.nyokinyoki.TimeTable.Course.Course;
import com.nyokinyoki.TimeTable.Course.TimeSlot.TimeSlot;

import java.time.*;
import java.util.*;

public class AttendanceManager {
    private final TimeTable timeTable;
    private final TimeCard timeCard;

    public AttendanceManager(TimeTable timeTable, TimeCard timeCard) {
        this.timeTable = timeTable;
        this.timeCard = timeCard;
    }

    public StampStatus getStampStatus(LocalDateTime timestamp, Course course) {
        int dayOfWeek = timestamp.getDayOfWeek().getValue();
        for (TimeSlot timeSlot : course.getTimeSlots()) {
            if (timeSlot.getDayOfWeek() != dayOfWeek) {
                continue;
            }
            if (timeSlot.isBetweenStampEndTime(timestamp)) {
                return new StampStatus(StampStatus.START, course);
            }
            if (timeSlot.isBetweenStampStartTime(timestamp)) {
                return new StampStatus(StampStatus.END, course);
            }
        }
        return new StampStatus(StampStatus.NONE, null);
    }

    public StampStatus getStampStatus(LocalDateTime timestamp) {
        List<Course> courses = timeTable.getCourses();
        for (Course course : courses) {
            StampStatus stampStatus = getStampStatus(timestamp, course);
            if (stampStatus.getStatus() != StampStatus.NONE) {
                return stampStatus;
            }
        }
        return new StampStatus(StampStatus.NONE, null);
    }

    public List<StampStatus> getStampStatus(List<LocalDateTime> timestamps) {
        List<StampStatus> stampStatuses = new ArrayList<>();
        for (LocalDateTime timestamp : timestamps) {
            stampStatuses.add(getStampStatus(timestamp));
        }
        return stampStatuses;
    }

    public List<StampStatus> getAllStampStatus() {
        List<LocalDateTime> timestamps = timeCard.getTimestamps();
        return getStampStatus(timestamps);
    }

    public List<StampStatus> getStampStatuses(LocalDateTime date) {
        List<LocalDateTime> timestamps = timeCard.getTimestampsByDate(date);
        return getStampStatus(timestamps);
    }

    public List<StampStatus> getStampStatus(Course course) {
        List<LocalDateTime> timestamps = timeCard.getTimestamps();
        List<StampStatus> stampStatuses = new ArrayList<>();
        for (LocalDateTime timestamp : timestamps) {
            StampStatus stampStatus = getStampStatus(timestamp, course);
            if (stampStatus.getStatus() != StampStatus.NONE) {
                stampStatuses.add(stampStatus);
            }
        }
        return stampStatuses;
    }

}
