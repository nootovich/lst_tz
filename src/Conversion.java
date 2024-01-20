import java.util.ArrayList;

public class Conversion {

    // 0bx0000000 + 0x20 (7+2)
    // 0bx00000xx + 0x20 (5+4)
    // 0bx000xxxx + 0x20 (3+6)
    // 0bx0xxxxxx + 0x20 (nxt)

    // usable ASCII 0x20 .. 0x7E
    private static final int start       = 0x20;
    private static final int end         = 0x7E;
    private static final int usable      = end - start + 1;
    private static final int usable_4th  = usable >> 2;
    private static final int usable_16th = usable >> 4;

    public static String serialize(int[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += 3) {
            int a = safeGetAt(data, i);
            int b = safeGetAt(data, i + 1);
            int c = safeGetAt(data, i + 2);

            if (a == 0) break;
            sb.appendCodePoint(start + a % usable);
            sb.appendCodePoint(start + a / usable + b % usable_4th * 4);

            if (b == 0) break;
            sb.appendCodePoint(start + b / usable_4th + c % usable_16th * 16);

            if (c == 0) break;
            sb.appendCodePoint(start + c / usable_16th);
        }
        return sb.toString();
    }

    public static int[] deserialize(String data) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i <= data.length() / 4; i++) {
            int a = safeGetCharAt(data, i * 4) + safeGetCharAt(data, i * 4 + 1) % 4 * usable;
            int b = safeGetCharAt(data, i * 4 + 1) / 4 + safeGetCharAt(data, i * 4 + 2) % 16 * usable_4th;
            int c = safeGetCharAt(data, i * 4 + 2) / 16 + safeGetCharAt(data, i * 4 + 3) % 64 * usable_16th;

            if (a == 0) break;
            result.add(a);

            if (b == 0) break;
            result.add(b);

            if (c == 0) break;
            result.add(c);
        }
        return result.stream().mapToInt(x -> x).toArray();
    }

    private static int safeGetAt(int[] arr, int i) {
        if (i < 0 || i >= arr.length) return 0;
        return arr[i];
    }

    private static int safeGetCharAt(String data, int i) {
        if (i < 0 || i >= data.length()) return 0;
        return data.charAt(i) - start;
    }

}
