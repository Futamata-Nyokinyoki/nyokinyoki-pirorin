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

                boolean success = false;
                for (int i = 0; i < patterns.size(); i++) {
                    Pattern p = patterns.get(i);
                    match = p.matcher(line);
                    if (match.matches()) {
                        hoge(i, match);
                        success = true;
                        break;
                    }
                }
                if (!success) {
                    alert("コマンドが不正確です。");
                    UtilizedTerminal.helpMessage();
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
            System.out.println("登録済みの講義を表示します。");
            showRegisteredCourses();
            break;
        }
        case 1: {
            System.out.println("履修可能な講義を表示します。");
            getAvailableCourses();
            break;
        }
        case 2: {
            System.out.println("履修可能な講義のうち、指定した時間にあるものを表示します。");
            if (groupCount <= 1) {
                System.out.println("getAvailableCourses: Invalid");
                return;
            }
            int dayOfWeek = Integer.parseInt(match.group(1));
            int beginPeriod = Integer.parseInt(match.group(2));
            System.out.println("日付: " + dayOfWeek);
            System.out.println("開始時刻: " + beginPeriod);
            getAvailableCoursesByTimeSlot(dayOfWeek, beginPeriod);
            break;
        }
        case 3: {
            System.out.println("履修登録をします");
            if (groupCount <= 0) {
                System.out.println("Add Course: Invalid Input");
            }
            int id = Integer.parseInt(match.group(1));
            System.out.println("コースID: " + id);
            addCourse(id);
            break;
        }
        case 4: {
            System.out.println("履修登録を解除します");
            if (groupCount <= 0) {
                System.out.println("Remove Course: Invalid Input");
            }
            int id = Integer.parseInt(match.group(1));
            System.out.println("コースID: " + id);
            removeCourse(id);
            break;
        }
        case 5: {
            System.out.println("打刻をします");
            if (groupCount <= 0) {
                System.out.println("Stamp: Invalid Input");
            }
            String timestampString = match.group(1);
            System.out.println("時刻: " + timestampString);
            stamp(timestampString);
            break;
        }
        case 6: {
            System.out.println("すべての打刻を表示します");
            showAllStampHistory();
            break;
        }
        case 7: {
            System.out.println("指定した日付のすべての打刻を表示します");
            if (groupCount <= 0) {
                System.out.println("Invalid Input");
            }
            String dateString = match.group(1);
            System.out.println("日付: " + dateString);
            showAttendHistoryByDate(dateString);
            break;
        }
        case 8: {
            System.out.println("指定した講義の打刻を表示");
            if (groupCount <= 0) {
                System.out.println("Invalid Input");
            }
            int id = Integer.parseInt(match.group(1));
            System.out.println("コースID: " + id);
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

    private static void alert(String sentence) {
        System.out.println("\u001b[00;31m" + sentence + "\u001b[00m");
    }
}
