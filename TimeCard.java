import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class TimeCard {

    // singleton
    private ArrayList<TimeStamp> timecard = new ArrayList<TimeStamp>();
    private static TimeCard tc = new TimeCard();

    private TimeCard() {
    }

    public void stamp2() {
        TimeStamp t = new TimeStamp();
        t.stamp();
        addCard(t);
    }

    public static TimeCard getInstance() {
        return tc;
    }

    // リストの中身を表示する
    public void getCard(int i) {
        System.out.println(timecard.get(i).getTimes(0) + " " + timecard.get(i).getTimes(1) + " "
                + timecard.get(i).getTimes(2) + " " + timecard.get(i).getTimes(3));
    }

    // リストのサイズを返す
    public int getCardLength() {
        return timecard.size();
    }

    // リストに打刻データを追加
    public void addCard(TimeStamp ts) {
        timecard.add(ts);
    }

}