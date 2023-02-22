package top.kwseeker.dsaa.slidewindow;

/**
 * 53. 最大子数组和
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组 是数组中的一个连续部分。
 * <p>
 * 示例：
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * <p>
 * 这里是滑动窗口解法
 */
public class LC53MaximumSubarray {

    public static void main(String[] args) {
        LC53MaximumSubarray handler = new LC53MaximumSubarray();

        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}; //6
        System.out.println(handler.maxSubArray(nums));
        nums = new int[]{1};
        System.out.println(handler.maxSubArray(nums));
        nums = new int[]{5, 4, -1, 7, 8};
        System.out.println(handler.maxSubArray(nums));
        nums = new int[]{-2, -1};
        System.out.println(handler.maxSubArray(nums));
        nums = new int[]{-2, -3, -1};
        System.out.println(handler.maxSubArray(nums));
        nums = new int[]{-2, 5, -1};
        System.out.println(handler.maxSubArray(nums));
        //nums = new int[]{8,-19,5,-4,20};
        nums = new int[]{8, -19};
        System.out.println(handler.maxSubArray(nums));
        nums = new int[]{0, -3, 1, 1};
        System.out.println(handler.maxSubArray(nums));
    }

    public int maxSubArray(int[] nums) {
        //当数组全是负数时，后面的算法会出错，所以这种情况单独处理
        Integer max = null;
        for (int num : nums) {
            if (max == null)
                max = num;
            else
                max = Math.max(max, num);
        }
        if (max == null) {
            System.out.println("数组不能为空");
            return 0;
        }
        if (max <= 0) {
            return max;
        }

        //当数组包含正数时，使用滑动窗口
        int l = 0, r = 0;
        int ml = l, mr = r;
        int wSum = 0;
        max = 0;

        while (r < nums.length) {
            wSum += nums[r];

            while (wSum < 0) {
                wSum -= nums[l++];
            }

            if (wSum > max) {
                max = wSum;
                ml = l;
                mr = r;
            }

            r++;
        }

        System.out.println("ml:" + ml + ", mr:" + mr);
        return max;
    }
}
