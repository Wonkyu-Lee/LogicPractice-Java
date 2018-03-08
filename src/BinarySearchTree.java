import java.util.LinkedList;

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

    public static void main(String[] args) {
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
}