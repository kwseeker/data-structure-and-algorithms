package top.kwseeker.dsaa.leetcode;

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 示例:
 *
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 进阶:
 *
 * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 *
 * 来源：力扣（LeetCode） 53
 */
public class MaxSubArray53 {

    public static void main(String[] args) {
        //int[] arr = {-2,1,-3,4,-1,2,1,-5,4};
        //int[] arr = {8,-19,5,-4,20};
        int[] arr = {8,-9,10,-4};
        System.out.println(maxSubArray(arr));
    }

    static int maxSubArray(int[] nums) {
        int ans = nums[0];
        int sum = 0;
        for(int num: nums) {
            if(sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            ans = Math.max(ans, sum);
        }
        return ans;
    }

    //动态规划：和卖股票（121）的差不多
    //第i个位置之前的最大子序和＝max(第i-1个位置之前的最大子序和, 第i个元素, 第i-1个位置之前的最大子序和结束位置到当前位置所有值的和
    //static int maxSubArray(int[] nums) {
    //    if(nums.length == 0) {
    //        return 0;
    //    }
    //    if(nums.length == 1) {
    //        return nums[0];
    //    }
    //
    //    //最大子序列值
    //    int maxTotal = nums[0];
    //    //从最大子序列头部开始到[i-1]位置所有元素的值
    //    int totalAll = nums[0];
    //    for (int i = 1; i < nums.length; i++) {
    //        int tmp = maxTotal;
    //        //然后比较maxTotal nums[0] totalAll＋nums[i]三者的大小,选择最大的
    //        maxTotal = Math.max(maxTotal, totalAll+nums[i]);
    //        maxTotal = Math.max(maxTotal, nums[i]);
    //
    //        if(maxTotal == tmp) {       //如果最大子序列未变
    //            totalAll = totalAll + nums[i];
    //        } else {                    //如果最大子序列改变
    //            totalAll = maxTotal;
    //        }
    //    }
    //    return maxTotal;
    //}
}
