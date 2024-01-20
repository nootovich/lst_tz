import java.util.Arrays;

public class Boilerplate {

    public static void main(String[] args) {
        int[] before = new int[100000];
        for (int i = 0; i < before.length; i++) {
            before[i] = (int) (Math.random() * 299) + 1;
        }
        String serialized = Conversion.serialize(before);
        int[]  after      = Conversion.deserialize(serialized);

        System.out.printf("before: %s%n", Arrays.toString(before));
        System.out.printf("--------------%nserialized: %s%n", serialized);
        System.out.printf("--------------%nafter: %s%n", Arrays.toString(after));
        System.out.printf("--------------%ndiff: %d%n", Arrays.compare(before, after));
    }
}
