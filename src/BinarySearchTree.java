import java.util.LinkedList;
import java.util.Stack;

public class BinarySearchTree {

    static class Node {
        int key;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
        }
    }

    static Node insert(Node tree, int key) {
        if (tree == null) {
            return new Node(key);
        }

        if (key <= tree.key) {
            tree.left = insert(tree.left, key);
        } else {
            tree.right = insert(tree.right, key);
        }

        return tree;
    }

    static Node find(Node tree, int key) {
        if (tree == null) {
            return null;
        }

        if (key < tree.key) {
            return find(tree.left, key);
        } else if (tree.key < key) {
            return find(tree.right, key);
        } else {
            return tree;
        }
    }

    static class Found {
        Node parent;
        Node node;
        Found(Node parent, Node node) {
            this.parent = parent;
            this.node = node;
        }
    }

    static Found findWithParent(Node node, Node parent, int key) {
        if (node == null) {
            return null;
        }

        if (key < node.key) {
            return findWithParent(node.left, node, key);
        } else if (node.key < key) {
            return findWithParent(node.right, node, key);
        } else {
            return new Found(parent, node);
        }
    }

    static LinkedList<Node> findNodes(Node tree, int key) {
        LinkedList<Node> nodes = new LinkedList<>();

        Node curNode = tree;
        while (curNode != null) {
            Node found = find(curNode, key);
            if (found == null)
                break;

            nodes.add(found);
            curNode = found.left;
        }

        return nodes;
    }

    // TODO: remember!!!
    static Node removeNode(Node tree, Node parent, Node node) {
        if (tree == node) {
            return removeNode(node);
        }

        if (parent.left == node) {
            parent.left = removeNode(node);
        } else {
            parent.right = removeNode(node);
        }
        return tree;
    }

    static Node removeNode(Node node) {
        if (node.left == null && node.right == null) {
            return null;
        } else if (node.left != null && node.right == null) {
            return node.left;
        } else if (node.left == null && node.right != null) {
            return node.right;
        } else {
            Node next = node.right;
            Node parentOfNext = null;
            while (next.left != null) {
                parentOfNext = next;
                next = next.left;
            }

            node.key = next.key;
            if (next == node.right) {
                node.right = next.right;
            } else {
                parentOfNext.left = next.right;
            }

            return node;
        }
    }

    static Node removeKey(Node tree, int key) {
        Found found;
        while (true) {
            found = findWithParent(tree, null, key);
            if (found == null)
                return tree;

            tree = removeNode(tree, found.parent, found.node);
        }
    }

    interface Visitor {
        void visit(Node node);
    }

    static void inorderTraverse(Node tree, Visitor visitor) {
        if (tree == null) return;

        if (tree.left != null) inorderTraverse(tree.left, visitor);
        visitor.visit(tree);
        if (tree.right != null) inorderTraverse(tree.right, visitor);
    }

    static class InorderTraversal {
        final Stack<Node> stack = new Stack<>();

        InorderTraversal(Node tree) {
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
            return stack.peek().key;
        }

        int next() {
            Node current = stack.pop();
            int key = current.key;
            current = current.right;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            return key;
        }
    }

    static class PreorderTraversal {
        final Stack<Node> stack = new Stack<>();

        PreorderTraversal(Node tree) {
            stack.push(tree);
        }

        boolean hasNext() {
            return !stack.isEmpty();
        }

        int peek() {
            return stack.peek().key;
        }

        int next() {
            Node current = stack.pop();
            int key = current.key;
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
            return key;
        }
    }

    static class PostorderTraversal {
        final Stack<Node> stack1 = new Stack<>();
        final Stack<Node> stack2 = new Stack<>();

        PostorderTraversal(Node tree) {
            stack1.push(tree);
            while (!stack1.isEmpty()) {
                Node node = stack1.pop();
                stack2.push(node);
                if (node.left != null) stack1.push(node.left);
                if (node.right != null) stack1.push(node.right);
            }
        }

        boolean hasNext() {
            return !stack2.isEmpty();
        }

        int peek() {
            return stack2.peek().key;
        }

        int next() {
            return stack2.pop().key;
        }
    }

    Node root = null;

    void add(int key) {
        root = insert(root, key);
    }

    boolean contains(int key) {
        return (find(root, key) != null);
    }

    int getCountOf(int key) {
        return findNodes(root, key).size();
    }

    void remove(int key) {
        root = removeKey(root, key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        inorderTraverse(root, (node -> {
            sb.append(node.key);
            sb.append(" ");
        }));
        return sb.toString();
    }

    static void testBst() {
        int[] keys = {4, 5, 2, 8, 3, 6, 1, 4, 7, 3, 10, 3};

        BinarySearchTree bst = new BinarySearchTree();
        for (int key : keys) {
            bst.add(key);
        }
        System.out.println(bst);

        System.out.printf("Count of 4: %d\n", bst.getCountOf(4));
        System.out.print("Remove 4: ");
        bst.remove(4);
        System.out.println(bst);
    }

    static void testTraversals() {
        int[] keys = {5, 3, 8, 2, 4, 6, 9, 1, 7};

        BinarySearchTree bst = new BinarySearchTree();
        for (int key : keys) {
            bst.add(key);
        }

        InorderTraversal inorder = new InorderTraversal(bst.root);
        while (inorder.hasNext()) {
            System.out.print(inorder.next() + " ");
        }
        System.out.println();

        PreorderTraversal preorder = new PreorderTraversal(bst.root);
        while (preorder.hasNext()) {
            System.out.print(preorder.next() + " ");
        }
        System.out.println();

        PostorderTraversal postorder = new PostorderTraversal(bst.root);
        while (postorder.hasNext()) {
            System.out.print(postorder.next() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        testTraversals();
    }
}