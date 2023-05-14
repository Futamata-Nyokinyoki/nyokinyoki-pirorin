package com.nyokinyoki;

import org.jline.reader.*;

import com.nyokinyoki.Terminal.UtilizedTerminal;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class App {
    static Timetable timeTable = new Timetable();
    static TimeCard timeCard = new TimeCard();
    static AttendManager attendanceManager = new AttendManager(timeTable, timeCard);

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
        System.out.println("i: " + i);
        switch (i) {
        case 0: {
            System.out.println("Show Registered Courses");
            showRegisteredCourses();
            break;
        }
        case 1: {
            System.out.println("Get Available Courses");
            getAvailableCourses();
            break;
        }
        case 2: {
            System.out.println("Get Available Courses By Time Slot");
            if (groupCount <= 1) {
                System.out.println("getAvailableCourses: Invalid");
                return;
            }
            int dayOfWeek = Integer.parseInt(match.group(1));
            int beginPeriod = Integer.parseInt(match.group(2));
            System.out.println("Day of week: " + dayOfWeek);
            System.out.println("Beginning Period: " + beginPeriod);
            getAvailableCoursesByTimeSlot(dayOfWeek, beginPeriod);
            break;
        }
        case 3: {
            System.out.println("Add Course");
            if (groupCount <= 0) {
                System.out.println("Add Course: Invalid Input");
            }
            int id = Integer.parseInt(match.group(1));
            System.out.println("Course ID: " + id);
            addCourse(id);
            break;
        }
        case 4: {
            System.out.println("Remove Course");
            if (groupCount <= 0) {
                System.out.println("Remove Course: Invalid Input");
            }
            int id = Integer.parseInt(match.group(1));
            System.out.println("Course ID: " + id);
            removeCourse(id);
            break;
        }
        case 5: {
            System.out.println("Stamp");
            if (groupCount <= 0) {
                System.out.println("Stamp: Invalid Input");
            }
            String timestampString = match.group(1);
            stamp(timestampString);
            break;
        }
        case 6: {
            System.out.println("Show All Stamp History");
            showAllStampHistory();
            break;
        }
        case 7: {
            System.out.println("Show Attend History By Date");
            if (groupCount <= 0) {
                System.out.println("Invalid Input");
            }
            String dateString = match.group(1);
            showAttendHistoryByDate(dateString);
            break;
        }
        case 8: {
            System.out.println("Show Attend History By Course");
            if (groupCount <= 0) {
                System.out.println("Invalid Input");
            }
            int id = Integer.parseInt(match.group(1));
            showAttendHistoryByCourse(id);
            break;
        }
        default: {
            throw new Error("Expected index from 0 to 4, but got: " + i);
        }
        }
    }

    private static void showRegisteredCourses() {
        System.out.println("Registered courses:");
        System.out.println(timeTable);
    }

    private static void addCourse(int id) {
        timeTable.addCourse(id);
    }

    private static void removeCourse(int id) {
        timeTable.removeCourse(id);
    }

    private static void getAvailableCourses() {
        List<Course> courses = timeTable.getAvailableCourses();
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private static void getAvailableCoursesByTimeSlot(int dayOfWeek, int beginPeriod) {
        List<Course> courses = timeTable.getAvailableCoursesByPeriod(dayOfWeek, beginPeriod);
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private static void stamp(String timestampString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);
        timeCard.stamp(timestamp);
        StampStatus status = attendanceManager.getStampStatus(timestamp);
        System.out.println(status);
    }

    private static void showAttendHistoryByDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        List<AttendStatus> attendStatuses = attendanceManager.getAttendStatusesByDate(date);
        for (AttendStatus status : attendStatuses) {
            System.out.println(status);
        }
    }

    private static void showAllStampHistory() {
        List<StampStatus> attendStatuses = attendanceManager.getAllStampStatuses();
        for (StampStatus status : attendStatuses) {
            System.out.println(status);
        }
    }

    private static void showAttendHistoryByCourse(int id) {
        Course course = CourseDAO.getInstance().getById(id);
        if (course == null) {
            System.out.println("Course not found");
            return;
        }
        List<AttendStatus> attendStatuses = attendanceManager.getAttendStatusesByCourse(course);
        for (AttendStatus status : attendStatuses) {
            System.out.println(status);
        }
    }
}
