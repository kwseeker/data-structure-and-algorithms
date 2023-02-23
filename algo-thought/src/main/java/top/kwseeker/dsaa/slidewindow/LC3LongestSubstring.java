package top.kwseeker.dsaa.slidewindow;

import java.util.*;

/**
 * 滑动窗口计算不重复子字符串最大长度
 */
public class LC3LongestSubstring {

    public static void main(String[] args) {
        LC3LongestSubstring handler = new LC3LongestSubstring();
        String s = "abcabcbb";
        System.out.println(handler.lengthOfLongestSubstring(s));
        s = "bbbbb";
        System.out.println(handler.lengthOfLongestSubstring(s));
        s = "tmmzuxt";
        System.out.println(handler.lengthOfLongestSubstring(s));
        s = " ";
        System.out.println(handler.lengthOfLongestSubstring(s));
    }

    /**
     * 优化版本
     */
    public int lengthOfLongestSubstring(String s) {
        int l = -1, r = 0;
        int maxLen = 0;

        int[] chars = new int[256]; //假设字符都是ASCII码标准字符
        Arrays.fill(chars, -1);
        while (r < s.length()) {
            char c = s.charAt(r);
            if (chars[c] != -1) {
                l = Math.max(chars[c], l);
            }
            maxLen = Math.max(maxLen, r - l);
            chars[c] = r;
            r++;
        }

        return maxLen;
    }

    /**
     * 容易理解的版本，缺点：效率一般，内存占用大
     * 可优化的点：使用数组替代Set，并不需要记录子字符串，只需要有开始和结尾的坐标即可
     */
    public int lengthOfLongestSubstringOld(String s) {
        int r = 0;
        int maxLen = 0;

        Set<Character> chars = new HashSet<>();
        Queue<Character> queue = new LinkedList<>();
        while (r < s.length()) {
            char c = s.charAt(r++);
            boolean repeat = chars.contains(c);
            queue.add(c);

            if (repeat) {
                Character c1;
                do {
                    c1 = queue.poll();
                    chars.remove(c1);
                } while (c1 != null && c1 != c);
            }

            chars.add(c);
            maxLen = Math.max(maxLen, queue.size());
        }

        return maxLen;
    }
}
