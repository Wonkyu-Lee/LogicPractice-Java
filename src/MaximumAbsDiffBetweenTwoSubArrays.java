// https://www.geeksforgeeks.org/maximum-absolute-difference-between-sum-of-two-contiguous-sub-arrays/
// https://ide.geeksforgeeks.org/mDBBxuvsTD
public class MaximumAbsDiffBetweenTwoSubArrays {
    static int[] getMaxSums(int[] A, boolean leftToRight) {
        int[] maxSums = new int[A.length];
        int maxSoFar = Integer.MIN_VALUE;
        int maxCur = 0;

        if (leftToRight) {
            for (int i = 0; i < A.length; ++i) {
                maxCur = Math.max(maxCur + A[i], A[i]);
                maxSoFar = Math.max(maxCur, maxSoFar);
                maxSums[i] = maxSoFar;
            }
        } else {
            for (int i = A.length - 1; i >= 0; --i) {
                maxCur = Math.max(maxCur + A[i], A[i]);
                maxSoFar = Math.max(maxCur, maxSoFar);
                maxSums[i] = maxSoFar;
            }
        }

        return maxSums;
    }

    static int[] getMinSums(int[] A, boolean leftToRight) {
        int[] minSums = new int[A.length];
        int minSoFar = Integer.MAX_VALUE;
        int minCur = 0;

        if (leftToRight) {
            for (int i = 0; i < A.length; ++i) {
                minCur = Math.min(minCur + A[i], A[i]);
                minSoFar = Math.min(minCur, minSoFar);
                minSums[i] = minSoFar;
            }
        } else {
            for (int i = A.length - 1; i >= 0; --i) {
                minCur = Math.min(minCur + A[i], A[i]);
                minSoFar = Math.min(minCur, minSoFar);
                minSums[i] = minSoFar;
            }
        }

        return minSums;
    }

    static int getMaxAbsDiff(int[] A) {
        int[] lMaxSums = getMaxSums(A, true);
        int[] lMinSums = getMinSums(A, true);
        int[] rMaxSums = getMaxSums(A, false);
        int[] rMinSums = getMinSums(A, false);

        int maxDiff = 0;
        for (int i = 0; i <= A.length - 2; ++i) {
            int lMinSum = lMinSums[i];
            int lMaxSum = lMaxSums[i];
            int rMinSum = rMinSums[i + 1];
            int rMaxSum = rMaxSums[i + 1];
            int diff = Math.max(Math.abs(lMinSum - rMaxSum), Math.abs(lMaxSum - rMinSum));
            maxDiff = Math.max(maxDiff, diff);
        }

        return maxDiff;
    }

    public static void main(String[] args) {
        int[] A = {-567, 657, 568, 457, 169, 95, 820};
        System.out.println(getMaxAbsDiff(A));
    }
}
