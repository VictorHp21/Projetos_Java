import javax.security.auth.callback.CallbackHandler;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main (String[] args){
        String s = "abcabcbb";

        int result = lengthOfLongestSubstring(s);

        System.out.println(result);


    }

    public static int lengthOfLongestSubstring(String s){
        Set<Character> sub = new HashSet<>();
        int i = 0, j = 0, max = 0;

        while (j < s.length()){
            char c = s.charAt(j);

            if (!sub.contains(c)){
                sub.add(c);
                j++;
                max = Math.max(max, j - i);
            } else {
                sub.remove(s.charAt(i));
                i++;
            }
        }

        return max;
    }
}
