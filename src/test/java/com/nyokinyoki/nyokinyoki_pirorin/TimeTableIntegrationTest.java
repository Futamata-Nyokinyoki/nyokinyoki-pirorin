package src.test.java.com.nyokinyoki.nyokinyoki_pirorin;

import java.util.List;

import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.Course;
import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.CourseDAO;
import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.TimeTable;
import src.main.java.com.nyokinyoki.nyokinyoki_pirorin.TimeTableDAO;

public class TimeTableIntegrationTest {

    private static TimeTable timeTable = new TimeTable(new TimeTableDAO(), new CourseDAO());

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Show registered courses");
            System.out.println("2. Add course");
            System.out.println("3. Remove course");
            System.out.println("4. Get available courses");
            System.out.println("5. Get available courses by time slot");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(System.console().readLine());

            switch (choice) {
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
                System.exit(0);
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
        List<Course> courses = timeTable.getAvailableCoursesByTimeSlot(dayOfWeek, beginPeriod);
        for (Course c : courses) {
            System.out.println(c);
        }
    }
}
