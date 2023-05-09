import java.util.List;

public class TimeTableIntegrationTest {
    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        while (true) {
            System.out.println("1. Show registered courses");
            System.out.println("2. Add course");
            System.out.println("3. Remove course");
            System.out.println("4. Get available courses");
            System.out.println("5. Get available courses by time slot");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(System.console().readLine());
            List<Course> courses;

            switch (choice) {
            case 1:
                System.out.println("Registered courses:");
                System.out.println(timeTable);
                break;
            case 2:
                System.out.print("Enter course id: ");
                int id = Integer.parseInt(System.console().readLine());
                timeTable.addCourse(id);
                break;
            case 3:
                System.out.print("Enter course id: ");
                id = Integer.parseInt(System.console().readLine());
                timeTable.removeCourse(id);
                break;
            case 4:
                courses = timeTable.getAvailableCourses();
                for (Course c : courses) {
                    System.out.println(c);
                }
                break;
            case 5:
                System.out.print("Enter course day of week: ");
                int dayOfWeek = Integer.parseInt(System.console().readLine());
                System.out.print("Enter course begin period: ");
                int beginPeriod = Integer.parseInt(System.console().readLine());
                courses = timeTable.getAvailableCoursesByTimeSlot(dayOfWeek, beginPeriod);
                for (Course c : courses) {
                    System.out.println(c);
                }
                break;
            case 6:
                System.exit(0);
            default:
                System.out.println("Invalid choice");
            }
        }
    }
}
