package top.kwseeker.dsaa.slidewindow;

import java.util.HashMap;
import java.util.Map;

public class LC76MinimumWindowSubstring {

    public static void main(String[] args) {
        LC76MinimumWindowSubstring handler = new LC76MinimumWindowSubstring();

        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(handler.minWindow(s, t));
        s = "a";
        t = "a";
        System.out.println(handler.minWindow(s, t));
        s = "a";
        t = "aa";
        System.out.println(handler.minWindow(s, t));
    }

    /**
     * 思路：
     * 先右指针滑动要遍历找全包含子串所有字符的子字符串，
     * 然后左指针滑动要小于所有字符最新的位置才能滑动；
     * 记录滑动过程中子字符串（可行解）长度最小时的左右指针索引。
     */
    public String minWindow(String s, String t) {
        //1 获取t中所有字符（可能重复）, char -> 出现次数
        //int[] chars = new int[256];
        //Arrays.fill(chars, -1);
        //for (int i = 0; i < t.length(); i++) {
        //    chars[t.charAt(i)]++;
        //}
        //使用int[]可能带来很多无效判断，还是用Map存储
        Map<Character, Integer> charMap = new HashMap<>();
        Map<Character, Integer> counterMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            if(charMap.putIfAbsent(t.charAt(i), 1) != null) {
                charMap.computeIfPresent(t.charAt(i), (k, v) -> v+1);
            }
        }

        int l = 0, r = 0;
        int minLen = Integer.MAX_VALUE;
        int ml=l, mr=r;
        while (r < s.length()) {
            char rc = s.charAt(r++);
            //c不在charMap中，r继续右移
            if (!charMap.containsKey(rc)) {
                continue;
            }
            //c在charMap中
            // counterMap记录窗口中出现的目标字符和次数
            if(counterMap.putIfAbsent(rc, 1) != null) {  //已经存在
                counterMap.computeIfPresent(rc, (k, v) -> v+1);
            }
            // 判断当前窗口中是否已经完整包含所有字符
            if (containsAll(counterMap, charMap)) {
                //左指针移动
                do {
                    char lc = s.charAt(l++);
                    Integer newV = counterMap.computeIfPresent(lc, (k, v) -> v - 1);
                    if (newV != null && newV == 0) {
                        counterMap.remove(lc);
                    }
                } while (containsAll(counterMap, charMap));
                //找到可行解，比较并记录最小长度
                if ((r - l+1) < minLen) {
                    minLen = r-l +1;
                    ml = l-1;
                    mr = r-1;
                }
            }
        }

        if (minLen == 0 || minLen == Integer.MAX_VALUE) {
            return "";
        }
        return s.substring(ml, mr+1);
    }

    private boolean containsAll(Map<Character, Integer> counterMap, Map<Character, Integer> charMap) {
        for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
            Character c = entry.getKey();
            if (counterMap.containsKey(c) && counterMap.get(c) >= charMap.get(c)) {
                continue;
            }
            return false;
        }
        return true;
    }
}
