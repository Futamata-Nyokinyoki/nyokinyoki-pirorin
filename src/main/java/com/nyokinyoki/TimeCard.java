package com.nyokinyoki;

import java.util.List;
import java.time.*;

public class TimeCard {
    private final TimestampDAO timestampDAO;

    public TimeCard(TimestampDAO timestampDAO) {
        this.timestampDAO = timestampDAO;
    }

    public void stamp(LocalDateTime timestamp) {
        timestampDAO.add(timestamp);
    }

    public List<LocalDateTime> getAllTimestamps() {
        return timestampDAO.getAll();
    }

    public List<LocalDateTime> getTimestampsByDate(LocalDate date) {
        return timestampDAO.getByDate(date);
    }

    public List<LocalDateTime> getTimestampsByCourse(Course course) {
        return timestampDAO.getByCourse(course);
    }

    public List<LocalDateTime> getTimestampsByDateAndTimeslot(LocalDate date, Timeslot timeslot) {
        return timestampDAO.getByDateAndTimeslot(date, timeslot);
    }
}