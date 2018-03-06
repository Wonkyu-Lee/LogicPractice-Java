import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AllKSizedSetsWithTargetSum {
    List<List<Integer>> findTargetSum(int[] arr, int size, int k) {
        List<List<Integer>> result = new LinkedList<>();
        Arrays.sort(arr);
        findTargetSum(arr, 0, arr.length - 1, size, k, result);
        return result;
    }

    void findTargetSum(int[] arr, int s, int e, int size, int k, List<List<Integer>> result) {
        if (size < 2)
            return;

        if (size == 2) {
            int l = s;
            int r = e;
            while (l < r) {
                int sum = arr[l] + arr[r];
                if (sum == k) {
                    // 중복 체크하려면 마지막에 넣은거랑 비교해서 같으면 건너 뛰어야 한다.
                    List<Integer> pair = new ArrayList<>(2);
                    pair.add(arr[l]);
                    pair.add(arr[r]);
                    result.add(pair);
                    ++l;
                    --r;
                } else if (sum < k) {
                        ++l;
                } else {
                        --r;
                }
            }

            return;
        }

        for (int i = s; i <= e - size + 1; ++i) {
            List<List<Integer>> partialResult = new LinkedList<>();
            findTargetSum(arr, i + 1, e, size - 1, k - arr[i], partialResult);
            for (List<Integer> each : partialResult) {
                each.add(arr[i]);
            }
            result.addAll(partialResult);
        }
    }

    AllKSizedSetsWithTargetSum() {
        int[] array = {1, 2, 4, 4, 45, 6, 10, -8, 12};
        List<List<Integer>> result = findTargetSum(array, 4, 16);

    }
}
