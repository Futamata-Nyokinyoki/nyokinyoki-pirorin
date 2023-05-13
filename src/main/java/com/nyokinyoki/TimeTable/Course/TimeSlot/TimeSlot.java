package com.nyokinyoki.TimeTable.Course.TimeSlot;

import java.time.*;

public class TimeSlot {
    private final int id;
    private final int courseId;
    private final int dayOfWeek;
    private final int beginPeriod;
    private final int endPeriod;

    public static final LocalDateTime FIRST_PERIOD_START_TIME = LocalDateTime.parse("2023-01-01T08:50:00");
    public static final int PERIOD_DURATION_MINUTES = 90;
    public static final int SHORT_BREAK_DURATION_MINUTES = 10;
    public static final int LUNCH_BREAK_DURATION_MINUTES = 60;
    public static final int FIRST_PERIOD_AFTER_LUNCH = 3;

    public static final int STAMP_START_DURATION_MINUTES_FIRST_AND_AFTER_LUNCH = 20;
    public static final int STAMP_START_DURATION_MINUTES = 10;
    public static final int STAMP_END_DURATION_MINUTES = 10;

    public static final int ABSENT = 0;
    public static final int LEFT_EARLY = 1;
    public static final int LATE = 2;
    public static final int PRESENT = 3;

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

    public int getDuration() {
        return endPeriod - beginPeriod + 1;
    }

    public boolean overlapsWith(TimeSlot other) {
        if (dayOfWeek != other.dayOfWeek) {
            return false;
        }

        return beginPeriod <= other.endPeriod && other.beginPeriod <= endPeriod;
    }

    @Override
    public String toString() {
        return String.format("TimeSlot {id=%d, courseId=%d, dayOfWeek=%d, beginPeriod=%d, endPeriod=%d}", id, courseId,
                dayOfWeek, beginPeriod, endPeriod);
    }

    public static LocalDateTime getStartTimeForPeriod(int period) {
        Duration duration = Duration.ofMinutes((period - 1) * (PERIOD_DURATION_MINUTES + SHORT_BREAK_DURATION_MINUTES));
        if (period > FIRST_PERIOD_AFTER_LUNCH) {
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
        return getStartTime().minusMinutes(STAMP_END_DURATION_MINUTES);
    }

    public LocalDateTime getEndStampTimeStart() {
        return getEndTime().minusMinutes(STAMP_END_DURATION_MINUTES);
    }

    public LocalDateTime getEndStampTimeEnd() {
        return getEndTime().plusMinutes(STAMP_END_DURATION_MINUTES);
    }

    public boolean isBetweenStampStartTime(LocalDateTime time) {
        return time.isAfter(getStartStampTimeStart()) && time.isBefore(getStartStampTimeEnd());
    }

    public boolean isBetweenStampEndTime(LocalDateTime time) {
        return time.isAfter(getEndStampTimeStart()) && time.isBefore(getEndStampTimeEnd());
    }

    public int getStampStatus(LocalDateTime time) {
        int status = ABSENT;
        if (isBetweenStampStartTime(time)) {
            status += 1;
        }
        if (isBetweenStampEndTime(time)) {
            status += 2;
        }
        return status;
    }
}
