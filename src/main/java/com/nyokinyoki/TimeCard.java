package com.nyokinyoki;

import java.util.List;
import java.time.LocalDateTime;

public class TimeCard {
    private final TimestampDAO timestampDAO;
    private final List<LocalDateTime> timestamps;

    private TimeCard(TimestampDAO timestampDAO) {
        this.timestampDAO = timestampDAO;
        this.timestamps = timestampDAO.getAll();
    }

    public List<LocalDateTime> getTimestamps() {
        return timestamps;
    }

    public void stamp(LocalDateTime timestamp) {
        timestampDAO.add(timestamp);
        timestamps.add(timestamp);
    }

    public List<LocalDateTime> getTimestampsBetween(LocalDateTime from, LocalDateTime to) {
        return timestampDAO.getBetween(from, to);
    }

    public List<LocalDateTime> getTimestampsByDate(LocalDateTime date) {
        return timestampDAO.getByDate(date);
    }

}