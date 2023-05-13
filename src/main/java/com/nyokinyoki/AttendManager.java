package com.nyokinyoki;

import java.time.*;
import java.util.*;
import java.util.stream.*;

public class AttendManager {
    private final TimeTable timeTable;
    private final TimeCard timeCard;

    public AttendManager(TimeTable timeTable, TimeCard timeCard) {
        this.timeTable = timeTable;
        this.timeCard = timeCard;
    }

    public StampStatus getStampStatus(LocalDateTime timestamp) {
        Optional<TimeSlot> optionalTimeSlot = timeTable.getOngoingTimeSlot(timestamp);

        if (optionalTimeSlot.isPresent()) {
            TimeSlot timeSlot = optionalTimeSlot.get();
            int status = timeSlot.getStampStatus(timestamp);
            return new StampStatus(status, timeSlot);
        } else {
            return new StampStatus(StampStatus.OUT_INVALID, null);
        }
    }

    public List<StampStatus> getStampStatusesByDate(LocalDate date) {
        List<StampStatus> stampStatuses = new ArrayList<>();
        List<LocalDateTime> timestamps = timeCard.getTimestampsByDate(date);
        for (LocalDateTime timestamp : timestamps) {
            Optional<TimeSlot> optionalTimeSlot = timeTable.getOngoingTimeSlot(timestamp);
            if (optionalTimeSlot.isPresent()) {
                TimeSlot timeSlot = optionalTimeSlot.get();
                int status = timeSlot.getStampStatus(timestamp);
                stampStatuses.add(new StampStatus(status, timeSlot));
            } else {
                stampStatuses.add(new StampStatus(StampStatus.OUT_INVALID, null));
            }
        }
        return stampStatuses;
    }

    public AttendStatus getAttendStatusByOngoingTimeSlot(List<LocalDateTime> timestamps) {
        LocalDate date = timestamps.get(0).toLocalDate();
        Optional<TimeSlot> optionalTimeSlot = timeTable.getOngoingTimeSlot(timestamps.get(0));
        if (optionalTimeSlot.isPresent()) {
            TimeSlot timeSlot = optionalTimeSlot.get();
            int status = timeSlot.getAttendStatus(timestamps);
            return new AttendStatus(date, timeSlot, status);
        } else {
            return new AttendStatus(date, null, AttendStatus.ABSENT);
        }
    }

    public AttendStatus getAttendStatusByDateAndTimeSlot(LocalDate date, TimeSlot timeSlot) {
        List<LocalDateTime> timestamps = timeCard.getTimestampsByDateAndTimeSlot(date, timeSlot);
        int status = timeSlot.getAttendStatus(timestamps);
        return new AttendStatus(date, timeSlot, status);
    }

    public List<AttendStatus> getAttendStatusesByDate(LocalDate date) {
        List<AttendStatus> attendStatuses = new ArrayList<>();
        List<TimeSlot> timeSlots = timeTable.getTimeSlotsByDayOfWeek(date.getDayOfWeek());
        for (TimeSlot timeSlot : timeSlots) {
            List<LocalDateTime> timestamps = timeCard.getTimestampsByDateAndTimeSlot(date, timeSlot);
            int status = timeSlot.getAttendStatus(timestamps);
            attendStatuses.add(new AttendStatus(date, timeSlot, status));
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
            List<TimeSlot> timeSlots = timeTable.getTimeSlotsByDayOfWeek(date.getDayOfWeek());
            for (TimeSlot timeSlot : timeSlots) {
                int status = timeSlot.getAttendStatus(timestampsByDate);
                attendStatuses.add(new AttendStatus(date, timeSlot, status));
            }
        }
        return attendStatuses;
    }
}
