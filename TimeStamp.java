import java.util.ArrayList;
import java.util.List;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

//Java 17　新機能　Sealedクラスで継承先を限定
public final class TimeStamp implements Stamp {

    private Timestamp timestamp;
    private String strYear, strMonth, strDay, strHour, strMinute;
    private int year, day, time, youbi;
    private int[] times;

    public TimeStamp() {
    }

    // 打刻をするメソッド
    // 現在時刻を取得して数値化する。

    public void stamp() {
        timestamp = new Timestamp(System.currentTimeMillis());
        times = convertToInt(timestamp);
        year = times[0];
        day = times[1];
        time = times[2];
        youbi = times[3];
        TimeStampDAO tsDAO = new TimeStampDAO();
        tsDAO.addDB(year, day, time, youbi);
    }

    // convertToInt関数にて使用

    public Timestamp getTime() {
        return timestamp;
    }

    // 年・月・日・時・分を取り出すのに使う
    // 引数iは上記の順番で0から4までで設定する

    public int getTimes(int i) {
        return times[i];
    }

    /* 以下のメソッドは他クラスでは直接使わない */

    // 現在時刻を年・日付・時間・曜日に分割して数値化する
    // 返り値は上記の順番で配列形式で返す
    // 曜日は、日曜を0として6までの整数

    public int[] convertToInt(Timestamp timestamp) {
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        strYear = sdfYear.format(timestamp.getTime());
        year = Integer.parseInt(strYear);
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        strMonth = sdfMonth.format(timestamp.getTime());
        int month = Integer.parseInt(strMonth);
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
        strDay = sdfDay.format(timestamp.getTime());
        day = Integer.parseInt(strDay);
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
        strHour = sdfHour.format(timestamp.getTime());
        int hour = Integer.parseInt(strHour);
        SimpleDateFormat sdfMinute = new SimpleDateFormat("mm");
        strMinute = sdfMinute.format(timestamp.getTime());
        int minute = Integer.parseInt(strMinute);

        youbi = getYoubi(year, month, day);
        time = hour * 100 + minute;
        day += 100 * month;

        int[] ret = { year, day, time, youbi };

        return ret;
    }

    // ツェラーの公式

    public int getYoubi(int year, int month, int day) {
        if (month == 1 || month == 2) {
            year--;
            month += 12;
        }
        return (year + year / 4 - year / 100 + year / 400 + (13 * month + 8) / 5 + day) % 7;
    }

}