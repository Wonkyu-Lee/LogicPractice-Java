import java.util.Stack;

public class PreorderOfBst {
    static boolean isPreorderOfBst(int[] preorder) {
        int lowerBound = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();

        for (int each : preorder) {
            if (each <= lowerBound) {
                return false;
            }

            while (!stack.isEmpty() && stack.peek() < each) {
                lowerBound = stack.pop();
            }

            stack.push(each);
        }

        return true;
    }

    public static void main(String[] args) {
        int[] preorder = {5, 3, 2, 1, 4, 7, 6, 7, 9, 8};
        System.out.println(isPreorderOfBst(preorder));
    }
}