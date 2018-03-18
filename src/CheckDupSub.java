import java.util.HashSet;

/**
 * https://practice.geeksforgeeks.org/problems/duplicate-subtree-in-binary-tree/1
 * https://ide.geeksforgeeks.org/Ovq2paN0O8
 */
public class CheckDupSub {

    static boolean hasDupSub(Node node) {
        Context context = new Context();
        visit(node, context);
        return context.hasDupSub;
    }

    static class Node {
        Node left;
        Node right;
        char value;
        Node(char value) {
            this.value = value;
        }
    }

    static class Context {
        HashSet<String> subtrees = new HashSet<>();
        boolean hasDupSub = false;
    }

    static int visit(Node node, Context context) {
        if (node == null) return 0;
        int lH = visit(node.left, context);
        int rH = visit(node.right, context);
        int h = Math.max(lH, rH) + 1;
        if (h == 2) {
            String key = makeKey(node);
            if (context.subtrees.contains(key)) {
                context.hasDupSub = true;
            } else {
                context.subtrees.add(key);
            }
        }
        return h;
    }

    static String makeKey(Node node) {
        return String.format("%c_%c_%c", node.value,
                (node.left == null ? 'x' : node.left.value),
                (node.right == null ? 'x' : node.right.value));
    }

    static Node buildTree(String expr) {
        return buildTree(new Cursor(expr));
    }

    static class Cursor {
        String expr;
        int index = 0;

        Cursor(String expr) {
            this.expr = expr;
        }

        boolean hasNext() {
            return index < expr.length();
        }

        char next() {
            return expr.charAt(index++);
        }
    }

    static Node buildTree(Cursor cursor) {
        Node node = null;

        char ch = cursor.next();
        if (ch == '(') {
            ch = cursor.next();
            if (ch == ')')
                return null;

            node = new Node(ch);
            node.left = buildTree(cursor);
            node.right = buildTree(cursor);
            cursor.next();
        }

        return node;
    }

    public static void main(String[] args) {
        System.out.println(hasDupSub(buildTree("(a(b(d()())(e()()))(c()(b(d()())(e()()))))")));
        System.out.println(hasDupSub(buildTree("(a(b()())(c()()))")));
    }
}
