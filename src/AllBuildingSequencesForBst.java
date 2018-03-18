import java.util.LinkedList;

public class AllBuildingSequencesForBst {

    static class Node {
        int value;
        Node left;
        Node right;
        Node(int value) {
            this.value = value;
        }
    }

    static Node insert(Node tree, int value) {
        if (tree == null) {
            return new Node(value);
        }

        if (value <= tree.value) {
            tree.left = insert(tree.left, value);
        } else {
            tree.right = insert(tree.right, value);
        }

        return tree;
    }

    public static LinkedList<LinkedList<Integer>> solve(Node node) {
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();
        if (node == null) {
            result.add(new LinkedList<>()); // TODO: 빈 거라도 반드시 들어가야 한다---(1)
            return result;
        }

        LinkedList<Integer> prefix = new LinkedList<>();
        prefix.add(node.value);

        LinkedList<LinkedList<Integer>> resultL = solve(node.left);
        LinkedList<LinkedList<Integer>> resultR = solve(node.right);

        // TODO: (1) 조치로 인해, l1이나 l2는 빈 리스트라도 반드시 존재하므로 for 문이 실행된다.
        for (LinkedList<Integer> l1 : resultL) {
            for (LinkedList<Integer> l2 : resultR) {
                weave(result, prefix, l1, l2);
            }
        }

        return result;
    }

    // TODO: 코딩량 줄이려면 result를 outparam으로 갖고 다니자.
    static void weave(
            LinkedList<LinkedList<Integer>> result,
            LinkedList<Integer> prefix,
            LinkedList<Integer> l1,
            LinkedList<Integer> l2) {

        if (l1.isEmpty() || l2.isEmpty()) {
            LinkedList<Integer> l = new LinkedList<>(prefix);
            l.addAll(l1);
            l.addAll(l2);
            result.add(l);
            return;
        }

        int e1 = l1.removeFirst();
        prefix.add(e1);
        weave(result, prefix, l1, l2);
        prefix.removeLast();
        l1.addFirst(e1);

        int e2 = l2.removeFirst();
        prefix.add(e2);
        weave(result, prefix, l1, l2);
        prefix.removeLast();
        l2.addFirst(e2);
    }

    /**
     *             5
     *          /      \
     *        3        7
     *       /       /  \
     *     2        6   8
     *    /
     *   1
     *
     *            5
     *     (3 2 1)   (7 6 8)
     *               (7 8 6)
     *
     *   6!/(3!*3!) * 2 = 40
     */


    public static void main(String[] args) {
        int[] values = {5, 3, 7, 2, 1, 8, 6};
        Node tree = null;
        for (int value : values) {
            tree = insert(tree, value);
        }

        LinkedList<LinkedList<Integer>> result = solve(tree);
        System.out.println("Count: " + result.size());
        for (LinkedList<Integer> list : result) {
            System.out.println(list);
        }
    }
}
