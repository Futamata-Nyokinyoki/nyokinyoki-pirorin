package com.nyokinyoki;

import com.nyokinyoki.TimeTable.Course.Course;

public class StampStatus {
    private final int status;
    private final Course course;

    public static final int NONE = 0;
    public static final int START = 1;
    public static final int END = 2;

    public StampStatus(int status, Course course) {
        this.status = status;
        this.course = course;
    }

    public int getStatus() {
        return status;
    }

    public Course getCourse() {
        return course;
    }
}