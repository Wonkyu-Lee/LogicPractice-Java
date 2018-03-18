import java.util.Stack;

/**
 * https://practice.geeksforgeeks.org/problems/merge-two-bst-s/1
 * https://ide.geeksforgeeks.org/KyIIvFNaG5
 */
public class MergeTwoBsts {
    static class Node {
        int data;
        Node left;
        Node right;
        Node(int data) {
            this.data = data;
        }
    }

    static Node insert(Node node, int value) {
        if (node == null) return new Node(value);
        if (value <= node.data) {
            node.left = insert(node.left, value);
        } else {
            node.right = insert(node.right, value);
        }
        return node;
    }

    static class Inorder {
        Stack<Node> stack = new Stack<>();

        Inorder(Node tree) {
            Node current = tree;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        boolean hasNext() {
            return !stack.isEmpty();
        }

        int peek() {
            Node current = stack.peek();
            return current.data;
        }

        int next() {
            Node current = stack.pop();
            int result = current.data;
            current = current.right;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            return result;
        }
    }

    public static void merge(Node root1, Node root2) {
        Inorder order1 = new Inorder(root1);
        Inorder order2 = new Inorder(root2);

        while (order1.hasNext() && order2.hasNext()) {
            int v1 = order1.peek();
            int v2 = order2.peek();
            if (v1 < v2) {
                System.out.print(v1 + " ");
                order1.next();
            } else {
                System.out.print(v2 + " ");
                order2.next();
            }
        }

        while (order1.hasNext()) {
            System.out.print(order1.next() + " ");
        }

        while (order2.hasNext()) {
            System.out.print(order2.next() + " ");
        }
    }

    public static void main(String[] args) {
        int[] A = {10, 6, 16, 4, 8, 12, 8, 2, 14};
        int[] B = {5, 3, 15, 1, 13, 17, 11, 7, 9};
        Node treeA = null;
        for (int each : A) { treeA = insert(treeA, each); }

        Node treeB = null;
        for (int each : B) { treeB = insert(treeB, each); }

        merge(treeA, treeB);
    }
}
