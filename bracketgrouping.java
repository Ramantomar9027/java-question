import java.util.*;

public class bracketgrouping {
    static int n, k;
    static String[] s;

    static boolean isRegular(String str) {
        int bal = 0;
        for (char c : str.toCharArray()) {
            if (c == '(') bal++;
            else bal--;
            if (bal < 0) return false;
        }
        return bal == 0;
    }


    static void genRBS(String cur, int open, int close, List<String> res) {
        if (cur.length() == k) {
            res.add(cur);
            return;
        }
        if (open < k / 2) genRBS(cur + "(", open + 1, close, res);
        if (close < open) genRBS(cur + ")", open, close + 1, res);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        k = sc.nextInt();
        s = new String[n];
        for (int i = 0; i < n; i++) s[i] = sc.next();

        List<String> allRBS = new ArrayList<>();
        genRBS("", 0, 0, allRBS);

        if (allRBS.isEmpty()) {
            System.out.println(-1);
            return;
        }

        Map<String, List<Integer>> forbidden = new HashMap<>();
        for (String rbs : allRBS) {
            List<Integer> bad = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (rbs.contains(s[i])) {
                    bad.add(i);
                }
            }
            forbidden.put(rbs, bad);
        }

        boolean[] used = new boolean[n];
        List<String> chosenRBS = new ArrayList<>();
        List<List<Integer>> groups = new ArrayList<>();

        for (String rbs : allRBS) {
            List<Integer> group = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (!used[i] && !rbs.contains(s[i])) {
                    group.add(i);
                }
            }
            if (!group.isEmpty()) {
                chosenRBS.add(rbs);
                groups.add(group);
                for (int idx : group) used[idx] = true;
            }
        }
        for (boolean u : used) {
            if (!u) {
                System.out.println(-1);
                return;
            }
        }
        System.out.println(groups.size());
        for (int g = 0; g < groups.size(); g++) {
            System.out.println(chosenRBS.get(g));
            System.out.println(groups.get(g).size());
            for (int idx : groups.get(g)) System.out.print((idx + 1) + " ");
            System.out.println();
        }
    }
}
