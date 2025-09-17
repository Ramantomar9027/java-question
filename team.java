import java.util.Scanner;

public class team {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); 
        int count = 0; 

        for (int i = 0; i < n; i++) {
            int petya = sc.nextInt();
            int vasya = sc.nextInt();
            int tonya = sc.nextInt();

            int sure = petya + vasya + tonya;

            if (sure >= 2) {  
                count++;
            }
        }

        System.out.println(count);
        sc.close();
    }
}
