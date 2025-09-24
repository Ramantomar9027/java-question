import java.util.Scanner;

public class bear {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long a = scanner.nextLong();
        long b = scanner.nextLong();
        int years = 0;
        while (a <= b) {
            a *= 3;
            b *= 2;
            years++;
        }
        System.out.println(years);
    }
}
