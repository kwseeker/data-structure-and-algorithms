package top.kwseeker.dsaa.leetcode.lc0128;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LongestConsecutive128Test {

    private static final Map<int[], Integer> CASES = new HashMap<>();

    @BeforeClass
    public static void init() {
        CASES.put(new int[]{100,4,200,1,3,2}, 4);
        CASES.put(new int[]{0,3,7,2,5,8,4,6,0,1}, 9);
    }

    @Test
    public void testLongestConsecutive() {
        LongestConsecutive128 instance = new LongestConsecutive128();
        CASES.forEach((arr, expectLen) -> {
            int len = instance.longestConsecutive(arr);
            assertEquals(expectLen.intValue(), len);
        });
    }
}