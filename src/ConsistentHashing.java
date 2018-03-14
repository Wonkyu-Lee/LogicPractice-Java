import java.util.*;

/**
 * 참고: http://www.tom-e-white.com/2007/11/consistent-hashing.html
 *
 * 이게 낫다.
 * https://github.com/fanfish/ConsistentHash/blob/master/ConsistentHashRouter.java
 */
public class ConsistentHashing {

    public interface HashFunction {
        int hash(String key);
    }

    public static class ConsistentHash<T> {
        private final HashFunction hashFunction;
        private final int numberOfReplicas;
        private final SortedMap<Integer, T> circle = new TreeMap<>();

        // TODO: hash function을 뭘 쓰는지가 중요한가?
        public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
            this.hashFunction = hashFunction;
            this.numberOfReplicas = numberOfReplicas;
            for (T node : nodes) {
                add(node);
            }
        }

        public void add(T node) {
            for (int i = 0; i < numberOfReplicas; ++i) {
                circle.put(hashFunction.hash(node.toString() + i), node);
            }
        }

        public void remove(T node) {
            for (int i = 0; i < numberOfReplicas; ++i) {
                circle.remove(hashFunction.hash(node.toString() + i));
            }
        }

        public T get(Object key) {
            int hash = hashFunction.hash(key.toString()); // TODO: string 이 key의 유일성을 보장해야 이게 되지.
            if (!circle.containsKey(hash)) {
                SortedMap<Integer, T> tailMap = circle.tailMap(hash);
                hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
            }

            return circle.get(hash);
        }
    }

    public static void main(String[] args) {
        LinkedList<HashMap> nodes = new LinkedList<>();
        for (int i = 0; i < 3; ++i) {
            nodes.add(new HashMap());
        }

        ConsistentHash<HashMap> consistentHash = new ConsistentHash<>(key -> key.hashCode(), 3, nodes);
        HashMap node = consistentHash.get(5);
        System.out.println(node.containsKey(5));
    }
}
