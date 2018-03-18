import java.util.HashSet;
import java.util.Set;

public class FindTripletsWithZeroSum {

    static boolean hasPairSum(int[] A, int s, int e, int targetSum) {
        Set<Integer> comps = new HashSet<>();
        for (int i = s; i <= e; ++i) {
            if (comps.contains(targetSum - A[i])) {
                return true;
            } else {
                comps.add(A[i]);
            }
        }
        return false;
    }

    static boolean hasTripletsWithZeroSum(int[] A) {
        for (int i = 0; i <= A.length - 3; ++i) {
            if (hasPairSum(A, i + 1, A.length - 1, -A[i]))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] A = {0, -1, 2, -3, 1};
        System.out.println(hasTripletsWithZeroSum(A));
    }
}
