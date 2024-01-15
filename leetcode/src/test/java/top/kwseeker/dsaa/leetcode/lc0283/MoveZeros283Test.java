package top.kwseeker.dsaa.leetcode.lc0283;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MoveZeros283Test {

    private static final Map<int[], int[]> CASES = new HashMap<>();

    @BeforeClass
    public static void init() {
        CASES.put(new int[]{0,1,0,3,12}, new int[]{1,3,12,0,0});
        CASES.put(new int[]{0}, new int[]{0});
        CASES.put(new int[]{4,2,4,0,0,3,0,5,1,0}, new int[]{4,2,4,3,5,1,0,0,0,0});
    }

    @Test
    public void testMoveZeroes() {
        MoveZeros283 instance = new MoveZeros283();
        CASES.forEach((arr, expectArr) -> {
            instance.moveZeroes(arr);
            System.out.println("arr moved: " + Arrays.toString(arr));
            assertArrayEquals(arr, expectArr);
        });
    }
}