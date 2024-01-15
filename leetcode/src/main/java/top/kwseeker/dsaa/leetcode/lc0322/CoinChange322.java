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
     *
     * 思路2：回溯法
     * 使用回溯法容易理解和实现，但是性能和内存表现比较差
     *
     * 思路3：动态规划
     * 先求状态转移方程，然后自底向上递推
     * 假设总金额是S, 有n种硬币, 面额 c1...cn，设 F(S) 是硬币最少数量
     * F(S) = min(F(S-c1),F(S-c2),...,F(S-cn)) + 1
     * ...
     * F(1) = min(F(1-c1),F(1-c2),...,F(1-cn))+1 //面额不可能小于1
     *      //假设面额最小的是1
     *      = min(F(0)) + 1
     *      = 1
     *      //假设面额最小是2
     *      = -1
     * F(0) = 0
     * 假设 S=17, 有4种硬币，面额分别是 3,4,5,10
     * 自下而上求解过程如下，动态规划代码实现就是描述这个过程
     * F(0) = 0										//F(0)也是有解的，不需要硬币也是一种解
     * F(1) 	min(F(1-3),F(1-4),F(1-5),F(1-10)) 	//因为所有子状态金额全部是负数，所以无解，所以F(1)无解，无解在代码中就会直接跳过
     * F(2) 										//同理无解跳过
     * F(3) = min(F(3-3),F(3-4),F(3-5),F(3-10)) + 1
     * 	 = min(F(0)) + 1							//存在一个子状态有解
     *      = 1     								//即F(3)最优解是1
     * F(4) = 1     								//同F(3)的求解方式, 这里省略过程
     * F(5) = 1										//同F(3)的求解方式, 这里省略过程
     * F(6) = min(F(6-3),F(6-4),F(6-5),F(6-10)) + 1
     * 	 = min(F(3)) + 1 							//过滤掉无解的子状态
     * 	 = 1 + 1
     * 	 = 2
     * ...
     * F(17)=...
     */
    public int coinChange(int[] coins, int amount) {    //思路3解法
        int[] dp = new int[amount+1];   //存储F(i)的最优解, 从0到amount实际是amount+1个元素
        Arrays.fill(dp, -1);        //暂且用-1表示无解
        dp[0] = 0;                      //这个解是约定俗成的

        int min;
        for (int i = 1; i <= amount ; i++) {  //这里描述从F(1)到F(amount)的过程
            //描述F(i) = min(F(i-C1),F(i-C2),...,F(i-Cn)) + 1，即依次判断所有子状态是否有最优解，选择最小值然后加1作为F(i)的最优解
            min = -1;
            for (int coin : coins) {
                if (i - coin < 0) {     //负值无解
                    continue;
                }
                int candidate = dp[i - coin];    //F(i-Cj)
                if (candidate == -1) {  //此子状态没有最优解直接跳过
                    continue;
                }
                min = min == -1 ? candidate : Math.min(min, candidate);
            }
            if (min == -1) {
                continue;               //F(i)没有最优解直接跳过
            }
            dp[i] = min + 1;            //有最优解+1保存
        }

        return dp[amount];
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
