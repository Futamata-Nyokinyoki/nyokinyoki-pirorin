package com.nyokinyoki;

import java.util.List;
import java.time.*;

public class TimeCard {

    public TimeCard() {
    }

    public void stamp(LocalDateTime timestamp) {
        TimestampDAO.getInstance().add(timestamp);
    }

    public List<LocalDateTime> getAllTimestamps() {
        return TimestampDAO.getInstance().getAll();
    }

    public List<LocalDateTime> getTimestampsByDate(LocalDate date) {
        return TimestampDAO.getInstance().getByDate(date);
    }

    public List<LocalDateTime> getTimestampsByCourse(Course course) {
        return TimestampDAO.getInstance().getByCourse(course);
    }

    public List<LocalDateTime> getTimestampsByDateAndTimeslot(LocalDate date, Timeslot timeslot) {
        return TimestampDAO.getInstance().getByDateAndTimeslot(date, timeslot);
    }
}