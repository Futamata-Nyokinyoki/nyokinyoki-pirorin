package com.nyokinyoki.Timestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.nyokinyoki.DAO.TimestampDAO;
import com.nyokinyoki.Timetable.Course;
import com.nyokinyoki.Timetable.Timeslot;

public class Timecard {

    public Timecard() {
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