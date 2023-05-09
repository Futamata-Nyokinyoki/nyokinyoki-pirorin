import java.sql.Timestamp;

public sealed interface Stamp permits TimeStamp {
    void stamp();

    Timestamp getTime();

    int getTimes(int i);

    int[] convertToInt(Timestamp ts);

    int getYoubi(int y, int m, int d);

}