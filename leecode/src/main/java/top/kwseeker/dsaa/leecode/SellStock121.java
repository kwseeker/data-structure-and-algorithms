package top.kwseeker.dsaa.leecode;

public class SellStock121 {

    public static void main(String[] args) {
        int[] arr = {7,1,5,3,6,4};
        System.out.println(maxProfit(arr));
    }

    //动态规划：前i天的最大收益 = max{前i-1天的最大收益，第i天的价格-前i-1天中的最小价格}
    static int maxProfit(int[] prices) {
        int max = 0, minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return max;
    }

    //暴力法：
    //static int maxProfit(int[] prices) {
    //    int len = prices.length;
    //    int max = 0;
    //    for (int i = 0; i < len; i++) {
    //        for (int j = i+1; j < len; j++) {
    //            if(prices[i] < prices[j] ) {
    //                int profit = prices[j]-prices[i];
    //                if(profit > max) {
    //                    max = profit;
    //                }
    //            }
    //        }
    //    }
    //    return max;
    //}
}
