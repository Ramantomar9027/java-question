import java.util.Scanner;

public class longword {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   // number of words
        sc.nextLine();          // consume newline

        for (int i = 0; i < n; i++) {
            String word = sc.nextLine();

            if (word.length() > 10) {
                int count = word.length() - 2;
                String result = word.charAt(0) + String.valueOf(count) + word.charAt(word.length() - 1);
                System.out.println(result);
            } else {
                System.out.println(word);
            }
        }

        sc.close();
    }
}