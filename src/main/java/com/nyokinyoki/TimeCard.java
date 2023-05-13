package com.nyokinyoki;

import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.nyokinyoki.TimeTable.Course.*;
import com.nyokinyoki.TimeTable.Course.TimeSlot.*;

public class TimeCard {
    private final TimestampDAO timestampDAO;

    public TimeCard(TimestampDAO timestampDAO) {
        this.timestampDAO = timestampDAO;
    }

    public void stamp(LocalDateTime timestamp) {
        timestampDAO.add(timestamp);
    }

    public List<LocalDateTime> getTimestampsByDate(LocalDateTime date) {
        return timestampDAO.getByDate(date);
    }

    public List<LocalDateTime> getTimestampsByDayOfWeek(DayOfWeek dayOfWeek) {
        return timestampDAO.getByDayOfWeek(dayOfWeek);
    }

    public List<LocalDateTime> getTimestampsByTimeSlot(TimeSlot timeSlot) {
        return timestampDAO.getByTimeSlot(timeSlot);
    }

    public List<LocalDateTime> getTimestampsByCourse(Course course) {
        return timestampDAO.getByCourse(course);
    }
}