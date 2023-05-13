package com.nyokinyoki;

import org.jline.reader.*;

import com.nyokinyoki.Terminal.UtilizedTerminal;
import com.nyokinyoki.TimeTable.TimeTable;
import com.nyokinyoki.TimeTable.TimeTableDAO;
import com.nyokinyoki.TimeTable.Course.Course;
import com.nyokinyoki.TimeTable.Course.CourseDAO;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.nyokinyoki.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class App {
    // Please uncomment out below if all is ready;
    // static TimeTable timeTable = new TimeTable(new TimeTableDAO(), new
    // CourseDAO());
    // static TimeCard timeCard = new TimeCard(new TimestampDAO());
    // static AttendanceManager attendanceManager = new AttendanceManager(timeTable,
    // timeCard);

    public static void main(String[] args) {
        LineReader reader = new UtilizedTerminal().getReader();
        List<Pattern> patterns = UtilizedTerminal.getAllOptions().stream()
                .map(option -> Pattern.compile(option.getPattern())).collect(Collectors.toList());
        try {
            while (true) {
                String line = reader.readLine("=====> ");
                Matcher match;
                for (int i = 0; i < patterns.size(); i++) {
                    Pattern p = patterns.get(i);
                    match = p.matcher(line);
                    if (match.matches()) {
                        hoge(i, match);
                        break;
                    }
                }
            }
        } catch (UserInterruptException e) {
            System.out.println("処理が中断されました");
        }
    }

    static void hoge(int i, Matcher match) {
        int groupCount = match.groupCount();
        switch (i) {
        case 0: {
            System.out.println(i);
            System.out.println("Show Registered Courses");
            // Please uncomment out below if all is ready;
            // showRegisteredCourses();
            break;
        }
        case 1: {
            System.out.println(i);
            System.out.println("Get Available Courses");
            // Please uncomment out below if all is ready;
            // getAvailableCourses();
            break;
        }
        case 2: {
            System.out.println(i);
            System.out.println("Get Available Courses By Time Slot");
            if (groupCount <= 1) {
                System.out.println("getAvailableCourses: Invalid");
                return;
            }
            int dayOfWeek = Integer.parseInt(match.group(1));
            int beginPeriod = Integer.parseInt(match.group(2));
            System.out.println("Day of week: " + dayOfWeek);
            System.out.println("Beginning Period: " + beginPeriod);
            // Please uncomment out below if all is ready;
            // getAvailableCoursesByTimeSlot(dayOfWeek, beginPeriod);
            break;
        }
        case 3: {
            System.out.println(i);
            System.out.println("Add Course");
            if (groupCount <= 0) {
                System.out.println("Add Course: Invalid Input");
            }
            int id = Integer.parseInt(match.group(1));
            System.out.println("Course ID: " + id);
            // Please uncomment out below if all is ready;
            // addCourse(id);
            break;
        }
        case 4: {
            System.out.println(i);
            System.out.println("Remove Course");
            if (groupCount <= 0) {
                System.out.println("Remove Course: Invalid Input");
            }
            int id = Integer.parseInt(match.group(1));
            System.out.println("Course ID: " + id);
            // Please uncomment out below if all is ready;
            // removeCourse(id);
            break;
        }
        default: {
            throw new Error("Expected index from 0 to 4, but got: " + i);
        }
        }
    }

    // private static void showRegisteredCourses() {
    // System.out.println("Registered courses:");
    // System.out.println(timeTable);
    // }

    // private static void addCourse(int id) {
    // timeTable.addCourse(id);
    // }

    // private static void removeCourse(int id) {
    // timeTable.removeCourse(id);
    // }

    // private static void getAvailableCourses() {
    // List<Course> courses = timeTable.getAvailableCourses();
    // for (Course c : courses) {
    // System.out.println(c);
    // }
    // }

    // private static void getAvailableCoursesByTimeSlot(int dayOfWeek, int
    // beginPeriod) {
    // List<Course> courses = timeTable.getAvailableCoursesByPeriod(dayOfWeek,
    // beginPeriod);
    // for (Course c : courses) {
    // System.out.println(c);
    // }
    // }
}
