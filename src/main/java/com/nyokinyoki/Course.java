package com.nyokinyoki;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

public class Course {
    private final int id;
    private final String courseName;
    private final List<Timeslot> timeslots;

    public Course(int id, String courseName) {
        this.id = id;
        this.courseName = courseName;
        this.timeslots = TimeslotDAO.getInstance().getByCourseId(id);
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public List<Timeslot> getTimeslotsByDayOfWeek(DayOfWeek dayOfWeek) {
        return timeslots.stream().filter(timeslot -> timeslot.getDayOfWeek() == dayOfWeek.getValue()).toList();
    }

    public boolean overlapsWith(Course other) {
        List<Timeslot> timeslots = this.getTimeslots();
        List<Timeslot> otherTimeslots = other.getTimeslots();

        return timeslots.stream().anyMatch(ts1 -> otherTimeslots.stream().anyMatch(ts2 -> ts1.overlapsWith(ts2)));
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

    public Timeslot getOngoingTimeslot(LocalDateTime timestamp) {
        return timeslots.stream().filter(timeslot -> timeslot.isOngoing(timestamp)).findFirst().orElse(null);
    }
}
