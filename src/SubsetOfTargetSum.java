import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SubsetOfTargetSum {

    static List<List<Integer>> allSubsetsOfTargetSum(int[] array, int targetSum, int size) {
        LinkedList<List<Integer>> subsets = new LinkedList<>();
        allSubsetsOfTargetSum(array, 0 , array.length - 1, targetSum, size, subsets);
        return subsets;
    }

    static void allSubsetsOfTargetSum(int[] array, int s, int e, int targetSum, int size, LinkedList<List<Integer>> subsets) {
        if (size == 0) {
            return;
        }

        if (size == 1) {
            for (int i = s; i <= e; ++i) {
                if (array[i] == targetSum) {
                    LinkedList<Integer> subset = new LinkedList<>();
                    subset.add(array[i]);
                    subsets.add(subset);
                    return;
                }
            }

            return;
        }

        if (size == 2) {
            HashSet<Integer> compliments = new HashSet<>();
            HashSet<Integer> processed = new HashSet<>(); // remove redundancy
            for (int i = s; i <= e; ++i) {
                if (processed.contains(array[i])) {
                    continue;
                }

                int compliment = targetSum - array[i];
                if (compliments.contains(compliment)) {
                    processed.add(array[i]);

                    LinkedList<Integer> subset = new LinkedList<>();
                    subset.add(compliment);
                    subset.add(array[i]);
                    subsets.add(subset);
                } else {
                    compliments.add(array[i]);
                }
            }

            return;
        }

        {
            HashSet<Integer> processed = new HashSet<>(); // remove redundancy
            for (int i = s; i <= e - size + 1; ++i) {
                if (processed.contains(array[i])) {
                    continue;
                } else {
                    processed.add(array[i]);
                }

                int first = array[i];
                LinkedList<List<Integer>> partialSubsets = new LinkedList<>();
                allSubsetsOfTargetSum(array, i + 1, e, targetSum - first, size - 1, partialSubsets);
                for (List<Integer> each : partialSubsets) {
                    each.add(first);
                }
                subsets.addAll(partialSubsets);
            }
        }
    }

    public static void main(String[] args) {
        int array[] = {1, 7, 2, 4, 5, 3, 6, 3, 8, 1, 4};
        Arrays.sort(array); // 이거 안 하면 중복됨 -_-;;
        List<List<Integer>> subsets = allSubsetsOfTargetSum(array, 9, 3);

        for (List<Integer> subset : subsets) {
            System.out.println(subset);
        }
    }
}
