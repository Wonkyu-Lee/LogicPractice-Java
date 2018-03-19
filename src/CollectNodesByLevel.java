import java.util.LinkedList;
import java.util.List;

public class CollectNodesByLevel {
    static class Node {
        int key;
        Node left;
        Node right;
        Node(int key) {
            this.key = key;
        }
    }

    /**
     *                  1
     *                 /  \
     *               2     3
     *              / \     \
     *             4   5     6
     *
     * list1:
     * list2:
     * result: [1] [2 3] [4 5 6]
     */
    static List<List<Node>> collectNodesByLevel(Node root) {
        List<List<Node>> result = new LinkedList<>();

        LinkedList<Node> list1 = new LinkedList<>();
        list1.add(root);

        while (true) {
            if (list1.isEmpty())
                break;

            LinkedList<Node> list2 = new LinkedList<>();
            for (Node each : list1) {
                if (each.left != null) list2.add(each.left);
                if (each.right != null) list2.add(each.right);
            }
            result.add(list1);
            list1 = list2;
        }

        return result;
    }

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        n1.left = n2; n1.right = n3;
        n2.left = n4; n2.right = n5;
        n3.right = n6;

        List<List<Node>> result = collectNodesByLevel(n1);
        int i = 0;
        for (List<Node> eachList : result) {
            List<Integer> values = new LinkedList<>();
            for (Node each: eachList) {
                values.add(each.key);
            }
            System.out.println("Level#" + i + ": " + values);
            ++i;
        }
    }
}
