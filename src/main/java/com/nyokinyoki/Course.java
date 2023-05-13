package com.nyokinyoki;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

public class Course {
    private final int id;
    private final String courseName;
    private final List<TimeSlot> timeSlots;

    public Course(int id, String courseName, TimeSlotDAO timeSlotDAO) {
        this.id = id;
        this.courseName = courseName;
        this.timeSlots = timeSlotDAO.getByCourseId(id);
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public List<TimeSlot> getTimeSlotsByDayOfWeek(DayOfWeek dayOfWeek) {
        return timeSlots.stream().filter(timeSlot -> timeSlot.getDayOfWeek() == dayOfWeek.getValue()).toList();
    }

    public boolean overlapsWith(Course other) {
        List<TimeSlot> timeSlots = this.getTimeSlots();
        List<TimeSlot> otherTimeSlots = other.getTimeSlots();

        return timeSlots.stream().anyMatch(ts1 -> otherTimeSlots.stream().anyMatch(ts2 -> ts1.overlapsWith(ts2)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course) {
            Course other = (Course) obj;
            return id == other.id;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", courseName='" + courseName + '\'' + '}';
    }

    public TimeSlot getOngoingTimeSlot(LocalDateTime timestamp) {
        return timeSlots.stream().filter(timeSlot -> timeSlot.isOngoing(timestamp)).findFirst().orElse(null);
    }
}
