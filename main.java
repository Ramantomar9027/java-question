import java.io.*;
import java.util.*;

public class main {
    static final int MOD = 998244353;

    // fast pow (base^exp mod MOD)
    static long modPow(long base, long exp) {
        long res = 1 % MOD;
        base %= MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) res = (res * base) % MOD;
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return res;
    }

    // get strictly increasing sequence of maxima when scanning from left
    static int[] getLeftView(int[] a) {
        ArrayList<Integer> list = new ArrayList<>();
        int cur = Integer.MIN_VALUE;
        for (int x : a) {
            if (x > cur) {
                list.add(x);
                cur = x;
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) res[i] = list.get(i);
        return res;
    }

    // lower_bound on sorted int[] arr for value x
    static int lowerBound(int[] arr, int x) {
        int l = 0, r = arr.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (arr[m] < x) l = m + 1;
            else r = m;
        }
        return l;
    }

    // Segment tree used in calc(): supports:
    // - inc(pos, val): add val to t[pos] (resolving pending pow factor along path)
    // - mulBy2(l, r): multiply interval [l, r) by 2
    // - get(pos): get t[pos] * 2^{pendingExponentOnPath}
    static class SegTree {
        int n;                 // number of leaves
        int[] f;               // lazy exponent increments (for nodes)
        long[] t;              // leaf values (size n)
        SegTree(int n) {
            this.n = n;
            f = new int[4 * n + 10];
            t = new long[n];
        }

        // compute value of leaf pos under node v: t[pos] * 2^{f[v]}
        long getVal(int v, int pos) {
            return (t[pos] * modPow(2, f[v])) % MOD;
        }

        void push(int v) {
            if (f[v] != 0) {
                f[v * 2 + 1] += f[v];
                f[v * 2 + 2] += f[v];
                f[v] = 0;
            }
        }

        // resolve pending exponent at node v for leaf pos (apply to t[pos])
        void resolve(int v, int l, int r, int pos) {
            if (f[v] != 0 && l == r - 1) {
                t[pos] = (t[pos] * modPow(2, f[v])) % MOD;
                f[v] = 0;
            }
        }

        long get(int v, int l, int r, int pos) {
            if (l == r - 1) {
                // leaf
                return getVal(v, pos);
            } else {
                push(v);
                int m = (l + r) >>> 1;
                if (pos < m) return get(v * 2 + 1, l, m, pos);
                else return get(v * 2 + 2, m, r, pos);
            }
        }

        long get(int pos) {
            return get(0, 0, n, pos);
        }

        void inc(int v, int l, int r, int pos, long val) {
            if (l == r - 1) {
                resolve(v, l, r, pos);
                t[pos] = (t[pos] + val) % MOD;
            } else {
                push(v);
                int m = (l + r) >>> 1;
                if (pos < m) inc(v * 2 + 1, l, m, pos, val);
                else inc(v * 2 + 2, m, r, pos, val);
            }
        }

        void inc(int pos, long val) {
            inc(0, 0, n, pos, val);
        }

        void mulBy2(int v, int l, int r, int L, int R) {
            if (L >= R) return;
            if (l == L && r == R) {
                f[v] += 1;
            } else {
                push(v);
                int m = (l + r) >>> 1;
                if (L < m) mulBy2(v * 2 + 1, l, m, L, Math.min(R, m));
                if (R > m) mulBy2(v * 2 + 2, m, r, Math.max(L, m), R);
            }
        }

        void mulBy2(int L, int R) {
            if (L >= R) return;
            mulBy2(0, 0, n, L, R);
        }
    }

    // calc(a, b) as in editorial: returns dp array length n
    // b is the sorted-increasing left_view (strictly increasing)
    static long[] calc(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        // tree size m+1 leaves (positions 0..m)
        SegTree tree = new SegTree(m + 1);
        // inc(0,1)
        tree.inc(0, 1);
        long[] res = new long[n];
        if (m == 0) return res; // no left view: all zero
        int maxVal = b[m - 1];
        for (int i = 0; i < n; i++) {
            int x = a[i];
            if (x > maxVal) continue;
            if (x == maxVal) {
                // dp value contributed when x equals maxVal: read position m-1
                res[i] = tree.get(m - 1);
            }
            int lf = lowerBound(b, x);
            // multiply by 2 the interval [lf+1, m+1)
            tree.mulBy2(lf + 1, m + 1);
            if (lf < m && b[lf] == x) {
                long cur = tree.get(lf);
                // add cur to position lf+1
                tree.inc(lf + 1, cur);
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder sb = new StringBuilder();
        int T = fs.nextInt();
        while (T-- > 0) {
            int n = fs.nextInt();
            int[] a = new int[n];
            int maxA = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                a[i] = fs.nextInt();
                if (a[i] > maxA) maxA = a[i];
            }

            int[] leftView = getLeftView(a);

            // get right view by reversing
            int[] aRev = new int[n];
            for (int i = 0; i < n; i++) aRev[i] = a[n - 1 - i];
            int[] rightViewRev = getLeftView(aRev); // this is right view reversed

            // calc dpL on original a for leftView
            long[] dpL = calc(a, leftView);

            // calc dpR: we do calc on reversed a with rightViewRev, then reverse dpR result back.
            long[] dpRrev = calc(aRev, rightViewRev);
            long[] dpR = new long[n];
            for (int i = 0; i < n; i++) dpR[i] = dpRrev[n - 1 - i];

            int globalMax = maxA;
            long ans = 0;

            long sumLeft = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] > globalMax) continue; // unnecessary but mirror editorial
                if (a[i] == globalMax) {
                    // ans += (sumLeft + dpL[i]) * dpR[i]
                    long add = (((sumLeft + dpL[i]) % MOD) * (dpR[i] % MOD)) % MOD;
                    ans += add;
                    if (ans >= MOD) ans -= MOD;
                }
                // sumLeft *= 2
                sumLeft = (sumLeft * 2) % MOD;
                if (a[i] == globalMax) {
                    sumLeft = (sumLeft + dpL[i]) % MOD;
                }
            }
            sb.append(ans % MOD).append('\n');
        }
        System.out.print(sb.toString());
    }

    // ---- fast scanner ----
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return Integer.MIN_VALUE;
            }
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
        // alias used in main
        int next() throws IOException { return nextInt(); }
        int nextIntNoEx() { // fallback if you prefer non-throwing interface (not used)
            try { return nextInt(); } catch (IOException e) { return Integer.MIN_VALUE; }
        }
    }
}
