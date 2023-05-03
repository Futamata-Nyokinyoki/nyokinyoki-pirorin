public class Course {
    private final int courseId;
    private final String courseName;
    private final int dayOfWeek;
    private final int classPeriod;

    public Course(int courseId, String courseName, int dayOfWeek, int classPeriod) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.dayOfWeek = dayOfWeek;
        this.classPeriod = classPeriod;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getClassPeriod() {
        return classPeriod;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + courseId + ", name=" + courseName + ", dayOfWeek=" + dayOfWeek + ", classPriod="
                + classPeriod + '}';
    }
}
