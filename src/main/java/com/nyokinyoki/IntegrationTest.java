package com.nyokinyoki;

import java.util.List;
import java.time.*;
import java.time.format.*;;

public class IntegrationTest {

    private static TimeTable timeTable = new TimeTable(new TimeTableDAO(), new CourseDAO());
    private static TimeCard timeCard = new TimeCard(new TimestampDAO());
    private static AttendManager attendanceManager = new AttendManager(timeTable, timeCard);

    public static void main(String[] args) {
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Show registered courses");
            System.out.println("2. Add course");
            System.out.println("3. Remove course");
            System.out.println("4. Get available courses");
            System.out.println("5. Get available courses by time slot");
            System.out.println("6. Stamp");
            System.out.println("7. ");
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
                getAvailableCoursesByTimeSlot();
                break;
            case 6:
                stamp();
                break;
            default:
                System.out.println("Invalid choice");
            }
        }
    }

    private static void showRegisteredCourses() {
        System.out.println("Registered courses:");
        System.out.println(timeTable);
    }

    private static void addCourse() {
        System.out.print("Enter course id: ");
        int id = Integer.parseInt(System.console().readLine());
        timeTable.addCourse(id);
    }

    private static void removeCourse() {
        System.out.print("Enter course id: ");
        int id = Integer.parseInt(System.console().readLine());
        timeTable.removeCourse(id);
    }

    private static void getAvailableCourses() {
        List<Course> courses = timeTable.getAvailableCourses();
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private static void getAvailableCoursesByTimeSlot() {
        System.out.print("Enter course day of week: ");
        int dayOfWeek = Integer.parseInt(System.console().readLine());
        System.out.print("Enter course begin period: ");
        int beginPeriod = Integer.parseInt(System.console().readLine());
        List<Course> courses = timeTable.getAvailableCoursesByPeriod(dayOfWeek, beginPeriod);
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private static void stamp() {
        System.out.println("Enter timestamp (yyyy-MM-dd HH:mm:ss): ");
        String timestampString = System.console().readLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);
        timeCard.stamp(timestamp);
        StampStatus status = attendanceManager.getStampStatus(timestamp);
        System.out.println(status);
    }

}
