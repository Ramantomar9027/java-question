import java.util.Scanner;

public class theatersquare {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();
        long m = sc.nextLong();
        long a = sc.nextLong();
        sc.close();

        long tilesAlongN = (n + a - 1) / a; 
        long tilesAlongM = (m + a - 1) / a; 

        System.out.println(tilesAlongN * tilesAlongM);
    }
}
