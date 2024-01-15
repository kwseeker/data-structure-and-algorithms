package top.kwseeker.dsaa.leetcode.lc0322;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CoinChange322Test {

    private static final Map<CoinChangeConfig, Integer> CASES = new HashMap<>();

    @BeforeClass
    public static void init() {
        CASES.put(new CoinChangeConfig(new int[]{1,2,5}, 11), 3);
        CASES.put(new CoinChangeConfig(new int[]{2}, 3), -1);
        CASES.put(new CoinChangeConfig(new int[]{1}, 0), 0);
        CASES.put(new CoinChangeConfig(new int[]{1}, 1), 1);
        CASES.put(new CoinChangeConfig(new int[]{1}, 2), 2);
        CASES.put(new CoinChangeConfig(new int[]{3,4,5,10}, 17), 3);
        CASES.put(new CoinChangeConfig(new int[]{474,83,404,3}, 264), 8);
    }

    @Test
    public void testMaxArea() {
        CoinChange322 instance = new CoinChange322();
        CASES.forEach((config, expect) -> {
            int result = instance.coinChange(config.coins, config.amount);
            assertEquals(result, expect.intValue());
        });
    }

    static class CoinChangeConfig {
        protected int[] coins;
        protected int amount;

        public CoinChangeConfig(int[] coins, int amount) {
            this.coins = coins;
            this.amount = amount;
        }
    }
}