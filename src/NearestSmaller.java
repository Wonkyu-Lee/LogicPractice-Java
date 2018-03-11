import java.util.Arrays;
import java.util.Stack;

public class NearestSmaller {

    /**
     *  0  1  2  3  4  5  6
     *  9  8  3  5  7  4  6
     * -1 -1 -1  2  3  2  5
     */
    static int[] findLeftSmallers(int[] h) {
        int[] result = new int[h.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < h.length; ++i) {
            while (!stack.isEmpty() && h[stack.peek()] >= h[i]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        return result;
    }

    /**
     *  0  1  2  3  4  5  6
     *  9  8  3  5  7  4  6
     * -1  0  1  1  1  3  4
     */
    static int[] findLeftGreaters(int[] h) {
        int[] result = new int[h.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < h.length; ++i) {
            while (!stack.isEmpty() && h[stack.peek()] <= h[i]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        return result;
    }

    /**
     *  0  1  2  3  4  5  6
     *  9  8  3  5  7  4  6
     *  1  2  7  5  5  7  7
     */
    static int[] findRightSmallers(int[] h) {
        int[] result = new int[h.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = h.length - 1; i >= 0; --i) {
            while (!stack.isEmpty() && h[stack.peek()] >= h[i]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? h.length : stack.peek();
            stack.push(i);
        }

        return result;
    }

    /**
     *  0  1  2  3  4  5  6
     *  9  8  3  5  7  4  6
     *  7  7  3  4  7  6  7
     */
    static int[] findRightGreaters(int[] h) {
        int[] result = new int[h.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = h.length - 1; i >= 0; --i) {
            while (!stack.isEmpty() && h[stack.peek()] <= h[i]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? h.length : stack.peek();
            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] h = {9, 8, 3, 5, 7, 4, 6};
        System.out.printf("Left smallers: %s\n", Arrays.toString(findLeftSmallers(h)));
        System.out.printf("Left greaters: %s\n", Arrays.toString(findLeftGreaters(h)));
        System.out.printf("Right smallers: %s\n", Arrays.toString(findRightSmallers(h)));
        System.out.printf("Right greaters: %s\n", Arrays.toString(findRightGreaters(h)));
    }
}
