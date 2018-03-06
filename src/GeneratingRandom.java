import java.util.Random;

public class GeneratingRandom {

    static Random random = new Random();

    static int rand7() {
        return random.nextInt(7);
    }

    static int rand8() {
        while (true) {
            int x = rand7() * 7 + rand7();
            if (x < 48) {
                return x % 8;
            }
        }
    }

    static int rand9() {
        while (true) {
            int x = rand7() * 7 + rand7();
            if (x < 45) {
                return x % 9;
            }
        }
    }

    public static void main(String[] args) {
        int[] counts = new int[9];
        for (int i = 0; i < 10000; ++i) {
            counts[rand9()]++;
        }

        for (int i = 0; i < counts.length; ++i) {
            System.out.printf("[%d] %d", i, counts[i]);
        }
    }
}