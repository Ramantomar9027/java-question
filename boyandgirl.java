import java.util.*;

public class boyandgirl {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine().trim();
        Set<Character> distinct = new HashSet<>();
        for (char c : username.toCharArray()) {
            distinct.add(c);
        }

        int count = distinct.size();
        if (count % 2 == 0) {
            System.out.println("CHAT WITH HER!");
        } else {
            System.out.println("IGNORE HIM!");
        }
    }
}
