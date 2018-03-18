import java.io.*;
import java.util.*;

/**
 * http://greatzzo.blogspot.kr/search/label/Java
 */

public class JavaTips {

    static void removeWhileIteration() {
        Integer[] array  = {0, 1, 2, 3, 4, 5}; // boxing 자료형으로 배열을 만들어야 Arrays.asList()를 사용해서 다른 컬렉션에 넣기 쉽다.
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(array));
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            int element = iterator.next();
            if (element % 2 == 0) {
                iterator.remove(); // next() 한 번당 remove() 한 번 가능.
            }
        }

        for (int each : list) {
            System.out.printf(each + " ");
        }
    }

    enum Stone { BLACK }
    static void createAndInitEnumArray() {
        Stone[] stones = new Stone[3]; // 오브젝트 배열엔 기본적으로 널 값이 들어간다.
        Arrays.fill(stones, Stone.BLACK); // 명시적으로 초기값을 넣어주자.
        for (Stone each : stones) {
            System.out.println(each);
        }
    }

    static void readFile(String filePath) throws IOException {
        FileReader reader = new FileReader(filePath);
        int ch;
        while ((ch = reader.read()) != -1) {
            System.out.println((char)ch);
        }
        reader.close();
    }

    static void readFile2(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    static void writeFile(String filePath, String content) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(content);
        writer.append("--- End of File ---");
        writer.close();
    }

    static void writeFile2(String filePath, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content);
        writer.newLine();
        writer.close();
    }

    /**
     * https://mytory.net/archives/285
     * \b \t \n \f \r \" \' \\
     */
    static void tokenize() {
        String s = "This is a test";
        for (String each : s.split("\\s")) {
            System.out.println(each);
        }
    }

    enum Color { RED, GREEN, BLUE }
    static void iterateEnumSet() {
        for (Color each : Color.values()) {
            System.out.println(each);
        }
    }

    static void iterateMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Lee", 7);
        map.put("Kim", 6);
        for (Map.Entry<String, Integer> each : map.entrySet()) {
            System.out.println(each.getKey() + ": " + each.getValue());
        }
    }

    static void treeSetUsage() {
        Integer[] array = {1, 5, 6, 6, 7, 2, 4};
        TreeSet<Integer> set = new TreeSet<>(Arrays.asList(array));
        System.out.println(set.headSet(5));
        System.out.println(set.tailSet(5));
        System.out.println(set.first());
        System.out.println(set.last());
        System.out.println(set.subSet(2, 6));
        System.out.println(set.floor(3));
        System.out.println(set.ceiling(3));
        System.out.println(set.higher(4));
        System.out.println(set.lower(2));

        Iterator<Integer> it = set.descendingIterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //removeWhileIteration();
        //createAndInitEnumArray();
        //tokenize();
        //iterateEnumSet();
        //iterateMap();
        treeSetUsage();
    }
}
