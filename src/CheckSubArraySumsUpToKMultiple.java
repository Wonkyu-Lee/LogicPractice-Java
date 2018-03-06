import java.util.LinkedList;

public class CheckSubArraySumsUpToKMultiple {
    /**
     * Google
     * Given a list of non-negative numbers and a target integer k,
     * write a function to check if the array has a continuous subarray of size at least 2 that sums up to the multiple of k, that is, sums up to n*k where n is also an integer.
     *
     * k = 3 -> 3, 6, 9, 12
     * 2  5  4 3 1 7 5 3 2 4 1 9
     2  7 11
     **/

    static boolean hasSubarraySumsUpToKMultiple(int[] A, int k) {
        if (A.length < 2) return false;

        // O(n)
        int[] accum = new int[A.length];
        accum[0] = A[0];
        for (int i = 1; i < accum.length; ++i) {
            accum[i] = accum[i - 1] + A[i];
        }

        // O(n^2)
        for (int n = 2; n <= A.length; ++n) {
            for (int i = 0; i <= A.length - n; ++i) {
                int accum1 = (i == 0) ? 0 : accum[i - 1];
                int accum2 = accum[i + n - 1];
                int sum = accum2 - accum1;
                if (sum % k == 0) {

                    LinkedList<Integer> list = new LinkedList<>();
                    for (int j = i; j <= i + n - 1; ++j) {
                        list.add(A[j]);
                    }

                    System.out.printf("K-multiple: %d, Subarray: %s", sum, list.toString());

                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[] A = {2, 5, 4, 3, 1, 7, 5, 3, 2, 4, 1, 9};
        hasSubarraySumsUpToKMultiple(A, 15);
    }
}
