package top.kwseeker.dsaa.leetcode.lc0322;

import java.util.*;

/**
 * 322. 零钱兑换
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 * 你可以认为每种硬币的数量是无限的。
 *
 * 示例 1：
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 *
 * 示例 2：
 * 输入：coins = [2], amount = 3
 * 输出：-1
 *
 * 示例 3：
 * 输入：coins = [1], amount = 0
 * 输出：0
 *
 * 示例 4：
 * 输入：coins = [1], amount = 1
 * 输出：1
 *
 * 示例 5：
 * 输入：coins = [1], amount = 2
 * 输出：2
 *
 * 提示：
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 2^31 - 1
 * 0 <= amount <= 104
 */
public class CoinChange322 {

    /**
     * 思路1：贪心算法
     * 贪心算法不一定能找到最优解，除非能证明给的面值可以对任意数额都有最优解，而这里的面值是不确定的所以不用这种方法解这道题
     * 思路2：回溯法
     * 使用回溯法容易理解和实现，但是性能和内存表现比较差
     * 思路3：动态规划
     *
     */
    public int coinChange(int[] coins, int amount) {    //思路3解法

        return 0;
    }


    public int coinChange2(int[] coins, int amount) {    //思路2解法
        if (amount == 0) {
            return 0;
        }
        //对Coins排序
        Arrays.sort(coins);
        if (amount < coins[0]) {
            return -1;
        }

        int[] subTaskBest = new int[amount+1];
        return coinChange(coins, amount, subTaskBest);
    }

    public int coinChange(int[] coins, int amount, int[] subTaskBest) {
        if (amount == 0) {
            return 0;
        }
        if (amount < coins[0]) {
            return -1;
        }
        if (subTaskBest[amount] > 0 || subTaskBest[amount] == -1) {
            return subTaskBest[amount];
        }

        int minCount = -1;
        //从最大面额开始, 可以减少递归和遍历
        for (int i = coins.length - 1; i >= 0; i--) {
            if (amount < coins[i]) {
                continue;
            }
            int count = coinChange(coins, amount - coins[i], subTaskBest);
            if (count == -1) {  //此子任务没有解, 继续尝试下一个分支
                subTaskBest[amount] = minCount;
                continue;
            }
            minCount = minCount == -1 ? count + 1 : Math.min(minCount, count + 1);
        }

        //记录子分支最优解,减少递归和遍历次数
        subTaskBest[amount] = minCount;

        //System.out.println("amount=" + amount + ", 最优解 minCount=" + minCount);
        return minCount;
    }

    //public int coinChange(int[] coins, int amount) {    //思路2解法
    //    if (amount == 0) {
    //        return 0;
    //    }
    //    //对Coins排序
    //    Arrays.sort(coins);
    //    if (amount < coins[0]) {
    //        return -1;
    //    }
    //
    //    Map<Integer, Integer> subTaskBestMap = new HashMap<>();
    //    return coinChange(coins, amount, subTaskBestMap);
    //}
    //public int coinChange(int[] coins, int amount, Map<Integer, Integer> subTaskBestMap) {
    //    if (amount == 0) {
    //        return 0;
    //    }
    //    if (amount < coins[0]) {
    //        return -1;
    //    }
    //    Integer cached = subTaskBestMap.get(amount);
    //    if (cached != null) {
    //        return cached;
    //    }
    //
    //    int minCount = -1;
    //    //从最大面额开始, 可以减少递归和遍历
    //    for (int i = coins.length - 1; i >= 0; i--) {
    //        if (amount < coins[i]) {
    //            continue;
    //        }
    //        int count = coinChange(coins, amount - coins[i], subTaskBestMap);
    //        if (count == -1) {  //此子任务没有解, 继续尝试下一个分支
    //            subTaskBestMap.put(amount, minCount);
    //            continue;
    //        }
    //        minCount = minCount == -1 ? count + 1 : Math.min(minCount, count + 1);
    //    }
    //
    //    //记录子分支最优解,减少递归和遍历次数
    //    subTaskBestMap.put(amount, minCount);
    //    //System.out.println("amount=" + amount + ", 最优解 minCount=" + minCount);
    //    return minCount;
    //}
}
