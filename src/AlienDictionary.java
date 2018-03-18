import java.util.*;

/**
 * https://ide.geeksforgeeks.org/7Td60XaX0a
 */
public class AlienDictionary {

    static String findOrder(String[] dic) {
        Graph g = new Graph();

        for (int i = 0; i < dic.length - 1; ++i) {
            String s1 = dic[i];
            String s2 = dic[i + 1];
            int j = 0;
            int len = Math.min(s1.length(), s2.length());
            while (j < len) {
                if (s1.charAt(j) != s2.charAt(j))
                    break;
                ++j;
            }

            if (j < len) {
                g.addEdge(s2.charAt(j), s1.charAt(j));
            }
        }

        return g.topologicalSort();
    }

    static class Graph {
        Map<Character, Node> nodes = new HashMap<>();
        class Node {
            Set<Character> neighbors = new HashSet<>();
            int inCount = 0;
        }

        void addEdge(char u, char v) {
            addVertex(u);
            addVertex(v);
            Node uNode = nodes.get(u);
            Node vNode = nodes.get(v);
            uNode.neighbors.add(v);
            vNode.inCount++;
        }

        void addVertex(char v) {
            if (!nodes.containsKey(v)) {
                nodes.put(v, new Node());
            }
        }

        enum VisitState { FRESH, VISITING, DONE }
        String topologicalSort() {
            LinkedList<Character> order = new LinkedList<>();
            Map<Character, VisitState> states = new HashMap<>();
            for (char each : nodes.keySet()) {
                states.put(each, VisitState.FRESH);
            }

            for (char each : nodes.keySet()) {
                if (states.get(each) == VisitState.FRESH) {
                    if (!visit(states, order, each)) {
                        return null;
                    }
                }
            }

            return makePath(order);
        }

        boolean visit(Map<Character, VisitState> states, LinkedList<Character> order, char s) {
            VisitState state = states.get(s);
            if (state == VisitState.VISITING) {
                return false;
            } else if (state == VisitState.DONE) {
                return true;
            }

            states.put(s, VisitState.VISITING);
            Node sNode = nodes.get(s);

            for (char neighbor : sNode.neighbors) {
                if (!visit(states, order, neighbor)) {
                    return false;
                }
            }

            states.put(s, VisitState.DONE);
            order.add(s);
            return true;
        }

        String makePath(LinkedList<Character> order) {
            StringBuilder sb = new StringBuilder();
            for (char each : order) {
                sb.append(each);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        {
            String[] dic = {"bac", "abcd", "abca", "cab", "cad"};
            System.out.println(findOrder(dic));
        }

        {
            String[] dic = {"caa", "aaa", "aab"};
            System.out.println(findOrder(dic));
        }
    }
}
