package top.kwseeker.dsaa.leetcode.lc0011;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MaxArea11Test {

    private static final Map<int[], Integer> CASES = new HashMap<>();

    @BeforeClass
    public static void init() {
        CASES.put(new int[]{1,8,6,2,5,4,8,3,7}, 49);
        CASES.put(new int[]{1,1}, 1);
    }

    @Test
    public void testMaxArea() {
        MaxArea11 instance = new MaxArea11();
        CASES.forEach((arr, expect) -> {
            int result = instance.maxArea(arr);
            assertEquals(result, expect.intValue());
        });
    }
}