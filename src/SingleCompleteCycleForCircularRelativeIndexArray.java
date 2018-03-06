public class SingleCompleteCycleForCircularRelativeIndexArray {
    static int move(int[] A, int cur) {
        cur = (cur + A[cur]) % A.length;
        if (cur < 0)
            cur += A.length;
        return cur;
    }

    static boolean hasSingleCompleteCycle(int[] A) {
        int cur = 0;
        for (int i = 0; i < A.length; ++i) {
            cur = move(A, cur);
            if (cur == 0 && i < A.length - 1) {
                return false;
            }
        }
        return cur == 0;
    }

    public static void main(String[] args) {
        int[] A = {2, -1, 2};
        System.out.println(hasSingleCompleteCycle(A));
    }
}
