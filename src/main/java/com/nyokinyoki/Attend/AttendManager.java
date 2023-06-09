package com.nyokinyoki.Attend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nyokinyoki.Timestamp.StampStatus;
import com.nyokinyoki.Timestamp.Timecard;
import com.nyokinyoki.Timetable.Course;
import com.nyokinyoki.Timetable.Timeslot;
import com.nyokinyoki.Timetable.Timetable;

public class AttendManager {
    private final Timetable timetable;
    private final Timecard timeCard;

    public AttendManager(Timetable timetable, Timecard timeCard) {
        this.timetable = timetable;
        this.timeCard = timeCard;
    }

    public StampStatus getStampStatus(LocalDateTime timestamp) {
        return getStampStatusFromTimestamp(timestamp);
    }

    public List<StampStatus> getAllStampStatuses() {
        return timeCard.getAllTimestamps().stream().map(this::getStampStatusFromTimestamp).collect(Collectors.toList());
    }

    public List<StampStatus> getStampStatusesByDate(LocalDate date) {
        return timeCard.getTimestampsByDate(date).stream().map(this::getStampStatusFromTimestamp)
                .collect(Collectors.toList());
    }

    public List<AttendStatus> getAttendStatusesByDate(LocalDate date) {
        List<AttendStatus> attendStatuses = new ArrayList<>();
        List<Timeslot> timeslots = timetable.getTimeslotsByDayOfWeek(date.getDayOfWeek());
        for (Timeslot timeslot : timeslots) {
            List<LocalDateTime> timestamps = timeCard.getTimestampsByDateAndTimeslot(date, timeslot);
            int status = timeslot.getAttendStatus(timestamps);
            attendStatuses.add(new AttendStatus(date, timeslot, status));
        }
        return attendStatuses;
    }

    public List<AttendStatus> getAttendStatusesByCourse(Course course) {
        List<AttendStatus> attendStatuses = new ArrayList<>();
        List<LocalDateTime> timestamps = timeCard.getTimestampsByCourse(course);
        if (timestamps.isEmpty()) {
            return attendStatuses;
        }
        Map<LocalDate, List<LocalDateTime>> groupedByDate = timestamps.stream()
                .collect(Collectors.groupingBy(LocalDateTime::toLocalDate));
        for (Map.Entry<LocalDate, List<LocalDateTime>> entry : groupedByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<LocalDateTime> timestampsByDate = entry.getValue();
            List<Timeslot> timeslots = course.getTimeslots();
            for (Timeslot timeslot : timeslots) {
                int status = timeslot.getAttendStatus(timestampsByDate);
                attendStatuses.add(new AttendStatus(date, timeslot, status));
            }
        }
        return attendStatuses;
    }

    private StampStatus getStampStatusFromTimestamp(LocalDateTime timestamp) {
        Optional<Timeslot> optionalTimeslot = timetable.getOngoingTimeslot(timestamp);
        if (optionalTimeslot.isPresent()) {
            Timeslot timeslot = optionalTimeslot.get();
            int status = timeslot.getStampStatus(timestamp);
            return new StampStatus(timestamp, status, timeslot);
        } else {
            return new StampStatus(timestamp, StampStatus.OUT_INVALID, null);
        }
    }
}
