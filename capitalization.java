import java.util.Scanner;

public class capitalization {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine().trim();
        
        if (!word.isEmpty() && Character.isLowerCase(word.charAt(0))) {
            word = Character.toUpperCase(word.charAt(0)) + word.substring(1);
        }
        
        System.out.println(word);
    }
}
