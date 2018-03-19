import java.util.*;

// TODO: 매우 유용해서 만들어 쓰면 좋겠다. 면접 때도 통할까? 인터페이스 정도는 만들어 주면 좋을 듯.
public class HashMapList<K, V> {
    private final HashMap<K, LinkedList<V>> map = new HashMap<>();

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public V put(K key, V value) {
        LinkedList<V> list = map.get(key);
        if (list == null) {
            list = new LinkedList<>();
            map.put(key, list);
        }
        list.add(value);
        return value;
    }

    public List<V> remove(K key) {
        return map.remove(key);
    }

    public V removeFirst(K key) {
        LinkedList<V> list = map.get(key);
        if (list == null)
            return null;

        V value = list.removeFirst();
        if (list.isEmpty()) {
            map.remove(key);
        }

        return value;
    }

    public V removeLast(K key) {
        LinkedList<V> list = map.get(key);
        if (list == null)
            return null;

        V value = list.removeLast();
        if (list.isEmpty()) {
            map.remove(key);
        }

        return value;
    }

    public V getFirst(K key) {
        LinkedList<V> list = map.get(key);
        if (list == null)
            throw new NoSuchElementException();

        return list.getFirst();
    }

    public V getLast(K key) {
        LinkedList<V> list = map.get(key);
        if (list == null)
            throw new NoSuchElementException();

        return list.getLast();
    }

    public List<V> get(K key) {
        return Collections.unmodifiableList(map.get(key));
    }

    public static void main(String[] args) {
        HashMapList<Integer, Integer> multiQueue = new HashMapList<>();

        int slots = 3;
        for (int i = 0; i < 10; ++i) {
            multiQueue.put(i % slots, i);
        }

        for (int i = 0; i < slots; ++i) {
            System.out.printf("Slot[%d] = %s\n", i, multiQueue.get(i));
        }

        System.out.print("PopFront from slot[2]: ");

        while (multiQueue.containsKey(2)) {
            int v = multiQueue.removeFirst(2);
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
