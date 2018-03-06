import java.util.LinkedList;
import java.util.List;

// https://www.careercup.com/question?id=5461867051876352
public class SmallerNumbersComposedOf0And1 {
    static int getDecimalWithBinaryDigits(int j) {
        int d = 1;
        int x = 0;
        while (j > 0) {
            x += (j % 2) * d;
            j /= 2;
            d *= 10;
        }
        return x;
    }

    static List<Integer> findSmallerNumbers(int n) {
        LinkedList<Integer> result = new LinkedList<>();
        int x = 0;
        int i = 0;
        while (x < n) {
            result.add(x);
            x = getDecimalWithBinaryDigits(++i);
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(findSmallerNumbers(123));
    }
}
