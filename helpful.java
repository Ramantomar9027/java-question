import java.util.*;

public class helpful {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();  

        String[] parts = s.split("\\+");

        int[] nums = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }
        
 
        Arrays.sort(nums);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            result.append(nums[i]);
            if (i != nums.length - 1) {
                result.append("+");
            }
        }
        
        System.out.println(result.toString());
    }
}
