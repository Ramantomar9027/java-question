import java.util.Scanner;

public class dominopiling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int M = sc.nextInt();
        int N = sc.nextInt();
        sc.close();

        int maxDominoes = (M * N) / 2; 
        System.out.println(maxDominoes);
    }
}
