import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class AlgorithmSort {

    static int getIndexForMaxValue(int[] A, int s, int e) {
        int indexAtMax = 0;
        int maxValue = A[0];
        for (int i = s + 1; i <= e; ++i) {
            if (A[i] > maxValue) {
                indexAtMax = i;
            }
        }
        return indexAtMax;
    }

    static void swap(int[] A, int i, int j) {
        int t = A[i];
        A[i] = A[j];
        A[j] = t;
    }

    static void selectionSort(int[] A) {
        for (int size = A.length; 1 < size; --size) {
            int index = getIndexForMaxValue(A, 0, size - 1);
            swap(A, index, size - 1);
        }
    }

    /**
     * index: 0 1 2 3 4 5 6 7
     *     A: 6 7 3 4 3 9 3 1
     *     8: 6 3 4 3 7 3 1 9
     *     7: 3 4 3 6 3 1 7 9
     *     6: 3 3 4 3 1 6 7 9
     *     5: 3 3 3 1 4 6 7 9
     *     4: 3 3 1 3 4 6 7 9
     *     3: 3 1 3 3 4 6 7 9
     *     2: 1 3 3 3 4 6 7 9
     */
    static void bubbleSort(int[] A) {
        for (int size = A.length; 1 < size; --size) {
            boolean swapped = false;
            for (int i = 0; i < size - 1; ++i) {
                if (A[i] > A[i + 1]) {
                    swap(A, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                return;
            }
        }
    }

    /**
     * index:   0 1 2 3 4 5 6 7
     *     A:   6 7 3 4 3 9 3 1
     *     A:   3 4 6 7 3 9 3 1
     *     i:           ^
     *     j:         ^
     *   j+1:           ^
     *
     *        ...
     */
    static void insertionSort(int[] A) {
        for (int i = 1; i < A.length; ++i) {
            int j = i - 1;
            while (0 <= j && A[j] > A[j + 1]) { // TODO: j가 0 이상임이 먼저 체크돼야 한다.
                swap(A, j, j + 1);
                --j;
            }
        }
    }


    /**
     * max heap -> increasing order
     * min heap -> decreasing order
     * k's parent = (k - 1) / 2
     * k's left child = 2 * k + 1
     * k's right child = 2 * k + 2
     */
    static void heapify(int[] A, int size, int k) {
        int l = 2 * k + 1;
        int r = 2 * k + 2;
        int greater = -1;

        if (r < size) {
            if (A[l] >= A[r]) {
                greater = l;
            } else {
                greater = r;
            }
        } else if (l < size) {
            greater = l;
        }

        if (greater == -1) {
            return;
        }

        if (A[k] < A[greater]) {
            swap(A, k, greater);
            heapify(A, size, greater);
        }
    }

    static void buildHeap(int[] A) {
        int last = A.length - 1;
        int parentOfLast = (last - 1) / 2;
        for (int i = parentOfLast; 0 <= i; --i) {
            heapify(A, A.length, i);
        }
    }

    static void heapSort(int[] A) {
        buildHeap(A);

        for (int size = A.length; 1 < size; --size) {
            swap(A, 0, size - 1);
            heapify(A, size - 1, 0);
        }
    }

    static void countSort(int[] A, int k) {
        int[] C = new int[k + 1];
        for (int i = 0; i < A.length; ++i) {
            C[A[i]]++;
        }

        for (int i = 1; i < k + 1; ++i) { // TODO: index k + 1 쓰는 것 주의
            C[i] += C[i - 1];
        }

        int[] B = new int[A.length];
        for (int i = A.length - 1; 0 <= i; --i) {
            int slot = A[i];
            B[C[slot] - 1] = A[i];
            C[slot]--;
        }

        for (int i = 0; i < B.length; ++i) {
            A[i] = B[i];
        }
    }

    /**
     * radix: 10
     *     k: 3 2 1 0
     *     x: 9 7 3 2
     */
    static int getKthDigit(int x, int radix, int k) {
        for (int i = 0; i < k; ++i) {
            x /= radix;
            if (x == 0)
                return 0;
        }

        return x % radix;
    }

    static void sortByKthDigit(int[] A, int radix, int k) {
        ArrayList<LinkedList<Integer>> slots = new ArrayList<>();
        for (int i = 0; i < radix; ++i) {
            slots.add(new LinkedList<>());
        }

        for (int i = 0; i < A.length; ++i) {
            int kthDigit = getKthDigit(A[i], radix, k);
            slots.get(kthDigit).add(A[i]); // TODO: add 대신 push 써서 제대로 안 나왔었다.
        }

        int j = 0;
        for (int i = 0; i < radix; ++i) {
            for (int e : slots.get(i)) {
                A[j++] = e;
            }
        }
    }

    static void radixSort(int[] A, int radix, int digits) {
        for (int k = 0; k < digits; ++k) {
            sortByKthDigit(A, radix, k);
        }
    }

    static void mergeSort(int[] A) {
        mergeSort(A, 0, A.length - 1);
    }

    static void mergeSort(int[] A, int start, int end) {
        if (start == end) { // TODO: 등호를 써야 함. start > end 로 썼더니 종료되지 않음.
            return;
        }

        int middle = (start + end) / 2;
        mergeSort(A, start, middle);
        mergeSort(A, middle + 1, end);
        merge(A, start, middle, end);
    }

    static void merge(int[] A, int start, int middle, int end) {
        int[] B = new int[end - start + 1];
        {
            int i = start;
            int j = middle + 1;
            int k = 0;

            while (i <= middle && j <= end) {
                if (A[i] <= A[j]) {
                    B[k++] = A[i++];
                } else {
                    B[k++] = A[j++];
                }
            }

            while (i <= middle) {
                B[k++] = A[i++];
            }

            while (j <= end) {
                B[k++] = A[j++];
            }
        }

        for (int i = 0; i < B.length; ++i) {
            A[start + i] = B[i];
        }
    }

    static void quickSort(int[] A) {
        quickSort(A, 0, A.length - 1);
    }

    static void quickSort(int[] A, int start, int end) {
        if (start > end)
            return;

        Middle middle = partition(A, start, end);
        quickSort(A, start, middle.lower - 1);
        quickSort(A, middle.upper + 1, end);
    }

    static class Middle {
        final int lower;
        final int upper;
        Middle(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

    static Random random = new Random();

    static Middle partition(int[] A, int start, int end) {
        int pivot = random.nextInt(end - start + 1) + start;
        int x = A[pivot];

        int l = start;
        int u = end;
        int i = l;

        while (i <= u) {
            if (A[i] < x) {
                swap(A, l++, i++);
            } else if (A[i] > x) {
                swap(A, i, u--);;
            } else {
                ++i;
            }
        }

        return new Middle(l, u);
    }

    public static void main(String[] args) {
        int array[] = {6, 7, 3, 4, 3, 9, 3, 1};

        {
            int A[] = Arrays.copyOf(array, array.length);
            selectionSort(A);
            System.out.print("Selection sort: ");
            System.out.println(Arrays.toString(A));
        }

        {
            int A[] = Arrays.copyOf(array, array.length);
            bubbleSort(A);
            System.out.print("Bubble sort: ");
            System.out.println(Arrays.toString(A));
        }

        {
            int A[] = Arrays.copyOf(array, array.length);
            insertionSort(A);
            System.out.print("Insertion sort: ");
            System.out.println(Arrays.toString(A));
        }

        {
            int A[] = Arrays.copyOf(array, array.length);
            heapSort(A);
            System.out.print("Heap sort: ");
            System.out.println(Arrays.toString(A));
        }

        {
            int A[] = Arrays.copyOf(array, array.length);
            countSort(A, 10);
            System.out.print("Count sort: ");
            System.out.println(Arrays.toString(A));
        }

        {
            int A[] = Arrays.copyOf(array, array.length);
            radixSort(A, 3, 5);
            System.out.print("Radix sort: ");
            System.out.println(Arrays.toString(A));
        }

        {
            int A[] = Arrays.copyOf(array, array.length);
            mergeSort(A);
            System.out.print("Merge sort: ");
            System.out.println(Arrays.toString(A));
        }

        {
            int A[] = Arrays.copyOf(array, array.length);
            quickSort(A);
            System.out.print("Quick sort: ");
            System.out.println(Arrays.toString(A));
        }
    }
}