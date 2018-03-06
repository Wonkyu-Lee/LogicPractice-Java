public class BulbsManagement {
    /**
     * For N light bulbs , implement two methods
     * I. isOn(int i) - find if the ith bulb is on or off.
     * II. toggle(int i, int j) - i <= j. Switch state (switch on if it's off, turn off if it's on) of every bulb in range i to j.
     * All bulbs are off initially.
     *
     *
     * Clarification
     * 전등 개수가 32개 이하냐?
     * bit operation 쓰길 바라냐?
     */

    int bulbs = 0x0;

    boolean isOn(int i) {
        int mask = 1 << i;
        return (bulbs & mask) != 0;
    }

    void toggle(int i, int j) {
        // 00111000
        // 76543210 (3~5)
        int n = j - i + 1;
        int mask;

        if (n == 32) {
            mask = -1;
        } else {
            mask = (1 << (n));
            mask -= 1;
            mask = mask << i;
        }

        bulbs = bulbs ^ mask;
    }

    BulbsManagement(int bulbCount) {
        if (bulbCount < 0 || 32 < bulbCount) {
            throw new IllegalArgumentException();
        }
    }


    public static void main(String[] args) {
        BulbsManagement bm = new BulbsManagement(8);
        bm.toggle(3, 5);

        for (int i = 0; i < 8; ++i) {
            System.out.printf("Bulg[%d] = %s\n", i, (bm.isOn(i) ? "On" : "Off"));
        }

        System.out.println();

        bm.toggle(2, 4);
        for (int i = 0; i < 8; ++i) {
            System.out.printf("Bulg[%d] = %s\n", i, (bm.isOn(i) ? "On" : "Off"));
        }
    }
}
