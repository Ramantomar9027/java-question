import java.io.*;
import java.util.*;

public class main2 {
    static boolean isRegular(String s) {
        int bal = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') bal++;
            else bal--;
            if (bal < 0) return false;
        }
        return bal == 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] first = br.readLine().split(" ");
        int n = Integer.parseInt(first[0]);
        int k = Integer.parseInt(first[1]);

        String[] seq = new String[n];
        for (int i = 0; i < n; i++) {
            seq[i] = br.readLine().trim();
    
            if (seq[i].length() == k && isRegular(seq[i])) {
                System.out.println(-1);
                return;
            }
        }
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < k / 2; i++) sb1.append("()");
        String balanced1 = sb1.toString();

        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < k / 2; i++) sb2.append("(");
        for (int i = 0; i < k / 2; i++) sb2.append(")");
        String balanced2 = sb2.toString();

        List<Integer> group1 = new ArrayList<>();
        List<Integer> group2 = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            boolean ok1 = !balanced1.contains(seq[i]);
            boolean ok2 = !balanced2.contains(seq[i]);

            if (!ok1 && !ok2) {
                System.out.println(-1);
                return;
            }
            if (ok1) group1.add(i + 1);
            else group2.add(i + 1);
        }

        int groups = 0;
        if (!group1.isEmpty()) groups++;
        if (!group2.isEmpty()) groups++;
        System.out.println(groups);

        if (!group1.isEmpty()) {
            System.out.println(balanced1);
            System.out.println(group1.size());
            for (int i = 0; i < group1.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(group1.get(i));
            }
            System.out.println();
        }
        if (!group2.isEmpty()) {
            System.out.println(balanced2);
            System.out.println(group2.size());
            for (int i = 0; i < group2.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(group2.get(i));
            }
            System.out.println();
        }
    }
}
