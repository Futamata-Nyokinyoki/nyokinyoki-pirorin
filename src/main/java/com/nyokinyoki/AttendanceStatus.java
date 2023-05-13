package com.nyokinyoki;

import com.nyokinyoki.TimeTable.Course.Course;

public class AttendanceStatus {
    private final Course course;
    private final int status;

    public AttendanceStatus(Course course, int status) {
        this.course = course;
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public int getStatus() {
        return status;
    }

}