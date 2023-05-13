package com.nyokinyoki;

import java.time.*;
import java.util.*;

public class TimeSlot {
    private final int id;
    private final int courseId;
    private final int dayOfWeek;
    private final int beginPeriod;
    private final int endPeriod;

    private static final LocalDateTime FIRST_PERIOD_START_TIME = LocalDateTime.parse("2023-01-01T08:50:00");
    private static final int PERIOD_DURATION_MINUTES = 90;
    private static final int SHORT_BREAK_DURATION_MINUTES = 10;
    private static final int LUNCH_BREAK_DURATION_MINUTES = 60;
    private static final int FIRST_PERIOD_AFTER_LUNCH = 3;

    public static final int STAMP_START_DURATION_MINUTES_FIRST_AND_AFTER_LUNCH = 20;
    public static final int STAMP_START_DURATION_MINUTES = 10;
    public static final int STAMP_END_DURATION_MINUTES = 10;

    public TimeSlot(int id, int courseId, int dayOfWeek, int beginPeriod, int endPeriod) {
        if (endPeriod < beginPeriod) {
            throw new IllegalArgumentException("End period must be greater than or equal to begin period.");
        }

        this.id = id;
        this.courseId = courseId;
        this.dayOfWeek = dayOfWeek;
        this.beginPeriod = beginPeriod;
        this.endPeriod = endPeriod;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getBeginPeriod() {
        return beginPeriod;
    }

    public int getEndPeriod() {
        return endPeriod;
    }

    public Course getCourse() {
        return new CourseDAO().getById(courseId);
    }

    public boolean overlapsWith(TimeSlot other) {
        if (dayOfWeek != other.dayOfWeek) {
            return false;
        }

        return beginPeriod <= other.endPeriod && other.beginPeriod <= endPeriod;
    }

    @Override
    public String toString() {
        return String.format("TimeSlot {dayOfWeek=%d, beginPeriod=%d, endPeriod=%d}", dayOfWeek, beginPeriod,
                endPeriod);
    }

    public static LocalDateTime getStartTimeForPeriod(int period) {
        Duration duration = Duration.ofMinutes((period - 1) * (PERIOD_DURATION_MINUTES + SHORT_BREAK_DURATION_MINUTES));
        if (period >= FIRST_PERIOD_AFTER_LUNCH) {
            duration = duration.plusMinutes(LUNCH_BREAK_DURATION_MINUTES - SHORT_BREAK_DURATION_MINUTES);
        }
        return FIRST_PERIOD_START_TIME.plus(duration);
    }

    public static LocalDateTime getEndTimeForPeriod(int period) {
        return getStartTimeForPeriod(period).plusMinutes(PERIOD_DURATION_MINUTES);
    }

    public LocalDateTime getStartTime() {
        return getStartTimeForPeriod(beginPeriod);
    }

    public LocalDateTime getEndTime() {
        return getEndTimeForPeriod(endPeriod);
    }

    public LocalDateTime getStartStampTimeStart() {
        LocalDateTime startTime = getStartTime();
        if (beginPeriod == 1 || beginPeriod == FIRST_PERIOD_AFTER_LUNCH) {
            startTime = startTime.minusMinutes(STAMP_START_DURATION_MINUTES_FIRST_AND_AFTER_LUNCH);
        } else {
            startTime = startTime.minusMinutes(STAMP_START_DURATION_MINUTES);
        }
        return startTime;
    }

    public LocalDateTime getStartStampTimeEnd() {
        return getStartTime().plusMinutes(STAMP_END_DURATION_MINUTES);
    }

    public LocalDateTime getEndStampTimeStart() {
        return getEndTime().minusMinutes(STAMP_END_DURATION_MINUTES);
    }

    public LocalDateTime getEndStampTimeEnd() {
        return getEndTime().plusMinutes(STAMP_END_DURATION_MINUTES);
    }

    public boolean isBetweenStampStartTime(LocalDateTime time) {
        if (time.getDayOfWeek().getValue() != dayOfWeek) {
            return false;
        }
        time = time.withDayOfYear(1);
        return time.isAfter(getStartStampTimeStart()) && time.isBefore(getStartStampTimeEnd());
    }

    public boolean isBetweenStampEndTime(LocalDateTime time) {
        if (time.getDayOfWeek().getValue() != dayOfWeek) {
            return false;
        }
        time = time.withDayOfYear(1);
        return time.isAfter(getEndStampTimeStart()) && time.isBefore(getEndStampTimeEnd());
    }

    public boolean isOngoing(LocalDateTime time) {
        if (time.getDayOfWeek().getValue() != dayOfWeek) {
            return false;
        }
        time = time.withDayOfYear(1);
        return time.isAfter(getStartStampTimeStart()) && time.isBefore(getEndStampTimeEnd());
    }

    public int getStampStatus(LocalDateTime time) {
        if (time.getDayOfWeek().getValue() != dayOfWeek) {
            return StampStatus.OUT_INVALID;
        }
        time = time.withDayOfYear(1);
        if (time.isBefore(getStartStampTimeStart())) {
            return StampStatus.OUT_INVALID;
        } else if (time.isBefore(getStartStampTimeEnd())) {
            return StampStatus.START;
        } else if (time.isBefore(getEndStampTimeStart())) {
            return StampStatus.IN_INVALID;
        } else if (time.isBefore(getEndStampTimeEnd())) {
            return StampStatus.END;
        } else {
            return StampStatus.OUT_INVALID;
        }
    }

    public int getAttendStatus(List<LocalDateTime> timestamps) {
        int status = AttendStatus.ABSENT;
        for (LocalDateTime timestamp : timestamps) {
            if (isBetweenStampStartTime(timestamp)) {
                status += AttendStatus.START_STAMP;
                break;
            }
        }
        for (LocalDateTime timestamp : timestamps) {
            if (isBetweenStampEndTime(timestamp)) {
                status += AttendStatus.END_STAMP;
                break;
            }
        }
        return status;
    }

}
