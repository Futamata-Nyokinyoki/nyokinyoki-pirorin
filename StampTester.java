import java.util.ArrayList;
import java.util.Scanner;

public class StampTester {
    public static void main(String[] args) {

        Scanner stdIn = new Scanner(System.in);
        TimeCard tc = TimeCard.getInstance();
        while (true) {
            System.out.println("打刻しますか?");
            System.out.println("はい...1/いいえ...0");
            System.out.print(":");
            int x = stdIn.nextInt();
            if (x == 1) {
                tc.stamp2();
            }

            System.out.println("打刻履歴を確認しますか。");
            System.out.println("はい...1/いいえ...0");
            System.out.print(":");
            int y = stdIn.nextInt();
            if (y == 1) {
                for (int i = 0; i < tc.getCardLength(); i++) {
                    tc.getCard(i);
                }
            }

            System.out.println("終わりますか:");
            System.out.println("はい...1/いいえ...0");
            int f = stdIn.nextInt();
            if (f == 1)
                break;

        }

    }
}