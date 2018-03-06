public class AlgorithmSelect {

    static void swap(int[] A, int i, int j) {
        int t = A[i];
        A[i] = A[j];
        A[j] = t;
    }

    /**
     * A:   1, 2, 0, 3, 4, 5, 6
     *                        ^
     * i:         ^
     * j:                     ^
     *
     *
     */
    static int partition(int[] A, int start, int end) {
        int x = A[end];
        int i = start - 1;
        int j = start;

        while (j < end) {
            if (A[j] > x) {
                ++j;
            } else {
                swap(A, i + 1, j);
                ++i;
                ++j;
            }
        }

        swap(A, i + 1, end);
        return i + 1;
    }

    /**
     *  kth in [start, end]
     *  kth = 5
     *  A: 5, 3, 1, 2, 4, 0, 6
     *     2, 0, 1, 3, 6, 4, 5
     *              ^
     * TODO: 에러로 -1을 리턴.
     */
    static int select(int[] A, int kth, int start, int end) {
        if (start == end)
            return kth == 0 ? A[start] : -1;

        int mid = partition(A, start, end);

        if (start + kth < mid) {
            return select(A, kth, start, mid - 1);
        } else if (mid < start + kth) {
            return select(A, start + kth - mid - 1, mid + 1, end);
        } else {
            return A[mid];
        }
    }

    static int select(int A[], int kth) {
        return select(A, kth, 0, A.length - 1);
    }


    public static void main(String[] args) {
        int[] A = {50, 30, 10, 20, 40, 0, 60};

        for (int i = 0; i < A.length; ++i) {
            System.out.println(select(A, i));

        }
    }
}
