public class TimeSlot {
    private final int id;
    private final int courseId;
    private final int dayOfWeek;
    private final int beginPeriod;
    private final int endPeriod;

    public TimeSlot(int id, int courseId, int dayOfWeek, int beginPeriod, int endPeriod) {
        if (endPeriod < beginPeriod) {
            throw new IllegalArgumentException("End period must be greater than or equal to begin period.");
        }

        this.id = id;
        this.courseId = courseId;
        this.dayOfWeek = dayOfWeek;
        this.beginPeriod = beginPeriod;
        this.endPeriod = endPeriod;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getBeginPeriod() {
        return beginPeriod;
    }

    public int getEndPeriod() {
        return endPeriod;
    }

    public int getDuration() {
        return endPeriod - beginPeriod + 1;
    }

    public boolean overlapsWith(TimeSlot other) {
        if (dayOfWeek != other.dayOfWeek) {
            return false;
        }

        return beginPeriod <= other.endPeriod && other.beginPeriod <= endPeriod;
    }

    @Override
    public String toString() {
        return String.format("TimeSlot {id=%d, courseId=%d, dayOfWeek=%d, beginPeriod=%d, endPeriod=%d}", id, courseId,
                dayOfWeek, beginPeriod, endPeriod);
    }
}
