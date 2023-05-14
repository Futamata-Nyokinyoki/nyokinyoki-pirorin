package com.nyokinyoki;

import java.util.List;
import java.time.*;
import java.time.format.*;;

public class IntegrationTest {

    private static Timetable timetable = new Timetable();
    private static TimeCard timeCard = new TimeCard();
    private static AttendManager attendanceManager = new AttendManager(timetable, timeCard);

    public static void main(String[] args) {
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Show registered courses");
            System.out.println("2. Add course");
            System.out.println("3. Remove course");
            System.out.println("4. Get available courses");
            System.out.println("5. Get available courses by time slot");
            System.out.println("6. Stamp");
            System.out.println("7. Show stamp history");
            System.out.println("8. Show attend history by date");
            System.out.println("9. Show attend history by course");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(System.console().readLine());

            switch (choice) {
            case 0:
                System.exit(0);
            case 1:
                showRegisteredCourses();
                break;
            case 2:
                addCourse();
                break;
            case 3:
                removeCourse();
                break;
            case 4:
                getAvailableCourses();
                break;
            case 5:
                getAvailableCoursesByTimeslot();
                break;
            case 6:
                stamp();
                break;
            case 7:
                showAllStampHistory();
                break;
            case 8:
                showAttendHistoryByDate();
                break;
            case 9:
                showAttendHistoryByCourse();
                break;
            default:
                System.out.println("Invalid choice");
            }
        }
    }

    private static void showRegisteredCourses() {
        System.out.println("Registered courses:");
        System.out.println(timetable);
    }

    private static void addCourse() {
        System.out.print("Enter course id: ");
        int id = Integer.parseInt(System.console().readLine());
        timetable.addCourse(id);
    }

    private static void removeCourse() {
        System.out.print("Enter course id: ");
        int id = Integer.parseInt(System.console().readLine());
        timetable.removeCourse(id);
    }

    private static void getAvailableCourses() {
        List<Course> courses = timetable.getAvailableCourses();
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private static void getAvailableCoursesByTimeslot() {
        System.out.print("Enter course day of week: ");
        int dayOfWeek = Integer.parseInt(System.console().readLine());
        System.out.print("Enter course begin period: ");
        int beginPeriod = Integer.parseInt(System.console().readLine());
        List<Course> courses = timetable.getAvailableCoursesByPeriod(dayOfWeek, beginPeriod);
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private static void stamp() {
        System.out.print("Enter timestamp (yyyy-MM-dd HH:mm:ss): ");
        String timestampString = System.console().readLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);
        timeCard.stamp(timestamp);
        StampStatus status = attendanceManager.getStampStatus(timestamp);
        System.out.println(status);
    }

    private static void showAllStampHistory() {
        List<StampStatus> attendStatuses = attendanceManager.getAllStampStatuses();
        for (StampStatus status : attendStatuses) {
            System.out.println(status);
        }
    }

    private static void showAttendHistoryByDate() {
        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateString = System.console().readLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        List<AttendStatus> attendStatuses = attendanceManager.getAttendStatusesByDate(date);
        for (AttendStatus status : attendStatuses) {
            System.out.println(status);
        }
    }

    private static void showAttendHistoryByCourse() {
        System.out.print("Enter course id: ");
        int id = Integer.parseInt(System.console().readLine());
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
