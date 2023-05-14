package com.nyokinyoki;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class Timetable {
    private final List<Course> courses;
    private final TimetableDAO timetableDAO;
    private final CourseDAO courseDAO;

    public Timetable(TimetableDAO timetableDAO, CourseDAO courseDAO) {
        this.timetableDAO = timetableDAO;
        this.courseDAO = courseDAO;
        this.courses = timetableDAO.getAll();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        if (!this.isAvailable(course)) {
            throw new IllegalArgumentException("Course is not available");
        }

        timetableDAO.add(course);
        courses.add(course);
    }

    public void addCourse(int id) {
        addCourse(courseDAO.getById(id));
    }

    public void removeCourse(Course course) {
        timetableDAO.remove(course.getId());
        courses.removeIf(c -> c.equals(course));
    }

    public void removeCourse(int id) {
        removeCourse(courseDAO.getById(id));
    }

    public Optional<Timeslot> getOngoingTimeslot(LocalDateTime timestamp) {
        return courses.stream().map(course -> course.getOngoingTimeslot(timestamp)).filter(Objects::nonNull)
                .findFirst();
    }

    public List<Timeslot> getTimeslotsByDayOfWeek(DayOfWeek dayOfWeek) {
        return courses.stream().flatMap(course -> course.getTimeslotsByDayOfWeek(dayOfWeek).stream())
                .collect(Collectors.toList());
    }

    public List<Course> getAvailableCourses() {
        return courseDAO.getAll().stream().filter(this::isAvailable).collect(Collectors.toList());
    }

    public List<Course> getAvailableCoursesByPeriod(int dayOfWeek, int beginPeriod) {
        return courseDAO.getByPeriod(dayOfWeek, beginPeriod).stream().filter(this::isAvailable)
                .collect(Collectors.toList());
    }

    public boolean isAvailable(Course course) {
        return courses.stream()
                .noneMatch(existingCourse -> existingCourse.equals(course) || existingCourse.overlapsWith(course));
    }

    @Override
    public String toString() {
        return courses.stream().map(Course::toString).collect(Collectors.joining("\n"));
    }
}
