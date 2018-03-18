/**
 * https://practice.geeksforgeeks.org/problems/root-to-leaf-paths-sum/1
 */

public class SumOfRootToLeafPaths {
    static class Node {
        int value;
        Node left;
        Node right;
    }

    static int solve(Node node) {
        return solve(node, 0);
    }

    static int solve(Node node, int runningSum) {
        if (node == null) return 0;

        int newSum = 10 * runningSum + node.value;
        if (node.left == null && node.right == null) {
            return newSum;
        }

        return solve(node.left, newSum) + solve(node.right, newSum);
    }

    public static void main(String[] args) {

    }
}
