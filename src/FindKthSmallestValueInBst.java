/**
 * https://practice.geeksforgeeks.org/problems/find-k-th-smallest-element-in-bst/1
 */
public class FindKthSmallestValueInBst {
    static class Node {
        int value;
        Node left;
        Node right;
        Node(int value) {
            this.value = value;
        }
    }

    static class Counter {
        int count;
        int value;
    }

    static int kth(Node root, int k) {
        Counter counter = new Counter();
        if (kth(root, k, counter)) {
            return counter.value;
        }

        return -1;
    }

    static boolean kth(Node root, int k, Counter counter) {
        if (root == null) return false;

        if (kth(root.left, k, counter)) {
            return true;
        }

        if (counter.count == k) {
            counter.value = root.value;
            return true;
        }

        counter.count++;

        return kth(root.right, k, counter);
    }

    static Node insert(Node root, int value) {
        if (root == null)
            return new Node(value);

        if (value <= root.value) {
            root.left = insert(root.left, value);
        } else {
            root.right = insert(root.right, value);
        }

        return root;
    }

    static void inorderTraverse(Node root) {
        if (root == null) return;

        if (root.left != null)
            inorderTraverse(root.left);

        System.out.print(root.value + " ");

        if (root.right != null)
            inorderTraverse(root.right);
    }

    public static void main(String[] args) {
        int[] values = {20, 8, 4, 22, 12};
        Node tree = null;
        for (int each : values) {
            tree = insert(tree, each);
        }

        inorderTraverse(tree);
        System.out.println();

        for (int i = 0; i < values.length + 3; ++i) {
            System.out.println(i + ": " + kth(tree, i));
        }
    }
}